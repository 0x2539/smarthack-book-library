import json
from datetime import timedelta
from functools import partial
from django.http import HttpResponse, JsonResponse
from django.core.serializers import serialize
from django.db.models import Q
from django.shortcuts import render

from .models import Book, User, Loan, Profile
from .login_utils import generate_login_token, login_only, hash_password, check_password
from .charting import compute_coords


GET = 'GET'
POST = 'POST'

json_serialize = partial(serialize, 'json')
json_response = lambda data: HttpResponse(json_serialize(data), content_type='application/json')
json_obj_response = lambda data: HttpResponse(json.dumps(json.loads(json_serialize(data))[0]), content_type='application/json')


def home(request):
    return HttpResponse('landing page here')


def chart(request):
    return render(request, 'chart.html')


def api_chart(request):
    df = compute_coords()
    return JsonResponse({col: [str(elem) for elem in df[col]] for col in df.columns})


def books(request):
    return json_response(Book.objects.all())


def search(request, terms):
    terms = terms.replace('_', ' ')
    results = Book.objects.filter(
      Q(title__contains=terms) |
      Q(author__contains=terms) |
      Q(description__contains=terms) |
      Q(genre__contains=terms)
    )
    return json_response(results)


def login(request):
    body = json.loads(request.body.decode('utf-8'))

    username = body.get('username', None)
    password = body.get('password', None)
    google_id = body.get('google_id', None)

    user = None
    if google_id:
        try:
            user = User.objects.get(username=google_id)
        except User.DoesNotExist:

            user = User(username=google_id)
            user.save()

            profile = Profile.objects.get(user_id=user.id)
            profile.google_id = google_id
            profile.hashed_password = '123'
            profile.save()

    elif username and password:
        try:
            user = User.objects.get(username=username)  # and password
            profile = Profile.objects.get(user_id=user.id)
            if not profile.hashed_password or profile.hashed_password == '123' or not check_password(password, profile.hashed_password):
                user = None
        except User.DoesNotExist:
            pass
        except Profile.DoesNotExist:
            pass

    if not user:
        return HttpResponse(status=400, content=json.dumps({'error': 'LOGIN_INVALID'}))

    token = generate_login_token(user.id)
    token_json = json.dumps({'token': token, 'username': user.username, 'user_id': user.id})

    return HttpResponse(status=200, content=token_json)


def sign_up(request):
    body = json.loads(request.body.decode('utf-8'))

    username = body.get('username', None)
    password = body.get('password', None)
    google_id = body.get('google_id', None)

    user = None
    if google_id:
        try:
            user = User.objects.get(google_id=google_id)
        except User.DoesNotExist:
            pass

    elif username:
        try:
            user = User.objects.get(username=username)  # and password
        except User.DoesNotExist:
            pass

    if user:
        return HttpResponse(status=400, content=json.dumps({'error': 'LOGIN_INVALID'}))

    if username and password:
        hashed_password = hash_password(password)
        user = User(username=username)
        user.save()

        profile = Profile.objects.get(user_id=user.id)
        profile.google_id = '123'
        profile.hashed_password = hashed_password
        profile.save()
    elif google_id:
        user = User(username=google_id)
        user.save()

        profile = Profile.objects.get(user_id=user.id)
        profile.google_id = google_id
        profile.hashed_password = '123'
        profile.save()
    else:
        return HttpResponse(status=400, content=json.dumps({'error': 'LOGIN_INVALID'}))

    token = generate_login_token(user.id)
    token_json = json.dumps({'token': token, 'username': user.username, 'user_id': user.id})

    return HttpResponse(status=200, content=token_json)


@login_only
def profile(request, user_id):
    try:
        user = User.objects.get(id=user_id)
    except User.DoesNotExist:
        # we have no object!  do something
        user = {}

    print (json.dumps(json.loads(json_serialize([user]))[0]))
    print ('user:', user.first_name)

    return json_obj_response([user])


@login_only
def update_profile(request, user_id):
    try:
        user = User.objects.get(id=user_id)
    except User.DoesNotExist:
        return HttpResponse(status=400, content='User & Password or Google ID not specified/incorrect')

    body = json.loads(request.body.decode('utf-8'))

    first_name = body.get('first_name', None)
    last_name = body.get('last_name', None)
    email = body.get('email', None)

    user.first_name = first_name
    user.last_name = last_name
    user.email = email
    user.save()

    return HttpResponse(status=200, content=json.dumps({}))


@login_only
def loaned_by(request, user_id):
    loans = Loan.objects.filter(user_id=user_id)
    books = [loan.book for loan in loans]
    books_json = json.loads(json_serialize(books))
    for i, book in enumerate(books_json):
        for loan in loans:
            if loan.book.id == book['pk']:
                books_json[i]['fields']['return_date'] = loan.return_date.strftime('%Y-%m-%d')
                break
    return HttpResponse(status=200, content=json.dumps(books_json))


def loaned_together_with(request, book_id):
    book = Book.objects.get(id=book_id)
    return json_response(book.loaned_together())


@login_only
def loan_date(request, user_id, book_id):
    try:
        loan = Loan.objects.get(
            Q(user_id=user_id) &
            Q(book_id=book_id)
        )
    except:
        return JsonResponse({'date': None})

    return JsonResponse({'date': loan.return_date.strftime('%-d %b %Y')})


@login_only
def all_loans(request, user_id):
    return json_response(Loan.objects.all())

# return json_response(Loan.objects.all())


@login_only
def place_loan(request, user_id, book_id):
    user = User.objects.get(id=user_id)
    book = Book.objects.get(id=book_id)
    loans = Loan.objects.filter(user_id=user_id)

    if len(loans) >= 0:
        return HttpResponse(status=400, content=json.dumps({'error': 'CANT_BORROW'}))

    try:
        # Loan already exists, extend its period
        loan = Loan.objects.get(user=user, book=book)
        loan.return_date += timedelta(days=30)
        loan.save()
    except:
        # Loan doesn't exit, create it now
        loan = Loan.objects.create(
            user=user,
            book=book
        )
        loan.save()

    return HttpResponse(status=200, content=json.dumps({}))

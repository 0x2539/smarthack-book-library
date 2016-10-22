import json
from functools import partial
from django.http import HttpResponse
from django.core.serializers import serialize

from .models import Book, User
from .login_utils import generate_login_token, login_only


GET = 'GET'
POST = 'POST'

json_serialize = partial(serialize, 'json')
json_response = lambda data: HttpResponse(json_serialize(data), content_type='application/json')


def home(request):
    return HttpResponse('landing page here')


def books(request):
    return json_response(Book.objects.all())


def login(request):
    body = json.loads(request.body.decode('utf-8'))

    username = body.get('username', None)
    password = body.get('password', None)
    google_id = body.get('google_id', None)

    user = None
    if google_id:
        user = User.objects.get(google_id=google_id)

    elif username and password:
        user = User.objects.get(username=username)  # and password

    if not user:
        return HttpResponse(status=400, content='User & Password or Google ID not specified/incorrect')

    token = generate_login_token(user.id)
    token_json = json.dumps({'token': token})

    return HttpResponse(status=200, content=token_json)


@login_only
def my_books(request, user_id):
    return HttpResponse(status=200, content=json.dumps({'lol': 'asd'}))


def book_details(request, id):
    book = Book.objects.get(id=id)
    return json_response([book])

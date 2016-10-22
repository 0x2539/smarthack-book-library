from django.http import HttpResponse
from django.shortcuts import render
from django.core.serializers import serialize

from .models import Book


GET = 'GET'
POST = 'POST'


def home(request):
    books = Book.objects.all()
    print(books)
    return render(request, 'home.html', context={
        'books': books
    })


def api_books(request):
    data = serialize('json', Book.objects.all())
    return HttpResponse(data, content_type='application/json')


# def api_login(request):
#     if request.method == POST:



"""SmartLibrary URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin

from ClientApp import views

urlpatterns = [
    url(r'^admin/?', admin.site.urls),
    url(r'^$', views.home, name='home'),

    url(r'^api/books', views.books, name='books'),
    url(r'^api/login', views.login, name='login'),
    url(r'^api/my_books', views.my_books, name='my books'),
    url(r'api/book_details/(?P<id>\d+)/?$', views.book_details, name='book details'),
]

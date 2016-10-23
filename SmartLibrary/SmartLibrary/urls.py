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
    url(r'^chart$', views.chart, name='chart'),

    url(r'^api/books', views.books, name='books'),
    url(r'^api/search/(?P<terms>\w{3,100})/?$', views.search, name='search'),

    url(r'^api/login', views.login, name='login'),
    url(r'^api/loaned_by', views.loaned_by, name='loaned by'),
    url(r'^api/loaned_together_with/(?P<book_id>\d+)/?$', views.loaned_together_with, name='loaned together with'),
    url(r'^api/loan_date/(?P<book_id>\d+)/?$', views.loan_date, name='loan date'),
    url(r'^api/place_loan/(?P<book_id>\d+)/?$', views.place_loan, name='place loan'),
    url(r'^api/all_loans', views.all_loans, name='all loans'),
    url(r'^api/profile', views.profile, name='profile'),
    url(r'^api/update_profile', views.update_profile, name='update profile'),

    url(r'^api/chart', views.api_chart, name='chart api'),
]

#!/usr/bin/env python
from datetime import datetime
from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save


class Book(models.Model):
    title = models.CharField(max_length=256, blank=False, null=False)
    author = models.CharField(max_length=512)
    description = models.CharField(max_length=2048)
    release_date = models.DateField()
    cover_url = models.CharField(max_length=512)

    GENRES = [
        ('SF', 'Sci-Fi'),
        ('Fan', 'Fantasy'),
        ('Prog', 'Programming'),
        ('SelfDev', 'Self Development'),
        ('R', 'Romance'),
    ]
    genre = models.CharField(choices=GENRES, max_length=16)

    def __str__(self):
        return self.title


class Loan(models.Model):
    user = models.OneToOneField(User)
    book = models.OneToOneField(Book)
    date = models.DateField(auto_now=True)


class Profile(models.Model):
    # access like: request.user.profile.google_id
    user = models.OneToOneField(User)
    google_id = models.CharField(max_length=64)


def create_user_profile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)


post_save.connect(create_user_profile, sender=User)


def populate():
    def make(model, **kwargs):
        obj, created = model.objects.get_or_create(**kwargs)
        obj.save()
        return obj

    # Books
    lotr = make(Book,
                title='Lord of the Rings',
                author='JRR Tolkein',
                description='Book with dwarfs and elves',
                release_date=datetime(day=24, month=1, year=1994),
                cover_url='http://cdn.collider.com/wp-content/uploads/2016/07/the-lord-of-the-rings-book-cover.jpg',
                genre='Fan')

    sw = make(Book,
              title='Star Wars',
              author='George Lucas',
              description='Jedis and Siths',
              release_date=datetime(day=31, month=8, year=1994),
              cover_url='https://s-media-cache-ak0.pinimg.com/originals/16/86/be/1686bee733af0dbccd952ea34e4955d3.jpg',
              genre='SF')

    cormen = make(Book,
                  title='Algorithms 3rd Edition',
                  author='Cormen et al',
                  description='Data structures and algorithms -- good book',
                  release_date=datetime(day=1, month=5, year=2000),
                  genre='Prog')

    got = make(Book,
               title='Game of Thrones',
               author='George RR Martin',
               description='Sex and slayings',
               release_date=datetime(day=5, month=6, year=2013),
               genre='Fan'
               )

    # Users
    stefan = make(User,
                  username='stefan',
                  password='parola123')

    alex = make(User,
                username='alex',
                password='parola123')

    # Loans
    stefan_lotr = make(Loan, user=stefan, book=lotr)
    stefan_cormen = make(Loan, user=stefan, book=lotr)

    alex_got = make(Loan, user=alex, book=got)

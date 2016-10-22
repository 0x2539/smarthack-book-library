#!/usr/bin/env python
from datetime import datetime
from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save


class Book(models.Model):
    title = models.CharField(max_length=256, blank=False, null=False)
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


class Profile(models.Model):
    # access like: request.user.profile.google_id
    user = models.OneToOneField(User)
    google_id = models.CharField(max_length=64)


def create_user_profile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)

post_save.connect(create_user_profile, sender=User)


def populate():
    Book.objects.create(title='Lord of the Rings',
                        description='Book with dwarfs and elves',
                        release_date=datetime(day=24, month=1, year=1994),
                        cover_url='http://cdn.collider.com/wp-content/uploads/2016/07/the-lord-of-the-rings-book-cover.jpg',
                        genre='Fan').save()

    Book.objects.create(title='Star Wars',
                        description='Jedis and Siths',
                        release_date=datetime(day=31, month=8, year=1994),
                        cover_url='https://s-media-cache-ak0.pinimg.com/originals/16/86/be/1686bee733af0dbccd952ea34e4955d3.jpg',
                        genre='SF').save()
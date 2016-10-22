#!/usr/bin/env python
from django.db import models
from datetime import datetime


class Book(models.Model):
    title = models.CharField(max_length=256, blank=False, null=False)
    description = models.CharField(max_length=2048, blank=False, null=False)
    release_date = models.DateField()

    GENRES = [
        ('SF', 'Sci-Fi'),
        ('Fan', 'Fantasy'),
        ('Prog', 'Programming'),
        ('SelfDev', 'Self Development'),
        ('R', 'Romance'),
    ]

    # Example query: Book.objects.filter(genre='SF')
    genre = models.CharField(choices=GENRES, max_length=16)


def populate():
    Book.objects.create(title='Lord of the Rings', description='Book with dwarfs and elves',
                        release_date=datetime(day=24, month=1, year=1994), genre='Fan').save()
    Book.objects.create(title='Star Wars', description='Jedis and Siths',
                        release_date=datetime(day=31, month=8, year=1994), genre='SF').save()

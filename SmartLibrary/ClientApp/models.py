#!/usr/bin/env python
from datetime import datetime, timedelta
from django.db import models
from django.contrib.auth.models import User
from itertools import chain
from django.db.models.signals import post_save

join_querysets = lambda sets: set(chain(*sets))


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

    def loaners(self):
        loans = Loan.objects.filter(book=self)
        return [loan.user for loan in loans]

    def loaned_together(self):
        loans = Loan.objects.filter(book=self)
        loans_around = [loan.user.profile.loaned_around(loan.date) for loan in loans]
        return join_querysets(loans_around) - {self}


def test():
    lotr = Book.objects.get(title__contains='rings')
    return lotr.loaned_together()

class Loan(models.Model):
    user = models.ForeignKey(User, related_name='loans')
    book = models.ForeignKey(Book)
    date = models.DateTimeField(default=datetime.now)

    def __str__(self):
        return '%s - %s' % (self.user, self.book)


class Profile(models.Model):
    # access like: request.user.profile.google_id
    user = models.OneToOneField(User)
    google_id = models.CharField(max_length=64)

    def loaned_around(self, date):
        before = date - timedelta(minutes=10)
        after  = date + timedelta(minutes=10)
        loans = self.user.loans.filter(date__range=[before, after])
        return [loan.book for loan in loans]


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

    swift = make(Book,
                 title='The Swift Porgramming Language',
                 author='Apple Inc',
                 description='Swift is a programming language for creating iOS, macOS, watchOS, and tvOS apps. Swift builds on the best of C and Objective-C, without the constraints of C compatibility.',
                 release_date=datetime(day=2, month=6, year=2014),
                 genre='Prog'
                 )

    # Users
    stefan = make(User,
                  username='stefan',
                  password='parola123')
    alex = make(User,
                username='alex',
                password='parola123')

    ade = make(User,
               username='ade',
               password='parola123')

    # Loans
    stefan_lotr = make(Loan, user=stefan, book=lotr, date=datetime(day=22, month=10, year=2016, hour=19, minute=30))
    stefan_sw = make(Loan, user=stefan, book=sw, date=datetime(day=22, month=10, year=2016, hour=19, minute=35))
    stefan_cormen = make(Loan, user=stefan, book=cormen, date=datetime(day=22, month=2, year=2016, hour=10, minute=0))

    alex_lotr = make(Loan, user=alex, book=lotr, date=datetime(day=22, month=10, year=2016, hour=18, minute=10))
    alex_got = make(Loan, user=alex, book=got, date=datetime(day=22, month=10, year=2016, hour=18, minute=11))

    ade_swift = make(Loan, user=ade, book=swift, date=datetime(day=5, month=6, year=2016, hour=12, minute=0))

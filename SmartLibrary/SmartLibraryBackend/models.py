from django.db import models


class Book:
    title = models.CharField(max_length=256, blank=False, null=False)
    description = models.CharField(max_length=2048, blank=False, null=False)
    release_date = models.DateField()

    GENRES = [
        ('SF', 'Sci-Fi'),
        ('Prog', 'Programming'),
        ('SelfDev', 'Self Development'),
        ('R', 'Romance'),
    ]

    # Example query: Book.objects.filter(genre='SF')
    genre = models.CharField(choices=GENRES)

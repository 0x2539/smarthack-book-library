# smarthack-book-library
This is the project for the smarthack hackathon (online book library)



## Install

Backend:

```bash
workon smart-library-env  # activate virtualenv
pip install -r requirements.txt
```

Frontend:

```bash
cd static
npm install
```



## Build

Create the DB (on disk):
```bash
./manage.py migrate --run-syncdb
```
After updating models, run this beforehand:

```bash
./manage.py makemigrations
```



## Run

```bash
./manage.py runserver 0.0.0.0:5728
```

Access at [0.0.0.0:5728](http://0.0.0.0:5728).



## Administrate

Interactive shell:

```bash
./manage.py shell
```

To play with DB objects:

```python
>>> from Backend.models import *
>>> Book.objects.all()  # get
<QuerySet ...>

>>> from datetime import datetime  # for dates
>>> new_book = Book.objects.create(title='Lord of the Rings', description='Book with dwarfs and elves', release_date=datetime(day=24, month=1, year=1994), genre='Fan')  # create
>>> new_book.save()  # commit

>>> Book.objects.filter(genre='Fan')  # select

>>> wrong_book = Book.objects.all()[0]
>>> wrong_book.title = 'Star Wars'
>>> wrong_book.save()  # update

>>> bad_book = Book.objects.all()[0]
>>> bad_book.delete()  # remove
```


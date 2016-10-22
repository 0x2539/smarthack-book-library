# smarthack-book-library
This is the project for the smarthack hackathon (online book library)



## Installation

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
This will also reload **updated models**.



## Run

```bash
./manage.py runserver
```

Access at [localhost:8000](http://localhost:8000).
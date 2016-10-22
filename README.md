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

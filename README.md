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

## Run the server
1) Make sure you have an environment setup. If not, create one:
```
cd ~/path/to/smarthack-book-library
virtualenv env
```
2) Activate the virtual environment:
```
. env/bin/activate
```
3) Install the requirements:
```
pip install -r requirements.txt
```
4) Create the DB (this will create it on disk):
```
python api/manage.py migrate --run-syncdb
```
5) Run the server:
```
python api/manage.py runserver 0.0.0.0:8000
```
__Usually it is enough to run step 2 and 5 (if you have your project setted up beforehand)__

### Updated the models?
1. Delete __db.sqlite3__ (delete it from disk)
2. Run the command:

  ```
  python api/manage.py migrate --run-syncdb
  ```

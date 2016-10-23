import json
from datetime import datetime, timedelta
import jwt
from django.http import HttpResponse
from ClientApp.utils import request_to_dict
import hashlib
import hmac

from passlib.hash import bcrypt


JWT_SECRET_KEY = 'SECRET'


def decode_token(jwt_token):
    try:
        decoded = jwt.decode(jwt_token, JWT_SECRET_KEY, algorithms=['HS256'])
    except:
        return None

    return int(decoded.get('userId'))


def generate_login_token(user_id):
    expiry = datetime.utcnow() + timedelta(days=365)
    payload = {
        'userId': user_id,
        'exp': expiry
    }
    encoded = jwt.encode(payload, JWT_SECRET_KEY, algorithm='HS256')
    return encoded.decode()


def login_only(api_function):
    def wrapper(request, *args, **kwargs):

        body = request_to_dict(request)
        token = body.get('token', None)

        user_id = decode_token(token)
        if user_id:
            kwargs.update({'user_id': user_id})
            return api_function(request, *args, **kwargs)

        return HttpResponse(status=400, content=json.dumps({'error': 'LOGIN_INVALID'}))

    return wrapper


def hash_password(password):
    # print 'sign up hashed pw', bcrypt.encrypt(password, rounds=12)
    return bcrypt.encrypt(password, rounds=4)


def check_password(password, password_db):

    # return True
    if password is None or len(password) == 0:
        return False

    return bcrypt.verify(password, password_db)


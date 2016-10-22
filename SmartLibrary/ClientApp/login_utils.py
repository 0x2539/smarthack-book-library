import json
from datetime import datetime, timedelta

import jwt
from django.http import HttpResponse

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

        body = json.loads(request.body.decode('utf-8'))
        token = body.get('token', None)

        if decode_token(token):
            return api_function(request, *args, **kwargs)

        return HttpResponse(status=400, content=json.dumps({'error': 'LOGIN_INVALID'}))

    return wrapper

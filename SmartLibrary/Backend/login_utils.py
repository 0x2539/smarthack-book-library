from datetime import datetime, timedelta

import jwt


JWT_SECRET_KEY = 'SECRET'


def decode_token(jwt_token):
    try:
        decoded = jwt.decode(jwt_token, JWT_SECRET_KEY, algorithms=['HS256'])
    except:
        return None

    return int(decoded.get('userId'))


def generate_login_token(userId):
    expiry = datetime.utcnow() + timedelta(days=365)
    payload = {
        'userId': userId,
        'exp': expiry
    }
    encoded = jwt.encode(payload, JWT_SECRET_KEY, algorithm='HS256')
    return encoded

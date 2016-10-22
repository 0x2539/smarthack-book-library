import json


def request_to_dict(request):
    return json.loads(request.body.decode('utf-8'))

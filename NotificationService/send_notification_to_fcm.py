from urllib2 import *
import urllib
import json
import sys

MY_API_KEY="AIzaSyDoivIdjVMIMyEkQKBf6UPS4OtS6rqMMPY"

def send_notification(book_id, book_title, user_id):
    data={
        "to": "/topics/user_%s" % user_id,
        "data" : {
            "action_type": 1,
            "book_id": book_id,
            "book_title": book_title
        },
        "priority": "normal",
        "content_available": True
    }
    dataAsJSON = json.dumps(data)
    request = Request(
        "https://fcm.googleapis.com/fcm/send",
        dataAsJSON,
        {
            "Authorization" : "key=" + MY_API_KEY,
            "Content-type" : "application/json"
        }
    )
    print urlopen(request).read()
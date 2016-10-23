from urllib2 import *
import urllib
import json
import sys

MY_API_KEY="AIzaSyDoivIdjVMIMyEkQKBf6UPS4OtS6rqMMPY"

data={
    "to": "/topics/user_1",
    "data" : {
        "action_type": 1,
        "book_id": 1,
        "book_title": "LOTR"
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
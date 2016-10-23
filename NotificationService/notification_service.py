from urllib2 import *
import urllib
import json
import sys
import thread
from send_notification_to_fcm import send_notification

URL_BASE = 'http://192.168.1.45:5728/api/'

def get_all_loans():
    dataAsJSON = json.dumps({'token': ''})
    request = Request(
        URL_BASE + 'all_loans',
        dataAsJSON,
        {
            "Content-type" : "application/json"
        }
    )
    response = urlopen(request).read()
    return json.load(response)

def send_all_notifications(loans):
    for loan in loans:
        try:
            thread.start_new_thread( send_notification, (loan['book_id'], loan['book_title'], loan['user_id'], ) )
        except:
            pass

if __name__ == '__main__':
    loans = get_all_loans()
    print loans
    # send_all_notifications(loans)

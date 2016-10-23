# from urllib2 import *
# import urllib
from urllib.request import urlopen
from urllib.request import Request
import json
import sys
import _thread
from dateutil import parser
import datetime
from send_notification_to_fcm import send_notification

URL_BASE = 'http://192.168.1.95:5728/api/'

def get_all_loans():
    dataAsJSON = json.dumps({'token': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjIsImV4cCI6MTUwODcxODA4M30.rx2Zos9rJQs_w9qTawEpVQjLK14MGpr5UCzBz_cu5RU'})
    request = Request(
        URL_BASE + 'all_loans',
        dataAsJSON.encode('utf-8'),
        {
            "Content-type" : "application/json"
        }
    )
    response = urlopen(request).read().decode('utf-8')
    return json.loads(response)

def send_all_notifications(loans):
    now = datetime.datetime.now()
    for loan in loans:
        try:
            # print loan
            return_date = parser.parse(loan['fields']['return_date'])
            diff = return_date - now
            # print (diff.days, loan['fields']['return_date'])
            if diff.days <= 30 and  diff.days > 0:
                print (diff, loan['fields']['book'], 'A book', loan['fields']['user'])
                send_notification(loan['fields']['book'], 'A book', loan['fields']['user'])
                # thread.start_new_thread( send_notification, (loan['fields']['book'], 'A book', loan['fields']['user'], ) )
        except:
            pass

if __name__ == '__main__':
    # crontab example:
    # env EDITOR=nano crontab -e  # to open the editor
    # * * * * * echo 'source /Users/alexbuicescu/git/my_projects/smarthack-book-library/env/bin/activate; python /Users/alexbuicescu/git/my_projects/smarthack-book-library/NotificationService/notification_service.py' | /bin/bash
    loans = get_all_loans()
    send_all_notifications(loans)

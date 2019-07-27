import json

from django.http import HttpResponse
from django.contrib.sessions.models import Session
from django.contrib.auth.hashers import check_password

from .models import User, Plant

def formulate_response(message, success, code, data=None):
    if data is None:
        resp_dict = {"success": success, "message": message}
    else:
        resp_dict = {"success": success, "message": message, "data": data}

    response = HttpResponse(
        json.dumps(resp_dict),
        content_type="application/json"
    )

    response.status_code = code
    return response

# returns a user for the current session
def session_validation(session_key):

    session = Session.objects.filter(session_key=session_key)
    # print("the sessions\n", session, type(session))
    if len(session) < 1:
        return None
    session_data = session[0].get_decoded()
    # print("data", session_data)
    if "user_email" not in session_data:
        # print("empty session data")
        return None
    return session_data['user_email']

def get_user_by_email(email):
    user = User.objects.mongo_find_one({'email': email})
    if user is None:
        return None
    del user['_id']
    del user['password']
    return dict(user)

def get_plant_by_name(name):
    plant = Plant.objects.mongo_find_one({'name': name})
    if plant is None:
        return None
    del plant['_id']
    return dict(plant)

def get_user_by_email_and_password(email, password):
    user = User.objects.mongo_find_one({'email': email})
    if user is None or not check_password(password, user['password']):
        return None
    del user['_id']
    del user['password']
    return dict(user)
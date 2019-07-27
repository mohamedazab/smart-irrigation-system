import json

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from django.utils import timezone
from django.conf import settings
from django.contrib.auth.hashers import check_password, make_password
from django.contrib.sessions.models import Session

from .models import Plant, User
from bson.objectid import ObjectId

import pprint

@csrf_exempt
def createPlant(request):
    # Only listen to POST requests
    if request.method != "POST" or request.body == None:
        response_success = False
        response_message = "Only POST requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    body = json.loads(request.body)
    Plant.objects.mongo_insert(body)
    response_success = True
    response_message = "Plant inserted succesfully"
    response_code = 300
    return formulateResponse(response_message, response_success, response_code)


@csrf_exempt
def retrievePlant(request, id):
    # Only listen to GET requests
    if request.method != "GET":
        response_success = False
        response_message = "Only GET requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    plant = Plant.objects.mongo_find_one({'_id': ObjectId(id)})
    # Plant not found
    if plant == None:
        response_success = False
        response_message = "No such plant exists"
        response_code = 400
        return formulateResponse(response_message, response_success, response_code)

    plant = dict(plant)
    plant['_id'] = id
    response_message = "account created succesfully"
    response_success = True
    response_code = 200
    response_data = plant
    return formulateResponse(response_message, response_success, response_code, response_data)


@csrf_exempt
def signUp(request):
   # response attributes
    response_message = "account created succesfully"
    response_success = True
    response_code = 200

    # Only listen to POST requests
    if request.method != "POST" or request.body == None:
        response_success = False
        response_message = "Only POST requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    body = json.loads(request.body)
    # fail if no sign up information
    if not body["password"] or not body["email"]:
        response_success = False
        response_message = "incomplete data"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    body["password"] = make_password(body["password"])
    tmp_user = {
        'email': body['email'],
        'password': body['password'],
        'premium': False,
        'grid': []
    }
    User.objects.mongo_insert(tmp_user)

    return formulateResponse(response_message, response_success, response_code)


@csrf_exempt
def logIn(request):
    # response attributes
    response_message = "login successful"
    response_success = True
    response_code = 200
    print(request.session.session_key)
    # Only listen to POST requests
    if request.method != "POST" or request.body == None:
        response_success = False
        response_message = "Only POST requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    if request.session.exists(request.session.session_key):
        # validate and obtain user info from session
        user_email = session_validation(request.session.session_key)
        print("user: ", user_email)
        response_message = "already logged in user"
        response_success = False
        response_code = 300
        candidate_user = User.objects.mongo_find_one({"email": user_email})
        candidate_user = dict(candidate_user)
        del candidate_user['_id']
        del candidate_user['password']
        return formulateResponse(response_message, response_success, response_code, candidate_user)

    # load body
    body = json.loads(request.body)

    # fail if no sign up information
    if not body["password"] or not body["email"]:
        response_success = False
        response_message = "missing login information"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    # get user from DB
    candidate_user = User.objects.mongo_find_one({"email": body["email"]})
    candidate_user = dict(candidate_user)

    if candidate_user is None:
        response_success = False
        response_message = "Incorrect email"
        response_code = 300
    elif not check_password(body["password"], candidate_user["password"]):
        response_success = False
        response_message = "Incorrect password"
        response_code = 300
    else:  # login granted
        print("creating new session")
        request.session.create()
        request.session["user_email"] = body["email"]

    return formulateResponse(response_message, response_success, response_code, candidate_user)

@csrf_exempt
def addPlantToGrid(request):
    # Only listen to PUT requests
    if request.method != "PUT" or request.body == None:
        response_success = False
        response_message = "Only PUT requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    user_email = session_validation(request.session.session_key)
    if user_email is None:
        response_success = False
        response_message = "Internal server error. Session was not found."
        response_code = 500
        return formulateResponse(response_message, response_success, response_code)

    user = User.objects.mongo_find_one({'email': user_email})
    user = dict(user)

    body = json.loads(request.body)

    plant = Plant.objects.mongo_find_one({'name': body['plant_name']})
    # Plant not found
    if plant is None:
        response_success = False
        response_message = "No such plant exists"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)
    
    plant = dict(plant)
    del plant['_id']
    tmp_cell = {
        'positionX': body['positionX'],
        'positionY': body['positionY'],
        'crop': plant,
        'current_moisture': plant['moisture_threshold']
    }
    user['grid'].append(tmp_cell)
    User.objects.mongo_update_one({'_id': user['_id']}, {'$set': user}, upsert=False)
    del user['_id']
    del user['password']
    response_success = True
    response_message = "User updated successfully"
    response_code = 200
    return formulateResponse(response_message, response_success, response_code, user)


def formulateResponse(message, success, code, data=None):
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
import json

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from django.utils import timezone 
from django.conf import settings
from django.contrib.auth.hashers import check_password,make_password
from django.contrib.sessions.models import Session

from .models import Plant, User
from bson.objectid import ObjectId


@csrf_exempt
def createPlant(request):
    # Only listen to POST requests
    if request.method != "POST" or request.body == None:
        response_success =  False
        response_message = "Only POST requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)
    
    body = json.loads(request.body)
    Plant.objects.mongo_insert(body)
    response_success =  True
    response_message = "Plant inserted succesfully"
    response_code = 300
    return formulateResponse(response_message, response_success, response_code)


@csrf_exempt
def retrievePlant(request, id):
    # Only listen to GET requests
    if request.method != "GET":
        response_success =  False
        response_message = "Only GET requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)
    
    plant = Plant.objects.mongo_find_one({'_id': ObjectId(id)})
    # Plant not found
    if plant == None:
        response_success =  False
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
        response_success =  False
        response_message ="Only POST requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    body = json.loads(request.body)
    #fail if no sign up information
    if not body["password"] or not body["email"]:
        response_success =  False
        response_message ="incomplete data"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    # ENCRYPT PASSWORD HERE
    body["password"] = make_password(body["password"])
    # OTHER AUTH-RELATED FUNCTION IF ANY
    User.objects.mongo_insert(body)
    
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
        response_success =  False
        response_message ="Only POST requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)
    #load body
    body = json.loads(request.body)

    #fail if no sign up information
    if not body["password"] or not body["email"]:
        response_success =  False
        response_message ="missing login information"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)
    
    candidate_user = User.objects.mongo_find_one({"email":body["email"]})
    valid_password =  check_password(body["password"], candidate_user["password"])
    if candidate_user is None or not valid_password:
        response_success =  False
        response_message ="Incorrect email or password"
        response_code = 300
    else:
        if not request.session.exists(request.session.session_key):
            request.session.create()
            request.session["user_email"] = body["email"]
        else:
            response_message = "already logged in"
    
    user  = session_validation(request.session.session_key)
    print("user: ",user)
    return formulateResponse(response_message, response_success, response_code) 

#returns a user for the current session
def session_validation(session_key):
    
    session = Session.objects.filter(session_key = session_key)
    print("the sessions\n", session, type(session))
    if len(session)<1:
        return None
    session_data = session[0].get_decoded()
    print("data", session_data)

    return session_data['user_email']


@csrf_exempt
def addPlantToGrid(request):
    # Only listen to PUT requests
    if request.method != "PUT" or request.body == None:
        response_success = False
        response_message = "Only PUT requests are allowed on this route"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    # TODO: Check if logged in.
    # TODO: Get USER from session.

    plant = Plant.objects.mongo_find_one({'name': request.body['plant_name']})
    # Plant not found
    if plant == None:
        response_success = False
        response_message = "No such plant exists"
        response_code = 300
        return formulateResponse(response_message, response_success, response_code)

    # TODO: Add plant to grid
    # TODO: Upsert/Update user in collection
    response_success = True
    response_message = "User updated successfully"
    response_code = 200
    return formulateResponse(response_message, response_success, response_code)


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
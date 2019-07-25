# from django.shortcuts import render
import json

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
 
from django.conf import settings
from django.contrib.auth.hashers import check_password,make_password



from .models import Plant, User
from bson.objectid import ObjectId


# hashtables for sessions
session_keys = {}
@csrf_exempt
def createPlant(request):
    # Only listen to POST requests
    if request.method != "POST" or request.body == None:
        response  = HttpResponse(
            '''{
                "success": false,
                "message": "Only POST requests are allowed on this route"
            }''',
            content_type="application/json"
        )
        response.status_code = 300
        return response
    
    body = json.loads(request.body)
    Plant.objects.mongo_insert(body)
    response = HttpResponse(
        '''{
            "success": true,
            "message": "Plant inserted successfully"
        }''',
        content_type="application/json"
    )
    response.status_code = 200
    return response


@csrf_exempt
def retrievePlant(request, id):
    # Only listen to GET requests
    if request.method != "GET":
        response  = HttpResponse(
            '''{
                "success": false, 
                "message": "Only GET requests are allowed on this route"
            }''',
            content_type="application/json"
        )
        response.status_code = 300
        return response
    
    plant = Plant.objects.mongo_find_one({'_id': ObjectId(id)})
    # Plant not found
    if plant == None:
        response  = HttpResponse(
            '''{
                "success": false,
                "message": "No such plant exists"
            }''',
            content_type="application/json"
        )
        response.status_code = 400
        return response

    plant = dict(plant)
    plant['_id'] = id
    data_json = json.dumps(plant)
    response = HttpResponse(
        '''{
            "success": true,
            "message": "Here is your plant",
            "data": ''' + str(data_json) + '''
        }''',
        content_type="application/json"
    )
    response.status_code = 200
    return response

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
        response.status_code = 300
        return formulateResponse(response_message, response_success, response_code)

    body = json.loads(request.body)
    #fail if no sign up information
    if not body["password"] or not body["email"]:
        response_success =  False
        response_message ="incomplete data"
        response.status_code = 300
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
        response.status_code = 300
        return formulateResponse(response_message, response_success, response_code)
    #load body
    body = json.loads(request.body)

    #fail if no sign up information
    if not body["password"] or not body["email"]:
        response_success =  False
        response_message ="missing login information"
        response.status_code = 300
        return formulateResponse(response_message, response_success, response_code)
         

    candidate_user = User.objects.mongo_find_one({"email":body["email"]})
    valid_password =  check_password(body["password"], candidate_user["password"])
    if candidate_user is None or not valid_password:
        response_success =  False
        response_message ="Incorrect email or password"
        response.status_code = 300
    else:
        if not request.session.exists(request.session.session_key):
            request.session.create()
        else:
            response_message = "already logged in"
            

    return formulateResponse(response_message, response_success, response_code) 

    # print(body["password"]," -- ", candidate_user["password"])
    # print("candidate user", check_password(body["password"], candidate_user["password"]))
    

def formulateResponse(message, success, code):
    resp_dict = {"success": success, "message": message}

    response = HttpResponse(
       json.dumps(resp_dict),
        content_type="application/json"
    )
    
    response.status_code = code
    return response
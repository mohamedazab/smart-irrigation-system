import json
import re

from django.views.decorators.csrf import csrf_exempt
from django.conf import settings
from django.contrib.auth.hashers import check_password, make_password

from .models import Plant, User
from .utils import formulate_response, session_validation, get_user_by_email, get_plant_by_name, get_user_by_email_and_password
from bson.objectid import ObjectId

@csrf_exempt
def create_plant(request):
    if request.method != "POST" or request.body == None:
        response_message = "Only POST requests are allowed on this route."
        response_code = 405
        return formulate_response(response_message, response_code)

    body = json.loads(request.body)
    tmp_plant = {
        'name': body['name'],
        'moisture_threshold': body['moisture_threshold'],
        'recommended_ph': body['recommended_ph'],
        'recommended_temperature': body['recommended_temperature']
    }
    Plant.objects.mongo_insert(tmp_plant)
    response_message = "Plant inserted succesfully."
    response_code = 200
    return formulate_response(response_message, response_code)

@csrf_exempt
def retrieve_plant(request, name):
    # Only listen to GET requests
    if request.method != "GET":
        response_message = "Only GET requests are allowed on this route."
        response_code = 405
        return formulate_response(response_message, response_code)

    # Get plant from DB
    plant = get_plant_by_name(name)
    # Plant not found
    if plant == None:
        response_message = "No such plant exists."
        response_code = 404
        return formulate_response(response_message, response_code)

    # Plant found
    response_message = "Plant retrieved succesfully."
    response_code = 200
    return formulate_response(response_message, response_code, plant)

@csrf_exempt
def sign_up(request):
    # Only listen to POST requests
    if request.method != "POST" or request.body == None:
        response_message = "Only POST requests are allowed on this route."
        response_code = 405
        return formulate_response(response_message, response_code)

    # Get request body as JSON
    body = json.loads(request.body)
    # Fail if no sign up information or wrong email format
    if 'password' not in body or 'email' not in body or not re.match(r'[^@]+@[^@]+\.[^@]+', body['email']):
        response_message = "Incomplete or incorrect data provided. Please try again."
        response_code = 400
        return formulate_response(response_message, response_code)

    # Check if user previously existed
    user = get_user_by_email(body['email'])
    if user is not None:
        response_message = "A user with this email already exists. Try logging in."
        response_code = 409
        return formulate_response(response_message, response_code)

    # Encrypt user password
    body["password"] = make_password(body["password"])
    tmp_user = {
        'email': body['email'],
        'password': body['password'],
        'premium': False,
        'grid': []
    }
    # Insert user in DB
    User.objects.mongo_insert(tmp_user)
    response_message = "Account created succesfully."
    response_code = 201
    return formulate_response(response_message, response_code)

@csrf_exempt
def log_in(request):
    # response attributes
    response_message = "Login successful."
    response_code = 200
    # Only listen to POST requests
    if request.method != "POST" or request.body == None:
        response_message = "Only POST requests are allowed on this route"
        response_code = 405
        return formulate_response(response_message, response_code)

    if request.session.exists(request.session.session_key):
        # validate and obtain user info from session
        user_email = session_validation(request.session.session_key)
        response_message = "User already logged in."
        response_code = 403
        candidate_user = get_user_by_email(user_email)
        return formulate_response(response_message, response_code, candidate_user)

    # Load body
    body = json.loads(request.body)

    # fail if no login information provided
    if 'password' not in body or 'email' not in body:
        response_message = "Missing login information."
        response_code = 400
        return formulate_response(response_message, response_code)

    # get user from DB
    candidate_user = get_user_by_email_and_password(body['email'], body['password'])

    if candidate_user is None:
        response_message = "Incorrect email or password. Are you sure you signed up?"
        response_code = 401
    else:  # login granted
        request.session.create()
        request.session["user_email"] = body["email"]

    return formulate_response(response_message, response_code, candidate_user)

@csrf_exempt
def log_out(request):
    request.session.clear()
    return formulate_response("",200)


@csrf_exempt
def add_plant_to_grid(request):
    # Only listen to PUT requests
    if request.method != "PUT" or request.body == None:
        response_message = "Only PUT requests are allowed on this route."
        response_code = 405
        return formulate_response(response_message, response_code)

    # Load body
    body = json.loads(request.body)

    # Check if body has all needed attributes
    if 'plant_name' not in body or 'positionX' not in body or 'positionY' not in body:
        response_message = "Missing required plant or grid information."
        response_code = 400
        return formulate_response(response_message, response_code)

    # Get user email
    user_email = session_validation(request.session.session_key)
    # User not logged in
    if user_email is None:
        response_message = "User not logged in. Session was not found."
        response_code = 401
        return formulate_response(response_message, response_code)

    # Get user
    user = get_user_by_email(user_email)
    # Get plant to add
    plant = get_plant_by_name(body['plant_name'])
    # Plant not found
    if plant is None:
        response_message = "No such plant exists."
        response_code = 404
        return formulate_response(response_message, response_code)
    
    tmp_cell = {
        'positionX': body['positionX'],
        'positionY': body['positionY'],
        'crop': plant,
        'current_moisture': plant['moisture_threshold']
    }
    # Add cell to grid
    user['grid'].append(tmp_cell)
    # Update user in DB
    User.objects.mongo_update_one({'email': user['email']}, {'$set': user}, upsert=False)
    response_message = "User updated successfully."
    response_code = 200
    return formulate_response(response_message, response_code, user)

@csrf_exempt
def set_current_moisture(request):
    # Only listen to PUT requests
    if request.method != "PUT" or request.body == None:
        response_message = "Only PUT requests are allowed on this route."
        response_code = 405
        return formulate_response(response_message, response_code)
    
    # Load body
    body = json.loads(request.body)

    if 'email' not in body or 'positionX' not in body or 'positionY' not in body\
                            or 'password' not in body or 'current_moisture' not in body:
        response_message = "Missing required plant or grid information."
        response_code = 400
        return formulate_response(response_message, response_code)

    email = body['email']
    password = body['password']
    positionX = body['positionX']
    positionY = body['positionY']
    current_moisture = body['current_moisture']

    # Get user
    user = get_user_by_email_and_password(email, password)

    grid = user['grid']
    curr_elem = -1
    for idx, elem in enumerate(grid):
        if elem['positionX'] == positionX and elem['positionY'] == positionY:
            curr_elem = idx
            break
    if curr_elem == -1:
        response_message = "Plant not found in grid. Please try again."
        response_code = 300
        return formulate_response(response_message, response_code)
    user['grid'][curr_elem]['current_moisture'] = current_moisture
    User.objects.mongo_update_one({'email': user['email']}, {'$set': user}, upsert=False)
    
    response_data = {
        "moisture_threhold": user['grid'][curr_elem]['crop']['moisture_threshold']
    }
    response_message = "Plant successfully updated."
    response_code = 200
    return formulate_response(response_message, response_code, response_data)

@csrf_exempt
def refresh(request):
    # Only listen to GET requests
    if request.method != "GET" or request.body == None:
        response_message = "Only GET requests are allowed on this route."
        response_code = 405
        return formulate_response(response_message, response_code)
    
    user_email = session_validation(request.session.session_key)
    if user_email is None:
        response_message = "User not logged in. Session was not found."
        response_code = 401
        return formulate_response(response_message, response_code)

    response_message = "User retrieved successfully."
    response_code = 200
    return formulate_response(response_message, response_code, get_user_by_email(user_email))

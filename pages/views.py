# from django.shortcuts import render
import json

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse

from .models import Plant, User
from bson.objectid import ObjectId

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
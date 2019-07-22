# from django.shortcuts import render
import json

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse

from .models import Plant, User

@csrf_exempt
def createPlant(request):#plantJSON):
    print("new request with: ", request.method)

    if request.method != "POST" or request.body== None:
        response  = HttpResponse('{"message": false}', content_type="application/json")
        response.status_code = 300
        return response
    
    body = json.loads(request.body)
    Plant.objects.mongo_insert(body)
    return HttpResponse('{"message": false}')

@csrf_exempt
def retrievePlant(plantID):
    return Plant.objects.mongo_find_one({'_id': plantID})
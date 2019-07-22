# from django.shortcuts import render
from django.http import HttpResponse
from .models import Plant, User

def createPlant(x,y,z):#plantJSON):
    print(x)
    print(y)
    print(z)
    return 1
    # Plant.objects.mongo_insert_one(plantJSON)

def retrievePlane(plantID):
    return Plant.objects.mongo_find_one({'_id': plantID})
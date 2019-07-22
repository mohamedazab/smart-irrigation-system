from django.urls import path
from .views import createPlant

urlpatterns = [
    path('api/plant', createPlant, name='Create Plant')
]

from django.urls import path
from .views import createPlant, retrievePlant

urlpatterns = [
    path('api/plant', createPlant, name='Create Plant'),
    path('api/plant/<str:id>', retrievePlant, name='Retrieve Plant')
]

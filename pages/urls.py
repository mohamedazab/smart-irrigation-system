from django.urls import path
from .views import createPlant, retrievePlant,signUp,logIn

urlpatterns = [
    path('api/login', logIn, name='login'),
    path('api/sign-up', signUp, name='sign-up'),
    path('api/plant', createPlant, name='Create Plant'),
    path('api/plant/<str:id>', retrievePlant, name='Retrieve Plant')
]

from django.urls import path
from .views import createPlant, retrievePlant, signUp, logIn, addPlantToGrid

urlpatterns = [
    path('api/login', logIn, name='login'),
    path('api/sign-up', signUp, name='sign-up'),
    path('api/plant', createPlant, name='Create Plant'),
    path('api/plant/<str:id>', retrievePlant, name='Retrieve Plant'),
    path('api/user/add', addPlantToGrid, name='Add Plant To Grid')
]

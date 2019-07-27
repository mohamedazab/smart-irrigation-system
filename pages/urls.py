from django.urls import path
from .views import create_plant, retrieve_plant, sign_up, log_in, add_plant_to_grid, refresh, set_current_moisture

urlpatterns = [
    path('api/login', log_in, name='login'),
    path('api/sign-up', sign_up, name='sign-up'),
    path('api/plant', create_plant, name='Create Plant'),
    path('api/plant/<str:name>', retrieve_plant, name='Retrieve Plant'),
    path('api/user/add', add_plant_to_grid, name='Add Plant To Grid'),
    path('api/user/', refresh, name='Refresh User'),
    path('api/user/update-moisture', set_current_moisture, name='Change current moisture.')
]

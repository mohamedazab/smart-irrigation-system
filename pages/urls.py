from django.urls import path
from .views import homePageView
#dummy comment to test CI and the deployed image on docker hub
urlpatterns = [
    path('', homePageView, name = 'home')
]

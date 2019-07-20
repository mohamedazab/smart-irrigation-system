from djongo import models

class Plant(models.Model):
    name = models.CharField(max_length=50)
    moisture_threshold = models.FloatField()
    recommended_ph = models.FloatField()
    recommended_temperature = models.FloatField()


class User(models.Model):
    premium = models.BooleanField()
    email = models.EmailField()
    password_salt = models.CharField(max_length=128)
    field = models.ArrayReferenceField(
        to=Plant,
        on_delete=models.DO_NOTHING
    )
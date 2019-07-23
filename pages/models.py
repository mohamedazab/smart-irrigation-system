from djongo import models

class Plant(models.Model):
    name = models.CharField(max_length=50)
    moisture_threshold = models.FloatField()
    recommended_ph = models.FloatField()
    recommended_temperature = models.FloatField()
    objects = models.DjongoManager()

class Cell(models.Model):
    positionX = models.IntegerField()
    positionY = models.IntegerField()
    crop = models.ForeignKey(
        to=Plant,
        on_delete=models.DO_NOTHING
    )
    current_moisture = models.FloatField()

class User(models.Model):
    premium = models.BooleanField()
    email = models.EmailField()
    password_salt = models.CharField(max_length=128)
    grid = models.ArrayModelField(Cell, default=None)
    objects = models.DjongoManager()

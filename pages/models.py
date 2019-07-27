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
    class Meta:
        abstract = True

class User(models.Model):
    premium = models.BooleanField()
    email = models.EmailField(unique = True)
    password = models.CharField(max_length=512,default = 'A')
    grid = models.ArrayModelField(Cell, default=None)
    objects = models.DjongoManager()

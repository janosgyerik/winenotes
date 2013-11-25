from datetime import datetime

from django.db import models


class WineType(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=20)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class Region(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=80)
    ascii_name = models.CharField(max_length=80)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class Flag(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=20)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class Winery(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=80)
    ascii_name = models.CharField(max_length=80)
    lat = models.FloatField()
    lon = models.FloatField()
    address = models.TextField()
    website = models.CharField(max_length=200)
    tel = models.CharField(max_length=50)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class Wine(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=80)
    ascii_name = models.CharField(max_length=80)
    winery = models.ForeignKey(Winery)
    summary = models.TextField()
    listing_text = models.TextField()
    price = models.FloatField()
    currency = models.CharField(max_length=3)
    winetype = models.ForeignKey(WineType)
    year = models.IntegerField()
    region = models.ForeignKey(Region)
    aroma_rating = models.IntegerField()
    taste_rating = models.IntegerField()
    aftertaste_rating = models.IntegerField()
    overall_rating = models.IntegerField()
    flag = models.ForeignKey(Flag)
    memo = models.TextField()
    display_image = models.CharField(max_length=80)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class Grape(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=80, unique=True)
    ascii_name = models.CharField(max_length=80, unique=True)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class WineGrape(models.Model):
    _id = models.AutoField(primary_key=True)
    wine = models.ForeignKey(Wine)
    grape = models.ForeignKey(Grape)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    class Meta:
        unique_together = (('wine', 'grape',))


class AromaImpression(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=80, unique=True)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class WineAromaImpression(models.Model):
    _id = models.AutoField(primary_key=True)
    wine = models.ForeignKey(Wine)
    aroma_impression = models.ForeignKey(AromaImpression)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    class Meta:
        unique_together = (('wine', 'aroma_impression',))


class TasteImpression(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=80, unique=True)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class WineTasteImpression(models.Model):
    _id = models.AutoField(primary_key=True)
    wine = models.ForeignKey(Wine)
    taste_impression = models.ForeignKey(TasteImpression)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    class Meta:
        unique_together = (('wine', 'taste_impression',))


class WineAfterTasteImpression(models.Model):
    _id = models.AutoField(primary_key=True)
    wine = models.ForeignKey(Wine)
    aftertaste_impression = models.ForeignKey(TasteImpression)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    class Meta:
        unique_together = (('wine', 'aftertaste_impression',))


class Tag(models.Model):
    _id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=40, unique=True)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


class WineTag(models.Model):
    _id = models.AutoField(primary_key=True)
    wine = models.ForeignKey(Wine)
    tag = models.ForeignKey(Tag)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    class Meta:
        unique_together = (('wine', 'tag',))


class WinePhoto(models.Model):
    _id = models.AutoField(primary_key=True)
    wine = models.ForeignKey(Wine)
    filename = models.CharField(max_length=50)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    class Meta:
        unique_together = (('wine', 'filename',))


class FavoriteWine(models.Model):
    _id = models.AutoField(primary_key=True)
    wine = models.ForeignKey(Wine, unique=True)
    display_order = models.IntegerField()
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)


# eof

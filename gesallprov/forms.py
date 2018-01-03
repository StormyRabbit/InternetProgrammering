from flask_wtf import FlaskForm
from wtforms import StringField, IntegerField, FileField, PasswordField


class ScanForm(FlaskForm):
    address = StringField("Address")
    startPort = IntegerField("Start Port")
    endPort = IntegerField("End Port")


class XmlForm(FlaskForm):
    uploadFile = FileField("Upload XML")
    uploadKeyFile = FileField("Upload Key file")
    keyPhrase = StringField("Start Port")
    endPort = IntegerField("End Port")


class SqlForm(FlaskForm):
    host = StringField("Host")
    user = StringField("User")
    pw = PasswordField("password")
    db = StringField("Database")
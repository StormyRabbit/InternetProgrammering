from flask_wtf import FlaskForm
from wtforms import StringField, IntegerField, FileField, PasswordField


class ScanForm(FlaskForm):
    address = StringField("Address")
    startPort = IntegerField("Start Port")
    endPort = IntegerField("End Port")


class XmlForm(FlaskForm):
    uploadKeyFile = FileField("Upload Key file")
    keyPhrase = StringField("Create key")


class SqlForm(FlaskForm):
    host = StringField("Host")
    user = StringField("User")
    pw = PasswordField("password")
    db = StringField("Database")
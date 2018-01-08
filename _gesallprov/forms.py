from flask_wtf import FlaskForm
from wtforms import StringField, IntegerField, PasswordField, validators


class ScanForm(FlaskForm):
    address = StringField("Address", [validators.DataRequired()])
    startPort = IntegerField("Start Port", [validators.DataRequired()])
    endPort = IntegerField("End Port", [validators.DataRequired()])


class SqlForm(FlaskForm):
    host = StringField("Host", [validators.DataRequired()])
    user = StringField("User", [validators.DataRequired()])
    pw = PasswordField("password", [validators.DataRequired()])
    db = StringField("Database", [validators.DataRequired()])

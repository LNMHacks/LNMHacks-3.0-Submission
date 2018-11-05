from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, BooleanField, SubmitField
from flask_wtf.file import FileRequired, FileField
from wtforms.validators import DataRequired


class LoginForm(FlaskForm):
	username = StringField('Username', validators=[DataRequired()])
	password = PasswordField('Password', validators=[DataRequired()])
	remember_me = BooleanField('Remember Me')
	submit = SubmitField('Sign In')

class UploadForm(FlaskForm):
	csv = FileField('Choose csv', validators=[FileRequired()])
	filename = StringField('Filename', validators=[DataRequired()])
	submit = SubmitField('Upload File')

class LoadForm(FlaskForm):
	pass

class SelectForm(FlaskForm):
	submit = SubmitField('Select Data?')

class SelectQueryForm(FlaskForm):
	column = StringField('Column Name - ', validators=[DataRequired()])
	single_int = StringField('Integer Match value?')
	range_int= StringField('Integer Range? Int - Int format')
	text_match = StringField('String to match')
	regex = BooleanField('Regex exp above ([a-z])')
	null = BooleanField('Want to check null values?')
	geo_point = BooleanField('Geo Cordinate check?')
	lat = StringField('Latitude')
	lng =StringField('Longitude')
	submit = SubmitField('Add Another filter?')

class GoNext(FlaskForm):
	submit = SubmitField('Run Query?')


class ChartButtonForm(FlaskForm):
	submit = SubmitField('Analyse?')

class Bar(FlaskForm):
	submit = SubmitField('Bar Chart?')

class Line(FlaskForm):
	submit = SubmitField('Line Chart?')

class Pie(FlaskForm):
	submit = SubmitField('Pie Chart?')

class ChartForm(FlaskForm):
	chart_type_bar = BooleanField('Bar?')
	chart_type_line = BooleanField('Line?')
	chart_type_pie = BooleanField('Pie?')
	x = StringField('X column', validators=[DataRequired()])
	y = StringField('Y column')
	submit = SubmitField('Build Chart')
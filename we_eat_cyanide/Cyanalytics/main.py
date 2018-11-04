from flask import Flask, render_template, request, flash,redirect, session
from config import Config
import re

from forms import LoginForm, UploadForm, SelectForm, ChartButtonForm, LoadForm,Bar, Pie, Line, ChartForm, SelectQueryForm, GoNext

from pymongo import MongoClient
import pandas as pd
import numpy as np

from bson.code import Code

client = MongoClient()
db = client.cyanalytics
users = db.users
datasets = db.datasets


app = Flask(__name__)
app.config.from_object(Config)

@app.route('/')
@app.route('/index')
def index():
    user = {'username': 'Miguel'}
    posts = [
        {
            'author': {'username': 'John'},
            'body': 'Beautiful day in Portland!'
        },
        {
            'author': {'username': 'Susan'},
            'body': 'The Avengers movie was so cool!'
        }
    ]
    return render_template('index.html', title='Home', heading='CyAnalytics', logged_in=False)


@app.route('/login', methods=['GET', 'POST'])
def login():
	form = LoginForm()
	if form.validate_on_submit():
		data = dict(request.form)
		
		username = data['username'][0]
		password = data['password'][0]

		user = users.find({
			'username':username,
			'password':password
		})

		# if not list(user):
		# 	flash('Invalid Login Credentials')
		# 	return redirect('/login')
		# else:
		flash('Login successful for {}'.format(
			form.username.data))
		return redirect('/dashboard')

	return render_template('login.html', title='Sign In', heading='CyAnalytics', form=form, logged_in=False)



@app.route('/dashboard', methods=['GET','POST'])
def dashboard():
	form = UploadForm()
	form2 = LoadForm()

	if form.validate_on_submit():
		f = request.files['csv']
		filename = form.filename.data

		file = datasets.find({
			'filename':filename
		})

		# q = list(file))

		if list(file):
			flash('FileName already present, choose another name.')
			return redirect('/dashboard')
		else:
			df = pd.read_csv(f, encoding='latin1')
			# print(df.dtypes.
			col_info = []
			for x,y in zip(df.columns, df.dtypes.values):
				# print(x+'---'+str(y))
				col_info.append({'key':x,
					'value':str(y)})
			col = db[filename]
			col.insert_many(df.to_dict('records'))

			docs = len(list(col.find()))
			datasets.insert_one({
				'filename': filename,
				'col_info':col_info
				})

			flash('Uploaded successfully with {} documents'.format(docs))
			session['filename'] = filename
			return redirect('/dataset/dashboard')

	return render_template('upload_load.html', title='Dashboard', heading='CyAnalytics', form=form, logged_in=True, form2=form2)


@app.route('/dataset/dashboard', methods=['GET','POST'])
def dataset_dashboard():



	# map_func = Code("function () {"
	# 				"for (var key in this) {emit(key, null);}"
	# 				"}"
	# 				)
	# reduce_func = Code("function (key, stuff) {return null; }")

	# result = db.iri.map_reduce(map_func, reduce_func, "myresults")

	# ans = result.find().distinct("_id")
	# ans =list(ans)

	ans = list(datasets.find({
		'filename':session['filename']
		}))[0]

	# print(ans['col_info'])

	query_obj = []# [{item['key']: None} for item in ans['col_info']]

	for item in ans['col_info']:
		count = len(list(db[session['filename']].find({item['key']:np.nan})))
		obj ={}
		obj['key'] = item['key']
		obj['count'] = count
		query_obj.append(obj)

	print(query_obj)

	# result = db.session['filename'].find(query_obj)

	# print(list(result))
	selectform = SelectForm()
	chartButtonForm = ChartButtonForm()

	if selectform.validate_on_submit():
		return redirect('/select/query')

	if chartButtonForm.validate_on_submit():
		return redirect('/charts')
	return render_template('dataset_dashboard.html', cols = ans['col_info'], missing=query_obj, select=selectform, chart=chartButtonForm, logged_in=True, heading='CyAnalytics')

@app.route('/select/query', methods=['GET','POST'])
def select_query():

	form = SelectQueryForm()
	form2 = GoNext()

	dataset = datasets.find({
		'filename':session['filename']
	})


	cols = list(dataset)[0]['col_info']

	print(cols)
	n_cols = []
	s_cols = []

	for col in cols:
		if col['value'] != "object":
			n_cols.append(col['key'])
		else:
			s_cols.append(col['key'])


	if form.validate_on_submit():
		print(session['query'])
		data = dict(request.form)

		column = data.get('column')[0]
		single_int = data.get('single_int')
		range_int = data.get('range_int')
		text_match = data.get('text_match')
		regex =  data.get('regex')
		null = data.get('null')
		geo_point = data.get('geo_point')
		lat = data.get('lat')
		lng = data.get('lng')

		print(null)

		if single_int[0]:
			session['query'][column] = int(single_int[0])

		if range_int[0]:
			x = range_int[0].split('-')
			print(range_int[0])
			y = int(x[1])
			x = int(x[0])
			session['query'][column] = {
				'$gte':x,
				'$lte':y
			}

		if text_match[0]:
			if regex[0]:
				r = re.compile(text_match[0])
				session['query'][column] = r
			else:
				session['query'][column] = text_match[0]

		if null:
			session['query'][column] = np.nan


		print(session['query'])
		flash('Output data - \n {}'.format(
				list(db[session['filename']].find(session['query']))
			))
		return render_template('select_query.html', heading='CyAnalytics', form=form, form2=form2, n_cols=n_cols, s_cols=s_cols, logged_in=True)

	elif form2.validate_on_submit():
		return render_template('select_query.html', heading='CyAnalytics', form=form, form2=form2, n_cols=n_cols, s_cols=s_cols, logged_in=True)
	
	return render_template('select_query.html', heading='CyAnalytics', form=form, form2=form2, n_cols=n_cols, s_cols=s_cols, logged_in=True, title='Cy')	

	


@app.route('/charts', methods=['GET','POST'])
def charts():

	bar = Bar()
	line = Line()
	pie =Pie()

	return redirect('/showchart')

	if pie.validate_on_submit():
		session['chart'] = 'pie'
		return redirect('/showchart')


	return render_template('chart_query.html', bar=bar, line=line, pie=pie, logged_in=True)


@app.route('/showchart', methods=['GET', 'POST'])
def showcharts():

	chartForm = ChartForm()

	dataset = datasets.find({
		'filename':session['filename']
	})


	cols = list(dataset)[0]['col_info']

	print(cols)
	n_cols = []
	s_cols = []

	for col in cols:
		if col['value'] != "object":
			n_cols.append(col['key'])
		else:
			s_cols.append(col['key'])



	if chartForm.validate_on_submit():
		data = dict(request.form)

		bar = data.get('chart_type_bar')
		line = data.get('chart_type_line')
		pie = data.get('chart_type_pie')

		x = data.get('x')
		y = data.get('y')


		if pie:
			x = x[0]
			if x in n_cols:
				print(x)
				query_obj = {
					x:{
						'$ne':None
					}
				}
				q = db[session['filename']].find(query_obj)

				q = list(q)
				print(q)
				data_x = [item[x] for item in q]
				labels_x = data_x

			else:
				query_obj = {
					'$group':{
						'_id': '$'+x,
						'count': {
							'$sum': 1
						}
					}
				}
				q = db[session['filename']].aggregate([query_obj])

				q =list(q)

				labels_x = [item['_id'] for item in q]
				data_x = [item['count'] for item in q]

			return render_template('final_chart.html', data_x=data_x, labels_x=labels_x, chart_type='pie', logged_in=True)

		if line:
			x = x[0]
			y = y[0]

			if x in s_cols and y in n_cols:
				pass
			else:
				x,y = y,x

			query_obj = {
				'$group':{
					'_id': '$'+x,
					'data':{
						'$addToSet': '$'+y
					}
				}
			}


			q = list(db[session['filename']].aggregate([query_obj]))


			return render_template('final_chart.html', data=enumerate(q), chart_type='line', logged_in=True)




		# if x:
		# 	x = x[0]
		# 	print(session['filename'])

		# 	if group_by_x:
		# 		a = db[session['filename']].aggregate([
		# 			{
		# 				'$group':{
		# 					'_id':'$'+x,
		# 					'count':{'$sum':1}
		# 				}
		# 			}
		# 		])
		# 		q= list(a)
		# 		labels_x = [item['_id'] for item in q]
		# 		data_x = [item['count'] for item in q]
		# 		# print(dbsession['filename'])
		# 		# print(list(db.users.find())
		# 		# b = db[session['filename']].find()
		# 		# print(list(a))
		# 		print(labels_x)
		# 	else:
		# 		a = db[session['filename']].find()
		# 		data_x = [item[x] for item in list(a)]
		
		# if y:
		# 	y = y[0]
		# 	print(session['filename'])

		# 	if group_by_y:
		# 		a = db[session['filename']].aggregate([
		# 			{
		# 				'$group':{
		# 					'_id':'$'+y,
		# 					'count':{'$sum':1}
		# 				}
		# 			}
		# 		])
		# 		data_y = [item['count'] for item in list(a)]
		# 		# print(dbsession['filename'])
		# 		# print(list(db.users.find())
		# 		# b = db[session['filename']].find()
		# 		# print(list(a))
		# 	else:
		# 		a = db[session['filename']].find()
		# 		data_y = [item[y] for item in list(a)]




		return render_template('final_chart.html', data_x=data_x, labels_x=labels_x, chart_type=session['chart'], logged_in=True)

	

	return render_template('showchart.html', heading='CyAnalytics', chart=session['filename'], form=chartForm, n_cols=n_cols, s_cols=s_cols, logged_in=True)



if __name__=='__main__':
	app.run(debug=True, use_reloader=True, port=5000)
from flask import Flask, render_template, session, redirect, url_for
from app import app
from .blockchain_connect import *

@app.route('/')
@app.route('/index')
def index():
	return render_template('ONE2T.html')

@app.route('/issue_certificate')
def issue_certificate():
	return render_template('form.html')

@app.route('/certificate_list')
def certificate_list():
	contract = get_contract_instance()
	data = []
	count = contract.functions.get_count(session['user_data']['address']).call()
	for certi in range(count+1):
		field_data = contract.functions.index_certificate(session['user_data']['address'],certi).call()
		data.append(field_data)
	return render_template('alog.html',my_certificates=data)

@app.route('/verification')
def verification():
	return render_template('verify.html')

@app.route('/signin')
def signin():
	return render_template('login.html')

@app.route('/logout')
def logout():
	session.pop('user_data',None)
	return redirect(url_for('signin'))
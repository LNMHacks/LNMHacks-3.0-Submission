from flask import Flask, render_template, session, url_for, escape, request, redirect
from app import app
import hashlib
import json
from .blockchain_connect import *
from web3 import Web3

users = {
	'issuer1' : {'password':'admin1','address': Web3.toChecksumAddress('0x9fa237ced930bcf1b67961f10774ff6bcdfea016'),'name':'Issuer 1','role':'issuer'},
	'issuer2' : {'password':'admin2','address': Web3.toChecksumAddress('0x0486e2e38d0c1e47eb850a7d999209029b78798d'),'name':'Issuer 2','role':'issuer'},
	'user1' : {'password':'qwerty1','address': Web3.toChecksumAddress('0x6b51d32a5ea3603c7c5101687c6714efdbe6beaf'),'name':'User 1','role':'user'},
	'user2' : {'password':'qwerty2','address': Web3.toChecksumAddress('0xd8ff299fb428d809abcbc39a8ef08d2f4abd9e32'),'name':'User 2','role':'user'},
}

@app.route('/get_certy', methods=['POST'])
def get_certy():
	certificate_data = dict(request.form)
	sender_address = request.form['address']
	w3.eth.defaultAccount = w3.eth.accounts[0]
	contract = get_contract_instance()
	checksum_address = Web3.toChecksumAddress(sender_address)
	tx_hash = contract.functions.insert_certificate(str(certificate_data), checksum_address).transact({})
	trans_receipt = get_trans_receipt(tx_hash)
	certificate_hash = hashlib.sha256(str(certificate_data).encode('utf-8')).hexdigest()
	return str(trans_receipt) + '\n'*2 +'This is the CIN [Cerification Idetification Number] Keep it safe for verifying purposes :- '+ certificate_hash

@app.route('/login', methods=['POST'])
def login():
	username = request.form['username']
	passord = request.form['password']

	if username in users and users[username]['password']:
		session['user_data'] = users[username]
		if users[username]['role'] == 'issuer':
			return redirect(url_for('issue_certificate'))
		else:
			return redirect(url_for('certificate_list'))

	return 'User not found'

@app.route('/verify_certificate', methods=['POST'])
def verify_certificate():
	address = request.form['address']
	sha_certificate = request.form['sha_certificate']
	#print(sha_certificate)

	contract = get_contract_instance()
	checksum_address = Web3.toChecksumAddress(address)
	count = contract.functions.get_count(checksum_address).call()

	for certi in range(count+1):
		data = contract.functions.index_certificate(checksum_address,certi).call()
		sha1 = hashlib.sha256(data.encode('utf-8')).hexdigest()
		print(sha1)
		if sha1 == sha_certificate:
			return 'The Certificate is Authentic.'

	return 'The certificate is forged.'

#282e2ebdd2f559439bbb1d72ffc31ce3edc035f07fbe95ad589e9562b8834415
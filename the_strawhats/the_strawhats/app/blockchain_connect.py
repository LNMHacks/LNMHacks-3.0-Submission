from web3 import Web3
import json
import os

basedir = os.path.abspath(os.path.dirname(__file__))

w3 = Web3(Web3.HTTPProvider("http://127.0.0.1:8545"))

contract_address = Web3.toChecksumAddress('0x1dedc9e65437c79e4e26000c552a5d60b64d7615')

with open(os.path.join(basedir,'Certificate.json'),encoding='utf-8') as file:
	file_json = json.load(file)
	abi = file_json['abi']

def get_contract_instance():
	contract = w3.eth.contract(address=contract_address, abi=abi)
	return contract

def get_trans_receipt(tx_hash):
	return w3.eth.getTransactionReceipt(tx_hash)
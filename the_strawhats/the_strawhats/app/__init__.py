from flask import Flask, session
import os

app=Flask(__name__)
app.secret_key = 'whateveryouthinksuitsyouthebest'

from app import routes,post_forms

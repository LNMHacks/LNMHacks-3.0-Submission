import sys
import requests
import ast
import smtplib

name = sys.argv[1]
u = sys.argv[2]


mail = smtplib.SMTP('smtp.gmail.com',587)
mail.ehlo()
mail.starttls()
mail.login('thealbatross1798@gmail.com','longlivemynation')
mail.sendmail('thealbatross1798@gmail.com',u,"This is send using Python "+name)
mail.close()
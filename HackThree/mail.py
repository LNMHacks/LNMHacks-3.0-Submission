import smtplib
from urllib.request import urlopen
from bs4 import BeautifulSoup
from datetime import date
import datetime
import mysql.connector
from mysql.connector import errorcode
import config
###user credential details block
#Email
EmailAddress=config.email
Password=config.password

#Mailing Function
def email(email,message): 
  #Please add the email address by which you want to send the mail
  fromaddr = EmailAddress
  toaddrs  = email
  msg = "\r\n".join([
      "From: your_email_address",
      "To:"+toaddrs,
      "Subject:  Top five recommendations for your genre",
      "",
      message
      ])
  username = fromaddr
  #Please add the password of your email address
  password = Password
  server = smtplib.SMTP('smtp.gmail.com:587')
  server.ehlo()
  server.starttls()
  server.login(username,password)
  server.sendmail(fromaddr, toaddrs, msg)
  server.quit()

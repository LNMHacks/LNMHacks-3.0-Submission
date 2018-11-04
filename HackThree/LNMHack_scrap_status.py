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
#Mysql
MysqlHost="localhost"
MysqlUser="root"
MysqlPassword="parul"

#Extracting Information about the series
def infoSeries(series):
  series=series.lower()
  
  #Functions Used to Abstract Information about the TV series release date
  def checkDate(ab):
    if(ab==0):
      return 0
    today=datetime.datetime.now()
    dd=today.day
    mm=today.month
    yyyy=today.year
    if len(str(ab))==4:
      if yyyy<=int(ab):
        return ab
      else:
        return 0
    d=int(ab%100)
    ab=ab/100
    m=int(ab%100)
    ab=ab/100
    y=int(ab)
    if yyyy<y:
      return datetime.date(y,m,d)
    elif yyyy==y:
      if mm<m:
        return datetime.date(y,m,d)
      elif mm==m:
        if dd<=d:
          return datetime.date(y,m,d)
        else:
          return 0
      else:
        return 0
    else:
      return 0 
  
  
  #Converting Date format
  def getNumberedDate(date):
    if len(date)==0:
      return 0
    datesDict = {'Jan.':1,
    'Feb.':2,
    'Mar.':3,
    'Apr.':4,
    'May':5,
    'Jun.':6,
    'Jul.':7,
    'Aug.':8,
    'Sep.':9,
    'Oct.':10,
    'Nov.':11,
    'Dec.':12
    }
    numberedDate = 1
    if(len(date) == 4):
        numberedDate = int(date)
        return numberedDate
    splits = date.split(" ")
    if len(splits)==3:
      date,month,year = splits
      month = datesDict[month]
    else:
      date=date[0:1]
      month=date[2:-4]
      year=date[-4:]
    numberedDate = int(year)
    numberedDate *= 100
    numberedDate += int(month)
    numberedDate *= 100
    numberedDate += int(date)
    return numberedDate
  
  series_name = series.split(" ")
  search_link = "https://www.imdb.com/find?ref_=nv_sr_fn&q="
  for word in range(len(series_name)):
      search_link += series_name[word]
      if word != len(series_name) - 1:
          search_link += "+"
  url=search_link+"&s=all"
  page = urlopen(url)
  soup = BeautifulSoup(page, 'html.parser')
  l=list()
  
  for link in soup.findAll('a', href=True):
      if(" ".join(series_name) in str(link.string).lower()):
          l.append("https://www.imdb.com"+link['href'])
  semi_final_link=l[0]
  quote_page = semi_final_link
  page = urlopen(quote_page)
  soup = BeautifulSoup(page, 'html.parser')
  name_box = soup.find('div', attrs={'class': 'seasons-and-year-nav'})
  a=name_box.find_all('a')
  done=1
  t=0
  check=0
  p=1
  for x in a:
    if check==1:
        check=2
    season=x['href']
    final_link='https://www.imdb.com'+season
    #print(final_link)
    quote_page = final_link
    page = urlopen(quote_page)
    soup = BeautifulSoup(page, 'html.parser')
    name_box = soup.find_all('span', attrs={'class': 'nobr'})
    name = name_box[0].text.strip()
    if name[-2]!=" " and name[-2]!="-":
      release_date= "The show has finished streaming all its episodes."
      done=0
    else:
      page = urlopen(final_link)
      soup = BeautifulSoup(page, 'html.parser')
      name_box = soup.find_all('div', attrs={'class': 'airdate'})
      l=0
      for item in name_box:
        name = item.text.strip() # strip() is used to remove starting and trailing
        ab=getNumberedDate(name)
        if len(name)!=0:
          if checkDate(int(ab))!=0 and len(name)==4:
            release_date="The next season begins in "+str(name)
            done=0
            break
          elif checkDate(ab)!=0:
            release_date="The next episode airs on "+str(checkDate(ab))
            done=0
            break
          else:
            l=1
            continue
        else:
          release_date="Information not available"
          done=1
          check=1
          break
          
    if done==0:
      return release_date
    elif l==1:
      return "Information not available" 
    elif check==2:
      return "Information not available"
    elif done==1:
      continue



#Mailing Function
def mail(email,message): 
  #Please add the email address by which you want to send the mail
  fromaddr = EmailAddress
  toaddrs  = email
  msg = "\r\n".join([
      "From: your_email_address",
      "To:"+toaddrs,
      "Subject: TV Series",
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
  
  
#Connecting with MySql
try:
  mydb = mysql.connector.connect(
    host=MysqlHost,
    user=MysqlUser,
    passwd=MysqlPassword,
    database="mydatabase"
  )
except mysql.connector.Error as err:
    if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
      print("Something is wrong with your user name or password")
    else:
      print(err)


mycursor = mydb.cursor()
try:
  mycursor.execute("CREATE DATABASE mydatabase")
except:
  pass
try:
  mycursor.execute("CREATE TABLE series_details(Id INT AUTO_INCREMENT PRIMARY KEY,Email VARCHAR(255), series VARCHAR(255))")
except:
  pass
print("Enter Number of Users you want to Register")
n=int(input())
for i in range(n):
  x=input("\nEmail address:")
  tv=input("TV series:")
  sql="INSERT INTO series_details(Email,series) VALUES(%s, %s)" 
  val=(x,tv)
  mycursor.execute(sql,val)
  mydb.commit()
mycursor.execute("SELECT Email,series FROM series_details")
mm={}
for x in mycursor:
  message=''
  for k in x[1].split(','):
    xx=k.strip()
    if xx not in mm:
      mm[xx]=infoSeries(xx)
    message+="Tv series name: "+str(xx)+"\r\n"+"Status: "+mm[xx]+"\r\n"    
    message+="\r\n"
  mail(x[0],message)
    




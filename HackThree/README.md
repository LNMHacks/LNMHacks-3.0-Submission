# LNMhacks

The given project will save the community from all the spoilers of their favourite tv series hovering around and to recommend top five movies according to their genre

The main aim of the project is to send an email to the user that contains the information about the Air Date of episode of the TV series a user likes ( air date = Date on which the episode will be broadcasted)


Input :
  User email
  List of Tv series user like
  List of genre user like

Getting Started:
Dependencies
1. Make sure these libraries are installed in your system
Libraries:
smtplib
urllib
bs4 ( BeautifulSoup)
datetime
Mysql.connector
Installation
Installing dependencies:
sudo apt-get install python3-mysql.connector
sudo apt-get install python3-bs4

Enter your Credentials at config.py code.
EmailAddress="Your_Email_Address"
Enter your email address through which you want to email all the users
Password="Your_Password"
Enter Password of your email addres​ s

Enter host name, username and password of your mysql
​ #Mysql
MysqlHost="Your_Host_Name"
MysqlUser="Your_User_Name"
MysqlPassword="Your_password"
Example:
MysqlHost=”local host”
MysqlUser="Innovaccer"
MysqlPassword="My password"
Your Gmail should allow third party apps(Less secure apps) to send mails.

Usage
Run this code in terminal and enter number of users you want to enter.
Enter user email address
example: sample@gmail.com
Enter tv series information requirement separating with comma
example: Game of thrones, black mirror, da vinci demons, breaking bad

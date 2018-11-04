#! C:/Users/Ankit/AppData/Local/Programs/Python/Python36-32/python.exe

import http.cookies

myc = http.cookies.SimpleCookie()

myc['unm']='admin' 
myc['unm']['expires']=36*24

myc['passw']='ankit' 
myc['passw']['expires']=36*24

#print(myc)
print("Content-type:text/html")
print("")

#print(myc)

import os,cgitb
cgitb.enable()
if 'HTTP_COOKIE' in os.environ:
	cookie_string=os.environ.get('HTTP_COOKIE')
	c=http.cookies.SimpleCookie()
	c.load(cookie_string)
	print(c)

	try:
		data=c['passw'].value
		print("cookie data: ",data,"<br>")
	except KeyError:
        	print("The cookie was not set or has expired<br>")
	
	print(cookie_string)
	coo_list = cookie_string.split(";")
	
	for coo_data in coo_list:
		(cnm,cval) = coo_data.split("=")
		print(cnm,'--->',cval,'<br>')
	
print("""

<html>
	<head>
		<title>Home Finder(upload details)</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
        <link rel="stylesheet" href="assets/css/main1.css" />
		<noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
        <noscript><link rel="stylesheet" href="assets/css/noscript1.css" /></noscript>
	</head>
    <style>
            #container{
                width:60%;
                height:auto;
                color:grey;
                opacity:0.5;
                padding:3%;
                margin:auto;
                align-items: center;
                -moz-align-items: center;
                -webkit-align-items: center;
                -ms-align-items: center;
		      align-items: center;
		      -moz-justify-content: center;
		      -webkit-justify-content: center;
		      -ms-justify-content: center;
		      justify-content: center;
              border: 2px solid grey;
                border-radius:5%;
            }
            #contact{
                  align-items: center;
                -moz-align-items: center;
                -webkit-align-items: center;
                -ms-align-items: center;
		      
            }
            .inputfile{
                width: 1.10px;
                height: 1.10px;
                opacity: 0;
                overflow: hidden;
                position: absolute;
                border: 3px solid grey;
                z-index: -1;
            }
            .inputfile + label{
                font-size: 1.25em;
                font-weight: 700;
                color: white;
                background-color: black;
                display: inline-block;
                cursor:pointer;
            }
	
    </style>
	<body>

		<!-- Wrapper -->
				<!-- Main -->
					<div id="container">
                    
                    <article class="contact ">
								<h2 class="major">Admin Login</h2>
								<form method="post">
									<div class="fields">
										<div class="field">
											<label for="username">UserName</label>
											<input type="text" name="usname" id="name" required/>
										</div>
										<div class="field">
											<label for="password">Password</label>
											<input type="password" name="upsword" id="password" required/>
										</div>
                                        </div>
                                        <ul class="actions">
                                            <li><input type="submit" name="btn" value="Login" class="primary" /></li>
                                            <li><input type="reset" value="Reset" /></li>
                                        </ul>
                                    </form>
									</article>
                    
			         </div>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>

	</body>
</html>


""")


import cgi
import cgitb
import MySQLdb
    
db = MySQLdb.connect(host="localhost", user='root',password='kuber', database='Homear')
    
curs = db.cursor()
form = cgi.FieldStorage()
n= form.getvalue('btn')
if n!=None:
    name=form.getvalue("usname")
    pwrd=form.getvalue("upsword")  
    query="select username,paswrd from admindet where username='"+name+"' AND paswrd='"+pwrd+"'"
    
    data=curs.execute(query)
    if data==1:
        print("connection granted")
        print("""<html><script>window.location="Apanel.py"</script></html>""")
    else:
        print("Wrong Credenitals")
    
db.commit()
curs.close()
db.close()

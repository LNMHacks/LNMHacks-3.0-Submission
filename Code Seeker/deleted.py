#! C:/Users/Ankit/AppData/Local/Programs/Python/Python36-32/python.exe

print("Content-type: text/html")
print("")

print("""

<html>
	<head>
		<title>Home Finder(Delete details)</title>
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
								<h2 class="major">Delete AR Frame</h2>
								<form method="post" enctype="multipart/form-data" >
                                        <div class="field">
											<label for="email">Email</label>
											<input type="email" name="email" id="email" required/>
										</div><br>
                                        <div class="field">
											<label for="pascode">PassCode</label>
											<input type="text" name="pascode" id="pascode" required/>
										</div>
                                        
                                        <br><br>
									<ul class="actions">
										<li><input type="submit" value="Delete" name="btn" class="primary" /></li>
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
import config
import MySQLdb

db = MySQLdb.connect(host="localhost", user='root',password='kuber', database='Homear')
curs = db.cursor()
form = cgi.FieldStorage()
n= form.getvalue('btn')

if n!=None:
    email=form.getvalue('email')
    pasc = form.getvalue('pascode')
    qsql="select email,pascode from pascode where email = '"+email+"' AND pascode = '"+pasc+"'"
    mark = curs.execute(qsql)
    if mark == 1:
        query="delete from approved where email = '"+email+"'"
        result = curs.execute(query)
        query = "delete from pascode where email = '"+email+"'"
        reset = curs.execute(query)
        if result==1:
            print("""<html><script>window.alert("Details deleted Successfully")</script></html>""")
    else:
        print("""<html><script>window.alert("Something went wrong!!!!")</script></html>""")
db.commit()
cursor.close()
db.close()

#! C:/Users/Ankit/AppData/Local/Programs/Python/Python36-32/python.exe

print("Content-type: text/html")
print("")

print("""

<html>
	<head>
		<title>Home Finder(upload markers)</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
        <link rel="stylesheet" href="assets/css/main1.css" />
		<noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
        <noscript><link rel="stylesheet" href="assets/css/noscript1.css" /></noscript>
	</head>
    <style>
    p{
        margin-bottom:0px;
    }
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
								<h2 class="major">Upload Details</h2>
								<form method="post" enctype="multipart/form-data" >
									<div class="fields">
										<div class="field">
											<label for="name">Name</label>
											<input type="text" name="name" id="name" required/>
                                            <p><b>Note : </b>If you are a paying guest write it as Name(On rent).</p>
										</div>
										<div class="field">
											<label for="pic">Marker Image</label>
                                            <label class="primary button"><span class="icon fa-upload"/> Image of Urs
											<input type="file" name="img" class="inputfile" required/>
                                            </label>
                                            <p><b>Note : </b>It should be of Marker own by you.</p>
										</div>
                                     </div>
									<ul class="actions">
										<li><input type="submit" value="Submit" name="btn" class="primary" /></li>
										<li><input type="reset" value="Reset" /></li>
									</ul>
								</form>
									</article>
                    
			         </div><br><br>
                     <center><a href="markused.py" class="primary button">Used Markers</a></center>    
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
print("1")
if n!=None:
    name=form.getvalue("name")
    pic=form['img']
    picpath=pic.filename
    picd=pic.file.read()
    picp= open("component/usedm/"+picpath,"wb")
    picp.write(picd)
    picp.close()
    query="insert into usedmark values('"+name+"','"+picpath+"')"
    result = curs.execute(query)
    if result==1:
        print("""<html><script>window.alert("Marker Image uploaded Successfully")</script></html>""")
    db.commit()
    cursor.close()
    db.close()

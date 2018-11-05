#! C:/Users/Ankit/AppData/Local/Programs/Python/Python36-32/python.exe
print("Content-type: text/html")
print("")
                          
import random
import cgi, cgitb, MySQLdb
db = MySQLdb.connect(host="localhost", user='root',password='kuber', database='Homear')
curs = db.cursor()
form=cgi.FieldStorage()
query="select * from updetails"
result=curs.execute(query)
data=curs.fetchall()

print("""


<html>
	<head>
		<title>Home Finder(Admin Console)</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
        <link rel="stylesheet" href="assets/css/main1.css" />
		<noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
        <noscript><link rel="stylesheet" href="assets/css/noscript1.css" /></noscript>
	</head>
    <style>
            #container{
                width:80%;
                height:auto;
                color:grey;
                opacity:0.5;
                padding:3%;
                align:center;
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
              margin:auto;
                margin-top:30px;
                border-radius:2%;
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
                    
                    <h2>Approve Details</h4>
                    
                   <!-- Homes  --> 
                    
                    <hr>
									<div class="table-wrapper">
										<form method="post">
                                        <table class="alt">
											<thead>
												<tr>
													<th>Name</th>
													<th>Email</th>
													<th>Mobile</th>
                                                    <th>Address</th>
                                                    <th>User Image</th>
                                                    <th>.Patt file </th>
												</tr>
											</thead>
		                                    <tbody>
												
        

""")        
for n in data:
    print("<tr>")
    print("<td>"+str(n[0])+"</td>")
    print("<td>"+str(n[1])+"</td>")
    print("<td>"+str(n[2])+"</td>")
    print("<td>"+str(n[3])+"</td>")
    print("<td>"+str(n[4])+"</td>")
    print("<td>"+str(n[5])+"</td>")
    print("""
    
    
                 <td><ul class="actions">
                                                    <li>
                                                        <input type="submit" name="add" value="Add" class="button"/>
                                                    </li>
                                                    <li>
                                                        <input type="submit" name="del" value="Delete" class="button"/>
                                                    </li></ul></td>
                                                </tr>

    
    """)

    print("</tr>")                
                            
print("""
            
                                            <tfoot>
												<tr>
													<td colspan="3"></td>
													
												</tr>
											</tfoot>
										
												
										</table>
                                        </form>
									</div>
""")
print("<hr>")
query="select * from buses"
result=curs.execute(query)
data=curs.fetchall()
    
print("""

                <!-- Buses  --> 
                                				<div class="table-wrapper">
										<form method="post">
                                        <table class="alt">
											<thead>
												<tr>
													<th>Bus No</th>
													<th>From(Source)</th>
													<th>TO(Destination)</th>
                                                    <th>Stops</th>
                                                    <th>User Image</th>
                                                    <th>.Patt file </th>
                                                    <th>Driver Contact</th>
                                                    <th>Conductor Contact</th>
												</tr>
											</thead>
		                                    <tbody>
												
        

""")        
for n in data:
    print("<tr>")
    print("<td>"+str(n[0])+"</td>")
    print("<td>"+str(n[1])+"</td>")
    print("<td>"+str(n[2])+"</td>")
    print("<td>"+str(n[3])+"</td>")
    print("<td>"+str(n[4])+"</td>")
    print("<td>"+str(n[5])+"</td>")
    print("<td>"+str(n[6])+"</td>")
    print("<td>"+str(n[7])+"</td>")
    print("""
    
    
                 <td><ul class="actions">
                                                    <li>
                                                        <input type="submit" name="addb" value="Add" class="button"/>
                                                    </li>
                                                    <li>
                                                        <input type="submit" name="delb" value="Delete" class="button"/>
                                                    </li></ul></td>
                                                </tr>

    
    """)
    print("</tr>")                
                            
print("""
            
                                            <tfoot>
												<tr>
													<td colspan="3"></td>
												</tr>
											</tfoot>
										
												
										</table>
                                        </form>
									</div>
""")
query="select * from multies"
result=curs.execute(query)
data=curs.fetchall()

print("""
                                
                                <!-- Multies  --> 
                                
                                <hr>
									<div class="table-wrapper">
										<form method="post">
                                        <table class="alt">
											<thead>
												<tr>
													<th>Builder Name</th>
													<th>Bilder Contact</th>
													<th>Area</th>
                                                    <th>Caretaker name</th>
                                                    <th>Caretaker Contact</th>
                                                    <th>Map Image </th>
                                                    <th>.Patt file </th>
												</tr>
											</thead>
		                                    <tbody>
												
        

""")        
for n in data:
    print("<tr>")
    print("<td>"+str(n[0])+"</td>")
    print("<td>"+str(n[1])+"</td>")
    print("<td>"+str(n[2])+"</td>")
    print("<td>"+str(n[3])+"</td>")
    print("<td>"+str(n[4])+"</td>")
    print("<td>"+str(n[5])+"</td>")
    print("<td>"+str(n[6])+"</td>")
    print("""
    
    
                 <td><ul class="actions">
                                                    <li>
                                                        <input type="submit" name="addm" value="Add" class="button"/>
                                                    </li>
                                                    <li>
                                                        <input type="submit" name="delm" value="Delete" class="button"/>
                                                    </li></ul></td>
                                                </tr>

    
    """)

    print("</tr>")                
                            
print("""
            
                                            <tfoot>
												<tr>
													<td colspan="3"></td>
													<td><a href="approved.py" class="primary button">Approved Details</a></td>

												</tr>
											</tfoot>
										
												
										</table>
                                        </form>
									</div>


								</section>

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

add=form.getvalue('add')
dele=form.getvalue('del')
if add=='Add':
    pasc = str(random.randint(100000, 999999))
    query1="insert into approved values('"+str(n[0])+"','"+str(n[1])+"','"+str(n[2])+"','"+str(n[3])+"','"+str(n[4])+"','"+str(n[5])+"')"
    result=curs.execute(query1)
    print("1")
    curs.execute("insert into pascode values('"+str(n[1])+"','"+pasc+"')")
    curs.execute("delete from updetails where email = '"+str(n[1])+"'")   
elif dele=='Delete':
    query1="delete from updetails where email = '"+str(n[1])+"'"
    result=curs.execute(query1)
    
addb=form.getvalue('addb')
deleb=form.getvalue('delb')
if addb=='Add':
    query1="insert into apprbus values('"+str(n[0])+"','"+str(n[1])+"','"+str(n[2])+"','"+str(n[3])+"','"+str(n[4])+"','"+str(n[5])+"','"+str(n[6])+"','"+str(n[7])+"')"
    result=curs.execute(query1)
    print("1")
    curs.execute("delete from buses where concon = '"+str(n[7])+"'")   
elif deleb=='Delete':
    query1="delete from buses where concon = '"+str(n[7])+"'"
    result=curs.execute(query1)


addm=form.getvalue('addm')
delem=form.getvalue('delm')
if addm=='Add':
    query1="insert into apprmul values('"+str(n[0])+"','"+str(n[1])+"','"+str(n[2])+"','"+str(n[3])+"','"+str(n[4])+"','"+str(n[5])+"','"+str(n[6])+"')"
    result=curs.execute(query1)
    print("1")
    curs.execute("delete from multies where Bcon = '"+str(n[1])+"'")   
elif dele=='Delete':
    query1="delete from multies where Bcon = '"+str(n[1])+"'"
    result=curs.execute(query1)


db.commit()
curs.close()
db.close()
    
#! C:/Users/Ankit/AppData/Local/Programs/Python/Python36-32/python.exe
print("Content-type: text/html")
print("")
                                        
import cgi, cgitb, MySQLdb
db = MySQLdb.connect(host="localhost", user='root',password='kuber', database='Homear')
curs = db.cursor()
form=cgi.FieldStorage()
query="select * from usedmark"
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
                border-radius:2%;
            }
            .alt{
                text-align:center;
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
                    
                    <h2>Used Markers</h4>
                    <hr>
									<div class="table-wrapper">
										<form method="post">
                                        <table class="alt">
											<thead>
												<tr>
													<th>Name</th>
													<th>Marker Image</th>
                                                    
												</tr>
											</thead>
		                                    <tbody>
												
        

""")        
for n in data:
    print("<tr>")
    print("<td>"+str(n[0])+"</td>")
    print("<td><img src='component/usedm/"+str(n[1])+"' width='100' height='100'/></td>")
    print("""
            
                 <td><ul class="actions">
                                                    <li>
                                                        <input type="submit" name="del" value="Delete" class="button"/>
                                                    </li></ul></td>
                                                </tr>
    
    """)
    print("</tr>")                
                            
print("""
            
                                            <tfoot>
												<tr>
													<td colspan="2"></td>
													<td><a href=""></td>
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
dele = form.getvalue('del')
if dele!=None:
    query1="delete from usedmark where name = '"+str(n[0])+"'"
    result=curs.execute(query1)
    
    

db.commit()
curs.close()
db.close()
    
#! C:/Users/Ankit/AppData/Local/Programs/Python/Python36-32/python.exe

print("Content-type: text/html")
print("")

                                        
import cgi, cgitb, MySQLdb
db = MySQLdb.connect(host="localhost", user='root',password='kuber', database='Homear')
curs = db.cursor()
form=cgi.FieldStorage()
query="select * from apprbus"
result=curs.execute(query)
data=curs.fetchall()

print("""

<html>
       <!-- include A-Frame obviously -->
    <head>
    <script src="assets/aframe.min.js"></script>
    <!-- include ar.js for A-Frame -->
    
    <script src="assets/aframe-ar.js"></script>
    <script type="text/javascript" src="assets/aframe-animation-component.min.js"></script>
    <script type="text/javascript" src="assets/aframe-particle-system-component.min.js"></script>
  </head>
<body style='margin : 0px; overflow: hidden;'>
  <a-scene embedded arjs>
  
      <a-marker type='pattern' preset='hiro'>
    
          <a-box position='0 -7 0' color='grey' depth='0.1' width='5' height='4' rotation='270 0 0' animation='property: scale; dir: alternate; dur: 3500; to: 3.15 3.15 3.15; loop: true'>
              <a-animation attribute='color' from='grey' to='black' dur='3500' repeat='indefinite' loop='true'></a-animation>
              <a-text value='Name : Ankit Gautam' position='-2.3 1 0.3'></a-text>
              <a-text value='Address : RajendraNagar,Indore' position='-2.3 0.5 0.3'></a-text>
              <a-text value='Contact : 860297****' position='-2.3 0 0.3'></a-text>
              <a-text value='Email : ankicode4u@gmail.com' position='-2.3 -0.5 0.3'></a-text>
              <a-text value='Marital status : single' position='-2.3 -1 0.3'></a-text>
              <a-box position='1.7 1 0.5' src='components/ankit.jpg' depth='0.1' width='1.5' height='1.5'></a-box>
        </a-box>
      </a-marker>   
      
      
    
      
    <!--    Dynamic content     -->
""")
    
    
    


for d in data:
    print("<a-marker type='pattern' url='bmarker/"+str(d[5])+"'>")
    print("""
             <a-box position="0 -7 0" color="grey" depth="0.1" width="5" height="4" rotation="270 0 0" animation="property: scale; dir: alternate; dur: 3500; to: 3.15 3.15 3.15; loop: true">
              <a-animation attribute="color" from="grey" to="black" dur="3500" repeat="indefinite" loop="true"></a-animation>
    """)
    print("<a-text value='Bus Number :"+str(d[0])+"' position='-2.3 1 0.3'></a-text>")
    print("<a-text value='From(Source) :"+str(d[1])+"' position='-2.3 0.5 0.3'></a-text>")
    print("<a-text value='TO(Destination) :"+str(d[2])+"' position='-2.3 0 0.3'></a-text>")
    print("<a-text value='Stops :"+str(d[3])+"' position='-2.3 -0.5 0.3'></a-text>")          
    print("<a-text value='Driver Contact :"+str(d[6])+"' position='-2.3 -1.0 0.3'></a-text>")          
    print("<a-text value='Conductor Contact :"+str(d[7])+"' position='-2.3 -1.5 0.3'></a-text>")          
    print("<a-circle position='1.7 1 0.5' src='bimage/"+str(d[4])+"' radius='0.7'></a-circle>")
    print("""

        </a-box>
     </a-marker>
      
""")
    
print("""
     
  </a-scene>
</body>
</html>


""")
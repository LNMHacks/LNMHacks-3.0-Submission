
<?php 
session_start();
require_once "pdo.php";

if (isset($_POST['Subm'])){
$var=$_POST['man'];
foreach($var as $value)
{
    $query="UPDATE users set status =1 and ngo_id =".$_SESSION['id']."WHERE sno=".$value;
    $connect->exec($query);
}

}
?>
<!DOCTYPE html>
<html>
<head> 
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
  <title>Google Maps Multiple Markers</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
  <script src="http://maps.google.com/maps/api/js?sensor=false" 
          type="text/javascript"></script>
</head> 
<body>
  <div id="map" style="width: 500px; height: 400px;"></div>
  
  <?php
  require_once "pdo.php";

$sql="SELECT * FROM users where status =0";
$count=0;
    echo('<form method="post" action="ngo1.php">');
 foreach($connect->query($sql, PDO::FETCH_ASSOC) as $row  ){
  
  $man =array();
    $man[$count]=$row['sno'];
    $splittedstring=explode(" ",$row['loc']);

    echo('<div class ="row" style="border:1px solid grey;"><div class="col-lg-2">'.$row['sno']." </div><div class=col-lg-2>".$row['id']."</div> <div class=col-lg-2>".$row['loc']."</div> <div class=col-lg-2>".$row['image']."</div> <div class=col-lg>".$row['status'].'</div></div>');
 echo('<input type="checkbox" name="man[]" ><br>');
$count =$count+1;
}
echo('<br><br><input type="submit" name="Subm" value="Submit"><br><br>');

  ?>
  </form>  

<script type="text/javascript">
    var locations = [
      ['abc', 26.9224,75.8147, 4],
      ['bds', 26.24,75.147, 5],
      ['dadsdah', 26.5224,75.8147, 3],
      ['MasdasBdasdch',26.9224,75.8147, 1]
    ];
    
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 10,
      center: new google.maps.LatLng(26.9224,75.8147),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker, i;

    for (i = 0; i < locations.length; i++) {  
      marker = new google.maps.Marker({
        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        map: map
      });

      google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
          infowindow.setContent(locations[i][0]);
          infowindow.open(map, marker);
        }
      })(marker, i));
    }
  </script>
</body>
</html>






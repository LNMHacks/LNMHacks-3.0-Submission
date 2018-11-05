<?php
            include('dbinfo.php');
            $query = "SELECT status,gameid FROM game WHERE invite=".$_GET['invite'].";";
          	$result = mysqli_query($con,$query);
          	$status = mysqli_fetch_array($result);
          	if($status)
          	{
          	    $arr = array ('status'=>$status['status'],'gameid'=>$status['gameid']);
          	    echo json_encode($arr);
          	}
            
?>
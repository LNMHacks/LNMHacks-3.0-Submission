<?php
include "login1.php";
   if($_POST) {
	   $email = $_POST['email'];
	$uname = $_POST['uname'];
	$password = $_POST['password'];
	$servername = "localhost";
	$username = "root";
$password1 = "";
$dbname = "soumya";
$conn = new mysqli($servername, $username, $password1, $dbname);
$sql = "SELECT email FROM admin WHERE username = '$uname' and password = '$password'";
	$result = mysqli_query($dbname,$sql);
	$row = mysqli_fetch_array($result,MYSQLI_ASSOC);
      $active = $row['active'];
	  
	$count = mysqli_num_rows($result);
	$count = mysqli_num_rows($result);	
	
	if($count == 1) {
         session_register("uname");
         $_SESSION['login_user'] = $uname;
         
         header("location: winnerspoint.php");
      }else {
         $error = "Your Login Name or Password is invalid";
      }
   }
?>
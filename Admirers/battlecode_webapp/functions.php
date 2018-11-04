<?php

session_start();

// checks if any user is logged in
function loggedin() {
  return isset($_SESSION['username']);
}

// connects to the database
function connectdb() {
  include('dbinfo.php');
  $con = mysqli_connect($host,$user,$password,$database) or die('Error connecting to database.');
}

// generates a random alpha numeric sequence. Used to generate salt
function randomAlphaNum($length){
  $rangeMin = pow(36, $length-1);
  $rangeMax = pow(36, $length)-1;
  $base10Rand = mt_rand($rangeMin, $rangeMax);
  $newRand = base_convert($base10Rand, 10, 36);
  return $newRand;
}

// gets the name of the event
function getName(){
  include('dbinfo.php');
  $con = mysqli_connect($host,$user,$password,$database) or die('Error connecting to database.');
  
  $query="SELECT name FROM prefs";
  $result = mysqli_query($con, $query);
  $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
  return $row['name'];
}

// converts text to an uniform only '\n' newline break
function treat($text) {
	$s1 = str_replace("\n\r", "\n", $text);
	return str_replace("\r", "", $s1);
}

<?php 
//Get values passes form login.php file
$username = $_POST['user'];
$password = $_POST['pass'];
$conn=new mysqli("localhost","root","","login");

//To prevent mysql injection
$username = stripslashes($username);
$password = stripslashes($password);
$username = mysqli_real_escape_string($conn,$username);
$password = mysqli_real_escape_string($conn,$password);

//Connect to the server and select database
//mysql_connect("localhost", "root", "");
//mysql_select_db("login");

//Query the database for user
$sql="select * from users where username = '$username' and password = '$password'";
$result = $conn-> query($sql);
//or die("Failed to query database".mysql_error());
$row = $result->fetch_array();
if ($row['username'] == $username && $row['password'] == $password) {
	if($password == 'GauravRoy'){
		$sql="select * from users where id=2";
		$result=$conn-> query($sql);
		$row = $result->fetch_array();
		print_r($row);
	}
	if($password == 'Sameervats'){
		$sql="select * from users where id=4";
		$result=$conn-> query($sql);
		$row = $result->fetch_array();
		print_r($row);
	}
	if ($password == 'Akashdwivedi') {
		$sql="select * from users where id=3";
		$result=$conn-> query($sql);
		$row = $result->fetch_array();
		print_r($row);
	}
	if ($password == 'Kartikeytiwari') {
		$sql="select * from users where id=1";
		$result=$conn-> query($sql);
		$row = $result->fetch_array();
		print_r($row);
	}
	

	
}
else{
	echo "Failed to login!!";
}
?>
<?php
if($_POST)
{
	$email = $_POST['email'];
	$uname = $_POST['uname'];
	$password = $_POST['password'];
	$servername = "localhost";
$username = "root";
$password1 = "";
$dbname = "soumya";
$conn = new mysqli($servername, $username, $password1, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "INSERT INTO sign values('$email','$uname','$password')";
if ($conn->query($sql) === TRUE) {
	header("location: login.html");
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

mysqli_close($conn);
}
?>
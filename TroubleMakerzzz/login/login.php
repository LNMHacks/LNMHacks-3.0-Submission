<?php
$email=$_POST['email'];
$password=$_POST['password'];
$con=mysqli_connect("localhost","id6450383_troublemakerzzzz1","qwerty@12","id6450383_details");
if(!$con)
	{
	die("conn. failed").mysqli_connect_error();
	}
$sql="SELECT * FROM user_details WHERE (email='$email' AND password=sha1('$password'))";
$result=mysqli_query($con,$sql);
$qq=mysqli_num_rows($result);
if($qq>0)
	{
		echo "Welcome ". $email;
}	
else
	{
		echo "Sorry please check the details again.";
	}
mysqli_close($con);
?>
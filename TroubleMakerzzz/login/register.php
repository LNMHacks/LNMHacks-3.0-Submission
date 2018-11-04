<?php
if($_POST)
{
			$email=$_POST['email'];
			$name=$_POST['name'];
			$password=$_POST['password'];
			$password1=$_POST['password1'];
if($password==$password1)
	{
$con=mysqli_connect("localhost","id6450383_troublemakerzzzz1","qwerty@12","id6450383_details");
			if(!$con)
			{
			die("can't connect to database").mysqli_connect_error();
			}

			
			$sql="INSERT INTO user_details VALUES('$email','$name',sha1('$password'),'0')";
			if(mysqli_query($con,$sql))
			{
			echo "Thnakyou $name for regestration.";
			}

			else
			{
			echo $sql.mysqli_error($con);
			}
			mysqli_close($con);
	}
	else echo "password doesnt match.";
}
else "ro";
?>
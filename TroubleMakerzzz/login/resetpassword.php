<?php
	if (isset($_GET["token"]) && isset($_GET["email"])) {
		$connection = mysqli_connect("localhost","root","","test");
		if(!$connection)
		{
		    die("cant connect").mysqli_connect_error();
		}
		$email = $connection->real_escape_string($_GET["email"]);
		$token = $connection->real_escape_string($_GET["token"]);
        //echo $email."<br>".$token;
		$data = $connection->query("SELECT * FROM details WHERE email='$email' AND token='$token' AND token <> '' AND expire > NOW()");
        echo $dta;
		if ($data->num_rows >= 0) {
			$str = "0123456789qwertzuioplkjhgfdsayxcvbnm";
			$str = str_shuffle($str);
			$str = substr($str, 0, 15);

			$password = sha1($str);

			$connection->query("UPDATE details SET password = '$password', token = '' WHERE email='$email'");

			echo "Your new password is: $str";
			echo "<br>";
			echo "use the above password to login again.";
			
		} else {
			echo "Please check your link!";
		}
	} else {
		header("Location: index.php");
		exit();
	}
?>
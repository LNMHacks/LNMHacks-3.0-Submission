<?php
	if (isset($_POST["forgotPass"])) {
		$connection = mysqli_connect("localhost","root","","test");
		
		$email = $connection->real_escape_string($_POST["email"]);
		
		$data = $connection->query("SELECT email FROM  details WHERE email='$email'");

		if ($data->num_rows > 0) {
			$str = "0123456789qwertzuioplkjhgfdsayxcvbnm";
			$str = str_shuffle($str);
			$str = substr($str, 0, 10);
			$url = "http://troublemakerzzzz.000webhostapp.com/resetpassword.php?token=$str&email=$email";

			mail($email, "Reset password", "To reset your password, please visit this url. This URL is only valid till 15 minutes.: $url", "From: Smart Irrigation System\r\n");

			$connection->query("UPDATE details SET token='$str' WHERE email='$email'");

			echo "Please check your email!";
		} else {
			echo "Please check your inputs!";
		}
	}
?>
<html>
	<body>
		<form action="forgotPassword.php" method="post">
			<input type="text" name="email" placeholder="Email"><br>
			<input type="submit" name="forgotPass" value="Request Password">
		</form>
	</body>
</html>
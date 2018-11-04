<?php
if(isset($_POST))
{
	$isbn=rand(100000000,999999999);
	$book_name=$_POST['book_name'];
	$author=$_POST['author'];
	$pub_year=rand(1990,2018);
	$publisher_array=array("Penguin Random House","Hachette Livre","Harper Collins","Pan Macmillan","Perason Education","Oxford University press","Bloomsbury","Simon & Schuster","jhon wiley & sons","Egmont & Co",);
	$publisher=$publisher_array[array_rand($publisher_array)];
	$edition=rand(1,5);
	$page_no=rand(200,700);
	$rating=rand(3,10);
	$hits=rand(10,87);
	$copies=rand(1,60);
	$price=rand(266,1476);
	$genre_array=array("science fiction","satire","drama","action","romance","mystery","horror","health","history","anthology","poerty","comics","art","diaries","journals","series");
	$genre_1=$genre_array[array_rand($genre_array)];
	$genre_2=$genre_array[array_rand($genre_array)];
	$genre_3=$genre_array[array_rand($genre_array)];
	$genre_4=$genre_array[array_rand($genre_array)];
$con=mysqli_connect("localhost","id6450383_troublemakerzzzz","qwerty@12","id6450383_book");
if(!$con)
	{
	die("conn. failed").mysqli_connect_error();
	}
$sql="INSERT INTO library_book values('$isbn','$book_name','$author','$pub_year','$publisher','$edition','$page_no','$rating','$hits','$copies','$price','$genre_1','$genre_2','$genre_3','$genre_4')";
if(mysqli_query($con,$sql))
			{
			include 'datadata.php';
			}

			else
			{
			echo $sql.mysqli_error($con);
			}
			mysqli_close($con);
    }
?>
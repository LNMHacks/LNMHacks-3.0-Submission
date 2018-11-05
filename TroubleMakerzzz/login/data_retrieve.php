<?php
if(isset($_POST))
{
	$book=$_POST['book'];
$con=mysqli_connect("localhost","id6450383_troublemakerzzzz","qwerty@12","id6450383_book");
if(!$con)
	{
	die("conn. failed").mysqli_connect_error();
	}
$sql="SELECT book_name , author , edition , price FROM library_book where book_name like '%$book%'";
$result=mysqli_query($con,$sql);
if(mysqli_num_rows($result)>0)
	{
	while($row=mysqli_fetch_assoc($result))
		{
		echo "<tr>";
		echo "<td>".$row['book_name']."  </td>";
		echo "<td>".$row['author']."        </td>";
		echo "<td>".$row['edition']."       </td>";
		echo "<td>".$row['price']."     </td>";
		echo "<br>";
		//echo "<td>".$row['age']."</td>";
		//echo "<td>".$row['status']."</td>";
		}
	//echo "</tr>";
		}
else
echo "zero result";
mysqli_close($con);
}
?>
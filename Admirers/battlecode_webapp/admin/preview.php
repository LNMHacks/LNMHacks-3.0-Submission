<?php

	if($_POST['action'] == 'preview') {
		// preview for the markdown problem statement
		if($_POST['title']=="" and $_POST['text']=="")
			echo("<div class=\"alert alert-error\">You have not entered either the title or the problem text!</div>");
		else {
			include('../markdown.php');
			$out = Markdown($_POST['text']);
			echo("<hr/>\n<h1>".$_POST['title']."</h1>\n");
			echo($out);
		}
	} else if($_POST['action'] == 'code' and is_numeric($_POST['id'])) {
		// formatting for codes
		include('../functions.php');
		include('../dbinfo.php');
		echo("<hr/><h1><small>".$_POST['name']."</small></h1>\n");
		$query = "SELECT filename, soln FROM solve WHERE (username='".mysqli_real_escape_string($_POST['uname'])."' AND problem_id='".$_POST['id']."')";
		$result = mysqli_query($con,$query);
		$row = mysqli_fetch_array($result);
		$str = str_replace("<", "&lt;", $row['soln']);
		echo("<strong>".$row['filename']."</strong><br/><br/>\n<pre>".str_replace(">", "&gt;", $str)."</pre>");
	}
?>

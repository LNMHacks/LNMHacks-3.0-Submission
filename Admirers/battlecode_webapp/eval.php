<?php
	require_once('functions.php');
	include('dbinfo.php');
	$live = 1;
        $query = "SELECT status FROM users WHERE username='".$_SESSION['username']."'";
        $result = mysqli_query($con,$query);
        $status = mysqli_fetch_array($result);
        
        
	    $api = "http://35.243.242.213/cgi-bin/docker.py";
	    
	
        // check if the user is banned or allowed to submit and SQL Injection checks
         if($status['status'] == 1 and is_numeric($_POST['id'])) {
        	$gameid = $_POST['gameid'];
        	//$lang = $_POST['lang'];
        	$lang="python";
        	$soln = $_POST['soln'];
        	//check if entries are empty
        	if(trim($soln) == "")
        		header("Location: solve.php?derror=1&gameid=".$_POST['gameid']);
        	else {

			if($live=1) {
			    
			    $query = "SELECT input, output FROM problems WHERE sl='".$_POST['id']."'";
				$result = mysqli_query($con,$query);
				$fields = mysqli_fetch_array($result);
				$soln = str_replace("\n", '$_n_$', treat($_POST['soln']));
				$input = str_replace("\n", '$_n_$', treat($fields['input']));
				
			    $ch = curl_init();

                curl_setopt($ch, CURLOPT_URL,$api);
                curl_setopt($ch, CURLOPT_POST, 1);
                curl_setopt($ch, CURLOPT_POSTFIELDS,"lang=".$_POST['lang']."&code=".$_POST['soln']."&input=".$input);
                
                
                // Receive server response ...
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                
                $server_output = curl_exec($ch);
                
                //print_r($server_output);
                
                $out = json_decode($server_output, true);
                
                curl_close ($ch);
                
                // Further processing ...
                $cstatus=$out[0];
                //echo $out[0];
                $contents=$out[1];
                
				if($cstatus != 0) {
					// oops! compile error
					$_SESSION['cerror'] = trim($contents);
					header("Location: solve.php?cerror=1&gameid=".$_POST['gameid']);
				} else if($cstatus == 0) {
					if(trim($contents) == trim($fields['output'])) {
						// holla! problem solved
						$query = "INSERT INTO `solve` ( `problem_id` , `username`, `soln`, `status`, `lang`) VALUES ('".$_POST['id']."', '".$_SESSION['username']."', '".$soln."', 2, '".$lang."')";
				        mysqli_query($con,$query);
				        $query = "Update game set status='finish', winner='".$_SESSION['username']."' where gameid='".$_POST['gameid']."'";
				        mysqli_query($con,$query);
						header("Location: end.php?gameid=".$gameid);
					} else {
						// duh! wrong output
						echo "INPUT : ".trim($contents)." , EXPECTED OUTOUT: ".trim($fields['output']);
						//header("Location: solve.php?oerror=1&gameid=".$_POST['gameid']);
					}
				} 
			} else
				header("Location: solve.php?serror=1&gameid=".$_POST['gameid']); // compiler server not running
		}
	}
?>

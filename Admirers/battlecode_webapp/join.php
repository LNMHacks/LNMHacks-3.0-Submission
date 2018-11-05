<?php
	require_once('functions.php');
	if(!loggedin())
		header("Location: login.php");
	
	else{
	    include('header.php');
	}
?>



</style>


              <li><a href="index.php">Home</a></li>
              <li><a href="account.php">Account</a></li>
              <li><a href="logout.php">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
<style>  
    input[type=text] {
    width: 10%;
    padding: 12px 14px;
    margin: 2px 0;
    color : transparent;
    text-shadow : 0 0 0 #000;
    box-sizing: border-box;
    border: none;
    border-bottom: 2px solid red;
	letter-spacing: 0.75em;
}
</style>

    <div class="container">

<?php
    if(isset($_POST['connect'])){
	        
    	    include('dbinfo.php');
            $query = "SELECT gameid FROM game WHERE invite=".$_POST['invite'].";";
          	$result = mysqli_query($con,$query);
          	$gameid = mysqli_fetch_array($result);
            
            if($gameid['gameid'])
            {
                $query = "Update game set status='running', user2='".$_SESSION['username']."' where gameid='".$gameid['gameid']."';";
          	    mysqli_query($con,$query);
          	    $_SESSION['start'] = time();
          	    $_SESSION['expire'] = $_SESSION['start'] + (30 * 60);
          	    $_SESSION['gameid'] = $gameid['gameid'];
          	    echo "<script>window.location = 'solve.php?gameid=".$gameid['gameid']."';</script>";
            }
            else{
                echo("<div class=\"alert alert-error\">\nInvalid invite code or Game not created.\n</div>");
            }
		}
?>
        
        
    <form method="POST" action="join.php">
    Here is your invite code, be ready for the battle!<br/><br/>
      <ul class="nav nav-list">
        <li class="nav-header" style="font-size: 20px">Enter Invite Code: <input type="text" id="invite" name="invite" maxlength = "5"></li>
        <br>
        
        <a href="#"><input class="btn btn-primary btn-large" type="submit" value="Connect" name="connect"/></a>
        
      </ul>
      <br><br>
      
      <br/>
      
     </form> 
    </div>

<?php
	include('footer.php');
?>

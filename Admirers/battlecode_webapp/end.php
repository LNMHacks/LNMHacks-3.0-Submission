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
    if(isset($_GET['gameid'])){
	        
    	    include('dbinfo.php');
            $query = 'SELECT status,winner FROM game WHERE gameid="'.$_GET['gameid'].'"';
            //echo $query;
          	$result = mysqli_query($con,$query);
          	$gameid = mysqli_fetch_array($result);
            
            if($gameid['status'] == "finish")
            {
                if($gameid['winner'] == $_SESSION['username'])
                {
                    echo("<div class=\"alert alert-error\">\nCongratulation, ".$_SESSION['username']."! You won the match.\n</div>");
                }
                else{
                    echo("<div class=\"alert alert-error\">\nSorry, ".$_SESSION['username']."! You lost the match.\n</div>");
                }
            }
            
		}
?>
        
    </div>

<?php
	include('footer.php');
?>
<?php

	require_once('functions.php');
	if(!loggedin())
		header("Location: login.php");
	else
	{
		include('header.php');
		include('dbinfo.php');
	
	    $now = time(); // Checking the time now when home page starts.

        if ($now < $_SESSION['expire']) {
            header("Location: solve.php?gameid='".$_SESSION['gameid']."'");
        }
	}
?>
              <li><a href="index.php">Home</a></li>
              <li><a href="account.php">Account</a></li>
              <li><a href="logout.php">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
    
    Its time to have a coding battle !<br/><br/>
      <ul class="nav nav-list">
        <li class="nav-header">WELCOME CODER !</li>
        
      </ul>
      <br/>
      
      <a href="create.php"><input class="btn btn-primary btn-large" type="submit" value="Create"/></a>
      <a href="join.php"><input class="btn btn-primary btn-large" type="submit" value="Join"/></a>
      
    </div>

<?php
	include('footer.php');
?>

<?php
	require_once('functions.php');
	if(!loggedin())
		header("Location: login.php");
	else
		include('header.php');
		include('dbinfo.php');
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
    <?php
    	if(isset($_GET['terror']))
          echo("<div class=\"alert alert-warning\">\nYour program exceeded the time limit. Maybe you should improve your algorithm.\n</div>");
        if(isset($_GET['cerror']))
          echo("<div class=\"alert alert-error\">\n<strong>The following errors occured:</strong><br/>\n<pre>\n".$_SESSION['cerror']."\n</pre>\n</div>");
        else if(isset($_GET['oerror']))
          echo("<div class=\"alert alert-error\">\nYour program output did not match the solution for the problem. Please check your program and try again.\n</div>");
        else if(isset($_GET['lerror']))
          echo("<div class=\"alert alert-error\">\nYou did not use one of the allowed languages. Please use a language that is allowed.\n</div>");
        else if(isset($_GET['serror']))
          echo("<div class=\"alert alert-error\">\nCould not connect to the compiler server. Please contact the admin to solve the problem.\n</div>");
        else if(isset($_GET['derror']))
          echo("<div class=\"alert alert-error\">\nPlease enter all the details asked before you can continue!\n</div>");
        else if(isset($_GET['ferror']))
          echo("<div class=\"alert alert-error\">\nPlease enter a legal filename.\n</div>");
          
        $query = "SELECT status FROM users WHERE username='".$_SESSION['username']."'";
        $result = mysqli_query($con,$query);
        $status = mysqli_fetch_array($result);
        if($status['status'] == 0)
          echo("<div class=\"alert alert-error\">\nYou have been banned. You cannot submit a solution.\n</div>");
        $query = "SELECT probid,user1,user2,invite FROM game WHERE gameid='".$_GET['gameid']."'";
        $result = mysqli_query($con,$query);
        $results = mysqli_fetch_array($result);
        if($results)
        {
            $probid=$results['probid'];
            $user1=$results['user1'];
            $user2=$results['user2'];
            $invite=$results['invite'];
        }
      ?>
    <h1>BattleCode Match:<?php echo $user1." v/s ".$user2; ?><small> ( <?php echo $_GET['gameid']; ?> )</small></h1>
    <h2>Problem: </h2>
      <?php
        // display the problem statement
      	if(isset($probid) and is_numeric($probid)) {
      		$query = "SELECT * FROM problems WHERE sl='".$probid."'";
          	$result = mysqli_query($con,$query);
          	$row = mysqli_fetch_array($result);
      	
		echo("<hr/>\n<h1>".$row['name']."</h1>\n");
		echo($row['text']);
      ?>
      <br/><span class="label label-info">Time Limit: <?php echo($row['time']/1000); ?> seconds</span>
      <hr/>
      <form method="post" action="eval.php">
          
      <input type="hidden" name="gameid" value="<?php echo $_GET['gameid'];?>"/>
      <input type="hidden" name="id" value="<?php if(is_numeric($probid)) echo($probid);?>"/>
      <input type="hidden" name="lang" id="hlang" value="<?php echo($fields['lang']);?>"/>
      <div class="btn-group">
        <div id="blank"></div>
        <a id="lang" class="btn dropdown-toggle" data-toggle="dropdown" href="#">Language: 
        <?php
        if($fields['lang']=='') echo('Python');
        else if($fields['lang']=='python') echo('Python');
          else if($fields['lang']=='c') echo('C');
          else if($fields['lang']=='java') echo('Java');
        ?>
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#" onclick="changeLang('Python');">Python</a></li>
          <li><a href="#" onclick="changeLang('C');">C</a></li>
          <li><a href="#" onclick="changeLang('Java');">Java</a></li>
        </ul>
      </div>
      <br/>Type your program below:<br/><br/>
      <textarea style="font-family: mono; height:400px;" class="span9" name="soln" id="text"></textarea><br/>
     
          
      <?php if($status['status'] == 1) echo("<input type=\"submit\" value=\"Run\" class=\"btn btn-primary btn-large\"/>");
            else echo("<input type=\"submit\" value=\"Run\" class=\"btn disabled btn-large\" disabled=\"disabled\"/>");
      ?>
      </form>
      <?php
	}
      ?>
    </div> <!-- /container -->
    
    <script language="javascript">
        $('#hlang').val('python');
      function changeLang(lang) {
        $('#lang').remove();
        $('#blank').after('<a id="lang" class="btn dropdown-toggle" data-toggle="dropdown" href="#">Language: ' + lang + ' <span class="caret"></span></a>');
        if(lang == 'C')
          $('#hlang').val('c');
        else if(lang== 'Java')
          $('#hlang').val('java');
        else if(lang== 'Python')
          $('#hlang').val('python');
      }
    </script>
    <script>
                setInterval(ajaxCall, 5000);
                
                function ajaxCall() {
                   $.ajax({
                    type: "GET",
                    url: 'chkgame.php?invite='+'<?php echo $invite; ?>',
                    success: function(data){
                        var obj = JSON.parse(data);
                		if (obj["status"] == "finish")
                		{
                			console.log(obj["status"]);
                			window.location = 'end.php?gameid='+obj["gameid"];
                		}
                    }
                	});
                
                }
    </script>
<?php
	include('footer.php');
?>

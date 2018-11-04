<?php
	require_once('functions.php');
	if(!loggedin())
		header("Location: login.php");
	else if(!isset($_GET['invite']))
	{
		include('dbinfo.php');
		$gameid = randomAlphaNum(5);
        $invite=rand(10000,99999);
        //$probid=rand(1,9);
        $probid=1;
        $status="created";
        $sql="INSERT INTO `game` ( `gameid` , `invite` , `probid` , `user1` , `status` ) VALUES ('".$gameid."', '$invite', '$probid', '".$_SESSION['username']."', '".$status."')";
		$result = mysqli_query($con, $sql);
		header("Location: create.php?invite=".$invite."");
	}
	else
	    include('header.php');
		include('dbinfo.php');
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

    <div class="container">Get ready for the battle with friend,Share the invite code with your friend!<br/><br/>
      <ul class="nav nav-list">
        <li class="nav-header" style="font-size: 20px">
            <?php
            	if(isset($_GET['invite']))
            	{
                  echo "Here is your invite code: ".$_GET['invite'] ;
            	}
        
            ?>
            <script>
                setInterval(ajaxCall, 10000);
                
                function ajaxCall() {
                   $.ajax({
                    type: "GET",
                    url: 'chkgame.php?invite='+'<?php echo $_GET['invite']; ?>',
                    success: function(data){
                        var obj = JSON.parse(data);
                		if (obj["status"] == "running")
                		{
                			console.log(obj["status"]);
                			window.location = 'solve.php?gameid='+obj["gameid"];
                		}
                    }
                	});
                
                }
            </script>
            
        </li>
        <li class="nav-header" style="font-size: 14px;">Waiting for player to join <img src="images/Loading_icon.gif" width="6%"></li>
      </ul>
      <br><br>
      
      <br/>
      <script type="text/javascript">
        $("document").ready(function(){
          $(".js-ajax-php-json").submit(function(){
            var data = {
              "action": "test"
            };
            data = $(this).serialize() + "&" + $.param(data);
            $.ajax({
              type: "POST",
              dataType: "json",
              url: "chkgame.php", //Relative or absolute path to response.php file
              data: data,
              success: function(data) {
                $(".the-return").html(
                  "Favorite beverage: " + data["favorite_beverage"] + "<br />Favorite restaurant: " + data["favorite_restaurant"] + "<br />Gender: " + data["gender"] + "<br />JSON: " + data["json"]
                );
        
                alert("Form submitted successfully.\nReturned json: " + data["json"]);
              }
            });
            return false;
          });
        });
      </script>
      
    </div>

<?php
	include('footer.php');
?>

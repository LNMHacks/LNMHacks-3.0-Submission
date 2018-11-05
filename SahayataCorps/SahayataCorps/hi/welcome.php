
      <!DOCTYPE html>
<html>
    <head>
      <title>Results</title>
      <!-- bootstrap-css -->
      <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
      <!--// bootstrap-css -->
      <!-- css -->
      <link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
      <!--// css -->
      <!-- font-awesome icons -->
      <link href="css/font-awesome.css" rel="stylesheet"> 
      <!-- //font-awesome icons -->
      <!-- font -->
      <link href="//fonts.googleapis.com/css?family=Righteous&subset=latin-ext" rel="stylesheet">
      <link href='//fonts.googleapis.com/css?family=Roboto+Condensed:400,700italic,700,400italic,300italic,300' rel='stylesheet' type='text/css'>
      <!-- //font -->
      <script src="js/jquery-1.11.1.min.js"></script>
      <script src="js/bootstrap.js"></script>
      <script src="js/SmoothScroll.min.js"></script>
    </head>
    <body>
     
     <!-- header -->
      <div class="site-header" style="background-color:#00081c; padding:10px 0px;">
            <div class="container">
              <a href="index.html" class="branding">
                <img src="images/praLogo.png" alt="logo" class="logo" width="80" height="65">
                <div class="logo-type">
                  <h1 class="site-title">Kisahan Seva</h1>
                  <small class="site-description">Your happiness helps us grow!</small>
                </div>
              </a>

              <!-- Default snippet for navigation -->
              <div class="main-navigation" style="margin-top:20px;">
                <button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
                <ul class="menu">
                  <li class="menu-item current-menu-item"><a href="index.html">Home</a></li>
                  <li class="menu-item"><a href="camera.html">Camera</a></li>
                  <li class="menu-item"><a href="upload1.html">Upload</a></li>
                  <li class="menu-item"><a href="library.html">Library</a></li>
                  <li class="menu-item"><a href="hi/index.html">Language</a></li>
                </ul> <!-- .menu -->
              </div> <!-- .main-navigation -->

              <div class="mobile-navigation"></div>
            </div>
      </div>

    <?php $val = $_POST["name"]; 
         $type =(string) $_POST["crop"];
     $file = (string)mt_rand(1,1000);
     $ext = ".png";
     $output_file = $file.$ext;
     
     $ifp = fopen( $output_file, 'wb' ); 

    // split the string on commas
    // $data[ 0 ] == "data:image/png;base64"
    // $data[ 1 ] == <actual base64 string>
    $data = explode( ',', $val );

    // saving image
    fwrite( $ifp, base64_decode( $data[ 1 ] ) );

    // clean up the file resource
    fclose( $ifp ); 
    //Calling Python Script
    if($type == "Apple" || $type == "Tomato"){// Using My Algorithm
        
        //$arr = exec("D:\home\python364x64\python TestApple.py $output_file $type");
        $arr = exec("C:\Users\hp\Anaconda3\python TestApple.py $output_file $type");
        
    }
      else{ // Using Azure Api
    $connectionInfo = array("UID" => "test@plantix", "pwd" => "Adarsh@123", "Database" => "jecrcHackathon", "LoginTimeout" => 30, "Encrypt" => 1, "TrustServerCertificate" => 0);
    $serverName = "tcp:plantix.database.windows.net,1433";
    $conn = sqlsrv_connect($serverName, $connectionInfo);
    $tsql= "select url from url where crop ='".$type."'";
    $getResults= sqlsrv_query($conn, $tsql);

    if ($getResults == FALSE)
        echo (sqlsrv_errors());
    while ($row = sqlsrv_fetch_array($getResults, SQLSRV_FETCH_ASSOC)) {
     
      $urlval = $row['url'];
    }

    $arr =  exec("python download.py $output_file $urlval");
  sqlsrv_free_stmt($getResults);}
    $arr2 = substr($arr,1,strlen($arr)-2);
    
    
        $aryy = explode( ',', $arr2 );
        $name1 = $aryy[0];
        $prob1 = $aryy[1];
        $name2 = $aryy[2];
        $prob2 = $aryy[3];
        $new=substr($aryy[2], 1);
        $img1 = $aryy[0].'.jpg';
        $img2 = 'di/'.$new.'.jpg';
        

        ?>
        <div class="row container-fluid" style= "padding: 15px" >
            <br>
     <div class="col-lg-6 text-center">
      <img src="di/<?php echo $img1?>">
      <br>
      <?php echo $name1?>
      <br>
      <?php echo $prob1?>%
      <a href="<?php echo $name1?>.html">
      <br>
      Know More...
      </a>
     </div>             
      
     <div class="col-lg-6 text-center">
      <img src="<?php echo $img2?>">
      <br>
      <?php echo $name2?>
      <br>
      <?php echo $prob2?>%
      <a href="<?php echo $name2?>.html">
     <br>
      Know More....
      </a>
     </div> 
   </div>
    <!-- footer -->
  <div class="footer">
    <div class="container">
      <div class="agileinfo_footer_grids">
        <div class="col-md-4 agileinfo_footer_grid">
          <div class="agile-logo">
            <h4><a href="index.html">Kisahan<span>Seva</span></a></h4>
          </div>
          <p>We provide technology to detect crop diseases and measures to prevent them from spreading.</p>
          
        <div class="clearfix"> </div>
      </div>
    </div>
  </div>
  <!-- //footer -->
  <script type="text/javascript" src="js/move-top.js"></script>
  <script type="text/javascript" src="js/easing.js"></script>
  <script src="js/jquery-1.11.1.min.js"></script>
  <script src="js/plugins.js"></script>
  <script src="js/app.js"></script>

    </body>
</html>
<?php
$connect = new PDO('mysql:host=localhost;port=3307;dbname=project','fred', 'zap');
// See the "errors" folder for details...
$connect->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

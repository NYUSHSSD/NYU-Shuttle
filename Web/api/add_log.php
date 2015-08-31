<?php

include 'db_helper.php';

$status = $_GET['status'];
$route =  $_GET['route'];
$driver =  $_GET['driver'];
$bus =  $_GET['bus'];

$sql = "INSERT INTO logs(status,route,driver,bus) VALUES (?,?,?,?)";


$rst = db_helper::query($sql , array($status,$route,$driver,$bus));
header('Content-Type: application/json');
if (!is_null( $rst))
{
   $rst =  "Log updated successfully";
   print(json_encode($rst));
}

//mysqli_close($con);
?>
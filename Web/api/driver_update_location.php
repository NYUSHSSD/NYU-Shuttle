<?php
include 'db_helper.php';

$route = $_GET['route'];
$longitude = $_GET['longitude'];
$latitude =$_GET['latitude'];
$created_at = date('Y-m-d H:i:s');

$sql="INSERT INTO drivers_location (route, latitude, longitude, created_at) VALUES (?,?,?,?)";

$rst = db_helper::query($sql , array($route,$latitude,$longitude,$created_at));
 header('Content-Type: application/json');  
if (!is_null( $rst))
{
    $rst =  "OK";
    print(json_encode($rst));
}

?>

<?php
include "config.php";

// Create connection
$con=mysqli_connect($db_server,$db_user,$db_pass,$db_database);

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$route = mysqli_real_escape_string($con, $_GET['route']);
$longitude = mysqli_real_escape_string($con, $_GET['longitude']);
$latitude = mysqli_real_escape_string($con, $_GET['latitude']);
$created_at = date('Y-m-d H:i:s');

$sql="INSERT INTO drivers_location (route, latitude, longitude, created_at) VALUES ('$route', '$latitude', '$longitude', '$created_at')";
if (mysqli_query($con,$sql))
{
   echo "OK";
}

mysqli_close($con);
?>

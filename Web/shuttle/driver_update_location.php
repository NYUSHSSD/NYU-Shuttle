<?php
include "config.php";

// Create connection
$con=mysqli_connect($db_server,$db_user,$db_pass,$db_database);

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$route = $_GET['route'];
$longitude = $_GET['longitude'];
$latitude = $_GET['latitude'];

$sql="UPDATE drivers_location SET latitude='$latitude', longitude='$longitude' WHERE route='$route'";
if (mysqli_query($con,$sql))
{
   echo "Values have been inserted successfully";
}

mysqli_close($con);
?>
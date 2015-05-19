<?php
include "config.php";

// Create connection
$con=mysqli_connect($db_server,$db_user,$db_pass,$db_database);

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$longitude = $_GET['longitude'];
$latitude = $_GET['latitude'];
$place = $_GET['place'];
$category = $_GET['category'];
$description = $_GET['description'];

$sql="INSERT into places VALUES('$latitude', '$longitude', '$place', '$category', '$description')";
if (mysqli_query($con,$sql))
{
   echo "Values have been inserted successfully";
}

mysqli_close($con);
?>
<?php
$con=mysqli_connect("db4free.net","nyushbus","busatnyush","shnyubus");
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$status = $_GET['status'];
$route = $_GET['route'];
$driver = $_GET['driver'];
$bus = $_GET['bus'];

$sql="INSERT INTO logs(status,route,driver,bus) VALUES ('$status','$route','$driver','$bus')";
if (mysqli_query($con,$sql))
{
   echo "Log updated successfully";
}

mysqli_close($con);
?>
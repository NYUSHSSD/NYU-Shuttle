<?php
include "config.php";

// Create connection
$con=mysqli_connect($db_server,$db_user,$db_pass,$db_database);

// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}


// escape variables for security
$msgName = mysqli_real_escape_string($con, $_POST['msgName']);
$msgNetid = mysqli_real_escape_string($con, $_POST['msgNetid']);
$msgMessage = mysqli_real_escape_string($con, $_POST['msgMessage']);


$sql="INSERT INTO feedback (name,netid,message)
VALUES ('$msgName','$msgNetid','$msgMessage')";

if (!mysqli_query($con,$sql)) {
  echo "<script type='text/javascript'>alert('Could not send message :('); window.history.back();</script>";
  die('Error: ' . mysqli_error($con));
}
else
{
 echo "<script type='text/javascript'>alert('Message Sent!'); window.history.back();</script>";
}
mysqli_close($con);
?>

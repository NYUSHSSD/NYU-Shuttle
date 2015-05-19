<?php
include "config.php";

// Create connection
$con=mysqli_connect($db_server,$db_user,$db_pass,$db_database);

// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}


// escape variables for security
$zzid = mysqli_real_escape_string($con, $_POST['zzid']);
$zzname = mysqli_real_escape_string($con, $_POST['zzname']);
$zzphone = mysqli_real_escape_string($con, $_POST['zzphone']);


$sql="INSERT INTO drivers (id,name,phone)
VALUES ('$zzid','$zzname','$zzphone')";

if (!mysqli_query($con,$sql)) {
  //echo "no record added";
  //die('Error: ' . mysqli_error($con));
  mysqli_close($con);
  header('Location: http://nyushapp.comli.com/shuttle/error2.htm');

}
else
{
// echo "1 record added";
mysqli_close($con);
header('Location: http://nyushapp.comli.com/shuttle/success2.htm');
}
?>

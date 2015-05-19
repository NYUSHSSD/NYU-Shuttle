<?php
include "config.php";

// Create connection
$con=mysqli_connect($db_server,$db_user,$db_pass,$db_database);

// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}


// escape variables for security
$zzroute = mysqli_real_escape_string($con, $_POST['zzroute']);
$zzfrom = mysqli_real_escape_string($con, $_POST['zzfrom']);
$zzto = mysqli_real_escape_string($con, $_POST['zzto']);
$zzdeparture= mysqli_real_escape_string($con, $_POST['zzdeparture']);
$zzarrival = mysqli_real_escape_string($con, $_POST['zzarrival']);

if (isset($_POST['zzmonday'])) {
    $zzmonday=1;}
else {$zzmonday=0;}
if (isset($_POST['zztuesday'])) {
    $zztuesday=1;}
else {$zztuesday=0;}
if (isset($_POST['zzwednesday'])) {
    $zzwednesday=1;}
else {$zzwednesday=0;}
if (isset($_POST['zzthursday'])) {
    $zzthursday=1;}
else {$zzthursday=0;}
if (isset($_POST['zzfriday'])) {
    $zzfriday=1;}
else {$zzfriday=0;}
if (isset($_POST['zzsaturday'])) {
    $zzsaturday=1;}
else {$zzsaturday=0;}
if (isset($_POST['zzsunday'])) {
    $zzsunday=1;}
else {$zzsunday=0;}


$sql="INSERT INTO schedule (going_from, going_to, departure, arrival, route, monday, tuesday, wednesday, thursday, friday, saturday, sunday, holiday)
VALUES ('$zzfrom', '$zzto', '$zzdeparture', '$zzarrival', '$zzroute', '$zzmonday', '$zztuesday', '$zzwednesday', '$zzthursday', '$zzfriday', '$zzsaturday', '$zzsunday',  false)";

if (!mysqli_query($con,$sql)) {
  //echo "no record added";
  //die('Error: ' . mysqli_error($con));
  mysqli_close($con);
  header('Location: http://nyushapp.comli.com/shuttle/error.htm');

}
else
{
// echo "1 record added";
mysqli_close($con);
header('Location: http://nyushapp.comli.com/shuttle/success.htm');
}
?>
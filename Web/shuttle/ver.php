<?php
include "config.php";

// Create connection
$con=mysqli_connect($db_server,$db_user,$db_pass,$db_database);

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$dir    = '/var/www/html/apks';
$files = scandir($dir);
$shuttle_ver = 0;
$driver_ver = 0;
//print_r($files);
for ($i=0; $i<count($files); $i++)
{
	if(strpos($files[$i], 'shuttle') !== false)
	{
		$pieces = explode("-",$files[$i]);
		$my_ver = explode(".",$pieces[1]);
		if (intval($my_ver[0])>$shuttle_ver)
		{
			$shuttle_ver=intval($my_ver[0]);
		}
	}
	else if(strpos($files[$i], 'driver') !== false)
	{
		$pieces = explode("-",$files[$i]);
		$my_ver = explode(".",$pieces[1]);
		if (intval($my_ver[0])>$driver_ver)
		{
			$driver_ver=intval($my_ver[0]);
		}
	}
}


$sql="UPDATE version SET user_ver='$shuttle_ver', driver_ver='$driver_ver'";
if (mysqli_query($con,$sql))
{
   echo "Updated versions successfully";
}

mysqli_close($con);

?>
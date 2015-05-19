<?php

include "config.php";

$con = mysql_connect($db_server,$db_user,$db_pass) or die("connection failed");

mysql_select_db($db_database,$con) or die("db selection failed");
 
$sql = "
    SELECT dl1.route,dl1.latitude,dl1.longitude FROM `drivers_location` dl1
    LEFT JOIN `drivers_location` dl2 ON dl1.route=dl2.route AND dl1.created_at < dl2.created_at
    WHERE dl2.id IS NULL
";

$r = mysql_query($sql, $con);

$flag=array();

while($row=mysql_fetch_array($r))
{
	$flag[]=$row;
}
 
print(json_encode($flag));
mysql_close($con);
?>

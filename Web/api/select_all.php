<?php
include "config.php";

$con = mysql_connect($db_server,$db_user,$db_pass) or die("connection failed");
	mysql_select_db($db_database,$con) or die("db selection failed");
	 
	$r=mysql_query("select * from schedule",$con);

	$flag=array();

	while($row=mysql_fetch_array($r))
	{
		$flag[]=$row;
	}
	 
	print(json_encode($flag));
	mysql_close($con);
?>
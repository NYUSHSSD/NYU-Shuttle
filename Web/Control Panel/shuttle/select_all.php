<?php

$host = "db4free.net";
$db = "shnyubus";
$uname ="nyushbus";
$pwd = "busatnyush";
$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$r=mysql_query("select * from schedule",$con);

	$flag=array();

	while($row=mysql_fetch_array($r))
	{
		$flag[]=$row;
	}
	 
	print(json_encode($flag));
	mysql_close($con);
?>
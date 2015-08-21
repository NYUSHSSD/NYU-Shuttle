<?php
include 'db_helper.php';

$sql = "select * from schedule";
$rst = db_helper::query($sql);
	$flag=array();
if ($rst)
{
   foreach($rst as $row){
       $flag[]=$row;
   }
}

print(json_encode($flag));

?>
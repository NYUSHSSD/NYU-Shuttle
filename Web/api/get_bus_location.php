<?php
include 'db_helper.php';

$sql = " SELECT   dl1.route,dl1.latitude,dl1.longitude FROM
 `drivers_location` dl1 WHERE  dl1.id IN (
  SELECT MAX(id) FROM drivers_location  GROUP BY route ORDER BY id DESC 
 )
";
$rst = db_helper::query($sql);
$flag=array();
if ($rst)
{
   foreach($rst as $row){
       $flag[]=$row;
   }
}

print(json_encode($flag));
//mysql_close($con);
?>

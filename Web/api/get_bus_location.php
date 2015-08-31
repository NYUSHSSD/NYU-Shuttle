<?php
include 'db_helper.php';

$ids = db_helper::query('SELECT MAX(id) AS id FROM drivers_location  GROUP BY route ORDER BY id DESC ');
$id_array = array();
if($ids){
    foreach($ids as $id){
        $id_array[] = $id['id'];
    }
}
$sql = " SELECT   dl1.route,dl1.latitude,dl1.longitude FROM
 `drivers_location` dl1 WHERE  dl1.id IN (
 " . implode(',', $id_array) . "
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
header('Content-Type: application/json');  
print(json_encode($flag));
//mysql_close($con);
?>

<?php
include 'db_helper.php';

$sql = "select * from schedule";
$rst = db_helper::query($sql);

$routes = array();
if ($rst) {
    foreach($rst as $row) {
        $route = $row['route'];
        $route = explode("-", $route);
        $route = $route[0];

        $routes[$route][] = $row;
    }
}


header('Content-Type: application/json');  
header("Access-Control-Allow-Origin: *");

print(json_encode($routes));

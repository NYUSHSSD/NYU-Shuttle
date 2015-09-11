<?php
include 'db_helper.php';

$result = db_helper::query("select * from schedule");

$routes = array();
if ($result) {
    foreach($result as $row) {
        $route = $row['route'];
        $route = explode("-", $route);
        $route = $route[0];

        if (!isset($routes[$route])) {
            $routes[$route] = array(
                'code'      => $route,
                'schedules' => array(),
                'from'      => $row['going_from'],
                'to'        => $row['going_to'],
            );
        }

        $routes[$route]['schedules'][] = array(
            'id'        => $row['route'],
            'departure' => $row['departure'],
            'arrival'   => $row['arrival'],
            'weekdays'  => array(
                filter_var($row['sunday'], FILTER_VALIDATE_BOOLEAN),
                filter_var($row['monday'], FILTER_VALIDATE_BOOLEAN),
                filter_var($row['tuesday'], FILTER_VALIDATE_BOOLEAN),
                filter_var($row['wednesday'], FILTER_VALIDATE_BOOLEAN),
                filter_var($row['thursday'], FILTER_VALIDATE_BOOLEAN),
                filter_var($row['friday'], FILTER_VALIDATE_BOOLEAN),
                filter_var($row['saturday'], FILTER_VALIDATE_BOOLEAN),
            )
        );
    }
}


header('Content-Type: application/json');  
header("Access-Control-Allow-Origin: *");

print(json_encode($routes));

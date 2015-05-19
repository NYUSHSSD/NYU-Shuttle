<?php
include "config.php";

$driver_name1 = "--";
$driver_name2 = "--";
$driver_phone1 = "--";
$driver_phone2 = "--";

$conn = new mysqli($db_server,$db_user,$db_pass,$db_database);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT drivers.name, drivers.phone, logs.bus FROM drivers, logs WHERE drivers.id=(SELECT logs.driver FROM logs WHERE logs.route='A' AND logs.time=(SELECT max(logs.time) FROM logs WHERE logs.route='A' AND logs.status='started'))";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
	$driver_name1 = $row["name"];
	$driver_phone1 = $row["phone"];
	}
} 

$conn->close();

$conn = new mysqli($db_server,$db_user,$db_pass,$db_database);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT drivers.name, drivers.phone, logs.bus FROM drivers, logs WHERE drivers.id=(SELECT logs.driver FROM logs WHERE logs.route='BC' AND logs.time=(SELECT max(logs.time) FROM logs WHERE logs.route='BC' AND logs.status='started'))";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
	$driver_name2 = $row["name"];
	$driver_phone2 = $row["phone"];
	}
} 

$conn->close();


echo "#";
echo "$driver_name1";
echo "#";
echo "$driver_phone1";
echo "#";
echo "$driver_name2";
echo "#";
echo "$driver_phone2";
echo "#";

?> 

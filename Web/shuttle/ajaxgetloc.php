<?php
include "config.php";

$lat1 = 0;
$lng1 = 0;
$lat2 = 0;
$lng2 = 0;
$lat3 = 0;
$lng3 = 0;


// Create connection
$conn = new mysqli($db_server,$db_user,$db_pass,$db_database);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT latitude, longitude FROM drivers_location";


$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
	if ($lat1 == 0)
	{
		$lat1 = $row["latitude"];
		$lng1 = $row["longitude"];
	}
	else if ($lat2 == 0)
	{
		$lat2 = $row["latitude"];
		$lng2 = $row["longitude"];
	}
	else
	{
		$lat3 = $row["latitude"];
		$lng3 = $row["longitude"];
	} 
    }
} 

echo "g";
echo "$lat1";
echo "g";
echo "$lng1";
echo "g";
echo "$lat2";
echo "g";
echo "$lng2";
echo "g";
echo "$lat3";
echo "g";
echo "$lng3";
echo "g";

$conn->close();


?> 

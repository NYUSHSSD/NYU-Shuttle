<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>View Logs</title>
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<!--[if IE]>
  		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>
	<body>
		<div class="container">
			<!-- Top Navigation -->
			<?php
	include "navigation.php";
?>

			<div class="component">
				<h2>Logs</h2>
				<table>
					<thead>
						<tr>
							<th>Status</th>
							<th>Route</th>
							<th>Driver</th>
							<th>Bus</th>
							<th>Time</th>
						</tr>
					</thead>
					<tbody>
						

<?php
include "config.php";

// Create connection
$conn = new mysqli($db_server,$db_user,$db_pass,$db_database);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT logs.status,logs.route,drivers.name,logs.bus,logs.time FROM logs JOIN drivers ON logs.driver=drivers.id ORDER BY time DESC";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        echo "<tr><td>" . htmlspecialchars($row["status"])
                ."</td><td>" . htmlspecialchars($row["route"])
                ."</td><td>" . htmlspecialchars($row["name"])
                ."</td><td>" . htmlspecialchars($row["bus"])
                ."</td><td>" . htmlspecialchars($row["time"]) ."</td></tr>";
    }
} else {
    echo ":(";
}
$conn->close();
?> 





											</tbody>
				</table>
						</div><!-- /container -->
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
		<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-throttle-debounce/1.1/jquery.ba-throttle-debounce.min.js"></script>
		<script src="js/jquery.stickyheader.js"></script>
	</body>
</html>

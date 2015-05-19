<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>View Routes</title>
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
				<h2>Existing Routes</h2>
				<table>
					<thead>
						<tr>
							<th>Route</th>
							<th>From</th>
							<th>To</th>
							<th>Departure</th>
							<th>Arrival</th>
							<th>M</th>
							<th>T</th>
							<th>W</th>
							<th>T</th>
							<th>F</th>
							<th>S</th>
							<th>S</th>


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

$sql = "SELECT route, going_from, going_to, departure, arrival, monday, tuesday, wednesday, thursday, friday, saturday, sunday FROM schedule";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        echo "<tr><td>".$row["route"]."</td><td>".$row["going_from"]."</td><td>".$row["going_to"]."</td><td>".$row["departure"]."</td><td>".$row["arrival"]."</td><td>".$row["monday"]."</td><td>".$row["tuesday"]."</td><td>".$row["wednesday"]."</td><td>".$row["thursday"]."</td><td>".$row["friday"]."</td><td>".$row["saturday"]."</td>
<td>".$row["sunday"]."</td></tr>";
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
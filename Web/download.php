<!DOCTYPE html>
<html lang="en">
<head>
  <title>NYU SHuttle | Download</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
  <link rel="stylesheet" href="css/style.css" type="text/css" media="all">
  <script type="text/javascript" src="js/jquery-1.4.2.min.js" ></script>
  <script type="text/javascript" src="js/cufon-yui.js"></script>
  <script type="text/javascript" src="js/Humanst521_BT_400.font.js"></script>
  <script type="text/javascript" src="js/Humanst521_Lt_BT_400.font.js"></script>
  <script type="text/javascript" src="js/cufon-replace.js"></script>
	<script type="text/javascript" src="js/roundabout.js"></script>
  <script type="text/javascript" src="js/roundabout_shapes.js"></script>
  <script type="text/javascript" src="js/gallery_init.js"></script>
  <!--[if lt IE 7]>
  	<link rel="stylesheet" href="css/ie/ie6.css" type="text/css" media="all">
  <![endif]-->
  <!--[if lt IE 9]>
  	<script type="text/javascript" src="js/html5.js"></script>
    <script type="text/javascript" src="js/IE9.js"></script>
  <![endif]-->
</head>

<body>
  <!-- header -->
<?php include 'header.php'; 

$dir    = '/var/www/html/apks';
$files = scandir($dir);
$shuttle_ver = 0;

for ($i=0; $i<count($files); $i++)
{
	if(strpos($files[$i], 'shuttle') !== false)
	{
		$pieces = explode("-",$files[$i]);
		$my_ver = explode(".",$pieces[1]);
		if (intval($my_ver[0])>$shuttle_ver)
		{
			$shuttle_ver=intval($my_ver[0]);
		}
	}
}

$file_name = "/apks/shuttle-" . $shuttle_ver . ".apk";

?>
  <!-- #gallery -->
  <section id="gallery">
  	<div class="container">
    	<ul id="myRoundabout">
      	<li><img src="images/slide3.jpg" alt=""></li>
        <li><img src="images/slide2.jpg" alt=""></li>
        <li><img src="images/slide5.jpg" alt=""></li>
        <li><img src="images/slide1.jpg" alt=""></li>
        <li><img src="images/slide4.jpg" alt=""></li>
      </ul>
  	</div>
  </section>
  <!-- /#gallery -->
  <div class="main-box">
    <div class="container">
      <div class="inside">
        <h2>Android <span>User App</span></h2>
        <p>If you would like to Beta test the user app and give us feedback on what we can do to improve it, then <a href="<?php echo $file_name; ?>">download it here!</a></p>
		<p>You can either download the APK file directly to your phone/ tablet, or through a computer and then transfer it via USB cable. All you need to do afterwards is tap on the downloaded file, and the classic installation process will begin. If you have any issues, contact us! We would be very glad to help.</p>
		<h2>iOS <span>User App</span></h2>
		<p>Coming Soon!</p>
		</div>
    </div>
  </div>
  <!-- footer -->
<?php include 'footer.php'; ?>
  <script type="text/javascript"> Cufon.now(); </script>
</body>
</html>

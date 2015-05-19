<!DOCTYPE html>
<html lang="en">
<head>
  <title>NYU SHuttle</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
  <link rel="stylesheet" href="css/style.css" type="text/css" media="all">
  <script type="text/javascript" src="js/jquery-1.4.2.min.js" ></script>
  <script type="text/javascript" src="js/cufon-yui.js"></script>
  <script type="text/javascript" src="js/Humanst521_BT_400.font.js"></script>
  <script type="text/javascript" src="js/Humanst521_Lt_BT_400.font.js"></script>
	<script type="text/javascript" src="js/roundabout.js"></script>
  <script type="text/javascript" src="js/roundabout_shapes.js"></script>
  <script type="text/javascript" src="js/gallery_init.js"></script>
  <script type="text/javascript" src="js/cufon-replace.js"></script>
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
<?php include 'header.php'; ?>
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
        <div class="wrapper">
        	<!-- aside -->
          <aside>
            <h2>Recent <span>News</span></h2>
            <!-- .news -->
            <ul class="news">
            	<li>
              	<figure><strong>27</strong>April</figure>
                <h3><a href="#">The Beta has started!</a></h3>
                A few users have been selected to Beta test the Android apps!<a href="#">...</a>
              </li>
              
            </ul>
            <!-- /.news -->
          </aside>
          <!-- content -->
          <section id="content">
            <article>
            	<h2>Welcome to <span>NYU Shanghai's Shuttle Bus Platform</span></h2>
              <p>Developed by NYU Shanghai's Program Management department's Student Software Developers, the Shuttle Bus Platform is a comprehensive tool aiming to considerably improve the Shuttle Bus experience.</p>
              <figure><a href="#"><img src="images/banner1.jpg" alt=""></a></figure>
              <p>The Platform currently consists of a User App, a Driver App, and a Web-based Command Panel that is only accessible by NYU's Public Safety.</p>
            </article> 
          </section>
        </div>
      </div>
    </div>
  </div>
  <!-- footer -->
<?php include 'footer.php'; ?>
  <script type="text/javascript"> Cufon.now(); </script>
</body>
</html>

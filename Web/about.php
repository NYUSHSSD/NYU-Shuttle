<!DOCTYPE html>
<html lang="en">
<head>
  <title>NYU SHuttle | About</title>
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
<?php include 'header.php'; ?>
<script>document.getElementById('about_page').className="current";</script>
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
            <h2>About <span>NYU SHuttle</span></h2>
            <div class="img-box">
            	<figure><img src="images/img1.jpg" alt=""></figure>
              We have created this platform so that NYU students can have a much better way of interacting with the Shuttle Buses.
            </div>
            <p>Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi.</p> 
            Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto.
          </aside>
          <!-- content -->
          <section id="content">
            <article>
            	<h2>Developer <span>Team</span></h2>
              <!-- .team-list -->
              <ul class="team-list">
              	<li>
                	<figure><img src="images/alex.png" width="73" height="101" alt=""></figure>
                  <h3><a href="#">Alexandru Grigoras</a></h3>
                  Lead Developer
                </li>
                <li>
                	<figure><img src="images/img3.jpg" alt=""></figure>
                  <h3><a href="#">Mercy Angela Nantongo</a></h3>
                 Researcher
                </li>
                <li>
                	<figure><img src="images/img4.jpg" alt=""></figure>
                  <h3><a href="#">Tristan Armitage</a></h3>
                  Resource Manager
                </li>
              </ul>
              <!-- /.team-list -->
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

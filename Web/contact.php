<!DOCTYPE html>
<html lang="en">
<head>
  <title>NYU SHuttle | Contact</title>
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
          
          <!-- content -->
          <section id="content">
            <article>
            	<h2>Contact <span>Us</span></h2>
              <form id="contacts-form" action="submit_feedback.php" method="post">
                <fieldset>
                  <div class="field">
                    <label>Name:</label>
                    <input type="text" name="msgName" value=""/>
                  </div>
                  <div class="field">
                    <label>NetID:</label>
                    <input type="text" name="msgNetid" value=""/>
                  </div>
                  <div class="field">
                    <label>Message:</label>
                    <textarea name="msgMessage">
					</textarea>
                  </div>
                  <div><input type="submit" class="button" value="Send" action="submit_feedback.php"/> </div>
                </fieldset>
              </form>
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

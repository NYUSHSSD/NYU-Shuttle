<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"????";}
		#allmap{height:500px;width:100%;}
		#r-result{width:100%;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=C5p1EpGiqabMnvBSINGG4zP7"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Live Map</title>
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<!--[if IE]>
  		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->


<style>
#wrapper {
    position: relative;
}
#wrapper .legend {
    position: absolute;
    top: 0;
    right: 40px;
    z-index: 999;
    list-style: none;
    background: white;
    padding: 10px 8px;
}
#wrapper .legend li {
    padding: 8px 5px;
}

#wrapper .legend li span {
    width: 30px;
    height: 18px;
    display: inline-block;
    margin-right: 6px;
}
#wrapper .legend li span.purple {
    background-color: purple;
}
#wrapper .legend li span.blue {
    background-color: blue;
}
</style>


<script>
function ajaxGetBusLocation() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
            }
        }
        xmlhttp.open("GET", "ajaxgetloc.php", true);
        xmlhttp.send();
    }

function ajaxGetDriverInfo() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("txtHint2").innerHTML = xmlhttp.responseText;
            }
        }
        xmlhttp.open("GET", "get_driver.php", true);
        xmlhttp.send();
    }

</script>

	</head>
	<body>
		<div class="container">
			<!-- Top Navigation -->
<?php
	include "navigation.php";
?>


<script>

var infoWindow1_status = 0;
var infoWindow2_status = 0;
var infoWindow3_status = 0;

var driver_name1 = "John Smith";
var driver_phone1 = "0000000000"
var driver_name2 = "Mike Johnson";
var driver_phone2 = "0000000000"



function jsfocusAB()
{
	var point = new BMap.Point(121.541163, 31.232451);
	map.centerAndZoom(point, 15);
	displaylabel1();
}

function jsfocus268()
{
	var point = new BMap.Point(121.545489, 31.236739);
	map.centerAndZoom(point, 15);
	displaylabel2();
}

function jsfocusGP()
{
	var point = new BMap.Point(121.538321,31.214594);
	map.centerAndZoom(point, 15);
	displaylabel3();
}

function jsfocusbus1()
{
	var point = new BMap.Point(busPoint.lng,busPoint.lat);
	map.centerAndZoom(point, 15);
	displaylabelBus();
}

function jsfocusbus2()
{
	var point = new BMap.Point(busPoint2.lng,busPoint2.lat);
	map.centerAndZoom(point, 15);
	displaylabelBus2();
}



</script>

<div id="txtHint" style="display: none;"></div>
<div id="txtHint2" style="display: none;"></div>
<!--
<b>Go to a place: </b>
<input id="ABfocus" type="button" value="Academic Building" onclick="jsfocusAB();" />
<input id="268focus" type="button" value="Motel 268" onclick="jsfocus268();" />
<input id="GPfocus" type="button" value="Grand Pujian" onclick="jsfocusGP();" />
<BR>
<b>Find a bus: </b>
<input id="bus1focus" type="button" value="Route A - Bus 1" onclick="jsfocusbus1();" />
<input id="bus2focus" type="button" value="Route BC - Bus 1" onclick="jsfocusbus2();" />
-->





    <div id="wrapper">
        <ul class="legend">
            <li>
                <span class="purple">&nbsp;</span><a href="#" onclick="jsfocusbus1();">Route A</a>
 - <a href="#" onclick="jsfocusAB();">Academic Building</a> to <a href="#" onclick="jsfocus268();">Motel 268</a>
            </li>
            <li>
                <span class="blue">&nbsp;</span><a href="#" onclick="jsfocusbus2();">Route B</a> - <a href="#" onclick="jsfocusAB();">Academic Building</a> to <a href="#" onclick="jsfocusGP();">Grand Pujian</a>
            </li>
            <li>
                <span class="blue">&nbsp;</span><a href="#" onclick="jsfocusbus2();">Route C</a> - <a href="#" onclick="jsfocusGP();">Grand Pujian</a> to <a href="#" onclick="jsfocusAB();">Academic Building</a>
            </li>
        </ul>
        <div id="allmap"></div>
    </div>

	<script type="text/javascript">
	// ????API??
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(121.544007, 31.231911);
	map.centerAndZoom(point, 15);
	
var purpleIcon = new BMap.Icon("map_marker.png", new BMap.Size(22,22));

var busIcon = new BMap.Icon("busIcon.png", new BMap.Size(25,25));

var markerBus = new BMap.Marker(new BMap.Point(0,0),{icon:busIcon});

var markerBus2 = new BMap.Marker(new BMap.Point(0,0),{icon:busIcon});


	var marker = new BMap.Marker(new BMap.Point(121.541163, 31.232451),{icon:purpleIcon});
var marker2 = new BMap.Marker(new BMap.Point(121.545489, 31.236739),{icon:purpleIcon}); 
var marker3 = new BMap.Marker(new BMap.Point(121.538321,31.214594),{icon:purpleIcon}); 

var IWoptions = { width : 300, height : 180 }

    var infoWindows = [
        new BMap.InfoWindow(generateMakerInfoWindowContent("Academic Building", "nyu.jpg", "NYU Shanghai exemplifies the highest ideals of contemporary higher education by uniting the intellectual resources of New York University’s global network with the multidimensional richness of China."),IWoptions),
        new BMap.InfoWindow(generateMakerInfoWindowContent("Motel 268", "268.jpg", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. "),IWoptions),
        new BMap.InfoWindow(generateMakerInfoWindowContent("Grand Pujian", "GPJ.jpg", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. "),IWoptions)
    ];

    // cache all the images, so that the info window will have the right height
    var cachedImages = [
        new Image('nyu.jpg'),
        new Image('268.jpg'),
        new Image('GPJ.jpg')
    ];

var busPoint;
var busPoint2;

	var divv = document.getElementById("txtHint");
	var divv2 = document.getElementById("txtHint2");
    
	
	var polyline = new BMap.Polyline([
		new BMap.Point(121.541163, 31.232451),
		new BMap.Point(121.542902, 31.231479),
		new BMap.Point(121.543027, 31.23156),
		new BMap.Point(121.544007, 31.231911),
		new BMap.Point(121.544105, 31.231973),
		new BMap.Point(121.542646, 31.235025),
		new BMap.Point(121.542996, 31.235203),
		new BMap.Point(121.545489, 31.236739)
	], {strokeColor:"purple", strokeWeight:5, strokeOpacity:0.5});   //????

	var polyline2 = new BMap.Polyline([
		new BMap.Point(121.54058,31.232794),
		new BMap.Point(121.539974,31.233115),
		new BMap.Point(121.539516,31.232532),
		new BMap.Point(121.538936,31.231775),
		new BMap.Point(121.541887,31.230116),
		new BMap.Point(121.541784,31.229988),
		new BMap.Point(121.544537,31.228437),
		new BMap.Point(121.543792,31.227545),
new BMap.Point(121.543244,31.226846),
new BMap.Point(121.54275,31.225542),
new BMap.Point(121.542354,31.224388),
new BMap.Point(121.541371,31.221948),
new BMap.Point(121.540872,31.220632),
new BMap.Point(121.540221,31.218925),
new BMap.Point(121.539507,31.217123),
new BMap.Point(121.539116,31.216235),
new BMap.Point(121.539062,31.216),
new BMap.Point(121.538815,31.215783),
new BMap.Point(121.538554,31.215165),
new BMap.Point(121.538321,31.214594)

	], {strokeColor:"blue", strokeWeight:5, strokeOpacity:0.5});   //????

	var polyline3 = new BMap.Polyline([
		new BMap.Point(121.536973,31.213868),
		new BMap.Point(121.532437,31.213969),
		new BMap.Point(121.532671,31.214424),
		new BMap.Point(121.533955,31.216779),
		new BMap.Point(121.53488,31.218501),
		new BMap.Point(121.535159,31.219149),
		new BMap.Point(121.535689,31.220578),
		new BMap.Point(121.535985,31.222477),
new BMap.Point(121.535824,31.22504),
new BMap.Point(121.535312,31.2267),
new BMap.Point(121.537099,31.227379),
new BMap.Point(121.540198,31.228506),
new BMap.Point(121.540701,31.228838),
new BMap.Point(121.541375,31.229475),
new BMap.Point(121.541878,31.230131),
new BMap.Point(121.538945,31.231787),
new BMap.Point(121.539974,31.233111),
new BMap.Point(121.540558,31.232802)
	], {strokeColor:"blue", strokeWeight:5, strokeOpacity:0.5});   //????

		

	function add_overlay(){
		map.addOverlay(marker);            
		map.addOverlay(marker2);
		map.addOverlay(marker3);
		map.addOverlay(polyline); 
		map.addOverlay(polyline2); 
		map.addOverlay(polyline3); 
         
	}

	function remove_overlay(){
		map.clearOverlays();         
	}

	add_overlay();


    function generateMakerInfoWindowContent(title, image, description) {
        return "<h4 style='margin:0 0 5px 0;padding:0.2em 0'>" + title + "</h4>" + 
            "<img style='float:left;margin:4px 8px 4px 0' src='" + image + "' width='139' height='104' title='" + title + "'/>" + 
            "<p style='margin:0;line-height:1.5;font-size:13px;'>" + description + "</p>" + 
            "</div>";
    }



function displaylabel1()
{
	if (infoWindow1_status == 0)
	{
		marker.openInfoWindow(infoWindows[0]);
		infoWindow1_status = 1;
	}
	else
	{ 
		marker.closeInfoWindow();
		infoWindow1_status = 0;
	}
}
function displaylabel2()
{
	if (infoWindow2_status == 0)
	{
		marker2.openInfoWindow(infoWindows[1]);
		infoWindow2_status = 1;
	}
	else
	{ 
		marker2.closeInfoWindow();
		infoWindow2_status = 0;
	}
}
function displaylabel3()
{
	if (infoWindow3_status == 0)
	{
		marker3.openInfoWindow(infoWindows[2]);
		infoWindow3_status = 1;
	}
	else
	{ 
		marker3.closeInfoWindow();
		infoWindow3_status = 0;
	}
}



marker.addEventListener("click", displaylabel1);
marker2.addEventListener("click", displaylabel2);
marker3.addEventListener("click", displaylabel3);



map.enableScrollWheelZoom();   
map.enableContinuousZoom();    

markerBus.addEventListener("click",displaylabelBus);
function displaylabelBus(){
if (markerBus.getLabel()==null)
{
var labelBus = new BMap.Label("<b>Route A</b> - Bus 1<BR><b>Driver:</b> " + driver_name1 + "<BR><b>Phone:</b> " + driver_phone1,{offset:new BMap.Size(20,-10)});
markerBus.setLabel(labelBus); 
}
else
{
markerBus.getLabel().remove();
}			
			
	}

markerBus2.addEventListener("click",displaylabelBus2);
function displaylabelBus2(){
if (markerBus2.getLabel()==null)
{
var labelBus2 = new BMap.Label("<b>Route BC</b> - Bus 1<BR> <b>Driver:</b> " + driver_name2 + "<BR><b>Phone:</b> " + driver_phone2,{offset:new BMap.Size(20,-10)});
markerBus2.setLabel(labelBus2); 
}
else
{
markerBus2.getLabel().remove();
}			
			
	}

translateCallback = function (point){
var ok = 0;
if (markerBus.getLabel()!=null)
{
	ok=1;
}
		map.removeOverlay(markerBus);
		markerBus = new BMap.Marker(point,{icon:busIcon});
		map.addOverlay(markerBus);
		markerBus.addEventListener("click",displaylabelBus);
if (ok==1)
{
	displaylabelBus();
}
	}

translateCallback2 = function (point){
var ok2 = 0;
if (markerBus2.getLabel()!=null)
{
	ok2=1;
}
		map.removeOverlay(markerBus2);
		markerBus2 = new BMap.Marker(point,{icon:busIcon});
		map.addOverlay(markerBus2);
	markerBus2.addEventListener("click",displaylabelBus2);
if (ok2==1)
{
	displaylabelBus2();
}

	}


	setInterval(function(){
	ajaxGetBusLocation();
	var buscoords = divv.textContent.split("g",8);
	busPoint = new BMap.Point(buscoords[2], buscoords[1]);
	
	busPoint2 = new BMap.Point(buscoords[4], buscoords[3]);

	BMap.Convertor.translate(busPoint,0,translateCallback); 
	BMap.Convertor.translate(busPoint2,0,translateCallback2);         	}, 3000);



function updateDriverDetails()
{
	ajaxGetDriverInfo();
	var driversInfo = divv2.textContent.split("#",5); 
	driver_name1 = driversInfo[1];
	driver_phone1 = driversInfo[2];
	driver_name2 = driversInfo[3];
	driver_phone2 = driversInfo[4];

}

var counterUpd = 0;

var drvInt = setInterval(function(){ 
	updateDriverDetails();
	counterUpd++;
}, 3000);

if (counterUpd >=5){
	clearInterval(drvInt);}

setInterval(function(){ 
	updateDriverDetails();
}, 300000);



</script>



<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
		<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-throttle-debounce/1.1/jquery.ba-throttle-debounce.min.js"></script>
		<script src="js/jquery.stickyheader.js"></script>
</div>
</body>
</html>


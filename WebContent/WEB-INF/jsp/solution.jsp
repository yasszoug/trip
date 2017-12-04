<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TripHub</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<link rel="stylesheet" href="css/skel.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/style-xlarge.css" />
		<script src="js/jquery.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/skel-layers.min.js"></script>
		<script src="js/init.js"></script>
	</head>
<body>
	<div id="map" style="width: 100%; height: 500px"></div>

	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAZNPvpzLwgxnwDZQIhDPER5P_m2rCUjSw"></script>
	<script>

		function generateRandomColor(){
			var letters = '0123456789ABCDEF';
			var color = '#';
			for (var i = 0; i < 6; i++ ) {
				color += letters[Math.floor(Math.random() * 16)];
			}
			return color;
		}

		var names = [];
		function myMap() {
			var bounds = new google.maps.LatLngBounds();
			var paths = [];
			var markers = [];
			<c:forEach var="i" begin="0" end="${startNb - 1}" step="1">
				paths[<c:out value="${i}" />] = [];
				names.push("<c:out value="${users[i].name}"/>");
				<c:forEach items="${users[i].path[0].path}" var="item">
					var point = new google.maps.LatLng(<c:out value="${item.station.latitude}"/>, <c:out value="${item.station.longitude}"/>);
					paths[<c:out value="${i}"/>].push(point);
					bounds.extend(point);
				</c:forEach>
			</c:forEach>

		  var mapCanvas = document.getElementById("map");
		  var mapOptions = {
		    center: new google.maps.LatLng(46.819041,8.3638133),
		    zoom: 8
		  };
			var map = new google.maps.Map(mapCanvas, mapOptions);
			var lines = [];
			for (var i = 0; i < paths.length; i++) {
				var departure = paths[i][0];
				var marker = new google.maps.Marker({
					position: departure,
					title: "Departure for " + names[i],
					map: map
				});
				markers.push(marker);
				lines.push(new google.maps.Polyline({
			        path: paths[i],
			        strokeColor: generateRandomColor(),
			        strokeOpacity: 0.5,
			        strokeWeight: 4
	      		}));

				lines[i].setMap(map);
			}
			map.fitBounds(bounds);
		}

        google.maps.event.addDomListener(window, 'load', myMap);
	</script>
	<c:forEach var="i" begin="0" end="${startNb - 1}" step="1">
		<div class="container 75%">
			<h3 style="margin-left: -50px;margin-top: 10px;">
				Travel for <strong><c:out value="${users[i].name}" /></strong>
			</h3>	
			<table style="width: 100%;">
				<tr>
					<th>Town</th>
					<th>Train number</th>
					<th>Arrival Time</th>
					<th>Departure Time</th>
					<th>Load</th>
				</tr>
				<c:forEach items="${users[i].path[0].path}" var="item">
					<tr>
						<td><c:out value="${item.station.name}" /></td>
						<td><c:out value="${item.train.serviceName}" /></td>
						<td><c:out value="${item.timeOfArrival}" /></td>
						<td><c:out value="${item.timeOfDeparture}" /></td>
						<td><c:out value="${item.train.load}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:forEach>	
	<footer id="footer">
		<div class="container">
			<div class="row">
				<div class="8u 12u$(medium)">
					<ul class="copyright">
						<li>&copy; Untitled. All rights reserved.</li>
						<li>Design: <a href="http://templated.co">TEMPLATED</a></li>
						<li>Images: <a href="http://unsplash.com">Unsplash</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--
	Transit by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>TripHub</title>
<link rel="stylesheet" href="css/skel.css" />
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/style-xlarge.css" />
<script src="js/jquery.min.js"></script>
<script src="js/skel.min.js"></script>
<script src="js/skel-layers.min.js"></script>
<script src="js/init.js"></script>
</head>
<body class="landing">

	<header id="header">
		<h1>
			<a href=".">TripHub</a>
		</h1>
	</header>

	<!-- Banner -->
	<section id="banner">
		<h2>Hi. This is TripHub.</h2>
		<p>A way to make your travels more social</p>
	</section>

	<!-- One -->
	<section id="three" class="wrapper style3 special">
		<div class="container 50%">
			<header class="major">
				<h2>First of all, please fill up the informations</h2>
			</header>
			<div class="row">
				<c:if test="${not startSet}">
					<div class="container 50%">
						<form method="get" action=".">
							<label for="Number of starts">Number of starts :</label> <input
								type="text" name="startNb" id="startNb" /> <br /> <input
								type="submit" class="special big" value="Proceed" />
						</form>
					</div>
				</c:if>
				<c:if test="${startSet}">
					<div class="container 50%">
						<form method="post" action="request">
							<fieldset>
								<h2>General Stuff:</h2>
								<c:if test="${errorDest}">
									<small style="color: red;">Please give a destination</small>
									<br />
								</c:if>
								<c:if test="${errorDate}">
									<small style="color: red;">Please give at least a
										starting point</small>
									<br />
								</c:if>
								<label for="destination">Destination :</label> <input
									type="text" name="dest" id="dest" /> <label for="date">Date
									of travel:</label> <input type="date" name="date" id="date" /> <br />
							</fieldset>
							<fieldset>
								<h2>User info:</h2>
								<c:if test="${errorStart}">
									<small style="color: red;">Please fill all location
										fields</small>
									<br />
								</c:if>
								<c:if test="${errorTime}">
									<small style="color: red;">Please fill all time fields</small>
									<br />
								</c:if>
								<c:if test="${errorName}">
									<small style="color: red;">Please fill all names</small>
									<br />
								</c:if>
								<c:forEach var="i" begin="0" end="${startNb - 1}" step="1">
									<p>
										User #
										<c:out value="${i + 1}" />
									</p>
									<label for="name${i}" style="margin: 20px; align: left;">Name
										:</label>
									<input type="text" name="name" id="name${i}" />
									<br />
									<label for="start${i}" style="margin: 20px; align: left;">Start
										:</label>
									<input type="text" name="start" id="start${i}" />
									<br />
									<label for="time${i}" style="margin: 20px; align: left;">At
										:</label>
									<input type="time" name="time" id="time${i}" />
									<br />
								</c:forEach>
							</fieldset>
							<br /> <input type="submit" class="special big" value="Submit" />
							</p>
						</form>
					</div>
				</c:if>
			</div>
		</div>
	</section>

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

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<%=request.getContextPath()%>">Docker Maps</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li><a href="<%=request.getContextPath()%>">Home</a></li>
			</ul>			
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="<%=request.getContextPath()%>/maps.jsp"><span class="glyphicon glyphicon-search"></span>Maps</a>
				</li>
			</ul>
		</div>
	</div>
</nav>
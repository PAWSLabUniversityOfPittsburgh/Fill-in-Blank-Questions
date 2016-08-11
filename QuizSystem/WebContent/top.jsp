<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>QuizPET</title>
<link rel="stylesheet" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=big5">
</head>
<%
	String languagString=getServletContext().getInitParameter("language");
%>
<body>
  <div class="top">
  <!-- logo part -->
  <div class="logo">
  </div>

<!-- top nav part-->
  <div class="top-nav">
    <ul>
      <li>
        <a href="main.html" target="mainFrame" class="top-nav-home">Home</a>
      </li>
      <li>
        <a href="" class="top-nav-help">Help</a>
      </li>
    </ul>
  </div>
</div>

</body>
</html>

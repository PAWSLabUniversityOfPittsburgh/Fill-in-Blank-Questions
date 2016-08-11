<%@page import="dao.JquizDAO"%>
<%@ page language="java"%>
<%@ page import="bean.*"%>
<%@ page import="java.util.*"%>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<%
	List<Jquestion> list = (List<Jquestion>) request.getAttribute("list");
%>
<body>
	<div class="menu">
	<%
					Iterator<Jquestion> iter = list.iterator();
					while (iter.hasNext()) {
						Jquestion q = iter.next();
	%>
		<div class="chapter ">
			<h3 class="chapter-noactive"><%=q.getTitle()%></h3>
			<ul>
<%
					List<Jquiz> innerlist = new JquizDAO().getListQuizebyQuestionID(q.getQuestionID());
						Iterator<Jquiz> innerIter = innerlist.iterator();
						while (innerIter.hasNext()) {
							Jquiz jquiz = innerIter.next();
				%>
				<li><a href="displayQuiz?rdfID=<%=jquiz.getRdfID()%>&title=<%=jquiz.getTitle()%>" target="mainFrame"><%=jquiz.getTitle()%></a></li>
<%} %>
			</ul>
		</div>
<%} %>
	</div>
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/script.js"></script>
</body>
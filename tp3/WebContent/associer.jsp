<%@ page language="java" import="java.util.*,pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Associer</title>
</head>
<body>
<form action='Serv' method='POST'>
<% 
Boolean cont = true;
if (request.getAttribute("personnes") == null) {
	out.println("Il n'y a personne dans la liste.<br>");
	cont = false;
}
if (request.getAttribute("adresses") == null) {
	out.println("Il n'y pas d'adresses dans la liste.<br>");
	cont = false;
}
if (cont) {
	Collection<Personne> lp = (Collection<Personne>) request.getAttribute("personnes");
	out.println("<p>Choisir la personne :<br>");
	for (Personne p : lp) {
		%><input type='RADIO' id='<%=p.getId()%>' name='pa' value='<%=p.getId()%>'/>
		<label for='<%=p.getId()%>'><%out.println(p.getPersonne());%></label><br><%
	}
	Collection<Adresse> la = (Collection<Adresse>) request.getAttribute("adresses");
	out.println("</p><p>Choisir l'adresse :<br>");
	for (Adresse a : la) {
		%><input type='RADIO' id='<%=a.getId()%>' name='aa' value='<%=a.getId()%>'/>
		<label for='<%=a.getId()%>'><%out.println(a.getAdresse());%></label><br><%
	}
}
%>
</p>
<p><input type='SUBMIT' value='OK'/></p>
<input type='HIDDEN' name='op' value='association'/>
</form>
</body>
</html>
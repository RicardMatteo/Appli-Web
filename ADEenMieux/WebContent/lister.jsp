<%@ page language="java" import="java.util.*,pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lister</title>
</head>
<body>
<% 
if (request.getAttribute("personnes") != null) {
	Collection<Personne> lp = (Collection<Personne>) request.getAttribute("personnes");
	for (Personne p : lp) {
		out.println(p.getPersonne() + "<br>");
		Collection<Adresse> la = p.getAddresses();
		if (la != null) {
			for (Adresse a : la) {
				out.println("&nbsp; &nbsp; &nbsp;" + a.getAdresse() + "<br>");
			}
		}
	}
} else {
	out.println("Il n'y a personne dans la liste.");
}
%>
</body>
</html>
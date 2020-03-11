<%
  String queryString = request.getQueryString();
  String redirectURL = request.getContextPath()  +"/dashbuilder.html?"+(queryString==null?"":queryString);
  response.sendRedirect(redirectURL);
%>
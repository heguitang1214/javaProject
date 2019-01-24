<%@ page import="org.apache.log4j.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=GBK" language="java"%>
<html>
<head>
<title>Log4J�������</title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<body>
	<h1>Log4J�������</h1>
	<%
		String logName = request.getParameter("log");
		if (null != logName) {
			Logger log = ("".equals(logName) ? Logger.getRootLogger() : Logger.getLogger(logName));
			log.setLevel(Level.toLevel(request.getParameter("level"), Level.DEBUG));
			Logger root = Logger.getRootLogger();
			root.setLevel(Level.DEBUG);
		}
	%>
	<c:set var="rootLogger" value="<%=Logger.getRootLogger()%>" />
	<form>
		<table border="1">
			<tr>
				<th>Level</th>
				<th>Logger</th>
				<th>Set New Level</th>
			</tr>
			<tr>
				<td>${rootLogger.level}</td>
				<td>${rootLogger.name}</td>
				<td><c:forTokens var="level" delims=","
						items="DEBUG,INFO,WARN,ERROR,OFF">
						<a href="log4j.jsp?log=&level=${level}">${level}</a>
					</c:forTokens></td>
			</tr>
			<c:forEach var="logger"
				items="${rootLogger.loggerRepository.currentLoggers}">
				<c:if
					test="${!empty logger.level.syslogEquivalent || param.showAll}">
					<tr>
						<td>${logger.level}</td>
						<td>${logger.name}</td>
						<td><c:forTokens var="level" delims=","
								items="DEBUG,INFO,WARN,ERROR,OFF">
								<a href="log4j.jsp?log=${logger.name}&level=${level}">${level}</a>
							</c:forTokens></td>
					</tr>
				</c:if>
			</c:forEach>
			<tr>
				<td></td>
				<td><input type="text" name="log" /></td>
				<td><select name="level">
						<c:forTokens var="level" delims=","
							items="DEBUG,INFO,WARN,ERROR,OFF">
							<option>${level}</option>
						</c:forTokens>
				</select> <input type="submit" value="Add New Logger" /></td>
			</tr>
		</table>
	</form>
	<a href="log4j.jsp?showAll=true">������֪ loggers</a>
</body>
</html>
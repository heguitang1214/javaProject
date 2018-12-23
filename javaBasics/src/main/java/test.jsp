<%@page pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    String remoteAddr = request.getRemoteAddr();
    String realIp = request.getHeader("X-Forwarded-For");
    String realIp2 = request.getHeader("X-Real-IP");
    String host = request.getHeader("Host");
    int serverPort = request.getServerPort();
    int remotePort = request.getRemotePort();
    String requestUrl1 = scheme + ":" + realIp + ":" + remotePort;
    String requestUrl2 = scheme + ":" + realIp2 + ":" + remotePort;
    String requestUrl3 = scheme + ":" + remoteAddr + ":" + remotePort;
    String requestUrl = scheme + "://" + serverName + ":" + serverPort;
%>
客户端地址1:<%=requestUrl1%><br>
客户端地址2:<%=requestUrl2%><br>
客户端地址3:<%=requestUrl3%><br>
客户端地址1:<%=requestUrl%><br>
客户端地址2:<%=host%><br>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%
response.addHeader("Access-Control-Allow-Origin", "*");
response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
response.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//统一接口入口
String requestParams = request.getParameter("requestParams");
if(requestParams == "" || requestParams == "null" || requestParams == null){
	out.println("{\"errcode\":\"-111\",\"errmsg\":\"参数有误\"}");
	return;
}

try{
JSONObject node = JSONObject.fromObject(requestParams);
if(!node.has("action")){
	out.println("{\"errcode\":\"-111\",\"errmsg\":\"参数有误\"}");
	return;
}

if(!node.has("method")){
	out.println("{\"errcode\":\"-111\",\"errmsg\":\"参数有误\"}");
	return;
}
String action = node.getString("action");
String method = node.getString("method");

request.getRequestDispatcher("/internal/report/"+action+"/"+method+".jhtml").forward(request,response);
}catch(Exception e){
	out.println(e.getMessage());
	out.println("{\"errcode\":\"-112\",\"errmsg\":\"解析失败\"}");
	return;
}
%>


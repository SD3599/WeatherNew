<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body background="${contextPath}/resources/css/bg.jpg">
<font size="5"  color="white">
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

    </c:if>

</div>
 </font>
 <font size="4"  color="orange" >
<form action="${contextPath}/getforecast/place" method="get">  

<div class="container">
<label>Forecast for 
</label>
<select name="place">
  <option value="Hyderabad">Hyderabad</option>
  <option value="Delhi">Delhi</option>
  <option value="Mumbai">Mumbai</option>
  <option value="Chennai">Chennai</option>
  <option value="Kolkata">Kolkata</option>
  <option value="Pune">Pune</option>
  <option value="Vienna">Vienna</option>
  <option value="Newyork">New york</option>
  <option value="Sydney">Sydney</option>
  <option value="Amsterdam">Amsterdam</option>
  <option value="Los Angeles">Los Angeles</option>
  
</select>
  in
   <select name="type">
   
   <option value="0">Celsius</option>
   <option value="1">Fahrenheit</option>
   </select>
   for next 
   <select name="days">
   
  
   <option value="1">1</option>
   <option value="2">2</option>
   <option value="3">3</option>
   <option value="4">4</option>
   <option value="5">5</option>
   <option value="6">6</option>
   <option value="7">7</option>
   <option value="8">8</option>
   <option value="9">9</option>
   <option value="10">10</option>
   
   
   </select>
    days
   <input type="submit" value="Get Forecast" />
   </div>  
  </form>  
  </font>
 <font size="3"  color="white">
  <div class="container">
  <%@ page import="com.WeatherApp.model.Weather" %>
  <%@page import="com.github.fedy2.weather.data.Forecast" %>
  <% 
  String defaultloc=(String)request.getAttribute("defaultloc");  
  if(defaultloc!=null){
	  out.print( "Temperature for "+defaultloc); 
  }
  String place=(String)request.getAttribute("place");  
  String c=(String)request.getAttribute("c");
  if(c==null)c=" C";
  if(place!=null){  
  out.print( "Temperature for "+place); 
  }
  
  else if(defaultloc==null)
	  out.print( "Temperature for hyderabad");
			  Weather w=(Weather)request.getAttribute("Weather");
			  if(w==null)
					out.print("empty"); 
			  else{
 //Integer t= (Integer)request.getAttribute("temperature");
 Integer t=w.getTemperature();
if(t!=null){  
out.print(" is "+t+c+"<br/>");
}
else
	out.print(" is "+"23"+"<br/>");  //will never reach here .. but just incase

//else
//out.print("23"); 


//Integer mintemp=(Integer)request.getAttribute("mintemp");
Integer mintemp=w.getMintemp();
if(mintemp!=null){  
out.print("Min temp: "+mintemp+c+"<br/>"); 

}
else 
	out.print("Min temp: "+21+"<br/>");

//Integer maxtemp=(Integer)request.getAttribute("maxtemp");
Integer maxtemp=w.getMaxtemp();
if(maxtemp!=null){  
out.print("Max temp: "+maxtemp+c+"<br/>"); 

}
else
out.print("Max temp: "+28+"<br/>");


//String weather=(String)request.getAttribute("weather");  
String weather=w.getWeather();
if(weather!=null){  
out.println("It is a "+weather+" day today!! <br/>"); 
}
//else
	//out.print("empty :("); 
//int no = Integer.parseInt(request.getParameter("days"));

String no=(String)request.getAttribute("days"); 

int noo=0;
if(no!=null){
  noo=Integer.parseInt(no);
  out.print("=======================================<br/>");
out.println("For "+noo+" days!! <br/>"); 
out.print("=======================================<br/>");
for(int i=0;i<noo;i++){
	
	Forecast tem=(Forecast)request.getAttribute("day"+i);  
	if(tem!=null){  
		out.print("Forecast for "+tem.getDay()+"<br/>");
	out.print("=======================================<br/>");
	out.print("Min temp: "+tem.getLow()+c+"<br/>"); 
	out.print("Max temp: "+tem.getHigh()+c+"<br/>"); 
	out.print("Weather : "+tem.getText()+"<br/>"); 
	out.print("=======================================<br/>");
	}
}
}

}

 %> 
  

</div>

<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</font>
</body>
</html>

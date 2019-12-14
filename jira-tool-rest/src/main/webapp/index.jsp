<html lang="en-US">
<head>
	<title>Jira Tool</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="CustomStyle.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src='JSUtilities.js'></script>
</head>
<body>
	
    	<div style="height:55px; background-color: Black;">
    		<p align="right"><font size="6" color="white" face="verdana">&emsp;Jira Tool&emsp;</font></p>
    	</div>
    	
    	<div class="row" style="margin-top: 80px;">
    		<div class="col-lg-5 col-md-5 col-sm-5 col-xs-2"></div>	
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-8 login-form">
				<center>
				<label><p align="center"><font size="5" color="black" face="verdana"> Username </font></p></label>				
				<input type="text" class="form-control" name="username" id="username" style="margin-bottom:20px" align="center">
				
				<label><p align="center"><font size="5" color="black" face="verdana"> Password </font></p></label>				
				<input type="password" class="form-control" name="password" id="password" style="margin-bottom:20px" align="center">
				
				<button type="button" id="login" class="btn btn-primary" onclick="login()"> 
					<font size="3" face="verdana"> Sign In </font>
				</button>
				</center>
			</div>	
			<div class="col-lg-5 col-md-5 col-sm-5 col-xs-2"></div>
    	</div>
</body>
</html>

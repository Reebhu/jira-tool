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
	
    	<div style="height:100px; background-color: Black;">
    		<p align="left"><font size="6" color="white" face="verdana"><br/> &emsp; Jira Tool </font></p>
    		
    		<p align="right" style="margin-top: -90px">
    		<table cellpadding="10" style="border:2px;">
    			<tr>
    				<td><p align="center"><font size="5" color="white" face="verdana"> Username </font></p>	</td>
    				<td><p align="center"><font size="5" color="white" face="verdana"> Password </font></p>	</td>	
    			</tr>	
				<tr>
					<td><input type="text" class="form-control" name="username" id="username" style="width: 220px;"></td>			
					<td><input type="password" class="form-control" name="password" id="password" style="width: 220px;"></td>
					<td id="login-out-toggle">
						<button type="button" id="login" class="btn btn-primary" onclick="login()"> 
							<font size="3" face="verdana"> Sign In </font>
						</button>
					</td>
				</tr>
			</table>
			</p>
    	</div>
    	
    	<div class="sidenav" id="sidebar" style="display:none;">
  			<p align="left" style="margin-top: 0px"><font size="6" color="white" face="verdana"><br/> &emsp; Jira Tool </font></p>
  			<br/>
  			<br/>
  			<button type="button" id="login" class="btn btn-primary" onclick="getIssuesCurrentUser()"> 
							<font size="3" face="verdana"> &nbsp; Get My Issues &nbsp;</font>
			</button>
			<br/>
  			<br/>
			<button type="button" id="login" class="btn btn-primary" onclick="getWorklogCurrentUser()"> 
							<font size="3" face="verdana"> Get My Work Log </font>
			</button>
		</div>
		
		<div>
			<table class="table" id="cardDetails" style="margin-left: 300px">
			</table>
		</div>
</body>
</html>

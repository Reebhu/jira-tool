<html lang="en-US">
<head>
	<title>Jira Tool</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="CustomStyle.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="jquery-3.4.1.min.js"></script>
	<script src='JSUtilities.js'></script>
</head>
<body>
	
    	<div style="height:100px; background-color: Black;">
    		<p align="left"><font size="6" color="white" face="verdana"><br/> &emsp; Jira Tool </font></p>
    		
    		<p align="right" style="margin-top: -90px">
    		<table cellpadding="10" style="border:2px;">
    			<tr id="usr_pswrd_tags">
    				<td><p align="center"><font size="5" color="white" face="verdana"> Username </font></p>	</td>
    				<td><p align="center"><font size="5" color="white" face="verdana"> Password </font></p>	</td>	
    			</tr>	
				<tr>
					<td id="usr_field"><input type="text" class="form-control" name="username" id="username" style="width: 220px;"></td>			
					<td id="pswrd_field"><input type="password" class="form-control" name="password" id="password" style="width: 220px;"></td>
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
  			<p align="left" style="margin-top: -15px"><font size="6" color="white" face="verdana"><br/> &emsp; Jira Tool </font></p>
  			<br/>
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
		
		<div >
			<table class="table-bordered table-hover" id="cardDetails" style="margin-left: 210px; margin-top: 10px; text-align: center">
			</table>
		</div>
</body>
</html>

var username;
var password;
var url;

function login()
{
	username = document.getElementById('username').value;
	password = document.getElementById('password').value;
	
	let requestJson = {};
	requestJson.username = username;
	requestJson.password = password;
	
	fetch('http://localhost:8080/jira-tool-rest/webapi/jira/login', 
		 {
		  	method: 'POST',
		  	headers: 
		  	{
		  		'Content-Type': 'application/json;charset=utf-8'
		  	},
		  	body: JSON.stringify(requestJson)
		 })
		 .then(function(loginResponse)
				 {
			 		if(loginResponse.status === 200)
			 		{
			 			var loginBtn = document.getElementById("login");
			 			loginBtn.parentNode.removeChild(loginBtn);
			 			
			 			var logoutBtn = document.createElement("input");
			 			logoutBtn.type = "button";
			 			logoutBtn.className = "btn btn-primary";
			 			logoutBtn.value = "Sign Out";
			 			logoutBtn.setAttribute("onclick", "logout()");
			 			document.getElementById("login-out-toggle").appendChild(logoutBtn);
			 			
			 			document.getElementById("sidebar").style.display = "block";
			 		}
			 		else
			 		{
			 			alert("Could not initialize the user");
			 		}
			 	 }
		 		);	
}

function getIssuesCurrentUser()
{
	alert("getIssuesCurrentUser");
	url = "http://localhost:8080/jira-tool-rest/webapi/jira/issues";
	fetch(url).then(function(response)
			  {
				  response.text().then(function(jsonResponse) 	
				  {    
					  alert(jsonResponse);
					  var detailTable = document.getElementById("cardDetails");
					  
					  var issues = JSON.parse(jsonResponse);
					  var data = '';
					  
					  for(var issue in issues)
					  {
						  data += "<tr>";
						  	data += "<td> " + issue + " </td> <td>" + issues[issue] + "</td>";
						  data += "</tr>"
					  }
					  
					  detailTable.innerHTML = data;
				  });
			  }).catch(function(err) 
			  {
				    console.log('Fetch Error :-S', err);
			  });	
	alert("Dont wait");
}

function getWorklogCurrentUser()
{
	alert("getWorklogCurrentUser");
	url = "http://localhost:8080/jira-tool-rest/webapi/jira/worklog";
	fetch(url).then(function(response)
			  {
				  response.text().then(function(jsonResponse) 	
				  {    
					  alert(jsonResponse);
					  var detailTable = document.getElementById("cardDetails");
					  
					  var worklogs = JSON.parse(jsonResponse);
					  var data = '';
					  
					  alert(worklogs);
					  
					  
					  detailTable.innerHTML = data;
				  });
			  }).catch(function(err) 
			  {
				    console.log('Fetch Error :-S', err);
			  });	
	alert("Dont wait");
}

function logout()
{
	username = document.getElementById('username').value;
	password = document.getElementById('password').value;
	
	let requestJson = {};
	requestJson.username = username;
	requestJson.password = password;
	
	fetch("http://localhost:8080/jira-tool-rest/webapi/jira/logout", 
			 {
			  	method: 'DELETE',
			  	headers: 
			  	{
			  		'Content-Type': 'application/json;charset=utf-8'
			  	},
			  	body: JSON.stringify(requestJson)
			 })
			 .then(function(logoutResponse)
					 {
				 		if(logoutResponse.status === 204)
				 		{
				 			window.open("http://localhost:8080/jira-tool-rest/");
				 		}
				 		else
				 		{
				 			
				 		}
				 	 }
			 		);	
}

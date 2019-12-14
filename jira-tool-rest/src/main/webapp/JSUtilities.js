var username;
var password;

function login()
{
	username = document.getElementById('username').value;
	password = document.getElementById('password').value;
	
	let requestJson = {};
	requestJson.username = username;
	requestJson.password = password;
	
	fetch('http://localhost:8080/jira-tool-rest/webapi/jira/user', 
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
			 			window.open("FilterIssues.html");
			 		else
			 		{
			 			alert("Could not initialize the user");
			 		}
			 	 }
		 		);	
}
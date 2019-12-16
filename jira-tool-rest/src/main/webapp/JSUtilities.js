var username;
var password;
var url;
var base64;
function login()
{
	username = document.getElementById('username').value;
	password = document.getElementById('password').value;
	
	document.body.style.cursor = 'wait';
	
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
	 			document.body.style.cursor = 'default';
			 	base64 = btoa(username+""+password);
	 			var loginBtn = document.getElementById("login");
	 			loginBtn.parentNode.removeChild(loginBtn);
			 			
	 			var logoutBtn = document.createElement("input");
	 			logoutBtn.type = "button";
	 			logoutBtn.className = "btn btn-primary";
	 			logoutBtn.value = "Sign Out";
	 			logoutBtn.setAttribute("onclick", "logout()");
	 			document.getElementById("login-out-toggle").appendChild(logoutBtn);
	 			$('#usr_pswrd_tags').hide();
	 			$('#usr_field').hide();
	 			$('#pswrd_field').hide();
	 			
	 			document.getElementById("sidebar").style.display = "block";
 		}
 		else
 		{
	 			alert("Could not initialize the user");
 		}
	 });	
}

function getIssuesCurrentUser()
{
	document.body.style.cursor = 'wait';
	
	url = "http://localhost:8080/jira-tool-rest/webapi/jira/issues";
	fetch(url,{
		headers:
		{
			'Authorization': 'Basic '+ base64
		}
	}).then(function(response)
    {
		  response.text().then(function(jsonResponse) 	
		  {    
			  document.body.style.cursor = 'default';
			  var detailTable = document.getElementById("cardDetails");
				  
			  var issues = JSON.parse(jsonResponse);
			  var data = '';
					  
			  for(var issue in issues)
			  {
			      data += '<tr>';
	    		  	data += '<td style="padding: 5px;"> ' + issue + ' </td> <td style="padding: 5px;">' + issues[issue] + '</td>';
			   	  data += '</tr>';
			  }
					  
			  detailTable.innerHTML = data;
		  });
	}).catch(function(err) 
	{
	    console.log('Fetch Error :-S', err);
	});	
}

function getWorklogCurrentUser()
{
	document.body.style.cursor = 'wait';
	
	var detailTable = document.getElementById("cardDetails");
	var workLogDetails = '';
	
	url = "http://localhost:8080/jira-tool-rest/webapi/jira/worklog";
	
	fetch(url,{
		headers:
		{
			'Authorization': 'Basic '+ base64
		}
	}).then(function(response)
    {
		  response.text().then(function(jsonResponse) 	
		  {    
			  document.body.style.cursor = 'default';
					  					  
			  var worklogs = JSON.parse(jsonResponse);
					  
			  workLogDetails += '<tr style="background-color: #c5e6f7c4;">';
	  			workLogDetails += '<th style="padding: 5px; font-size: large; text-align: center;"> Issue Number </th>';
	  			workLogDetails += '<th style="padding: 5px; font-size: large; text-align: center;"> Description </th>';
	  			workLogDetails += '<th style="padding: 5px; font-size: large; text-align: center;"> Logged Hours </th>';
	  			workLogDetails += '<th style="padding: 5px; font-size: large; text-align: center;"> Comment </th>';
	  		  workLogDetails += '</tr>';
					  					  
	    	  for(var worklog in worklogs)
			 {
				  	workLogDetails += '<tr class="success"> <th colspan="4" style="padding: 5px; text-align: center;"> Date : ' +worklogs[worklog].date+ '</th></tr>'
						  	
				  	for(var i in worklogs[worklog].worklogResponses)
				  	{
				  		workLogDetails += '<tr>';
				  			workLogDetails += '<td style="padding: 5px;">'+ worklogs[worklog].worklogResponses[i].issueKey +'</td>';
				  			workLogDetails += '<td style="padding: 5px;">'+ worklogs[worklog].worklogResponses[i].issueSummary +'</td>';
				  			workLogDetails += '<td style="padding: 5px;">'+ worklogs[worklog].worklogResponses[i].hoursLogged +'</td>';
				  			workLogDetails += '<td style="padding: 5px;">'+ worklogs[worklog].worklogResponses[i].comment +'</td>';
 				  		workLogDetails += '</tr>';
				  	}
			   }
					  
			  detailTable.innerHTML = workLogDetails;
		  });
     }).catch(function(err) 
	   {
		    console.log('Fetch Error :-S', err);
	  });	
}

function logout()
{
	document.body.style.cursor = 'wait';
	
    username = "";
    password = "";
    base64 = "";
    document.body.style.cursor = 'default';
	window.open("http://localhost:8080/jira-tool-rest/");

}

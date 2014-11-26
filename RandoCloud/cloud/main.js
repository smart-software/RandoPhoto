
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
/*
  Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});
*/
Parse.Cloud.afterSave("Photo", function(request) {
	
	if(request.object.existed() == false) {
	  var reviewersList = request.object.get("Reviewers");
	  for (var i = 0; i < reviewersList.length; i++) {
		  Parse.Push.send({
			  channels: [ "user_"+reviewersList[i] ],
			  data: {
			    alert: "New Rando to review!"
			  }
			}, {
			  success: function() {
			    // Push was successful
			  },
			  error: function(error) {
			    // Handle error
			  }
			});
		}
	  }
	});
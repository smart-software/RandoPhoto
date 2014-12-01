
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
/*
  Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});
*/

Parse.Cloud.define("pick2RandomUsers", function(request, response){
	var queryCount = new Parse.Query(Parse.User);
	queryCount.ascending("createdAt");
	queryCount.notContainedIn("id", [request.user.id]);
	queryCount.count({
	  success: function(count) {
		    // The count request succeeded. Pick two random numbers
		  	var min = 0;
		  	var max = count;
		    var userIndex1 = Math.floor(Math.random() * (max - min + 1)) + min;
		    var userIndex2 = 1;
		    for (var i = 0; i < 100; i++) {
				userIndex2 = Math.floor(Math.random() * (max - min + 1)) + min;
				if (userIndex1 != userIndex2) {
					break;
				}
			}
		    
		    queryCount.skip(userIndex1-1);
		    queryCount.limit(1);
		    queryCount.find({
		    	  success: function(users) {
		    		  	  var userId1 = new String();
		    		      userId1 = users[0].id;
		    		      queryCount.skip(userIndex2-1);
		    		      queryCount.find({
		    		    	  success: function(users) {
		    		    		  var userId2 = new String();
		    		    		  userId2 = users[0].id;
		    		    		  var myResponse = { "userId1": userId1, "userId2": userId2};
		    		    		  response.success(myResponse);
		    		    	  }
		    		    	});
		    		  }
		    		});
		    
		    
		    
		  },
		  error: function(error) {
		    // The request failed
		  }
		});
});

Parse.Cloud.define("likePhoto", function(request, response) {
	var photoId = request.params.photoId;
	var userId = request.user.id;
	var query = new Parse.Query("Photo");
	query.get(photoId, {
		  success: function(photo) {
		    photo.addUnique("likes_id", userId);
		    photo.remove("Reviewers", userId);
		    photo.increment("sendCount");
		    photo.increment("likes");
		    var currentDate = new Date();
		    photo.set("LastLikedAt", currentDate);
		    photo.save();
		    response.success();
		  },
		  error: function(object, error) {
			response.error();
		  }
		});
	
});

Parse.Cloud.afterSave("Photo", function(request) {
if(request.object.existed() == false) {	
	var queryCount = new Parse.Query(Parse.User);
	queryCount.ascending("createdAt");
	queryCount.notContainedIn("id", [request.object.get("createdBy")]);
	queryCount.count({
	  success: function(count) {
		    // The count request succeeded. Pick two random numbers
		  	var min = 0;
		  	var max = count;
		    var userIndex1 = Math.floor(Math.random() * (max - min + 1)) + min;
		    var userIndex2 = 1;
		    for (var i = 0; i < 100; i++) {
				userIndex2 = Math.floor(Math.random() * (max - min + 1)) + min;
				if (userIndex1 != userIndex2) {
					break;
				}
			}
		    
		    queryCount.skip(userIndex1-1);
		    queryCount.limit(1);
		    queryCount.find({
		    	  success: function(users) {
		    		  	  var userId1 = new String();
		    		      userId1 = users[0].id;
		    		      queryCount.skip(userIndex2-1);
		    		      queryCount.find({
		    		    	  success: function(users) {
		    		    		  var userId2 = new String();
		    		    		  userId2 = users[0].id; 
		    		    		  var reviewersList = new Array( userId1, userId2);
		    		    		  request.object.set("Reviewers", reviewersList);
		    		  		      var currentDate = new Date();
		    		  		      request.object.set("LastLikedAt", currentDate);
		    		    		  request.object.save();
		    		    	  }
		    		    	});
		    		  }
		    		});
		  },
		  error: function(error) {
		    // The request failed
		  }
		});
}

	
	if(request.object.existed()==true) { 
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

Parse.Cloud.job("getRidOfRandos", function(request, status) {
	  // Set up to modify user data
	  Parse.Cloud.useMasterKey();
	  var counter = 0;
	  // Query for all users
	  var currentDateMinusOneDay = new Date();
	  currentDateMinusOneDay.setDate(currentDateMinusOneDay.getDate()-1);
	  var query = new Parse.Query("Photo");
	  query.lessThan("LastLikedAt", currentDateMinusOneDay);
	  query.each(function(photo) {
	      // Update
		  photo.destroy();
	      if (counter % 100 === 0) {
	        // Set the  job's progress status
	        status.message(counter + " photo destroyed");
	      }
	      counter += 1;
	  }).then(function() {
	    // Set the job's success status
	    status.success("Not liked photos deleted.");
	  }, function(error) {
	    // Set the job's error status
	    status.error("Error delelting not liked photos.");
	  });
	});
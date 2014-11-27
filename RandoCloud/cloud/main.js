
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
/*
  Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});
*/

Parse.Cloud.define("likePhoto", function(request, response) {
	var photoId = request.params.photoId;
	var userId = request.user.id;
	var query = new Parse.Query("Photo");
	query.get(photoId, {
		  success: function(photo) {
		    photo.addUnique("likes_id", userId);
		    photo.save();
		  },
		  error: function(object, error) {
		  }
		});
	
});

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
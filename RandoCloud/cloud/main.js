
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
/*
  Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});
*/

Parse.Cloud.define("pick2RandomUsersSimple", function(request, response){

	var query = new Parse.Query("holder");
	query.get("tsaSJggSgZ", { //This is holder for last user number
		  success: function(holder) {
			var min = 1; //for further improvements
		    var userCount =  holder.get("userCount");
			var max = userCount;
		    var userCount1 = Math.floor(Math.random() * (max - min + 1)) + min;
		    var userCount2 = 1; // initialized for crash protection. TODO - deprecate
		    for (var i = 0; i < 100; i++) { //TODO random using [0;n) (n;max] where n  - is userCount1
				userCount2 = Math.floor(Math.random() * (max - min + 1)) + min;
				if (userCount1 != userCount2) {
					break;
				}
			}
		    var userArray =[userCount1,userCount2];
		    response.success(userArray); 
			}
	});
});





Parse.Cloud.define("likePhoto", function(request, response) { //TODO  for increment
	var photoId = request.params.photoId;
	var query = new Parse.Query("Photo");
	var queryPick2RandomUsers = new Parse.Query("holder");
	
	query.notEqualTo("likes_id", request.user.id);
	query.equalTo("Reviewers",request.user.id);
	
	Parse.Promise.when(queryPick2RandomUsers.get("tsaSJggSgZ"),query.get(photoId))
	.then(function(holder, photoRecieved){
		
		
		var min = 1; //for further improvements
	    var userCount =  holder.get("userCount");
		var max = userCount;
	    var userCount1 = Math.floor(Math.random() * (max - min + 1)) + min;
	    var userCount2 = 1; // initialized for crash protection. TODO - deprecate
	    for (var i = 0; i < 100; i++) { //TODO random using [0;n) (n;max] where n  - is userCount1
			userCount2 = Math.floor(Math.random() * (max - min + 1)) + min;
			if (userCount1 != userCount2) {
				break;
			}
		}
	    newReviewers =[userCount1,userCount2];
 
		var userId = request.user.id;
		var userNum = request.user.get("userNumber");
		var currentDate = new Date();
		
		photoRecieved.set("LastLikedAt", currentDate);
		
		photoRecieved.addUnique("likes_id", userId);
		photoRecieved.addUnique("likes_id_num", userNum);
		photoRecieved.addUnique("Reviewers_num", newReviewers[0]); //indian code alert! TODO
		photoRecieved.addUnique("Reviewers_num", newReviewers[1]);
	    
		photoRecieved.increment("sendCount");
		photoRecieved.increment("likes");
		Parse.Cloud.run("sendPushForPhotoNewReviewers", newReviewers);
		var photo = photoRecieved; // TODO - get rid of this
		photoRecieved.save().then(writeReviwersIds(photo, newReviewers));
	});
	
});

Parse.Cloud.define("sendPushForPhotoObject", function(request) {
	var userQuery = new Parse.Query(Parse.User);
	userQuery.containedIn("userNumber", request.get("Reviewers"));
	
	// Find devices associated with these users
	var pushQuery = new Parse.Query(Parse.Installation);
	pushQuery.matchesQuery("user", userQuery);
	
	// Send push notification to query
	Parse.Push.send({
		where: pushQuery,
		data: {
			alert: "New Rando to review!"
		}, 
			  success: function() {
				    // Push was successful
				  },
				  error: function(error) {
				    // Handle error
				  }
		
	});

});

Parse.Cloud.define("sendPushForPhotoNewReviewers", function(request) {
	var userQuery = new Parse.Query(Parse.User);
	userQuery.containedIn("userNumber", request);
	
	// Find devices associated with these users
	var pushQuery = new Parse.Query(Parse.Installation);
	pushQuery.matchesQuery("user", userQuery);
	
	// Send push notification to query
	Parse.Push.send({
		where: pushQuery,
		data: {
			alert: "New Rando to review!"
		}, 
			  success: function() {
				    // Push was successful
				  },
				  error: function(error) {
				    // Handle error
				  } 
		
	});

});

Parse.Cloud.afterSave("Photo", function(request) {
	if(request.object.existed() == false) {
		var query = new Parse.Query("holder"); //TODO - make this thru pick2RandomUsersSimple
 		query.get("tsaSJggSgZ", { //This is holder for last user number
 			  success: function(holder) {
 				var min = 1; //for further improvements
 			    var userCount =  holder.get("userCount");
 				var max = userCount;
 			    var userCount1 = Math.floor(Math.random() * (max - min + 1)) + min;
 			    var userCount2 = 1; // initialized for crash protection. TODO - deprecate
 			    for (var i = 0; i < 100; i++) { //TODO random using [0;n) (n;max] where n  - is userCount1
 					userCount2 = Math.floor(Math.random() * (max - min + 1)) + min;
 					if (userCount1 != userCount2) {
 						break;
 					}
 				}
 			    var userArray =[userCount1,userCount2];
 			    
 				 var photo = request.object;
 			 		photo.set("Reviewers_num", userArray); // hope result is array
 			 		
 			 		photo.save().then(writeReviwersIds(photo, userArray));
 			 		
 			 		//Parse.Cloud.run("writeReviwersIds", {photo:photo,array:userArray});
 			 		Parse.Cloud.run("sendPushForPhotoNewReviewers", userArray);
 			 		
 				}
 		}); 
	}	
});

Parse.Cloud.afterSave(Parse.User, function(request) {
	if(request.object.existed() == false) {
		var query = new Parse.Query("holder");
		query.get("tsaSJggSgZ", { //This is holder for last user number
			  success: function(holder) {
			    var userCount =  holder.get("userCount");
			    userCount = userCount+1;
			    request.object.set("userNumber", userCount);
			    holder.increment("userCount");
			    holder.save();
			    request.object.save();
				}
		});
	}
});

function writeReviwersIds(photo, array) {
	var userQuery = new Parse.Query(Parse.User);
	userQuery.containedIn("userNumber", array);
	userQuery.find({
		success:function(list){
			for (var i = 0; i < list.length; i++) {
				var userId = list[i].id;
				photo.add("Reviewers",userId);
			}
			photo.save();
		}	
	});
};

function removeOldReviewer(photo, id, num){
    photo.remove("Reviewers", id);
    photo.remove("Reviewers_num", num);
    photo.save();
}

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


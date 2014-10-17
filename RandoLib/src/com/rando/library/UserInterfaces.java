package com.rando.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SERGant on 11.10.2014.
 * 
 * Alexey Kazin - alexeykazin@gmail.com
 */
public class UserInterfaces {
    public interface IUser {
        public String GetUID(); // Unique id for user. Consists from letters (all cases) and digits
        public String GetUserName(); // Username - can be formed from letters (all cases) and digits
        public void SetUserName(String userName); // Sets the name in IUser
        public String GetAvatarUrl(); // GetAvatar
        public File GetAvatar(); // GetAvatar
    }

    public interface ILoggedUser extends IUser {
        String GetUserEmail(); //get email
        void SetUserEmail(String userEmail);// Sets the email
    }

    public enum LOGINRESULT {
        SUCCESS, BADPASSWORD, NOTEXIST, UNDEFINED //for IUserLoginResult
    }

    public interface IUserLoginResult {
        LOGINRESULT GetUserLoginResult(); //See LOGINRESULT for possible values
        ILoggedUser GetLoggedUser(); // Returns null if login failed
    }

    public interface IUserLoginCallback {
        void OnUserLogin(IUserLoginResult loginResult); //implement for callback purposes
    }

    public enum REGISTERRESULT {
        SUCCESS, USEREXISTS, BADPASSWORD, EMPTYDATA,UNDEFINED //for IUserRegisterResult
    }

    public interface IUserRegisterResult {
        REGISTERRESULT GetUserRegisterResult(); //see REGISTERRESULT for possible values
        IUser GetRegisteredUser(); // null if register failed
    }

    public interface IUserRegisterCallback {
        void OnUserRegister(IUserRegisterResult registerResult); //implement for callback purposes
    }
    
    public interface IUserSaveResult{
    	GENERALERROR getUserSaveResult();
    }
     
    public interface IUserSaveCallback{
    	void OnUserSave(IUserSaveResult saveResult); //implement for callback purposes
    }

    public interface IUserManager {
        public ILoggedUser GetCurrentUser(); //Returns current user by ParseUser.getCurrentUser (locally). Returns null if no logged user. - fast
        public void LogInUser(String userName, String userPassword, IUserLoginCallback loginCallback); //Login registered user - background web task - ~1 sec
        public void LogOffUser(); // Logoff current user - locally, web access optional - fast
        public void RegisterUser(String userName, String userPassword, String userEmail, IUserRegisterCallback registerCallback); // Register user - web background task - ~1 sec
        public void SaveCurrentUser(IUserSaveCallback userSaveCallback); // Save current logged Parse User. 
        public void SaveIPhoto(IPhoto photo, IPhotoSaveCallback photoSaveCallback); //Save photo to database. Also sends to 2 random users. Note: saves eventually. If no network will save when connection established.
        public void SaveIComment(IComment comment, ICommentSaveCallback commentSaveCallback);//Save comment to database. Note: saves eventually. If no network will save when connection established.
        public void GetPhotoComments(String photoId, boolean recent, int numberOfRecentComments, ICommentGetCommentCallback commentGetCallback); // get all cooments for this PhotoID
        public void GetPhotoById(String photoId, IPhotoGetCallback photoGetResult);// gets single photo by id
    }
    
    public interface IPhoto {
    	public String GetPhotoId(); //photo unique id
    	public String GetTitle(); // photo title
    	public String GetPhotoUrl(); //gets url of photo file for loading purposes
    	public File GetPhoto(); // actual file of photo - less than 10mb
    	public int GetLikes(); // number of likes. Counts by number of id's of users that liked.
    	public String GetCreatedBy(); // id of user that created this photo
    	public Date GetCreatedAt(); // returns time of creation
    	public Date LastLikeAt(); // for calculating of expire date/time
    	public List<String> getReviewersIds(); //returns reviewers Ids
    }
    

    public interface IPhotoSaveResult{
    	GENERALERROR GetPhotoSaveResult();
    }
    
    public interface IPhotoSaveCallback {
    	void onPhotoSave(IPhotoSaveResult photoSaveResult);
    }
    
    public interface IPhotoGetResult {
    	GENERALERROR getPhotoGetResult();
    	IPhoto getPhoto();
    }
    
    public interface IPhotoGetCallback {
    	void onIPhotoGet(IPhotoGetResult photoGetResult);
    }
    
    public interface IComment {
    	public String GetCommentId(); //get comment id
    	public Date GetCreatedAt(); // returns creation time
    	public String GetParentId(); //returns parent id. Parent = commented photo
    	public String GetCommentString(); //returns actual comment
    	public String GetLocale(); // Locale in string format
    }
    
    public interface ICommentSaveResult{
    	GENERALERROR GetCommentSaveResult();
    }
    
    public interface ICommentSaveCallback {
    	void onCommentSave(ICommentSaveResult saveResult);
    }
    
    public interface ICommentGetResult {
    	GENERALERROR GetErrorCode(); 
    	IComment[] GetComments();
    }
    public interface  ICommentGetCommentCallback{
    	void onGetComment(ICommentGetResult getCommentResult);
    }
    
    public enum GENERALERROR{
    	OTHERCAUSE,INTERNALSERVERERROR,CONNECTIONFAILED,OBJECTNOTFOUND,INVALIDQUERY,INVALIDCLASSNAME,MISSINGOBJECTID,INVALIDKEYNAME,INVALIDPOINTER,INVALIDJSON,COMMANDUNAVAILABLE,NOTINITIALIZED,INCORRECTTYPE,INVALIDCHANNELNAME,PUSHMISCONFIGURED,OBJECTTOOLARGE,OPERATIONFORBIDDEN,CACHEMISS,INVALIDNESTEDKEY,INVALIDFILENAME,INVALIDACL,TIMEOUT,INVALIDEMAILADDRESS,DUPLICATEVALUE,INVALIDROLENAME,EXCEEDEDQUOTA,SCRIPTFAILED,USERNAMEMISSING,PASSWORDMISSING,USERNAMETAKEN,EMAILTAKEN,EMAILMISSING,EMAILNOTFOUND,SESSIONMISSING,MUSTCREATEUSERTHROUGHSIGNUP,ACCOUNTALREADYLINKED,LINKEDIDMISSING,INVALIDLINKEDSESSION,UNSUPPORTEDSERVICE, SUCCESS
    }
}

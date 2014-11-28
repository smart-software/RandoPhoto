package com.rando.library.randomanager;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.rando.library.LibManager.GENERALERROR;

public class IRandoManagerInterfaces {
	

public interface IRandoManager {
    public IRandoPhoto GetLastRando(IGetLastRandoCallback getLastRandoCallback);
    public void SaveIPhoto(Bitmap photo, IPhotoSaveCallback photoSaveCallback); //Save photo to database. Also sends to 2 random users. Note: saves eventually. If no network will save when connection established.
    public void SaveIComment(IComment comment, ICommentSaveCallback commentSaveCallback);//Save comment to database. Note: saves eventually. If no network will save when connection established.
    public void GetRecentPhotoComments(String photoId, int numberOfRecentComments, ICommentGetCommentCallback commentGetCallback); // get all cooments for this PhotoID
    public void GetComments(String photoId, int fromComment, int toComment, ICommentGetCommentCallback commentGetCallback); // fromComment - the most recent one
    public void GetTotalNumberOfComments(String photoId, IGetTotalNumberOfCommentsCallback callback);
    public void GetPhotoById(String photoId, IPhotoGetCallback photoGetResult);// gets single photo by id
    public void LikePhotoById(String photoId, ILikePhotoCallback likePhotoCallback);
}
public interface IGetLastRandoResult {
	public IRandoPhoto getRando();
	public GENERALERROR getError(); 
}
public interface IGetLastRandoCallback {
	public void OnGetLastRando(IGetLastRandoResult getLastRandoResult);
}

public interface IPhotoSaveResult{
	GENERALERROR GetPhotoSaveResult();
}

public interface IPhotoSaveCallback {
	void onPhotoSave(IPhotoSaveResult photoSaveResult);
}

public interface IRandoPhotoGetResult {
	GENERALERROR getPhotoGetResult();
	IRandoPhoto getPhoto();
}

public interface IPhotoGetCallback {
	void onIPhotoGet(IRandoPhotoGetResult photoGetResult);
}
public interface IComment {
	public String GetCommentId(); //get comment id
	public Date GetCreatedAt(); // returns creation time
	public String GetParentId(); //returns parent id. Parent = commented photo
	public String GetCommentString(); //returns actual comment
	public String GetLocale(); // Locale in string format
	public String GetCreatedBy(); // returns author of comment
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

public interface IGetTotalNumberOfCommentsResult {
	int getTotalNumberOfComments();
	GENERALERROR GetErrorCode(); 
}

public interface IGetTotalNumberOfCommentsCallback {
	void onGetTotalNumberOfComments(IGetTotalNumberOfCommentsResult result);
}

public interface IPushReceiveResult {
	String getMessage();
}

public interface IPushReceiveCallback {
	void onPushOpen(Context context, IPushReceiveResult pushReceiveResult);
	Class<? extends Activity> onPushGetActivityForIntent( Intent intent);
}

public interface ILikePhotoCallback {
	void onSuccess(String photoId);
	void onFail(String photoId, GENERALERROR error);
}
}
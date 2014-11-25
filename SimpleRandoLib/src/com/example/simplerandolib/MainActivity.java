package com.example.simplerandolib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.callbacks.CommentGetCommentCallback;
import com.example.callbacks.CommentSaveCallback;
import com.example.callbacks.GetLastRandoCallback;
import com.example.callbacks.GetTotalNumberOfCommentsCallback;
import com.example.callbacks.PhotoGetCallback;
import com.example.callbacks.PhotoSaveCallback;
import com.example.callbacks.PushReceiveCallback;
import com.example.callbacks.UserGetAvatarCallback;
import com.parse.ParseUser;
import com.rando.library.LibManager;
import com.rando.library.randomanager.Comment;
import com.rando.library.randomanager.IRandoManagerInterfaces.IComment;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentGetCommentCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.ICommentSaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetLastRandoCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IGetTotalNumberOfCommentsCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoGetCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPhotoSaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IRandoManager;
import com.rando.library.randomanager.IRandoPhoto;
import com.rando.library.randomanager.RandoManager;
import com.rando.library.randomanager.RandoPhoto;
import com.rando.library.usermanager.User;
import com.rando.library.usermanager.UserInterfaces.ILoggedUser;
import com.rando.library.usermanager.UserInterfaces.IUser;
import com.rando.library.usermanager.UserInterfaces.IUserGetAvatarCallback;
import com.rando.library.usermanager.UserInterfaces.IUserManager;
import com.rando.library.usermanager.UserManager;

public class MainActivity extends Activity {
	
	Button but1,but2,but3,but4,but5,but6,but7,but8,but9,but10,but11,but12,but13,but14,but15,but16,but17,but18,but19;
	TextView textCallback;
	static Context CONTEXT;
	IUserManager mUserManager = new UserManager();
	IRandoManager mRandoManager = new RandoManager();
	IUser mGenericUser;
	static TextView mCallbackText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CONTEXT = getApplication().getApplicationContext();
        textCallback = (TextView) findViewById(R.id.textCallback);
        mCallbackText = (TextView) findViewById(R.id.callback_general_text);
        initializeButtons();
    
    }
    
    @Override
    protected void onResume() {
    	if (getIntent().hasExtra("push")) {
    	String pushMessage;
    	pushMessage = getIntent().getExtras().getString("push");
    	mCallbackText.setText("Push opened" + pushMessage);
    	}
    	
    	super.onResume();
    }
    
    

private void initializeButtons(){
    but1 = (Button) findViewById(R.id.Button_1);
    but2 = (Button) findViewById(R.id.Button_2);
    but3 = (Button) findViewById(R.id.Button_3);
    but4 = (Button) findViewById(R.id.Button_4);
    but5 = (Button) findViewById(R.id.Button_5);
    but6 = (Button) findViewById(R.id.Button_6);
    but7 = (Button) findViewById(R.id.Button_7);
    but8 = (Button) findViewById(R.id.Button_8);
    but9 = (Button) findViewById(R.id.Button_9);
    but10 = (Button) findViewById(R.id.Button_10);
    but11 = (Button) findViewById(R.id.Button_11);
    but12 = (Button) findViewById(R.id.Button_12);
    but13 = (Button) findViewById(R.id.Button_13);
    but14 = (Button) findViewById(R.id.Button_14);
    but15 = (Button) findViewById(R.id.Button_15);
    but16 = (Button) findViewById(R.id.Button_16);
    but17 = (Button) findViewById(R.id.Button_17);
    but18 = (Button) findViewById(R.id.Button_18);
    but19 = (Button) findViewById(R.id.Button_19);
    
    
    but1.setText("Initialize");
    but1.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		LibManager.InitializeLibraryLight(CONTEXT);
    	}
    });
    
    but2.setText("LogIn");
    but2.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		mUserManager.LogInUser("user24677", "qwerty", null);
    	}
    });
    
    but3.setText("GetCurrentUser");
    but3.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		ILoggedUser user = mUserManager.GetCurrentUser();
    		if (user!=null){
    			textCallback.setText("CurrentUser" + user.GetUserName()+", email:"+user.GetUserEmail());
    		}
    		else {
    			textCallback.setText("User is null");
    		}
    	}
    });
    
    but4.setText("LogOff");
    but4.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		mUserManager.LogOffUser();
    		textCallback.setText("User logged off");
    	}
    });
    
    but5.setText("RegisterUser");
    but5.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		String numberUser = Long.toString(System.currentTimeMillis());
    		String username = "user" + numberUser.substring(numberUser.length()-5);
    		File avatar = createFile();

    		mUserManager.RegisterUser(username, "qwerty", username+"@randoPhoto.org", avatar, CONTEXT, null);
    	}
    });
    
    but6.setText("SaveCurrentUser");
    but6.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		mUserManager.SaveCurrentUser(null);
    	}
    });
    
    but7.setText("HasAvatarFile");
    but7.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		File file = createFile();
    		IUser user = new User("EVHodIfCao", "user25409", file);
    		boolean hasAvatar = user.HasAvatarFile();
    	}
    });
    
    but8.setText("GetLastRando");
    but8.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		IGetLastRandoCallback callback = new GetLastRandoCallback();
    		mRandoManager.GetLastRando(callback);
    	}
    });
    
    but9.setText("saveIPhoto");
    but9.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		File file = createFile();
    		IRandoPhoto photo = new RandoPhoto("Test Photo", file, mUserManager.GetCurrentUser().GetUID());
    		IPhotoSaveCallback callback = new PhotoSaveCallback();
    		mRandoManager.SaveIPhoto(photo, callback);
    	}
    });
    
    but10.setText("SaveIComment");
    but10.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    		List<String> reviewersIDs = new ArrayList<String>();
    		reviewersIDs.add("user38637"); reviewersIDs.add("user11470");
    		String fileUrl = "http://files.parsetfss.com/876b74df-b84e-4ef6-9e2c-ae46b8ef18d4/tfss-d4e13990-a070-4763-8ab6-b41ea3776bf9-avatar.jpg";
    		
    		IRandoPhoto photo = new RandoPhoto("kbyUAkRG31", cal.getTime(), "Test Title", fileUrl, 0, ParseUser.getCurrentUser().getObjectId(), cal.getTime(), reviewersIDs);
    		ICommentSaveCallback callback = new CommentSaveCallback();
    		IComment comment = new Comment(photo.GetRandoID(),mUserManager.GetCurrentUser().GetUserName(), "Test comment string.", "ENG");
    		mRandoManager.SaveIComment(comment, callback);
    	}
    });
    
    but11.setText("GetRecentPhotoComments");
    but11.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		String photoId = "r9dxl8r6Wt";
    		ICommentGetCommentCallback callback = new CommentGetCommentCallback();
    		mRandoManager.GetRecentPhotoComments(photoId, 15, callback);
    	}
    });
    
    but12.setText("GetPhotoById");
    but12.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		String photoId = "r9dxl8r6Wt"; 
    		IPhotoGetCallback callback = new PhotoGetCallback();
    		mRandoManager.GetPhotoById(photoId, callback);
    	}
    });
    
    but13.setText("GetPhotoComments");
    but13.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		String photoId = "r9dxl8r6Wt";
    		int fromPhoto = 15;
    		int toPhoto = 30;
    		ICommentGetCommentCallback callback = new CommentGetCommentCallback();
    		mRandoManager.GetComments(photoId, fromPhoto, toPhoto, callback);
    	}
    });
    
    but14.setText("GetPhotoTotalComments");
    but14.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		String photoId = "r9dxl8r6Wt";
    		IGetTotalNumberOfCommentsCallback callback = new GetTotalNumberOfCommentsCallback();
    		mRandoManager.GetTotalNumberOfComments(photoId, callback);
    	}
    });
    
    but15.setText("GetTotalRandos");
    but15.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		IUser user = new User("EVHodIfCao", "user25409");
    		user.GetTotalRandos(null);
    	}
    });
    
    but16.setText("GetTotalLikes");
    but16.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		IUser user = new User("EVHodIfCao", "user25409");
    		
    		user.GetTotalLikes(null);
    	}
    });
    
    but17.setText("GetUserAvatarFile");
    but17.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		IUser user = new User("EVHodIfCao", "user25409");
    		IUserGetAvatarCallback callback = new UserGetAvatarCallback();
    		File avatarLocalFile = user.GetAvatar(callback);
    	}
    });
    
    but18.setText("Initialize broadcast");
    but18.setOnClickListener(new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		LibManager.setPushCallback(new PushReceiveCallback());
    		LibManager.EnablePushNotifications();
    	}
    });

}

protected File createFile() {
		// To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
	File mediaFile = null;
		if (isExternalStorageAvailable()) {
			// 1. Get the external storage directory
			String appName = MainActivity.this.getString(R.string.app_name);
			File mediaStorageDir = new File(
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					appName);
			
			// 2. Create our subdirectory
			if (! mediaStorageDir.exists()) {
				if (! mediaStorageDir.mkdirs()) {
					Log.e("TAG", "Failed to create directory.");
					return null;
				}
			}
			
			// 3. Create a file name
			// 4. Create the file
			
			Date now = new Date();
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
			
			String path = mediaStorageDir.getPath() + File.separator;
			mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
			ImageView view = (ImageView) findViewById(R.id.imageViewLocal);
			Bitmap bitmap = drawableToBitmap(view.getBackground());
			writeTofile(bitmap, mediaFile);
			
		}
		return mediaFile;
}
private boolean isExternalStorageAvailable() {
	String state = Environment.getExternalStorageState();
			
	if (state.equals(Environment.MEDIA_MOUNTED)) {
	return true;
	}
	else {
	return false;
	}
}
public static Bitmap drawableToBitmap (Drawable drawable) {
		    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(bitmap); 
		    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		    drawable.draw(canvas);
		    return bitmap;
		}
private void writeTofile(Bitmap bitmap, File file) {
	FileOutputStream out = null;
	try {
	    out = new FileOutputStream(file);
	    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
	    // PNG is a lossless format, the compression factor (100) is ignored
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
	        if (out != null) {
	            out.close();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

public static TextView getCallbackTExtView(){
	return mCallbackText;
}

public static Context getContext(){
	return CONTEXT;
}


}
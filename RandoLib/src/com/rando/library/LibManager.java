package com.rando.library;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.rando.library.randomanager.IRandoManagerInterfaces.IPushReceiveCallback;

public final class LibManager{
    private static final String LIBVERSION = "3.2";
    private static final String LIBTAG = "[RandoLib]";
    public static IPushReceiveCallback pushCallbackStatic; //TODO Find another way to pass this callback to pushRecevier

    public static void InitializeLibrary (Context context)
    {
        // keys of RandoApp in Parse.com database. Client keys.
        Parse.initialize(context, "CBAorkA9uvUOf6PFYmVE2zw0Tkf54D8FX4LWaB6l", "axKtzQMEXuOK3Q9hzk84MQxE9Uk1Y6fty9RhA14B");
        EnablePushNotifications();
        createAppDir();
    }

    public static void LogText(String text) {
        Log.d(LIBTAG, text);
    }

    public static String GetLibraryVersion() {
        return LIBVERSION;
    }
    
    public static void InitializeLibraryLight (Context context)
    {
        // keys of RandoApp in Parse.com database. Client keys.
        Parse.initialize(context, "CBAorkA9uvUOf6PFYmVE2zw0Tkf54D8FX4LWaB6l", "axKtzQMEXuOK3Q9hzk84MQxE9Uk1Y6fty9RhA14B");
    }

    
    public static void EnableDataStore(Context context){
    	// enable datastore (sd and internal filesystem)
        Parse.enableLocalDatastore(context);
    }
    
    public static void EnablePushNotifications(){
    	// enable Push Notifications by subscribing to broadcast channel
    	ParsePush.subscribeInBackground("", new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				//TODO - callback. Note: this method works "eventually"
				if (e == null) {
				      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
				    } else {
				      Log.e("com.parse.push", "failed to subscribe for push", e);
				    }
			}
		});
    	if (ParseUser.getCurrentUser()!=null) {
    	String userPersonalChannel = "user_"+ParseUser.getCurrentUser().getObjectId();	
    	ParsePush.subscribeInBackground(userPersonalChannel, new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				// TODO  - callback. Note: this method works "eventually"
				if (e == null) {
				      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
				    } else {
				      Log.e("com.parse.push", "failed to subscribe for push", e);
				    }
			}
		});
    	}
    	
    	// Save the current Installation to Parse.
    	ParseInstallation installation = ParseInstallation.getCurrentInstallation();
    	installation.put("user",ParseUser.getCurrentUser());
    	installation.saveInBackground();
    }
    
    public static void setPushCallback(IPushReceiveCallback pushCallback) {
    	pushCallbackStatic = pushCallback;
    }
    
    public static enum GENERALERROR{
    	OTHERCAUSE,INTERNALSERVERERROR,CONNECTIONFAILED,OBJECTNOTFOUND,INVALIDQUERY,INVALIDCLASSNAME,MISSINGOBJECTID,INVALIDKEYNAME,INVALIDPOINTER,INVALIDJSON,COMMANDUNAVAILABLE,NOTINITIALIZED,INCORRECTTYPE,INVALIDCHANNELNAME,PUSHMISCONFIGURED,OBJECTTOOLARGE,OPERATIONFORBIDDEN,CACHEMISS,INVALIDNESTEDKEY,INVALIDFILENAME,INVALIDACL,TIMEOUT,INVALIDEMAILADDRESS,DUPLICATEVALUE,INVALIDROLENAME,EXCEEDEDQUOTA,SCRIPTFAILED,USERNAMEMISSING,PASSWORDMISSING,USERNAMETAKEN,EMAILTAKEN,EMAILMISSING,EMAILNOTFOUND,SESSIONMISSING,MUSTCREATEUSERTHROUGHSIGNUP,ACCOUNTALREADYLINKED,LINKEDIDMISSING,INVALIDLINKEDSESSION,UNSUPPORTEDSERVICE, SUCCESS
    }
    
	public static GENERALERROR decodeError(int errorCode){
		GENERALERROR error;
		int[] listOfErrors = {-1,1,100,101,102,103,104,105,106,107,108,109,111,112,115,116,119,120,121,122,123,124,125,137,139,140,141,200,201,202,203,204,205,206,207,208,250,251,252};
		int indexError = Arrays.asList(listOfErrors).indexOf(errorCode);
		if (indexError == -1 ) { //"-1" means "not contained in array"
			indexError = 0;
		}
		error = GENERALERROR.values()[indexError];
		return error;
	}
	public static ParseFile uriToParseFile(Context context, Uri uri){
		byte[] fileBytes = FileHelper.getByteArrayFromFile(context, uri);
		ParseFile file = null;
    	if (fileBytes == null) {
			//TODO Error getting Bytes from file
		}
		else {
			fileBytes = FileHelper.reduceImageForUpload(fileBytes); //SHORT_SIDE_TARGET = 1600
			file = new ParseFile("file.jpg", fileBytes);
		}
		return file;
	}
	public static  ParseFile fileToParseFile(File fileInput) {
		byte[] fileBytes = convertFileToByte(fileInput);
		fileBytes = FileHelper.reduceImageForUpload(fileBytes); //SHORT_SIDE_TARGET = 1600
		ParseFile fileParse = new ParseFile("file.jpg", fileBytes);
		return fileParse;
	}
	
	public static ParseFile BitmapToParseFile(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		byteArray = FileHelper.reduceImageForUpload(byteArray); //SHORT_SIDE_TARGET = 1600
		ParseFile fileParse = new ParseFile("file", byteArray);
		return fileParse;
	}
	public static  byte[] convertFileToByte(File fileInput){
	    int size = (int) fileInput.length();
	    byte[] bytes = new byte[size];
	    try {
	        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(fileInput));
	        buf.read(bytes, 0, bytes.length);
	        buf.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return bytes;
	}
	
	public static File convertByteToFile(String filename, byte[] byteArray) { 
        File file = createFile(filename);
        byte[] bFile = byteArray;
 
        try { //convert array of bytes into file
	    FileOutputStream fileOuputStream = 
                  new FileOutputStream(file); 
	    fileOuputStream.write(bFile);
	    fileOuputStream.close();
 
	    System.out.println("Done");
        }catch(Exception e){
            e.printStackTrace();
        }
		return file;
	}
	
	public static File createFile(String filename) {
		// To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.
	File mediaFile = null;
		if (isExternalStorageAvailable()) {
			// 1. Get the external storage directory
			String appName = "RandoPhoto";
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
			
			String path = mediaStorageDir.getPath() + File.separator;
			mediaFile = new File(path + filename + ".png");	
		}
		return mediaFile;
}
	
	private static void createAppDir(){
		if (isExternalStorageAvailable()) {
			// 1. Get the external storage directory
			String appName = "RandoPhoto";
			File mediaStorageDir = new File(
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					appName);
			
			// 2. Create our subdirectory
			if (! mediaStorageDir.exists()) {
				if (! mediaStorageDir.mkdirs()) {
					Log.e("TAG", "Failed to create directory.");
				}
			}
	}
}

	private static boolean isExternalStorageAvailable() {
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

public static Bitmap byteToBitmap(byte[] bitmapdata) {
	Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length);
	return bitmap;
}

public static void writeTofile(Bitmap bitmap, File file) {
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
}

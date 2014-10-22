package com.rando.library;
/**
 * Created by SERGant on 11.10.2014.
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public final class LibManager{
    private static final String LIBVERSION = "0.2";
    private static final String LIBTAG = "[RandoLib]";

    public static void InitializeLibrary (Context context)
    {
    	// enable datastore (sd and internal filesystem)
        Parse.enableLocalDatastore(context);
        // keys of RandoApp in Parse.com database. Client keys.
        Parse.initialize(context, "CBAorkA9uvUOf6PFYmVE2zw0Tkf54D8FX4LWaB6l", "axKtzQMEXuOK3Q9hzk84MQxE9Uk1Y6fty9RhA14B");
   
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
			public void done(ParseException arg0) {
				//TODO - callback. Note: this method works "eventually"
			}
		});
    	if (ParseUser.getCurrentUser()!=null) {
    	String userPersonalChannel = "user_"+ParseUser.getCurrentUser().getObjectId();	
    	ParsePush.subscribeInBackground(userPersonalChannel, new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// TODO  - callback. Note: this method works "eventually"
			}
		});
    	}
    }
    
    public static enum GENERALERROR{
    	OTHERCAUSE,INTERNALSERVERERROR,CONNECTIONFAILED,OBJECTNOTFOUND,INVALIDQUERY,INVALIDCLASSNAME,MISSINGOBJECTID,INVALIDKEYNAME,INVALIDPOINTER,INVALIDJSON,COMMANDUNAVAILABLE,NOTINITIALIZED,INCORRECTTYPE,INVALIDCHANNELNAME,PUSHMISCONFIGURED,OBJECTTOOLARGE,OPERATIONFORBIDDEN,CACHEMISS,INVALIDNESTEDKEY,INVALIDFILENAME,INVALIDACL,TIMEOUT,INVALIDEMAILADDRESS,DUPLICATEVALUE,INVALIDROLENAME,EXCEEDEDQUOTA,SCRIPTFAILED,USERNAMEMISSING,PASSWORDMISSING,USERNAMETAKEN,EMAILTAKEN,EMAILMISSING,EMAILNOTFOUND,SESSIONMISSING,MUSTCREATEUSERTHROUGHSIGNUP,ACCOUNTALREADYLINKED,LINKEDIDMISSING,INVALIDLINKEDSESSION,UNSUPPORTEDSERVICE, SUCCESS
    }
    
	public static GENERALERROR decodeError(int errorCode){
		GENERALERROR error;
		int[] listOfErrors = {-1,1,100,101,102,103,104,105,106,107,108,109,111,112,115,116,119,120,121,122,123,124,125,137,139,140,141,200,201,202,203,204,205,206,207,208,250,251,252};
		int indexError = Arrays.asList(listOfErrors).indexOf(errorCode);
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
			fileBytes = FileHelper.reduceImageForUpload(fileBytes); //SHORT_SIDE_TARGET = 1280
			file = new ParseFile("avatar.jpg", fileBytes);
		}
		return file;
	}
	public static  ParseFile fileToParseFile(File fileInput) {
		byte[] fileBytes = convertFileToByte(fileInput);
		fileBytes = FileHelper.reduceImageForUpload(fileBytes); //SHORT_SIDE_TARGET = 1280
		ParseFile fileParse = new ParseFile("avatar.jpg", fileBytes);
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
}

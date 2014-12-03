package com.rando.library;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

public class FileHelper {
	
	public static final String TAG = FileHelper.class.getSimpleName();
	
	public static final int SHORT_SIDE_TARGET = 1600;
	public static final int SHORT_SIDE_Preview_TARGET = 600;
	
	public static byte[] getByteArrayFromFile(Context context, Uri uri) {
		byte[] fileBytes = null;
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        
        if (uri.getScheme().equals("content")) {
        	try {
        		inStream = context.getContentResolver().openInputStream(uri);
        		outStream = new ByteArrayOutputStream();
            
        		byte[] bytesFromFile = new byte[1024*1024]; // buffer size (1 MB)
        		int bytesRead = inStream.read(bytesFromFile);
        		while (bytesRead != -1) {
        			outStream.write(bytesFromFile, 0, bytesRead);
        			bytesRead = inStream.read(bytesFromFile);
        		}
            
        		fileBytes = outStream.toByteArray();
        	}
	        catch (IOException e) {
	        	Log.e(TAG, e.getMessage());
	        }
	        finally {
	        	try {
	        		inStream.close();
	        		outStream.close();
	        	}
	        	catch (IOException e) { /*( Intentionally blank */ }
	        }
        }
        else {
        	try {
	        	File file = new File(uri.getPath());
	        	FileInputStream fileInput = new FileInputStream(file);
	        	fileBytes = IOUtils.toByteArray(fileInput);
        	}
        	catch (IOException e) {
        		Log.e(TAG, e.getMessage());
        	}
       	}
        
        return fileBytes;
	}
	
	public static byte[] reduceImageForUpload(byte[] imageData) {
		Bitmap bitmap = ImageResizer.resizeImageMaintainAspectRatio(imageData, SHORT_SIDE_TARGET);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		byte[] reducedData = outputStream.toByteArray();
		try {
			outputStream.close();
		}
		catch (IOException e) {
			// Intentionally blank
		}
		
		return reducedData;
	}
	
	public static byte[] reduceImageForPreview(byte[] imageData) {
		Bitmap bitmap = ImageResizer.resizeImageMaintainAspectRatio(imageData, SHORT_SIDE_Preview_TARGET);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		byte[] reducedData = outputStream.toByteArray();
		try {
			outputStream.close();
		}
		catch (IOException e) {
			// Intentionally blank
		}
		
		return reducedData;
	}

	public static String getFileName(Context context, Uri uri, String fileType) {
		String fileName = "uploaded_file.";
		fileName += "png";
		
		
		return fileName;
	}

	public static String getFileNamePreview(Context context, Uri uri, String fileType) {
		String fileName = "preview_file.";
			fileName += "png";

		return fileName;
	}
}
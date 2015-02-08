package com.example.smartdiet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Path;
import android.provider.MediaStore.Files;
import android.util.Log;

/* Class to create and open a food database. Author: Hieu Tran */
public class FoodDatabaseHelper extends SQLiteOpenHelper {
	
	private static String DB_PATH = "";
	private static final String DB_NAME = "usda";
	private static int version = 1;
	
	private Context helperContext;
	SQLiteDatabase db;
	
	public FoodDatabaseHelper(Context context) {
		super(context, DB_NAME, null, version);
		
		if(android.os.Build.VERSION.SDK_INT >= 17){
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";         
		}
		else
		{
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		this.helperContext = context;
		
		db = getWritableDatabase();
	}
	
	/**
	 *  Change the version of the database 
	 *  */
	public void setVersion(int newVersion){
		version = newVersion;
	}
	
	 /**
     * Creates a empty database on the system and rewrites it
     * */
    public void createDataBase(){
    		
    		Log.i("database", "database does not exist yet");
 
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    }
 
   // }
    
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open local db as the input stream
    	InputStream myInput = helperContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
    	
    	Log.i("database", "writing " + DB_NAME + " from " + helperContext.getAssets());
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	
    	Log.i("database", "wrote " + Integer.toString(length));
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
    
    public void openDataBase() throws SQLException{
    	 
    	//Open the database
    	Log.i("database", "opening database " + DB_NAME);
        String myPath = DB_PATH + DB_NAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    @Override
	public synchronized void close() {
    	if(db != null)
    	    db.close();
 
    	super.close();
	}
    
    

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}

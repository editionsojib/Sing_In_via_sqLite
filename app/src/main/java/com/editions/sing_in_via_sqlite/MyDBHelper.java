package com.editions.sing_in_via_sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "usersDatabase.db";
    public static final String TABLE_NAME = "user_table";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final int VERSION_NUMBER = 2;
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) NOT NULL, "+EMAIL+" VARCHAR(255) NOT NULL, "+USERNAME+" VARCHAR(255) NOT NULL, "+PASSWORD+" VArCHAR(255) NOT NULL)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    Context context;//Get Context from MainActivity
    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Log.d("MyDBHelper", "Table created successfully");
            //Toast.makeText(context, "Table created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error creating table", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
            Log.d("MyDBHelper", "Table upgraded successfully");
        } catch (SQLException e) {
            Log.e("MyDBHelper", "Error upgrading table", e);
        }

    }//END

    //Insert Data from ActivityRegister for user
    public long insertData(UserDetails userDetails){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //ContentValues Get Data From userDetails Getter Method
        contentValues.put(NAME, userDetails.getName());
        contentValues.put(EMAIL, userDetails.getEmail());
        contentValues.put(USERNAME, userDetails.getUsername());
        contentValues.put(PASSWORD, userDetails.getPassword());

        //Insert Data to Database
        long rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        //sqLiteDatabase.close(); // Close the database
        return rowId;//Data Insert Successful RowId Return 1 else -1
    }//END





    //Find User From Database for Login via Email & Password
    public Boolean findUser(String email, String password){
        //Call Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //Select All data from user_table & Store in Cursor
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        boolean result = false;//Boolean Default False




        //Check Data From Database
        if (cursor.getCount() ==0){
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                @SuppressLint("Range") String emailDB = cursor.getString(cursor.getColumnIndex(EMAIL));//Get Email From Database
                @SuppressLint("Range") String passwordDB = cursor.getString(cursor.getColumnIndex(PASSWORD));//Get Password From Database
                //Check Email & Password Match
                if (email.equals(emailDB) && password.equals(passwordDB)){
                    result = true;
                    break;//When Email & Password Match Stop Loop
                }
            }

        }
        return result;//is success return true else false

    }//END
}//END

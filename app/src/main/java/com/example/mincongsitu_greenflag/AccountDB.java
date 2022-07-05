package com.example.mincongsitu_greenflag;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AccountDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "AccountsDB";
    private static final int DB_VERSION = 1;

    private static final String ACCOUNTS = "Accounts";

    private static final String EMAIL_COL = "email";

    private static final String PW_COL = "password";


    // creating a constructor for our database handler.
    public AccountDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + ACCOUNTS + " ("
                + EMAIL_COL + " TEXT PRIMARY KEY, "
                + PW_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewAccount(String email, String password) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(EMAIL_COL, email);
        values.put(PW_COL, password);

        // after adding all values we are passing
        // content values to our table.
        db.insert(ACCOUNTS, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    public int doesEmailExist(String email){
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                AccountDB.EMAIL_COL
        };

// Filter results WHERE "title" = 'My Title'
        String selection = AccountDB.EMAIL_COL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                AccountDB.ACCOUNTS,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        onCreate(db);
    }
}


package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB_manager extends SQLiteOpenHelper {

    private static final String DbName = "200748D.sqlite";
    private static final int DB_version = 1;
    public DB_manager( Context context) {
        super(context , DbName , null,DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Query1 = "CREATE TABLE Account("
                + "AccNo TEXT primary key,"
                + "NameOfBank TEXT,"
                + "Name TEXT,"
                + "Balance REAL)";
        String Query2 = "CREATE TABLE Transactions ("
                + "Transaction_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "date TEXT,"
                + "AccNo TEXT,"
                + "Expenses_type TEXT ,"
                + "Amount REAL,"
                + "FOREIGN KEY(AccNo) REFERENCES Account(AccNo))";
        sqLiteDatabase.execSQL(Query1);
        sqLiteDatabase.execSQL(Query2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Account");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Transactions");
        onCreate(sqLiteDatabase);
    }
}

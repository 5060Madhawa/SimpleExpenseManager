package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DB_manager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    private DB_manager db_manager;
    public PersistentTransactionDAO(DB_manager db_manager){
        this.db_manager = db_manager;
    }
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount){
        SQLiteDatabase sqLiteDatabase = this.db_manager.getWritableDatabase();
        ContentValues values = new ContentValues();
        Long l1 = date.getTime();
        values.put("date",l1);
        values.put("AccNo",accountNo);
        values.put("Expenses_type",expenseType.toString());
        values.put("Amount",amount);

        sqLiteDatabase.insert("Transactions",null , values);
        sqLiteDatabase.close();

    }
    public List<Transaction> getAllTransactionLogs(){
        SQLiteDatabase sqLiteDatabase = this.db_manager.getReadableDatabase();
        Cursor cursorTransactions=sqLiteDatabase.rawQuery("select * from Transactions",null);
        List<Transaction> transactions = new ArrayList<>();

        if(cursorTransactions.moveToFirst()){
            do{
                transactions.add(new Transaction(new Date(cursorTransactions.getLong(1)),
                        cursorTransactions.getString(2),
                        ExpenseType.valueOf(cursorTransactions.getString(3)),
                        cursorTransactions.getDouble(4)));

            }while (cursorTransactions.moveToNext());
        }
        return transactions;

    }
    public List<Transaction> getPaginatedTransactionLogs(int limit){
        List<Transaction> transactions = getAllTransactionLogs();
        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        return transactions.subList(size - limit, size);

    }
}

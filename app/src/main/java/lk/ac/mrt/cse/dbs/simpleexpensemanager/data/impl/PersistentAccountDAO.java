package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DB_manager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {
    private DB_manager db_manager;
    public PersistentAccountDAO(DB_manager db_manager){
        this.db_manager  = db_manager;
    }
    public List<String> getAccountNumbersList(){
        SQLiteDatabase sqLiteDatabase = this.db_manager.getReadableDatabase();
        Cursor cursorAccountNumbers = sqLiteDatabase.rawQuery("select AccNo from Account",null);
        List<String> AccNumbers = new ArrayList<>();

        if (cursorAccountNumbers.moveToFirst()){
            do{
                AccNumbers.add(cursorAccountNumbers.getString(0));
            }while(cursorAccountNumbers.moveToNext());
        }
        return AccNumbers;

    }
    public List<Account> getAccountsList(){
        SQLiteDatabase sqLiteDatabase = this.db_manager.getReadableDatabase();
        Cursor cursorAccounts = sqLiteDatabase.rawQuery("select * from Account",null);
        List<Account> Accounts = new ArrayList<>();

        if (cursorAccounts.moveToFirst()){
            do{
                Accounts.add(new Account(cursorAccounts.getString(0),
                                         cursorAccounts.getString(1),
                                         cursorAccounts.getString(2),
                                         cursorAccounts.getDouble(3)));
            }while (cursorAccounts.moveToNext());
        }
        return Accounts;

    }
    public Account getAccount(String accountNo) throws InvalidAccountException{
        SQLiteDatabase sqLiteDatabase = this.db_manager.getReadableDatabase();
        String[] params = new String[]{accountNo};
        Cursor cursorAccount = sqLiteDatabase.rawQuery("select * from Account where AccNo = ?",params);

        if (cursorAccount.moveToFirst()){
            return new Account(cursorAccount.getString(0),
                               cursorAccount.getString(1),
                                cursorAccount.getString(2),
                                cursorAccount.getDouble(3));
        }
        String msg = "Account" + accountNo + "is invalid";
        throw new InvalidAccountException(msg);

    }
    public void addAccount(Account account){
        SQLiteDatabase sqLiteDatabase = this.db_manager.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("AccNo",account.getAccountNo());
        values.put("NameOfBank",account.getBankName());
        values.put("Name",account.getAccountHolderName());
        values.put("Balance",account.getBalance());

        sqLiteDatabase.insert("account",null,values);
        sqLiteDatabase.close();

    }
    public void removeAccount(String accountNo) throws InvalidAccountException{
        SQLiteDatabase sqLiteDatabase = this.db_manager.getWritableDatabase();
        sqLiteDatabase.delete("account","AccNo=?",new String[]{accountNo});
        sqLiteDatabase.close();

    }
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException{
        Account account = this.getAccount(accountNo);

        SQLiteDatabase sqLiteDatabase = this.db_manager.getWritableDatabase();
        ContentValues values = new ContentValues();

        double newBalance;
        if (expenseType == ExpenseType.EXPENSE){
            newBalance = account.getBalance() - amount;

        }
        else{
            newBalance = account.getBalance()+amount;
        }


        values.put("balance",newBalance);
        account.setBalance(newBalance);

        sqLiteDatabase.update("account",values,"AccNo = ?",new String[]{accountNo});

    }


}

package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.DB_manager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

public class PersistentDemoExpenseManager extends ExpenseManager {

    Context context;
    public PersistentDemoExpenseManager(Context context){
        this.context = context;
        try {
            setup();
        }catch (ExpenseManagerException e){
            e.printStackTrace();
        }

    }

    public void setup() throws ExpenseManagerException{
        DB_manager db_manager = new DB_manager(this.context);
        TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(db_manager);
        setTransactionsDAO(persistentTransactionDAO);

        AccountDAO persistentAccountDAO = new PersistentAccountDAO(db_manager);
        setAccountsDAO(persistentAccountDAO);
    }
}

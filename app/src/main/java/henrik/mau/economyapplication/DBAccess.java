package henrik.mau.economyapplication;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import Database.Database;
import Entities.Expense;
import Entities.Income;
import Database.DatabaseAccess;

public class DBAccess {
    private static final String DATABASE_NAME = "MYECONOMYAPPLICATION";
    private Database database;
    private DatabaseAccess dbAccess;
    private Controller controller;

    public DBAccess(Context context){
        database = Room.databaseBuilder(context, Database.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        dbAccess = database.databaseAccess();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void insertIncome(Income... income){
        dbAccess.insertIncome(income);
    }

    public void insertExpense(Expense... expense){
        dbAccess.insertExpense(expense);
    }

    public Expense getExpense(int id){
        Expense expense = dbAccess.getExpense(id);
        return expense;
    }

    public Income getIncome(int id){
        Income income = dbAccess.getIncome(id);
        return income;
    }

    public List<Income> getFilteredIncomeList(long dateFrom, long dateTo){
        return dbAccess.filterIncome(dateFrom, dateTo);
    }

    public List<Income> getAllIncomes(){
        return dbAccess.getAllIncomes();
    }

    public List<Expense> getFilteredExpenseList(long dateFrom, long dateTo) {
        return dbAccess.filterExpense(dateFrom, dateTo);
    }

    public List<Expense> getAllExpenses() {
        return dbAccess.getAllExpenses();
    }

    public void deleteExpense(int expenseId){
        dbAccess.deleteExpense(expenseId);
    }

    public void deleteIncome(int incomeId){
        dbAccess.deleteIncome(incomeId);
    }
}

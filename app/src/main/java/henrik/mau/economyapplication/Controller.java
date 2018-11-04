package henrik.mau.economyapplication;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import Entities.Expense;
import Entities.Income;
import Entities.User;
import Fragments.AddExpenseFragment;
import Fragments.AddIncomeFragment;
import Fragments.ButtonsFragment;
import Fragments.DataFragment;
import Fragments.ExpenseTransactionsFragment;
import Fragments.IncomeTransactionsFragment;
import Fragments.LogInFragment;

public class Controller {
    private MainActivity ui;
    private LogInFragment logInFragment;
    private DataFragment dataFragment;
    private ButtonsFragment buttonsFragment;
    private AddIncomeFragment addIncomeFragment;
    private IncomeTransactionsFragment incomeTransactionsFragment;
    private AddExpenseFragment addExpenseFragment;
    private ExpenseTransactionsFragment expenseTransactionsFragment;
    private DBAccess dbAccess;

    public Controller(MainActivity ui){
        this.ui = ui;
        initializeDataFragment();
        initializeAllFragments();
        initializeDatabase();
    }

    //INITIALIZE DATABASE
    private void initializeDatabase(){
        dbAccess = new DBAccess(ui);
    }

    //INITIALIZING ALL THE FRAGMENTS

    private void initializeAllFragments(){
        initializeLogInFragment();
        initializeButtonsFragment();
        initializeIncomesFragment();
        initializeExpensesFragment();
        initializeAddIncomeFragment();
        initializeAddExpenseFragment();
        initializeEditIncomeFragment();
        initializeEditExpenseFragment();

        setFragment(dataFragment.getActiveFragment());
    }

    private void initializeDataFragment() {
        dataFragment = (DataFragment) ui.getFragment("DataFragment");
        if(dataFragment == null){
            dataFragment = new DataFragment();
            ui.addFragment(dataFragment, "DataFragment");
            dataFragment.setActiveFragment("LogInFragment");
        }
        dataFragment.setController(this);
    }

    private void initializeLogInFragment(){
        logInFragment = (LogInFragment) ui.getFragment("LogInFragment");
        if(logInFragment == null){
            logInFragment = new LogInFragment();
        }
        logInFragment.setController(this);
        addLogInFragment();
    }

    private void initializeButtonsFragment(){
        buttonsFragment = (ButtonsFragment) ui.getFragment("ButtonsFragment");
        if(buttonsFragment == null){
            buttonsFragment = new ButtonsFragment();
        }
        buttonsFragment.setController(this);
    }

    private void initializeIncomesFragment(){
        incomeTransactionsFragment = (IncomeTransactionsFragment) ui.getFragment("IncomeTransactionsFragment");
        if(incomeTransactionsFragment == null){
            incomeTransactionsFragment = new IncomeTransactionsFragment();
        }
        incomeTransactionsFragment.setController(this);
    }

    private void initializeExpensesFragment(){
        expenseTransactionsFragment = (ExpenseTransactionsFragment) ui.getFragment("ExpenseTransactionsFragment");
        if(expenseTransactionsFragment == null){
            expenseTransactionsFragment = new ExpenseTransactionsFragment();
        }
        expenseTransactionsFragment.setController(this);
    }

    private void initializeAddIncomeFragment(){
        addIncomeFragment = (AddIncomeFragment) ui.getFragment("AddIncomeFragment");
        if(addIncomeFragment == null){
            addIncomeFragment = new AddIncomeFragment();
        }
        addIncomeFragment.setController(this);
    }

    private void initializeAddExpenseFragment(){
        addExpenseFragment = (AddExpenseFragment) ui.getFragment("AddExpenseFragment");
        if(addExpenseFragment == null){
            addExpenseFragment = new AddExpenseFragment();
        }
        addExpenseFragment.setController(this);
    }

    private void initializeEditIncomeFragment(){

    }

    private void initializeEditExpenseFragment(){

    }

    //END OF INITIALIZING ALL THE FRAGMENTS

    //ADDING FRAGMENTS TO THE GRAPHICAL USER INTERFACE

    public void addLogInFragment(){
        setFragment("LogInFragment");
    }

    public void addButtonsFragment(){
        setFragment("ButtonsFragment");
    }

    public void addAddIncomeFragment(){
        setFragment("AddIncomeFragment");
    }

    public void addIncomeTransactionsFragment(){
        setFragment("IncomeTransactionsFragment");
    }

    public void addAddExpenseFragment(){
        setFragment("AddExpenseFragment");
    }

    public void addExpenseTransactionsFragment(){
        setFragment("ExpenseTransactionsFragment");
    }
    //END OF ADDING FRAGMENTS TO THE GRAPHICAL USER INTERFACE

    //ONBACKPRESSED CHECKS IF THE ACTIVE FRAGMENT IS ANYTHING ELSE THEN LOG IN SCREEN IT WILL RETURN TRUE

    public boolean onBackPressed() {
        String activeFragment = dataFragment.getActiveFragment();

        if(activeFragment.equals("LogInFragment")){
            return false;
        }

        switch(activeFragment){
            case "ButtonsFragment":
                setFragment("LogInFragment");
                break;
            case "IncomeTransactionsFragment":
                setFragment("ButtonsFragment");
                break;
            case "AddIncomeFragment":
                setFragment("ButtonsFragment");
                break;
            case "AddExpenseFragment":
                setFragment("ButtonsFragment");
                break;
            case "ExpenseTransactionsFragment":
                setFragment("ButtonsFragment");
                break;
        }
        return false;
    }

    //METHODS RELATED TO SETTING CURRENT ACTIVE FRAGMENT

    private void setFragment(String tag){

        switch(tag){
            case "LogInFragment":
                setFragment(logInFragment,tag);
                break;
            case "ButtonsFragment":
                setFragment(buttonsFragment, tag);
                break;
            case "AddIncomeFragment":
                setFragment(addIncomeFragment, tag);
                break;
            case "IncomeTransactionsFragment":
                setFragment(incomeTransactionsFragment, tag);
                break;
            case "AddExpenseFragment":
                setFragment(addExpenseFragment, tag);
                break;
            case "ExpenseTransactionsFragment":
                setFragment(expenseTransactionsFragment, tag);
                break;
        }
    }


    public void setFragment(Fragment fragment, String tag){
        ui.setFragment(fragment,tag);
        dataFragment.setActiveFragment(tag);
    }

    //END OF METHOD RELATED TO SETTING CURRENT ACTIVE FRAGMENT

    //LOG IN USER

    public void logIn(String userName) {
        User user = new User(userName);
        buttonsFragment.setUserName(user.getUserName());
        addButtonsFragment();
    }

    //COMMUNICATING WITH THE DATABASE OPERATIONS

    public void addIncome( final String title, final String category, final long date, final Double price){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Income income = new Income(title,category,date,price);
                    dbAccess.insertIncome(income);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void filterIncomeList(final long dateFrom, final long dateTo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Income> filteredList = dbAccess.getFilteredIncomeList(dateFrom, dateTo);
                    incomeTransactionsFragment.setContent(filteredList);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void addIncomeList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Income> incomeList = dbAccess.getAllIncomes();
                     incomeTransactionsFragment.setContent(incomeList);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addExpense(final String title, final String category, final long date, final Double price){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Expense expense = new Expense(title,category,date,price);
                    dbAccess.insertExpense(expense);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void filterExpenseList(final long dateFrom, final long dateTo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Expense> filteredList = dbAccess.getFilteredExpenseList(dateFrom, dateTo);
                expenseTransactionsFragment.setContent(filteredList);
            }
        }).start();
    }

    public void addExpenseList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Expense> expenseList = dbAccess.getAllExpenses();
                expenseTransactionsFragment.setContent(expenseList);
            }
        }).start();
    }



    //END OF COMMUNICATING WITH THE DATABASE OPERATIONS
}

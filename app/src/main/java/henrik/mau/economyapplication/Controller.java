package henrik.mau.economyapplication;

import android.content.Context;
import android.content.SharedPreferences;
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
import Fragments.EditExpenseFragment;
import Fragments.EditIncomeFragment;
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
    private EditExpenseFragment editExpenseFragment;
    private EditIncomeFragment editIncomeFragment;
    private DBAccess dbAccess;
    private User user;
    private String name;
    private int totalIncome;
    private int totalExpense;
    private int balance;

    public Controller(MainActivity ui){
        this.ui = ui;
        initializeDataFragment();
        initializeAllFragments();
        initializeDatabase();
        loadUser();
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
        editIncomeFragment = (EditIncomeFragment) ui.getFragment("EditIncomeFragment");
        if(editIncomeFragment == null){
            editIncomeFragment = new EditIncomeFragment();
        }
        editIncomeFragment.setController(this);

    }

    private void initializeEditExpenseFragment(){
        editExpenseFragment = (EditExpenseFragment) ui.getFragment("EditExpenseFragment");
        if(editExpenseFragment == null){
            editExpenseFragment = new EditExpenseFragment();
        }
        editExpenseFragment.setController(this);
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

    public void addEditExpenseFragment(){
        setFragment("EditExpenseFragment");
    }

    public void addEditIncomeFragment(){
        setFragment("EditIncomeFragment");
    }

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
            case "EditExpenseFragment":
                setFragment("ExpenseTransactionsFragment");
                break;
            case "EditIncomeFragment":
                setFragment("IncomeTransactionsFragment");
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
            case "EditExpenseFragment":
                setFragment(editExpenseFragment, tag);
                break;
            case "EditIncomeFragment":
                setFragment(editIncomeFragment, tag);
                break;
        }
    }


    public void setFragment(Fragment fragment, String tag){
        ui.setFragment(fragment,tag);
        dataFragment.setActiveFragment(tag);
    }

    //END OF METHOD RELATED TO SETTING CURRENT ACTIVE FRAGMENT


    public void setName(String name){
        this.name = name;
        saveUser();
    }

    public String getName(){
        return name;
    }


    //COMMUNICATING WITH THE DATABASE OPERATIONS

    public void addIncome( final String title, final String category, final long date, final Double price){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Income income = new Income(title,category,date,price);
                    Log.d("TESTARDETTA", income.toString());
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

    public void editExpense(final int expenseId){
       // addEditExpenseFragment();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Expense expense = dbAccess.getExpense(expenseId);
                editExpenseFragment.setExpense(expense);
                addEditExpenseFragment();
                dbAccess.deleteExpense(expenseId);
            }
        }).start();
    }

    public void editIncome(final int incomeId){
   //     addEditIncomeFragment();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Income income = dbAccess.getIncome(incomeId);
                editIncomeFragment.setIncome(income);
                addEditIncomeFragment();
                dbAccess.deleteIncome(incomeId);
            }
        }).start();
    }

    public int getTotalIncome(final long from, final long to){
        new Thread(new Runnable() {
            @Override
            public void run() {
                totalIncome = dbAccess.getTotalIncome(from,to);
            }
        }).start();
        return totalIncome;
    }

    public int getTotalExpense(final long from, final long to){
        new Thread(new Runnable() {
            @Override
            public void run() {
                totalExpense = dbAccess.getTotalExpense(from, to);
            }
        }).start();
        return totalExpense;
    }

    public int getBalance(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int totalIncomeForBalance = dbAccess.getTotalIncomeForBalance();
                int totalExpenseForBalance = dbAccess.getTotalExpenseForBalance();
                balance = totalIncomeForBalance - totalExpenseForBalance;
            }
        }).start();
        return balance;
    }



    public void saveUser(){
        SharedPreferences sp = ui.getSharedPreferences("Controller", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.commit();
    }

    public void loadUser(){
        SharedPreferences sp = ui.getSharedPreferences("Controller", Context.MODE_PRIVATE);
        name = sp.getString("name", "");
    }

}

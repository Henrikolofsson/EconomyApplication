package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonsFragment extends Fragment {
    private Controller controller;
    private Button btnIncomes;
    private Button btnAddIncome;
    private Button btnExpenses;
    private Button btnAddExpense;
    private TextView tvWelcomeUser;
    private String username;

    public ButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buttons, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public String getUserName(){
        return username;
    }

    private void initializeComponents(View view) {
        btnIncomes = (Button) view.findViewById(R.id.btnIncomes);
        btnExpenses = (Button) view.findViewById(R.id.btnExpenses);
        btnAddIncome = (Button) view.findViewById(R.id.btnAddIncome);
        btnAddExpense = (Button) view.findViewById(R.id.btnAddExpense);
        tvWelcomeUser = (TextView) view.findViewById(R.id.tvWelcomeUser);
        tvWelcomeUser.setText("Welcome, good to see you again " + getUserName());
    }

    private void registerListeners(){
        btnIncomes.setOnClickListener(new IncomesButtonListener());
        btnExpenses.setOnClickListener(new ExpensesButtonListener());
        btnAddIncome.setOnClickListener(new AddIncomeButtonListener());
        btnAddExpense.setOnClickListener(new AddExpenseButtonListener());
    }

    private class IncomesButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.addIncomeList();
            controller.addIncomeTransactionsFragment();
        }
    }

    private class ExpensesButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.addExpenseList();
            controller.addExpenseTransactionsFragment();
        }
    }

    private class AddIncomeButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.addAddIncomeFragment();
        }
    }

    private class AddExpenseButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.addAddExpenseFragment();
        }
    }

}

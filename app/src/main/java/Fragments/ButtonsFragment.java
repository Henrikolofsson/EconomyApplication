package Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private TextView tvBalance;
    private String name;

    public ButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buttons, container, false);
        initializeComponents(view);
        registerListeners();
        setWelcomeText();
        setBalanceText();

        return view;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setWelcomeText(){
        tvWelcomeUser.setText("Welcome User:" + controller.getName());
    }

    public void setBalanceText(){
        tvBalance.setText("Your total balance is: " + controller.getBalance());
    }


    private void initializeComponents(View view) {
        btnIncomes = (Button) view.findViewById(R.id.btnIncomes);
        btnExpenses = (Button) view.findViewById(R.id.btnExpenses);
        btnAddIncome = (Button) view.findViewById(R.id.btnAddIncome);
        btnAddExpense = (Button) view.findViewById(R.id.btnAddExpense);
        tvWelcomeUser = (TextView) view.findViewById(R.id.tvWelcomeUser);
        tvBalance = (TextView) view.findViewById(R.id.tvBalance);

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

    @Override
    public void onResume() {
        super.onResume();
        readName();

    }

    @Override
    public void onPause() {
        super.onPause();
        writeName();
    }


    public void writeName(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ButtonsFragment", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", name);
        editor.commit();
    }

    public void readName(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ButtonsFragment", AppCompatActivity.MODE_PRIVATE);
        String nullcheck;
        nullcheck = sharedPref.getString("name","");
        Log.d("readName.Buttons", nullcheck);

        if(nullcheck!=null){
            name=nullcheck;
        }
        System.out.print(name);
    }
}

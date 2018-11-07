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

        if(name!=(null)){
            Log.d("onCreateView.Buttons", name);
            setWelcomeText(name);}

        return view;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setName(String name){
        Log.d("setName.Buttons", name);
        this.name = name;
        setWelcomeText(name);
    }

    public String getName(){
        Log.d("getName.Buttons", name);
        return name;
    }

    public void setWelcomeText(String name){
        Log.d("The name is", name);
            tvWelcomeUser.setText("Welcome user: " + name);
    }


    private void initializeComponents(View view) {
        btnIncomes = (Button) view.findViewById(R.id.btnIncomes);
        btnExpenses = (Button) view.findViewById(R.id.btnExpenses);
        btnAddIncome = (Button) view.findViewById(R.id.btnAddIncome);
        btnAddExpense = (Button) view.findViewById(R.id.btnAddExpense);
        tvWelcomeUser = (TextView) view.findViewById(R.id.tvWelcomeUser);
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
        if(name!=null){
        setWelcomeText(name);}
        Log.d("LOGIN", "onResume: ");

    }

    @Override
    public void onPause() {
        super.onPause();
        writeName();


        Log.d("LOGIN", "onPause: ");
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

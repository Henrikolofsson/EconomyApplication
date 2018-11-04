package Fragments;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Adapters.ExpenseAdapter;
import Entities.Expense;
import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.DateHelper;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseTransactionsFragment extends Fragment {
    private Controller controller;
    private ExpenseAdapter expenseAdapter;
    private RecyclerView rvExpense;
    private ArrayList<Expense> content = new ArrayList<Expense>();
    private Button btnFilter;
    private Button btnDateFrom;
    private Button btnDateTo;
    private Button btnAll;
    private long dateFrom;
    private long dateTo;
    private TextView tvDateFrom;
    private TextView tvDateTo;


    public ExpenseTransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setRetainInstance(true);
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_expense_transactions, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void registerListeners() {
        btnDateFrom.setOnClickListener(new ButtonDateFromListener());
        btnDateTo.setOnClickListener(new ButtonDateToListener());
        btnFilter.setOnClickListener(new ButtonFilterListener());
        btnAll.setOnClickListener(new ButtonAllListener());
    }

    private void initializeComponents(View view) {
        rvExpense = (RecyclerView) view.findViewById(R.id.rvExpenseTransactions);
        rvExpense.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnFilter = (Button) view.findViewById(R.id.btnFilterExpense);
        btnDateFrom = (Button) view.findViewById(R.id.btnExpenseDateFrom);
        btnDateTo = (Button) view.findViewById(R.id.btnExpenseDateTo);
        btnAll = (Button) view.findViewById(R.id.btnUnfilterExpense);
        tvDateFrom = (TextView) view.findViewById(R.id.tvExpenseDateFrom);
        tvDateTo = (TextView) view.findViewById(R.id.tvExpenseDateTo);

        expenseAdapter = new ExpenseAdapter(getActivity(), content);
        rvExpense.setAdapter(expenseAdapter);
        expenseAdapter.setController(controller);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setContent(List<Expense> content){
        this.content = (ArrayList<Expense>) content; //ArrayList eller list?
    }

    public void setDateTo(long dateTo){
        this.dateTo = dateTo;
    }

    public long getDateTo(){
        return dateTo;
    }

    public void setDateFrom(long dateFrom){
        this.dateFrom = dateFrom;
    }

    public long getDateFrom(){
        return dateFrom;
    }

    public void update(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    private class ButtonDateFromListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            setDateFrom(calendar.getTimeInMillis());
            String dateFrom = DateHelper.convertDate(getDateFrom());
            tvDateFrom.setText("Date from: " + dateFrom);
        }

        @Override
        public void onClick(View v) {
        DateHelperFragment dhf = new DateHelperFragment();
        dhf.setOnDateListener(this);
        dhf.show(getFragmentManager(), "datePicker");
        }
    }

    private class ButtonDateToListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            setDateTo(calendar.getTimeInMillis());
            String dateTo = DateHelper.convertDate(getDateTo());
            tvDateTo.setText("Date from: " + dateTo);
        }

        @Override
        public void onClick(View v) {
            DateHelperFragment dhf = new DateHelperFragment();
            dhf.setOnDateListener(this);
            dhf.show(getFragmentManager(), "datePicker");
        }
    }

    private class ButtonFilterListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(dateFrom > 0 && dateTo > 0){
                controller.filterExpenseList(getDateFrom(), getDateTo());
                update();
            }
        }
    }

    private class ButtonAllListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.addExpenseList();
            update();
        }
    }

    //TESTING
    @Override
    public void onResume(){
        super.onResume();
        loadData();
    }

    @Override
    public void onPause(){
        super.onPause();
        saveData();
    }

    private void saveData(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ExpenseTransactionsFragment",AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(content);
        editor.putString("content", json);
        editor.apply();
        editor.commit();
    }

    private void loadData(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ExpenseTransactionsFragment",AppCompatActivity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("content", null);
        Type type = new TypeToken<ArrayList<Expense>>(){}.getType();
        content = gson.fromJson(json, type);

        if(content == null){
            content = new ArrayList<Expense>();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            controller.addExpenseTransactionsFragment();
            loadData();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveData();
    }
}

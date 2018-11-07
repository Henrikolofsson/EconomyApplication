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
    private ArrayList<Expense> filteredContent = new ArrayList<>();
    private Button btnFilter;
    private Button btnDateFrom;
    private Button btnDateTo;
    private Button btnAll;
    private long dateFrom;
    private long dateTo;
    private TextView tvDateFrom;
    private TextView tvDateTo;
    private TextView tvTotalExpense;
    private boolean filtered = false;


    public ExpenseTransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_transactions, container, false);
        initializeComponents(view);
        registerListeners();
        expenseAdapter = new ExpenseAdapter(getActivity(), content);
        rvExpense.setAdapter(expenseAdapter);
        expenseAdapter.setController(controller);

        if(savedInstanceState!=null){
            long newDateFrom = savedInstanceState.getLong("dateFrom");
            long newDateTo = savedInstanceState.getLong("dateTo");
            ArrayList<Expense> recreatedContent = (ArrayList<Expense>) savedInstanceState.getSerializable("contentExpense");
            filtered = savedInstanceState.getBoolean("filteredExpense");
            if((newDateFrom > 0) && (newDateTo > 0)) {
                setDateFrom(newDateFrom);
                setDateTo(newDateTo);
                tvDateFrom.setText("Date from: " + DateHelper.convertDate(getDateFrom()));
                tvDateTo.setText("Date from: " + DateHelper.convertDate(getDateTo()));
            }

            setContent(recreatedContent);
            expenseAdapter.setContent(recreatedContent);
        }
        setTotalExpenseText();
        return view;
    }

    public void setTotalExpenseText(){
        if(dateFrom > 0 && dateTo > 0) {
            tvTotalExpense.setText("Your total expense between " + DateHelper.convertDate(dateFrom) + " and " + DateHelper.convertDate(dateTo) + ": "
                    + controller.getTotalExpense(dateFrom, dateTo));
        } else {
            tvTotalExpense.setText("You need to choose an intervall to get total expense");
        }
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
        tvTotalExpense = (TextView) view.findViewById(R.id.tvTotalExpense);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setContent(List<Expense> content){
        this.content = (ArrayList<Expense>) content;
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
                filtered = true;
                setTotalExpenseText();
                update();
            }
        }
    }

    private class ButtonAllListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.addExpenseList();
            filtered = false;
            update();
        }
    }

    //TESTING
    @Override
    public void onResume(){
        super.onResume();
        if(content!=null){
            expenseAdapter.setContent(content);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("filteredExpense", filtered);
        if((dateFrom > 0) && (dateTo > 0)) {
            outState.putLong("dateFrom", dateFrom);
            outState.putLong("dateTo", dateTo);
        }
        outState.putSerializable("contentExpense", content);
        outState.putSerializable("filteredcontentExpense", filteredContent);
    }

}

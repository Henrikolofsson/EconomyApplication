package Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

import Entities.Income;
import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.DateHelper;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditIncomeFragment extends Fragment {
    private Controller controller;
    private EditText etTitle;
    private EditText etPrice;
    private Button btnAddIncome;
    private Button btnAddDate;
    private RadioGroup rgIncome;
    private RadioButton rbAddIncomeSalary;
    private RadioButton rbAddIncomeOther;
    private TextView tvAddIncomeDate;
    private int checkedRadioButtonId;
    private String checkedRadioButtonText;
    private long time;
    private Income income;


    public EditIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_income, container, false);
        initializeComponents(view);
        registerListeners();

        if(savedInstanceState!=null){
            long time = savedInstanceState.getLong("date");
            income = (Income) savedInstanceState.getSerializable("income");
            setTime(time);
            setIncome(income);
        }
        initSettings();
        return view;
    }

    private void initializeComponents(View view){
        btnAddIncome = (Button) view.findViewById(R.id.btnEditIncome);
        btnAddDate = (Button) view.findViewById(R.id.btnEditIncomeDate);
        etTitle = (EditText) view.findViewById(R.id.etEditIncomeTitle);
        etPrice = (EditText) view.findViewById(R.id.etEditIncomePrice);
        rgIncome = (RadioGroup) view.findViewById(R.id.rgEditIncome);
        rbAddIncomeSalary = (RadioButton) view.findViewById(R.id.rbEditIncomeSalary);
        rbAddIncomeOther = (RadioButton) view.findViewById(R.id.rbEditIncomeOther);
        tvAddIncomeDate = (TextView) view.findViewById(R.id.tvEditIncomeDate);
    }

    private void registerListeners(){
        btnAddDate.setOnClickListener(new ButtonDateListener());
        btnAddIncome.setOnClickListener(new ButtonAddIncomeListener());
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public long getTime(){
        return time;
    }

    public void setTime(long time){
        this.time = time;
    }

    public void setIncome(Income income){
        this.income = income;
    }

    public Income getIncome(){
        return income;
    }

    public void initSettings(){
        etTitle.setText(income.getTitle());
        String category = income.getCategory();
        switch(category){
            case "Salary":
                rbAddIncomeSalary.setChecked(true);
                break;
            case "Other":
                rbAddIncomeOther.setChecked(true);
                break;
        }
        long date = income.getDate();
        setTime(date);
        tvAddIncomeDate.setText("Date: " + DateHelper.convertDate(date));
        double price = income.getPrice();
        etPrice.setText(Double.toString(price));
    }

    public String getCheckedRadioButtonText(View view){
        if(rgIncome.getCheckedRadioButtonId() != -1){
            checkedRadioButtonId = rgIncome.getCheckedRadioButtonId();
            View radioButton = rgIncome.findViewById(checkedRadioButtonId);
            int radioId = rgIncome.indexOfChild(radioButton);
            RadioButton rdbtn = (RadioButton) rgIncome.getChildAt(radioId);
            checkedRadioButtonText = rdbtn.getText().toString();
        }
        return checkedRadioButtonText;
    }

    private class ButtonDateListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

        @Override
        public void onClick(View view){
            DateHelperFragment dhf = new DateHelperFragment();
            dhf.setOnDateListener(this);
            dhf.show(getFragmentManager(), "datePicker");
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            setTime(calendar.getTimeInMillis());
            String date = DateHelper.convertDate(time);
            tvAddIncomeDate.setText("Date: " + date);
        }
    }

    private class ButtonAddIncomeListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String title = etTitle.getText().toString();
            String category = getCheckedRadioButtonText(view);
            long date = time;
            Double price = Double.parseDouble(etPrice.getText().toString());
            controller.addIncome(title,category,date,price);
            controller.addIncomeList();
            controller.addIncomeTransactionsFragment();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong("date", time);
        outState.putSerializable("income", income);
    }
}

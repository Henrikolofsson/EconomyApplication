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

import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.DateHelper;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIncomeFragment extends Fragment {
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


    public AddIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_income, container, false);
        initializeComponents(view);
        registerListeners();

        if(savedInstanceState != null){
            long oldTime = savedInstanceState.getLong("date");
            setTime(oldTime);
            tvAddIncomeDate.setText("Date: " + DateHelper.convertDate(oldTime));
        }
        return view;
    }

    public long getTime(){
        return time;
    }

    public void setTime(long time){
        this.time = time;
    }

    private void initializeComponents(View view){
        btnAddIncome = (Button) view.findViewById(R.id.btnAddIncome);
        btnAddDate = (Button) view.findViewById(R.id.btnAddIncomeDate);
        etTitle = (EditText) view.findViewById(R.id.etAddIncomeTitle);
        etPrice = (EditText) view.findViewById(R.id.etAddIncomePrice);
        rgIncome = (RadioGroup) view.findViewById(R.id.rgAddIncome);
        rbAddIncomeSalary = (RadioButton) view.findViewById(R.id.rbAddIncomeSalary);
        rbAddIncomeOther = (RadioButton) view.findViewById(R.id.rbAddIncomeOther);
        tvAddIncomeDate = (TextView) view.findViewById(R.id.tvAddIncomeDate);
    }

    public void registerListeners(){
        btnAddDate.setOnClickListener(new ButtonDateListener());
        btnAddIncome.setOnClickListener(new ButtonAddIncomeListener());
    }

    public void setController(Controller controller) {
        this.controller = controller;
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
    }

}

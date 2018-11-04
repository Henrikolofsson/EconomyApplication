package Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.DateHelper;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {
    private Controller controller;
    private EditText etTitle;
    private EditText etPrice;
    private EditText etDate;
    private RadioGroup rgExpense;
    private RadioButton rbGroceries;
    private RadioButton rbTravel;
    private RadioButton rbAccommodation;
    private RadioButton rbOther;
    private RadioButton rbSparetime;
    private Button btnAddExpense;
    private Button btnAddDate;
    private int checkedRadioButtonId;
    private String checkedRadioButtonText;
    private long time;

    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        initializeComponents(view);
        registerListeners();
        return view;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void initializeComponents(View view){
        etTitle = (EditText) view.findViewById(R.id.etAddExpenseTitle);
        etPrice = (EditText) view.findViewById(R.id.etAddExpensePrice);
        rgExpense = (RadioGroup) view.findViewById(R.id.rgAddExpense);
        rbGroceries = (RadioButton) view.findViewById(R.id.rbAddExpenseGroceries);
        rbTravel = (RadioButton) view.findViewById(R.id.rbAddExpenseTravel);
        rbAccommodation = (RadioButton) view.findViewById(R.id.rbAddExpenseAccommodation);
        rbOther = (RadioButton) view.findViewById(R.id.rbAddExpenseOther);
        rbSparetime = (RadioButton) view.findViewById(R.id.rbAddExpenseSparetime);
        btnAddExpense = (Button) view.findViewById(R.id.btnAddExpense);
        btnAddDate = (Button) view.findViewById(R.id.btnAddExpenseDate);
    }

    public void registerListeners(){
        btnAddExpense.setOnClickListener(new ButtonAddExpenseListener());
        btnAddDate.setOnClickListener(new ButtonAddDateListener());
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getCheckedRadioButtonText(View view){
        if(rgExpense.getCheckedRadioButtonId() != -1){
            checkedRadioButtonId = rgExpense.getCheckedRadioButtonId();
            View radioButton = rgExpense.findViewById(checkedRadioButtonId);
            int radioId = rgExpense.indexOfChild(radioButton);
            RadioButton rdbtn = (RadioButton) rgExpense.getChildAt(radioId);
            checkedRadioButtonText = rdbtn.getText().toString();
        }
        return checkedRadioButtonText;
    }

    private class ButtonAddExpenseListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String title = etTitle.getText().toString();
            String category = getCheckedRadioButtonText(v);
            Double price = Double.parseDouble(etPrice.getText().toString());
            long date = getTime();
            controller.addExpense(title,category,date,price);
            controller.addExpenseList();
            controller.addExpenseTransactionsFragment();
        }
    }

    private class ButtonAddDateListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

        @Override
        public void onClick(View v) {
            DateHelperFragment dhf = new DateHelperFragment();
            dhf.setOnDateListener(this);
            dhf.show(getFragmentManager(), "datePicker");
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            setTime(calendar.getTimeInMillis());
            DateHelper.convertDate(getTime());
        }
    }



}

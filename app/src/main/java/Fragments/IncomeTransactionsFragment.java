package Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapters.IncomeAdapter;
import Entities.Income;
import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.DateHelper;
import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeTransactionsFragment extends Fragment {
    private Controller controller;
    private IncomeAdapter incomeAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Income> content = new ArrayList<Income>();
    private Button btnFilter;
    private Button btnDateFrom;
    private Button btnDateTo;
    private Button btnAll;
    private long dateFrom;
    private long dateTo;
    private TextView tvDateFrom;
    private TextView tvDateTo;


    public IncomeTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_transactions, container, false);
        initializeComponents(view);
        registerListeners();
        incomeAdapter = new IncomeAdapter(getActivity(), content);
        incomeAdapter.setController(controller);
        recyclerView.setAdapter(incomeAdapter);
        return view;
    }

    private void initializeComponents(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rvIncomeTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnFilter = (Button) view.findViewById(R.id.btnFilterIncome);
        btnAll = (Button) view.findViewById(R.id.btnUnfilterIncome);
        btnDateFrom = (Button) view.findViewById(R.id.btnIncomeDateFrom);
        btnDateTo = (Button) view.findViewById(R.id.btnIncomeDateTo);
        tvDateFrom = (TextView) view.findViewById(R.id.tvIncomeDateFrom);
        tvDateTo = (TextView) view.findViewById(R.id.tvIncomeDateTo);
    }

    public void registerListeners(){
        btnDateTo.setOnClickListener(new ButtonDateToListener());
        btnDateFrom.setOnClickListener(new ButtonDateFromListener());
        btnFilter.setOnClickListener(new ButtonFilterIncomeListener());
        btnAll.setOnClickListener(new ButtonUnfilterIncomeListener());
    }

    public void onResume(){
        super.onResume();
        incomeAdapter.setContent(content);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setContent(List<Income> content){
        this.content = (ArrayList<Income>) content; //ArrayList eller list?
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

    private class ButtonDateFromListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            setDateFrom(calendar.getTimeInMillis());
            String dateFrom = DateHelper.convertDate(getDateFrom());
            tvDateFrom.setText("Date From: " + dateFrom);
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
            tvDateTo.setText("Date To: " + dateTo);
        }

        @Override
        public void onClick(View v) {
            DateHelperFragment dhf = new DateHelperFragment();
            dhf.setOnDateListener(this);
            dhf.show(getFragmentManager(), "datePicker");
        }
    }

    private class ButtonFilterIncomeListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(dateFrom > 0 && dateTo > 0){
                controller.filterIncomeList(getDateFrom(), getDateTo());
                update();
            }
        }
    }

    private class ButtonUnfilterIncomeListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.addIncomeList();
            update();
        }
    }

    public void update(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

}

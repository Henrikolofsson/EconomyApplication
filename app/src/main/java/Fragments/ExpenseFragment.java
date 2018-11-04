package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvPrice;
    private ImageView ivCategory;

    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view){
        tvTitle = (TextView) view.findViewById(R.id.tvExpenseTitle);
        tvDate = (TextView) view.findViewById(R.id.tvExpenseDate);
        tvPrice = (TextView) view.findViewById(R.id.tvExpensePrice);
        ivCategory = (ImageView) view.findViewById(R.id.ivCategory);
    }

    public String getTitle(){
        return tvTitle.getText().toString();
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public String getDate(){
        return tvDate.getText().toString();
    }

    public void setDate(String date){
        tvDate.setText(date);
    }

    public Double getPrice(){
        return Double.parseDouble(tvPrice.getText().toString());
    }

    public void setPrice(Double price){
        tvPrice.setText(Double.toString(price));
    }
}

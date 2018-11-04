package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import henrik.mau.economyapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {
    private TextView tvTitle;
    private TextView tvCategory;
    private TextView tvDate;
    private TextView tvPrice;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvIncomeTitle);
        tvCategory = (TextView) view.findViewById(R.id.tvIncomeCategory);
        tvDate = (TextView) view.findViewById(R.id.tvIncomeDate);
        tvPrice = (TextView) view.findViewById(R.id.tvIncomePrice);
    }

    public String getTvTitle() {
        return tvTitle.getText().toString();
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle.setText(tvTitle);
    }

    public String getTvDate() {
        return tvDate.getText().toString();
    }

    public void setTvDate(String tvDate) {
        this.tvDate.setText(tvDate);
    }

    public String getTvPrice() {
        return tvPrice.getText().toString();
    }

    public void setTvPrice(String tvPrice) {
        this.tvPrice.setText(tvPrice);
    }

    public String getTvCategory() {
        return tvCategory.getText().toString();
    }

    public void setTvCategory(String tvCategory) {
        this.tvCategory.setText(tvCategory);
    }

}

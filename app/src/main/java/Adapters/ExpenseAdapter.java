package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Entities.Expense;
import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.DateHelper;
import henrik.mau.economyapplication.R;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.Holder> {
    private LayoutInflater inflater;
    private List<Expense> content;
    private Controller controller;

    public ExpenseAdapter(Context context){
        this(context, new ArrayList<Expense>());
    }

    public ExpenseAdapter(Context context, List<Expense> content){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.content = content;
    }

    public void setContent(List<Expense> content){
        this.content = content;
        super.notifyDataSetChanged();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view = inflater.inflate(R.layout.fragment_expense, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int pos) {
        holder.tvTitle.setText(content.get(pos).getTitle());
        holder.tvDate.setText(DateHelper.convertDate(content.get(pos).getDate()));
        holder.tvPrice.setText(""+content.get(pos).getPrice());

        switch(content.get(pos).getCategory()){
            case "Groceries":
                holder.ivCateogry.setImageResource(R.drawable.groceries);
                break;
            case "Sparetime":
                holder.ivCateogry.setImageResource(R.drawable.sparetime);
                break;
            case "Travel":
                holder.ivCateogry.setImageResource(R.drawable.travel);
                break;
            case "Accommodation":
                holder.ivCateogry.setImageResource(R.drawable.accommodation);
                break;
            case "Other":
                holder.ivCateogry.setImageResource(R.drawable.other);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvPrice;
        private ImageView ivCateogry;

        public Holder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvExpenseTitle);
            tvDate = itemView.findViewById(R.id.tvExpenseDate);
            tvPrice = itemView.findViewById(R.id.tvExpensePrice);
            ivCateogry = itemView.findViewById(R.id.ivCategory);
            ivCateogry.getLayoutParams().height = 80;
            ivCateogry.getLayoutParams().width = 80;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

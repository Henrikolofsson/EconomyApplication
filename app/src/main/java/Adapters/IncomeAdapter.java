package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Entities.Income;
import henrik.mau.economyapplication.Controller;
import henrik.mau.economyapplication.DateHelper;
import henrik.mau.economyapplication.R;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.Holder> {
    private LayoutInflater inflater;
    private List<Income> content;
    private Controller controller;
    private int incomeId;

    public IncomeAdapter(Context context){
        this(context, new ArrayList<Income>());
    }

    public IncomeAdapter(Context context, List<Income> content){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.content = content;
    }

    public void setContent(List<Income> content){
        this.content = content;
        super.notifyDataSetChanged();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.fragment_income, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvTitle.setText(content.get(position).getTitle());
        holder.tvCategory.setText(content.get(position).getCategory());
        holder.tvDate.setText(DateHelper.convertDate(content.get(position).getDate()));
        holder.tvPrice.setText(""+content.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvCategory;
        private TextView tvDate;
        private TextView tvPrice;

        public Holder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvIncomeTitle);
            tvCategory = itemView.findViewById(R.id.tvIncomeCategory);
            tvDate = itemView.findViewById(R.id.tvIncomeDate);
            tvPrice = itemView.findViewById(R.id.tvIncomePrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            int position = getAdapterPosition();
            incomeId = content.get(position).getId();
            controller.editIncome(incomeId);

        }
    }
}

package hector.developers.alabaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hector.developers.alabaster.R;
import hector.developers.alabaster.details.FinanceDetailsActivity;
import hector.developers.alabaster.details.MembersDetailsActivity;
import hector.developers.alabaster.model.Finance;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.ViewHolder> {
    Context context;
    List<Finance> financeList;

    public FinanceAdapter(List<Finance> financeList, Context context) {
        this.financeList = financeList;
        this.context = context;
    }

    @NonNull
    @Override
    public FinanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.financelist, parent, false);
        return new FinanceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceAdapter.ViewHolder holder, int position) {
        Finance finance = financeList.get(position);
        holder.tvRevDate.setText(finance.getDate());
        holder.tvDay.setText(finance.getDay());
        holder.tvProgram.setText(finance.getProgramme());
        holder.tvTotalAmount.setText(finance.getTotal());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E8EAF6"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FinanceDetailsActivity.class);
                intent.putExtra("key", finance);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRevDate;
        private final TextView tvDay;
        private final TextView tvProgram;
        private final TextView tvTotalAmount;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRevDate = itemView.findViewById(R.id.tvRevDate);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvProgram = itemView.findViewById(R.id.tvProgram);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
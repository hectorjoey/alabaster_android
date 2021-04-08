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
import hector.developers.alabaster.details.BirthdayDetailsActivity;
import hector.developers.alabaster.model.Members;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.ViewHolder> {

    Context context;
    List<Members> membersList;

    public BirthdayAdapter(List<Members> membersList, Context context) {
        this.membersList = membersList;
        this.context = context;
    }

    @NonNull
    @Override
    public BirthdayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.birthdhaylist, parent, false);
        return new BirthdayAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayAdapter.ViewHolder holder, int position) {
        Members members = membersList.get(position);
        holder.tvFirstName.setText(members.getFirstname());
//        holder.tvEmail.setText(members.getEmail());
        holder.tvDob.setText(members.getDateOfBirth());
        holder.tvPhone.setText(members.getPhone());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E8EAF6"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BirthdayDetailsActivity.class);
                intent.putExtra("key", members);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFirstName;
        private final TextView tvDob;
        private final TextView tvPhone;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tvBdFirstname);
//            tvEmail = itemView.findViewById(R.id.tvDbEmail);
            tvDob = itemView.findViewById(R.id.tvBdDob);
            tvPhone = itemView.findViewById(R.id.tvDbPhone);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

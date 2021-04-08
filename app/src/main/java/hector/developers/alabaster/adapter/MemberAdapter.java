package hector.developers.alabaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import hector.developers.alabaster.R;
import hector.developers.alabaster.details.MembersDetailsActivity;
import hector.developers.alabaster.model.Members;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Members> membersList;
    private List<Members> membersListFull;

    public MemberAdapter(List<Members> membersList, Context context) {
        this.membersList = membersList;
        this.context = context;
        membersListFull = new ArrayList<>(membersList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.memberlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Members members = membersList.get(position);
        holder.tvFirstName.setText(members.getFirstname());
        holder.tvEmail.setText(members.getEmail());
        holder.tvDob.setText(members.getDateOfBirth());
        holder.tvState.setText(members.getState());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E8EAF6"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MembersDetailsActivity.class);
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
        private final TextView tvEmail;
        private final TextView tvDob;
        private final TextView tvState;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tvFirstname);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvDob = itemView.findViewById(R.id.tvDob);
            tvState = itemView.findViewById(R.id.tvState);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Members> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(membersListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Members member : membersListFull) {
                    if (member.getFirstname().toLowerCase().contains(filterPattern)
                            || member.getEmail().toLowerCase().contains(filterPattern)) {
                        filteredList.add(member);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            membersList.clear();
            membersList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
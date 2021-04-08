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
import hector.developers.alabaster.details.RequestDetailActivity;
import hector.developers.alabaster.model.Attendance;
import hector.developers.alabaster.model.Request;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    Context context;
    List<Request> requestList;

    public RequestAdapter(List<Request> requestList, Context context) {
        this.requestList = requestList;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestlist, parent, false);
        return new RequestAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        Request request = requestList.get(position);
        holder.tvDateOfRequest.setText(request.getDate());
        holder.tvDepartment.setText(request.getDepartment());
        holder.tvNameOfRequester.setText(request.getNameOfRequester());
        holder.tvApproval.setText(request.getApprovalStatus());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        if (holder.tvApproval.getText().equals("PENDING")) {
            holder.cardView.setBackgroundColor(Color.parseColor("#F9E79F"));
        } else {
            holder.cardView.setBackgroundColor(Color.parseColor("#229954"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RequestDetailActivity.class);
                intent.putExtra("key", request);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDateOfRequest;
        private final TextView tvDepartment;
        private final TextView tvApproval;
        private final TextView tvNameOfRequester;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDateOfRequest = itemView.findViewById(R.id.tvDateOfRequest);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvApproval = itemView.findViewById(R.id.tvApproval);
            tvNameOfRequester = itemView.findViewById(R.id.tvNameOfRequester);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

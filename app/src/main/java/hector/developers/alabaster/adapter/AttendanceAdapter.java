package hector.developers.alabaster.adapter;

import android.content.Context;
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
import hector.developers.alabaster.model.Attendance;
import hector.developers.alabaster.model.Members;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder>{

    Context context;
    List<Attendance> attendanceList;

    public AttendanceAdapter(   List<Attendance> attendanceList, Context context) {
        this.attendanceList = attendanceList;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendancelist, parent, false);
        return new AttendanceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        holder.tvNom.setText(attendance.getNumberOfMales());
        holder.tvNow.setText(attendance.getNumberOfFemales());
        holder.tvNoc.setText(attendance.getNumberOfChildren());
        holder.tvTotal.setText(attendance.getTotal());
        holder.tvDate.setText(attendance.getDate());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E8EAF6"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, BirthDetailActivity.class);
//                intent.putExtra("key", staff);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNom;
        private final TextView tvNow;
        private final TextView tvNoc;
        private final TextView tvTotal;
        private final TextView tvDate;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNom);
            tvNow = itemView.findViewById(R.id.tvNow);
            tvNoc = itemView.findViewById(R.id.tvNoc);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvDate= itemView.findViewById(R.id.tvDt);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
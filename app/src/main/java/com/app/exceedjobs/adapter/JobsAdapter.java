package com.app.exceedjobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.exceedjobs.R;
import com.app.exceedjobs.activity.JobDetailActivity;
import com.app.exceedjobs.model.JobModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private List<JobModel> jobs;
    private Context mContext;

    public JobsAdapter(Context mContext, List<JobModel> jobs) {
        this.jobs = jobs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.job_list_layout,parent, false);
       return new JobsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.title_TV.setText(jobs.get(position).getTitle());
        holder.location_TV.setText(" "+jobs.get(position).getLocation());
        holder.salary_TV.setText(" "+jobs.get(position).getSalary());
        holder.experience_TV.setText("Experience: "+jobs.get(position).getExperience()+ " years");
        holder.date_TV.setText(jobs.get(position).getAdded_date());
        holder.mode_TV.setText(jobs.get(position).getMode());

        holder.jobCard_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                intent.putExtra("job",(Parcelable) jobs.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView title_TV, location_TV, salary_TV, mode_TV, date_TV, experience_TV;
        MaterialCardView jobCard_CV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            title_TV = mView.findViewById(R.id.jobListTitle_TV);
            location_TV = mView.findViewById(R.id.jobListLocation_TV);
            salary_TV = mView.findViewById(R.id.jobListSalary_TV);
            mode_TV = mView.findViewById(R.id.jobListMode_TV);
            date_TV = mView.findViewById(R.id.jobListDate_TV);
            experience_TV = mView.findViewById(R.id.jobListExperience_TV);
            jobCard_CV = mView.findViewById(R.id.jobListCard_CV);
        }
    }
}

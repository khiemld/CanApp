package com.example.canapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canapp.DetailProjectActivity;
import com.example.canapp.R;
import com.example.canapp.model.project.DetailProject;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private Context context;

    private List<DetailProject> projects;

    private boolean isowner;

    public PlanAdapter(Context context) {
        this.context = context;
        this.isowner = isowner;
    }

    public void setProjects(List<DetailProject> list) {
        this.projects = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        DetailProject project = projects.get(position);
        if (project == null) {
            return;
        }
        holder.txt_nameOwner.setText(project.getManager().getName());
        holder.txt_titleHomeTask.setText(project.getName());
        holder.txt_descriptionHome.setText(project.getDescription());
        holder.txt_dateAddProject.setText(project.getBeginTime());

        if (!project.getManager().getAvatar().equals("null")) {
            Glide.with(context).load(project.getManager().getAvatar())
                    .into(holder.img_avatarLeader);
        } else {
            holder.img_avatarLeader.setImageResource(R.drawable.avatar_profile);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent projectIntent = new Intent(context, DetailProjectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("project", project.get_id());
                projectIntent.putExtras(bundle);
                context.startActivity(projectIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (projects != null) {
            return projects.size();
        }
        return 0;
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_nameOwner, txt_titleHomeTask, txt_descriptionHome, txt_dateAddProject;
        private ImageView img_avatarLeader;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_nameOwner = itemView.findViewById(R.id.tv_nameowner);
            txt_titleHomeTask = itemView.findViewById(R.id.tv_titlehometask);
            txt_descriptionHome = itemView.findViewById(R.id.tv_descriptionHome);
            txt_dateAddProject = itemView.findViewById(R.id.tv_dateAddHome);
            img_avatarLeader = itemView.findViewById(R.id.cr_avatarHome);
        }
    }
}

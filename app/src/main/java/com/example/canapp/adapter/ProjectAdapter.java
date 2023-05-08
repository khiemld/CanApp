package com.example.canapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canapp.R;
import com.example.canapp.model.Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private Context context;
    private List<Project> projects;

    public ProjectAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Project> list){
        this.projects = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projects.get(position);
        if (project == null){
            return;
        }
        holder.txt_nameOwner.setText(project.getNameOwner());
        holder.txt_titleHomeTask.setText(project.getNameProject());
        holder.txt_descriptionHome.setText(project.getDescription());
        holder.txt_dateAddProject.setText(project.getDateaddProject());
        /*Glide.with(context).load(project.getAvatarLeader()).into(holder.img_avatarLeader);*/
        holder.img_avatarLeader.setImageResource(project.getAvatarLeader());
    }

    @Override
    public int getItemCount() {
        if(projects != null){
            return projects.size();
        }
        return 0;
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nameOwner, txt_titleHomeTask, txt_descriptionHome, txt_dateAddProject;
        private ImageView img_avatarLeader;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameOwner = itemView.findViewById(R.id.tv_nameowner);
            txt_titleHomeTask = itemView.findViewById(R.id.tv_titlehometask);
            txt_descriptionHome = itemView.findViewById(R.id.tv_descriptionHome);
            txt_dateAddProject = itemView.findViewById(R.id.tv_dateAddHome);
            img_avatarLeader = itemView.findViewById(R.id.cr_avatarHome);
        }
    }
}

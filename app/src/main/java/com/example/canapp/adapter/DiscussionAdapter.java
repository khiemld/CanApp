package com.example.canapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canapp.R;
import com.example.canapp.model.discussion.Discussion;

import java.util.List;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder>{

    private Context context;

    private List<Discussion> discussions;

    public DiscussionAdapter(Context context) {
        this.context = context;
    }

    public void setDiscussion(List<Discussion> list){
        this.discussions = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discussion, parent, false);
        return new DiscussionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionViewHolder holder, int position) {
        Discussion discussion = discussions.get(position);
        if (discussion == null){
            return;
        }
        holder.txt_titleDiscussion.setText(discussion.getTitleDiscussion());
        holder.txt_nameownerDiscussion.setText(discussion.getNameownerDiscussion());
        holder.txt_dateDiscussion.setText(discussion.getDateDiscussion());
    }

    @Override
    public int getItemCount() {
        if (discussions != null){
            return discussions.size();
        }
        return 0;
    }

    public class DiscussionViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_titleDiscussion, txt_nameownerDiscussion, txt_dateDiscussion;

        public DiscussionViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_titleDiscussion = itemView.findViewById(R.id.tv_nameDiscussion);
            txt_nameownerDiscussion = itemView.findViewById(R.id.tv_nameownerDiscussion);
            txt_dateDiscussion = itemView.findViewById(R.id.tv_dateDiscussion);
        }
    }
}

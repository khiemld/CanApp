package com.example.canapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentTransaction;

import com.example.canapp.AddMemberToTaskFragment;
import com.example.canapp.DetailTaskFragment;
import com.example.canapp.MemberList;
import com.example.canapp.ProjectInfo;
import com.example.canapp.R;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.task.Task;
import com.example.canapp.model.user.User;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends DragItemAdapter<Pair<Long, Task>, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    List<Task> taskLists;

    public List<Task> getTaskLists() {
        return taskLists;
    }

    public void setTaskLists(List<Task> taskLists) {
        this.taskLists = taskLists;
    }

    ProjectInProjectDetail mProject;

    private Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ProjectInProjectDetail getmProject() {
        return mProject;
    }

    public void setmProject(ProjectInProjectDetail mProject) {
        this.mProject = mProject;
    }

    public ItemAdapter(ArrayList<Pair<Long, Task>> list, int layoutId, int grabHandleId,
                       boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setItemList(list);
        taskLists = new ArrayList<>();
        for (Pair<Long, Task> itemList : list
        ) {
            taskLists.add(itemList.second);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Task currentTask = mItemList.get(position).second;
        String text = currentTask.getName();
        holder.mText.setText(text);
        holder.itemView.setTag(mItemList.get(position));

        if (currentTask.getMembers().size() == 0) {
            holder.memberGroup.setVisibility(View.GONE);
        } else {
            holder.memberGroup.setVisibility(View.VISIBLE);
            holder.memberCount.setText(Integer.toString(currentTask.getMembers().size()));
        }
        if (true) {
            holder.dateGroup.setVisibility(View.GONE);
        } else {
            holder.dateGroup.setVisibility(View.VISIBLE);
            holder.startDate.setText("Ngày bắt đầu");
            holder.endDate.setText("Ngày kết thúc");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = dialog(R.layout.layout_progress_dialop);

                FragmentTransaction fragmentTransaction =
                        getActivityFromContext(mContext).getSupportFragmentManager()
                                .beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,
                        DetailTaskFragment.newInstance(taskLists.get(position), mProject));
                fragmentTransaction.addToBackStack(DetailTaskFragment.TAG);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).first;
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        TextView mText;
        TextView startDate, endDate, memberCount;
        LinearLayout dateGroup, memberGroup;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            mText = (TextView) itemView.findViewById(R.id.taskName);
            startDate = (TextView) itemView.findViewById(R.id.task_startDate);
            endDate = (TextView) itemView.findViewById(R.id.task_endDate);
            memberCount = (TextView) itemView.findViewById(R.id.memberCountLabel);
            dateGroup = (LinearLayout) itemView.findViewById(R.id.dateGroup);
            memberGroup = (LinearLayout) itemView.findViewById(R.id.memberCountGroup);
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(),
                            mItemList.get(getBindingAdapterPosition()).second.getName(),
                            Toast.LENGTH_SHORT)
                    .show();

            Task currentTask = mItemList.get(getBindingAdapterPosition()).second;
            Toast.makeText(view.getContext(), currentTask.getName().toString(), Toast.LENGTH_SHORT)
                    .show();


        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    Dialog dialog(int layout) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    public AppCompatActivity getActivityFromContext(Context context) {
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof AppCompatActivity) {
                return (AppCompatActivity) baseContext;
            } else {
                return getActivityFromContext(baseContext);
            }
        }
        return null;
    }
}

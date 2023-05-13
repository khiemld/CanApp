package com.example.canapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.example.canapp.R;
import com.example.canapp.model.task.Task;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

public class ItemAdapter extends DragItemAdapter<Pair<Long, Task>, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;

    public ItemAdapter(ArrayList<Pair<Long, Task>> list, int layoutId, int grabHandleId,
                       boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setItemList(list);
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
}

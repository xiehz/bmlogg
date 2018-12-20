package com.szbm.wh.x.bmlogg.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.LoggingListItemBinding;
import com.szbm.wh.x.bmlogg.pojo.LoggingListItem;
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class LoggingListAdapter extends RecyclerView.Adapter<LoggingListAdapter.LoggingItemViewHolder> {

    List<LoggingListItem> loggingList;
    ClickListener loggingListener;
    ClickListener deleteListener;
    ClickListener uploadListener;

    public LoggingListAdapter(ClickListener loggingListener, ClickListener deleteClickListener, ClickListener uploadListener) {
        super();
        this.loggingList = new ArrayList<>();
        this.loggingListener = loggingListener;
        this.deleteListener = deleteClickListener;
        this.uploadListener = uploadListener;
    }

    public void submit(List<LoggingListItem> loggingListItems) {
        this.loggingList = loggingListItems;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoggingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logging_list_item, parent, false);
        return new LoggingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoggingItemViewHolder holder, int position) {
        holder.bind(loggingList.get(position));
    }

    @Override
    public int getItemCount() {
        return loggingList.size();
    }

    public LoggingListItem getItem(int position){
        return loggingList.get(position);
    }

    public void addItem(LoggingListItem loggingListItem){
        if(loggingListItem != null)
        {
            int p = this.getItemCount();
            this.loggingList.add(loggingListItem);
            this.notifyItemInserted(p);
        }
    }

    public void removeItem(LoggingListItem loggingListItem){
        if(loggingListItem != null){
            int index = this.loggingList.indexOf(loggingListItem);
            this.loggingList.remove(index);
            this.notifyItemRemoved(index);
        }
    }

    public void updateItem(LoggingListItem current, LoggingListItem loggingListItem){
        if(loggingListItem != null){
            current.setIid(loggingListItem.getIid());
            current.setFromServer(loggingListItem.getFromServer());
            this.notifyItemChanged(this.loggingList.indexOf(current));
        }
    }

    class LoggingItemViewHolder extends RecyclerView.ViewHolder {
        LoggingListItemBinding binding;

        public LoggingItemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.executePendingBindings();
        }

        public void bind(LoggingListItem loggingListItem) {
            binding.setListitem(loggingListItem);
            binding.setDelete(new DeleteClickListener());
            binding.setLogging(new LoggingClickListener());
            binding.setUpload(new UpLoadClickListener());
            binding.textView21.setTag(loggingListItem);
            binding.textView22.setTag(loggingListItem);
            binding.cardView.setTag(loggingListItem);
        }
    }

    class LoggingClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (loggingListener != null)
                loggingListener.onClick((LoggingListItem) v.getTag());
        }
    }

    class UpLoadClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (uploadListener != null)
                uploadListener.onClick((LoggingListItem) v.getTag());
        }
    }

    class DeleteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (deleteListener != null)
                deleteListener.onClick((LoggingListItem) v.getTag());
        }
    }

    public interface ClickListener {
        void onClick(LoggingListItem loggingListItem);
    }
}

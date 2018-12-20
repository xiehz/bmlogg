package com.szbm.wh.x.bmlogg.ui.ui.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.ProjectItemBinding;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectAdapter extends PagedListAdapter<ProjectInfo,ProjectAdapter.ProjectInfoViewHolder> {

    private ProjectInfo current;
    private CurrentChangedListener currentLister;

    public void setCurrent(ProjectInfo projectInfo)
    {
        this.current = projectInfo;
    }
    protected ProjectAdapter(@NonNull DiffUtil.ItemCallback<ProjectInfo> diffCallback,
                             CurrentChangedListener currentChangedListener) {
        super(diffCallback);
        this.currentLister = currentChangedListener;
    }

    protected ProjectAdapter(@NonNull AsyncDifferConfig<ProjectInfo> config) {
        super(config);
    }



    @Override
    public void submitList(@Nullable PagedList<ProjectInfo> pagedList) {
        super.submitList(pagedList);
    }

    @Override
    public void submitList(@Nullable PagedList<ProjectInfo> pagedList, @Nullable Runnable commitCallback) {
        super.submitList(pagedList, commitCallback);
    }

    @Nullable
    @Override
    protected ProjectInfo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Nullable
    @Override
    public PagedList<ProjectInfo> getCurrentList() {
        return super.getCurrentList();
    }

    @Override
    public void onCurrentListChanged(@Nullable PagedList<ProjectInfo> previousList, @Nullable PagedList<ProjectInfo> currentList) {
        super.onCurrentListChanged(previousList, currentList);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectInfoViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @NonNull
    @Override
    public ProjectInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false);
        return new ProjectInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectInfoViewHolder holder, int position) {
        holder.bind(position);
    }

    class ProjectInfoViewHolder extends RecyclerView.ViewHolder{

        ProjectItemBinding projectItemBinding;
        public ProjectInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            projectItemBinding = DataBindingUtil.bind(itemView);
            projectItemBinding.executePendingBindings();
        }

        public void bind(int position){
            projectItemBinding.setProject(getItem(position));
            projectItemBinding.setCurrent(current);
            projectItemBinding.setCurrentListener(new CurrentClickListener(position));
        }

    }

    static class DiffCallbackBuilder extends DiffUtil.ItemCallback<ProjectInfo>{

        public DiffCallbackBuilder() {
            super();
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull ProjectInfo oldItem, @NonNull ProjectInfo newItem) {
            return super.getChangePayload(oldItem, newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull ProjectInfo oldItem, @NonNull ProjectInfo newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProjectInfo oldItem, @NonNull ProjectInfo newItem) {
            return oldItem.getIid() == newItem.getIid();
        }
    }

        class CurrentClickListener implements View.OnClickListener{

        int position;
        CurrentClickListener(int position)
        {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            current = getItem(position);
            currentLister.setCurrent(current.getIid());
        }
    }

    interface CurrentChangedListener{
        void setCurrent(long projectInfo);
    }
}

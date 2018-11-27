package com.szbm.wh.x.bmlogg.ui.ui.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.ProjectItemBinding;
import com.szbm.wh.x.bmlogg.ui.ui.main.MainReceiver;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectInfoViewHolder> {

    private Context context;
    private List<ProjectInfo> projectInfoList;
    private ProjectInfo current;
    private CurrentChangedListener currentLister;
    public ProjectAdapter(Context context,ProjectInfo current, List<ProjectInfo> projectInfos,
                          CurrentChangedListener listener) {
        super();
        this.context = context;
        this.current = current;
        this.projectInfoList = projectInfos;
        this.currentLister = listener;
    }

    @NonNull
    @Override
    public ProjectInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_item,parent,false);
        return new ProjectInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectInfoViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return projectInfoList == null ? 0: projectInfoList.size();
    }

    class ProjectInfoViewHolder extends RecyclerView.ViewHolder{

        ProjectItemBinding projectItemBinding;
        CompoundButton.OnCheckedChangeListener checkedChangeListener;
        public ProjectInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            projectItemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(int position){
            projectItemBinding.setProject(projectInfoList.get(position));
            projectItemBinding.setCurrent(current);
            projectItemBinding.setCurrentListener(new CurrentClickListener(position));
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
            if(current == null || current.getIid() != projectInfoList.get(position).getIid()){
                current = projectInfoList.get(position);
                currentLister.setCurrent(current.getIid());
            }
        }
    }

    interface CurrentChangedListener{
        void setCurrent(int project_id);
    }
}

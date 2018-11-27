package com.szbm.wh.x.bmlogg.ui.ui.boreholes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.binding.BindingAdapters;
import com.szbm.wh.x.bmlogg.databinding.BoreholeItemBinding;
import com.szbm.wh.x.bmlogg.databinding.ProjectItemBinding;
import com.szbm.wh.x.bmlogg.ui.ui.project.ProjectAdapter;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BoreholeAdapter extends PagedListAdapter<BH_BoreholeInfo,BoreholeAdapter.BoreholeViewHolder> {

    protected BoreholeAdapter(@NonNull DiffUtil.ItemCallback<BH_BoreholeInfo> diffCallback) {
        super(diffCallback);
    }

    protected BoreholeAdapter(@NonNull AsyncDifferConfig<BH_BoreholeInfo> config) {
        super(config);
    }

    @Override
    public void submitList(@Nullable PagedList<BH_BoreholeInfo> pagedList) {
        super.submitList(pagedList);
    }

    @Override
    public void submitList(@Nullable PagedList<BH_BoreholeInfo> pagedList, @Nullable Runnable commitCallback) {
        super.submitList(pagedList, commitCallback);
    }

    @Nullable
    @Override
    protected BH_BoreholeInfo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Nullable
    @Override
    public PagedList<BH_BoreholeInfo> getCurrentList() {
        return super.getCurrentList();
    }

    @Override
    public void onCurrentListChanged(@Nullable PagedList<BH_BoreholeInfo> previousList, @Nullable PagedList<BH_BoreholeInfo> currentList) {
        super.onCurrentListChanged(previousList, currentList);
    }

    @Override
    public void onBindViewHolder(@NonNull BoreholeViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @NonNull
    @Override
    public BoreholeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.borehole_item,parent,false);
        return new BoreholeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoreholeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class BoreholeViewHolder extends RecyclerView.ViewHolder{

        BoreholeItemBinding boreholeItemBinding;
        public BoreholeViewHolder(@NonNull View itemView) {
            super(itemView);
            boreholeItemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(BH_BoreholeInfo bh_boreholeInfo){
            boreholeItemBinding.setBorehole(bh_boreholeInfo);
        }

    }

    static class DiffCallbackBuilder extends DiffUtil.ItemCallback<BH_BoreholeInfo>{

        public DiffCallbackBuilder() {
            super();
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull BH_BoreholeInfo oldItem, @NonNull BH_BoreholeInfo newItem) {
            return super.getChangePayload(oldItem, newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull BH_BoreholeInfo oldItem, @NonNull BH_BoreholeInfo newItem) {
            return oldItem.getIid() == newItem.getIid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull BH_BoreholeInfo oldItem, @NonNull BH_BoreholeInfo newItem) {
            return oldItem == newItem;
        }
    }
}

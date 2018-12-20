package com.szbm.wh.x.bmlogg.ui.ui.bh;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.binding.Converter;
import com.szbm.wh.x.bmlogg.databinding.RockSoilFragmentBinding;
import com.szbm.wh.x.bmlogg.pojo.LoggingListItem;
import com.szbm.wh.x.bmlogg.ui.common.CSKeys;
import com.szbm.wh.x.bmlogg.ui.common.LoggingListAdapter;
import com.szbm.wh.x.bmlogg.vo.BH_CoreCatalog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RockSoilFragment extends LoggingListFragment {

    private RockSoilViewModel mViewModel;
    @Inject
    ViewModelProvider.Factory factory;
    long borehole_iid;
    String borehole_name;
    RockSoilFragmentBinding binding;
    LoggingListAdapter loggingListAdapter;


    public static RockSoilFragment newInstance() {
        return new RockSoilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.rock_soil_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,factory).get(RockSoilViewModel.class);
        borehole_iid = getArguments().getLong(CSKeys.cs_bh_iid,-1L);
        borehole_name = getArguments().getString(CSKeys.cs_bh_name);

        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        binding.records.setAdapter(getLoggingListAdapter());
        binding.fab.setOnClickListener((v)->{
            LoggingListItem item = getLast();
            add(item);
        });
        mViewModel.borehole.setValue(borehole_iid);
        onSource();
        mViewModel.deleteCore.observe(this,bh_coreCatalog -> {
            if(bh_coreCatalog == null)
                onLoggingDeleted();
        });
    }

    private void onSource(){
        mViewModel.coreCatalogLiveDatas.observe(this, coreCatalogs -> {
            if(coreCatalogs != null){
                List<LoggingListItem> loggingListItems = new ArrayList<>(coreCatalogs.size());
                String title = null,sub = null;

                for (BH_CoreCatalog item :coreCatalogs){
                    if(item.getDepth() != null){
                        float bottom = item.getDepth() + item.getStep();
                        title = Converter.floatToString(getContext(),item.getDepth()) + "~" + Converter.floatToString(getContext(),bottom);
                    }else{
                        title = Converter.floatToString(getContext(),item.getDepth());
                    }
                    sub = item.getStratum_type();
                    LoggingListItem loggingListItem =
                            new LoggingListItem(title,sub,item.getFrom_server(),item.getIid());
                    loggingListItems.add(loggingListItem);
                }
                submit(loggingListItems);
            }
        });
    }

    @Override
    public void logging(LoggingListItem loggingListItem) {

    }

    @Override
    public void uploading(LoggingListItem loggingListItem) {

    }

    @Override
    public void delete(LoggingListItem loggingListItem) {
        mViewModel.delete(loggingListItem);
    }

    @Override
    public void add(LoggingListItem last) {
        mViewModel.delete(last);

    }
}

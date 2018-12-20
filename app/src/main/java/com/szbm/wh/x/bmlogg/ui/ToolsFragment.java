package com.szbm.wh.x.bmlogg.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.ToolsFragmentBinding;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.ui.location.CustomLocationActivity;
import com.szbm.wh.x.bmlogg.ui.location.CustomLocationModeActivity;
import com.szbm.wh.x.bmlogg.ui.location.LocationModeSourceActivity;
import com.szbm.wh.x.bmlogg.ui.location.Location_Activity;

import javax.inject.Inject;

public class ToolsFragment extends InjectFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private ToolsViewModel mViewModel;
    ToolsFragmentBinding toolsFragmentBinding;

    public static ToolsFragment newInstance() {
        return new ToolsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        toolsFragmentBinding =DataBindingUtil.inflate(inflater,R.layout.tools_fragment, container, false);
        return toolsFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,factory).get(ToolsViewModel.class);

        toolsFragmentBinding.cardView2.setOnClickListener((v)->{
            startActivity(new Intent(getActivity(),CustomLocationActivity.class));
        });
        toolsFragmentBinding.cardView3.setOnClickListener((v)->{
            startActivity(new Intent(getActivity(),CustomLocationModeActivity.class));
        });
        toolsFragmentBinding.cardView4.setOnClickListener((v)->{
            startActivity(new Intent(getActivity(),Location_Activity.class));
        });
        toolsFragmentBinding.cardView5.setOnClickListener((v)->{
            startActivity(new Intent(getActivity(),LocationModeSourceActivity.class));
        });
    }

}

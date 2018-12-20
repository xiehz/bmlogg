package com.szbm.wh.x.bmlogg.ui.ui.bh;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;

import javax.inject.Inject;

public class SptFragment extends InjectFragment {

    private SptViewModel mViewModel;
    @Inject
    ViewModelProvider.Factory factory;

    public static SptFragment newInstance() {
        return new SptFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spt_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,factory).get(SptViewModel.class);
        // TODO: Use the ViewModel
    }

}

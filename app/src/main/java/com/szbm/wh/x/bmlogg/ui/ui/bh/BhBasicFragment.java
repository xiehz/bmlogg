package com.szbm.wh.x.bmlogg.ui.ui.bh;

import androidx.databinding.DataBindingUtil;
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
import com.szbm.wh.x.bmlogg.databinding.BhBasicFragmentBinding;
import com.szbm.wh.x.bmlogg.ui.common.CSKeys;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;

import javax.inject.Inject;

public class BhBasicFragment extends InjectFragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private BhBasicViewModel mViewModel;
    BhBasicFragmentBinding binding;

    public static BhBasicFragment newInstance() {
        return new BhBasicFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.bh_basic_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(BhBasicViewModel.class);
//        binding.setBh(mViewModel);

        int iid = getArguments().getInt(CSKeys.cs_bh_iid,-1);
        mViewModel.index.setValue(iid);
        binding.swipeRefresh.setOnRefreshListener(()->{
            mViewModel.index.setValue(iid);
        });
    }

}

package com.szbm.wh.x.bmlogg.ui.ui.bh;

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

public class RockSoilFragment extends InjectFragment {

    private RockSoilViewModel mViewModel;

    public static RockSoilFragment newInstance() {
        return new RockSoilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rock_soil_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RockSoilViewModel.class);
        // TODO: Use the ViewModel
    }

}

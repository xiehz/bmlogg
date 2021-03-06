package com.szbm.wh.x.bmlogg.ui.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.MainFragmentBinding;
import com.szbm.wh.x.bmlogg.di.Injectable;
import com.szbm.wh.x.bmlogg.pojo.MainProjectInfo;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;

import javax.inject.Inject;

public class MainFragment extends InjectFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel mViewModel;
    MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment, container, false);
       return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel.class);

        refreshProject();

        mViewModel.projectInfoResource.observe(this, projectInfo -> {
            binding.setInfo(projectInfo);
        });
        mViewModel.boreholeCountResource.observe(this, integerResource -> {
            binding.setResource(integerResource);
            binding.setCallback(()->refreshProject());

            if(integerResource.getStatus() == Status.SUCCESS || integerResource.getStatus() == Status.ERROR)
            {
                binding.swipeRefresh.setRefreshing(false);
                if(integerResource.getData() != null){
                    binding.setBoreholes(integerResource.getData());
                    binding.setShowEntry(true);
                    binding.floatingActionButton.setOnClickListener((v)->{
                        Navigation.findNavController(binding.getRoot()).navigate(
                                R.id.action_mainFragment_to_boreholesActivity);
                    });
                }
                else{
                    binding.setShowEntry(false);
                }
            }
        });

        binding.swipeRefresh.setOnRefreshListener(
                ()-> refreshProject()
        );
    }

    private void refreshProject(){
        mViewModel.projectLiveData.setValue(mViewModel.loadCurrent());
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

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
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;

import javax.inject.Inject;

public class MainFragment extends InjectFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel mViewModel;
    MainFragmentBinding binding;
    ProjectTreeView projectTreeView;
    MainReceiver mainReceiver;

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

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        projectTreeView = new ProjectTreeView(getActivity());
        projectTreeView.setLayoutParams(layoutParams);
        projectTreeView.setOrientation(LinearLayout.VERTICAL);
        binding.projectTree.addView(projectTreeView);

        mViewModel.projectTreeAdapterLiveData
        .observe(this,resource ->
        {
            binding.setResource(resource);
            binding.setCallback(()->mViewModel.getProject());

            if(resource!=null && resource.getStatus()== Status.SUCCESS)
            {
                if(resource !=null&&resource.getData() != null)
                {
                    projectTreeView.setAdapter(resource.getData());
                    binding.setProject(mViewModel.projectInfoMutableLiveData.getValue());
                    binding.setShowEntry(true);
                    binding.floatingActionButton.setOnClickListener((v)->{
                        mViewModel.saveProject(projectTreeView.adater);
                    });

                }
                else
                    binding.setShowEntry(false);
            }
            else
                binding.setShowEntry(false);
        });
        mViewModel.projectInfoMutableLiveData.observe(this,projectInfo -> binding.setProject(projectInfo));
        mViewModel.saved.observe(this,stringResource -> {
            binding.setResource(stringResource);
            binding.setCallback(()->mViewModel.saveProject(projectTreeView.adater));
            if(stringResource.getStatus() == Status.SUCCESS){
//                Snackbar.make()
                Navigation.findNavController(binding.getRoot()).navigate(
                        R.id.action_mainFragment_to_boreholesActivity);
            }
        });
        mViewModel.getProject();
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

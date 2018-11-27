package com.szbm.wh.x.bmlogg.ui.ui.project;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.State;
import androidx.work.WorkStatus;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.LoginActivity2FragmentBinding;
import com.szbm.wh.x.bmlogg.databinding.ProjectFragmentBinding;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.ui.ui.main.MainReceiver;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.worker.Constants;
import com.szbm.wh.x.bmlogg.worker.DBDownloadOperation;

import java.util.List;

import javax.inject.Inject;

public class ProjectFragment extends InjectFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    ProjectFragmentBinding binding;

    private ProjectViewModel mViewModel;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =
                DataBindingUtil.inflate(inflater,R.layout.project_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(ProjectViewModel.class);
        mViewModel.loadCurrent();
        mViewModel.loadLocalDb();

        mViewModel.resourceMediatorLiveData.observe(this,observer->{
            binding.setResource(observer);
            if(observer.getData() != null)
            {
                ProjectAdapter projectAdapter = new ProjectAdapter(
                        getActivity(),
                        mViewModel.current.getValue(),
                        observer.getData(),project_id ->
                {
                    mViewModel.setCurrent(project_id);
                    binding.setProject(mViewModel.current.getValue());
                });
                binding.projectRecyclerView.setAdapter(projectAdapter);
            }
        });

        mViewModel.current.observe(this,observer-> binding.setProject(observer));
        this.onWorker();

        binding.projectFloatingActionButton.setOnClickListener( v-> mViewModel.loadFromNetWork());
        binding.setCallback(()->mViewModel.loadFromNetWork());
        binding.setDownloadListener((v)->{
            DBDownloadOperation imageOperations = new
                    DBDownloadOperation.Builder(mViewModel.current.getValue().getIid())
                    .build();

            mViewModel.apply(imageOperations);
        });


    }

    private void onWorker(){
        mViewModel.getProjectStatus().observe(this,workStatuses -> {
            if (workStatuses == null || workStatuses.isEmpty()) {
                return;
            }

            WorkStatus status = workStatuses.get(0);
            String output = status.getOutputData().getString(Constants.PROJECT_DATA_OUTPUT);
            switch (status.getState())
            {
                case FAILED:
                    if(output == null || output.isEmpty())
                        output = "下载工程遇到错误，请重新下载！";
                    Toast.makeText(getContext(),output,
                            Toast.LENGTH_LONG).show();
                    binding.setResource(Resource.Companion.loading(null));
                    break;
                case ENQUEUED:
                    Toast.makeText(getContext(),"开始下载工程！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case RUNNING:
                    Toast.makeText(getContext(),"正在下载工程！",
                            Toast.LENGTH_SHORT).show();
                    binding.setResource(Resource.Companion.loading(null));
                    break;
                case SUCCEEDED:
                    Toast.makeText(getContext(),"下载工程成功！",
                            Toast.LENGTH_SHORT).show();
                    binding.setResource(Resource.Companion.success(null));
                    break;
            }
        });

        mViewModel.getBHStatus().observe(this,workStatuses -> {
            if (workStatuses == null || workStatuses.isEmpty()) {
                return;
            }

            WorkStatus status = workStatuses.get(0);
            String output = status.getOutputData().getString(Constants.BH_DATA_OUTPUT);
            switch (status.getState())
            {
                case FAILED:
                    if(output == null || output.isEmpty())
                        output = "下载钻孔的过程中遇见错误，请重新下载！";
                    Toast.makeText(getContext(),output,
                            Toast.LENGTH_LONG).show();
                    binding.setResource(Resource.Companion.success(null));
                    break;
                case ENQUEUED:
                    Toast.makeText(getContext(),"开始下载钻孔数据！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case RUNNING:
                    Toast.makeText(getContext(),"正在下载钻孔数据！",
                            Toast.LENGTH_SHORT).show();
                    binding.setResource(Resource.Companion.loading(null));
                    break;
                case SUCCEEDED:
                    Toast.makeText(getContext(),"下载钻孔数据完成！",
                            Toast.LENGTH_SHORT).show();
                    binding.setResource(Resource.Companion.success(null));
                    break;
            }
        });
    }

}

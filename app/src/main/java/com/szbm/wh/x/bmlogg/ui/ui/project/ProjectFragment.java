package com.szbm.wh.x.bmlogg.ui.ui.project;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.WorkStatus;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.LoginActivity2FragmentBinding;
import com.szbm.wh.x.bmlogg.databinding.ProjectFragmentBinding;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.ui.ui.main.MainReceiver;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;
import com.szbm.wh.x.bmlogg.worker.Constants;
import com.szbm.wh.x.bmlogg.worker.DBDownloadOperation;

import java.util.List;

import javax.inject.Inject;

public class ProjectFragment extends InjectFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ProjectFragmentBinding binding;

    private ProjectViewModel mViewModel;
    private MaterialDialog materialDialog;


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

        materialDialog =  new MaterialDialog.Builder(getContext())
                .title("我的项目")
                .content("同步项目中的数据")
                .progress(true,0,false)
                .canceledOnTouchOutside(false)
                .keyListener((dialog, keyCode, event) -> {
                    if(keyCode == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
                        return true;
                    else
                        return false;
                })
                .build();
        //工程列表
        ProjectAdapter projectAdapter = new ProjectAdapter(
                new ProjectAdapter.DiffCallbackBuilder(),listener->{
                materialDialog.show();
                //关联
                mViewModel.releation.setValue(listener);
        });
        binding.projectRecyclerView.setAdapter(projectAdapter);

        mViewModel.diskResult.get().observe(this,projectInfos -> {
            if(projectInfos == null|| projectInfos.size() == 0){
                new MaterialDialog.Builder(getActivity())
                        .title("我的项目")
                        .content("没有发现缓存项目，是否从网络加载？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(((dialog, which) -> {
                            mViewModel.loadFromNetWork();
                        }))
                        .onNegative(((dialog, which) -> {

                        }))
                        .build()
                        .show();
            }
            else{
                projectAdapter.submitList(projectInfos);
            }
            mViewModel.diskResult.get().removeObservers(this);
        });

        //下载工程
        mViewModel.projectBoreholesLiveData.observe(this,
                resource -> {
                    binding.setResource(resource);
                    if(resource.getStatus()== Status.SUCCESS
                            || resource.getStatus() == Status.ERROR){
                        materialDialog.dismiss();
                    }
                    if(resource.getData() != null){
                        mViewModel.setCurrent(resource.getData().getIid());
                        mViewModel.loadCurrent();
                    }
                });

        mViewModel.result.observe(this,
                pagedListResource -> {
                    binding.setResource(pagedListResource);
                    if(pagedListResource != null){
                        switch (pagedListResource.getStatus()){
                            case LOADING:
                                break;
                            case SUCCESS:
                            case ERROR:
                                binding.setCallback(()->mViewModel.loadFromNetWork());
                                binding.swipeRefresh.setRefreshing(false);
                                break;
                        }
                    }
                });

        binding.swipeRefresh.setOnRefreshListener(()->mViewModel.loadFromNetWork()
        );

        //当前项目
        mViewModel.current.observe(this,observer->
            binding.setProject(observer)
        );
        mViewModel.loadCurrent();

        this.onWorker();
        binding.setDownloadListener((v)->{
            DBDownloadOperation imageOperations = new
                    DBDownloadOperation.Builder(mViewModel.current.getValue().getIid(),
                    mViewModel.bh_logger.getNumber())
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

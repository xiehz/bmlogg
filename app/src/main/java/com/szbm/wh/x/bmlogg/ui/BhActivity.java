package com.szbm.wh.x.bmlogg.ui;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.szbm.wh.x.bmlogg.databinding.BhActivityBinding;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.common.CSKeys;
import com.szbm.wh.x.bmlogg.ui.common.FragmentAdapter;
import com.szbm.wh.x.bmlogg.ui.common.InjectActivity;
import com.szbm.wh.x.bmlogg.ui.ui.bh.BhBasicFragment;
import com.szbm.wh.x.bmlogg.ui.ui.bh.BhBeginFragment;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.ui.ui.bh.BhViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class BhActivity extends InjectActivity {

    BhActivityBinding bhActivityBinding;
    @Inject
    ViewModelProvider.Factory factory;
    private BhViewModel mViewModel;
    String name_bh;
    long iid_bh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bhActivityBinding = DataBindingUtil.setContentView(this,R.layout.bh_activity);

        iid_bh = getIntent().getLongExtra(CSKeys.cs_bh_iid,-1L);
        name_bh = getIntent().getStringExtra(CSKeys.cs_bh_name);
        mViewModel = ViewModelProviders.of(this,factory).get(BhViewModel.class);

        bhActivityBinding.setLifecycleOwner(this);
        bhActivityBinding.setViewModel(mViewModel);

        bhActivityBinding.fabMain.setOnClickListener((v)->{
            Intent intent = new Intent(this,LoggingDetailActivity.class);
            intent.putExtra(CSKeys.cs_bh_iid,iid_bh);
            intent.putExtra(CSKeys.cs_bh_name,name_bh);
            startActivity(intent);
        });
        mViewModel.index.setValue(iid_bh);

        mViewModel.resourceLiveData.observe(this,boreholeSetResource -> {
                    bhActivityBinding.setResource(boreholeSetResource);
                    bhActivityBinding.setCallback(()->mViewModel.invoker.setValue(iid_bh));
                    if(boreholeSetResource.getStatus() != Status.LOADING){
                        bhActivityBinding.swipeRefresh.setRefreshing(false);
                    }
                });
        bhActivityBinding.swipeRefresh.setOnRefreshListener(
                ()->mViewModel.invoker.setValue(iid_bh)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_location:
                Intent intent = new Intent(this,LoggerBHActivity.class);
                intent.putExtra(LoggerBHActivity.s_index,iid_bh);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

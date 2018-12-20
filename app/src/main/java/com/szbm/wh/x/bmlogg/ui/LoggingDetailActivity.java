package com.szbm.wh.x.bmlogg.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.BhActivityBinding;
import com.szbm.wh.x.bmlogg.databinding.LoggingDetailActivityBinding;
import com.szbm.wh.x.bmlogg.ui.common.CSKeys;
import com.szbm.wh.x.bmlogg.ui.common.FragmentAdapter;
import com.szbm.wh.x.bmlogg.ui.common.InjectActivity;
import com.szbm.wh.x.bmlogg.ui.ui.bh.BhViewModel;
import com.szbm.wh.x.bmlogg.ui.ui.loggingdetail.LoggingDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class LoggingDetailActivity extends InjectActivity {

    private LoggingDetailViewModel mViewModel;
    @Inject
    ViewModelProvider.Factory factory;
    LoggingDetailActivityBinding binding;
    String name_bh;
    long iid_bh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.logging_detail_activity);

        iid_bh = getIntent().getLongExtra(CSKeys.cs_bh_iid,-1L);
        name_bh = getIntent().getStringExtra(CSKeys.cs_bh_name);
        setSupportActionBar(binding.toolbarMain);
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.begin_borehole));
        titles.add(getString(R.string.rocksoil_borehole));
        titles.add(getString(R.string.sampling_borehole));
        titles.add(getString(R.string.spt_borehole));
        titles.add(getString(R.string.end_borehole));

        TabLayout tabLayoutMain = binding.tabLayoutMain;
        for (String t:titles
                ) {
            tabLayoutMain.addTab(tabLayoutMain.newTab().setText(t));
        }


        binding.viewPagerMain.setOffscreenPageLimit(1);
        FragmentAdapter mFragmentAdapter = FragmentAdapter.Builder.build(iid_bh,getSupportFragmentManager(),titles);
        binding.viewPagerMain.setAdapter(mFragmentAdapter);
        tabLayoutMain.setupWithViewPager(binding.viewPagerMain);
        tabLayoutMain.setTabsFromPagerAdapter(mFragmentAdapter);
        binding.viewPagerMain.addOnPageChangeListener(pageChangeListener);

        mViewModel = ViewModelProviders.of(this,factory).
                get(LoggingDetailViewModel.class);

    }

    private ViewPager.OnPageChangeListener pageChangeListener =
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 2) {
//                fab.show();
                    } else {
//                fab.hide();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
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
            case R.id.action_upload_begin:
                break;
            case R.id.action_upload_rocksoil:
                break;
            case R.id.action_upload_sample:
                break;
            case R.id.action_upload_spt:
                break;
            case R.id.action_upload_end:
                break;
            case R.id.action_upload:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

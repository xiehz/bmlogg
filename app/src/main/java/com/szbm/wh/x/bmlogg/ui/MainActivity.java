package com.szbm.wh.x.bmlogg.ui;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.ActivityMainBinding;
import com.szbm.wh.x.bmlogg.databinding.NavHeaderMainBinding;
import com.szbm.wh.x.bmlogg.ui.common.InjectActivity;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;

import javax.inject.Inject;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import androidx.drawerlayout.widget.DrawerLayout;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends InjectActivity
{

    @Inject
    BH_Logger bh_logger;

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.
                setContentView(this,R.layout.activity_main);

        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),R.layout.nav_header_main,
                null,false
        );
        navHeaderMainBinding.setTel(bh_logger.getTel());
        binding.navView.addHeaderView(navHeaderMainBinding.getRoot());

        this.drawerLayout = binding.drawerLayout;
        NavController navController = Navigation.findNavController(this,R.id.nav_host_main);
        setSupportActionBar(binding.toolbar);
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout);
        NavigationUI.setupWithNavController(binding.navView,navController);
    }



    @Override
    public boolean onSupportNavigateUp(){
        return NavigationUI.navigateUp(
                drawerLayout,
                Navigation.findNavController(this, R.id.nav_host_main));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}

package com.szbm.wh.x.bmlogg.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import android.os.Bundle;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.ui.common.InjectActivity;
import com.szbm.wh.x.bmlogg.ui.ui.loginactivity2.LoginActivity2Fragment;

import javax.inject.Inject;

public class LoginActivity2 extends InjectActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity2_activity);
    }

}

package com.szbm.wh.x.bmlogg.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.szbm.wh.x.bmlogg.ui.ui.bh.BhFragment;
import com.szbm.wh.x.bmlogg.R;


public class BhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bh_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BhFragment.newInstance())
                    .commitNow();
        }
    }
}

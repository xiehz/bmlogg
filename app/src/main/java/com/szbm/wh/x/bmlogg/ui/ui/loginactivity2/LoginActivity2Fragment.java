package com.szbm.wh.x.bmlogg.ui.ui.loginactivity2;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.databinding.LoginActivity2FragmentBinding;
import com.szbm.wh.x.bmlogg.di.Injectable;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;


import javax.inject.Inject;

public class LoginActivity2Fragment extends InjectFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    LoginActivity2FragmentBinding binding;

    private LoginActivity2ViewModel mViewModel;

    public static LoginActivity2Fragment newInstance() {
        return new LoginActivity2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.login_activity2_fragment,
                container,
                false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this,viewModelFactory).
                get(LoginActivity2ViewModel.class);
        mViewModel.initialize();
        binding.setLogger(mViewModel);

        binding.setLoginListener((View view)->{
            dismissKeyboard(view.getWindowToken());
            mViewModel.login();
        });


        binding.setCallback(()-> {
            mViewModel.login();
        });

        mViewModel.getResults().observe(this,observer->{
            binding.setResource(observer);
            if(observer  != null)
            {
                if(observer.getData() != null){
                    mViewModel.save(observer.getData());
                    Navigation.findNavController(binding.getRoot()).navigate(
                            R.id.action_loginActivity2Fragment_to_mainActivity
                    );
                }
            }
        });

        // TODO: Use the ViewModel
    }

    private void dismissKeyboard(IBinder windowToken) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(windowToken,0);
    }
}

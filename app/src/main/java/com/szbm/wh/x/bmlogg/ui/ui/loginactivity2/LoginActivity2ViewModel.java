package com.szbm.wh.x.bmlogg.ui.ui.loginactivity2;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.LoginRepository;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.databinding.ObservableViewModel;

import java.nio.channels.MulticastChannel;
import java.util.List;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class LoginActivity2ViewModel extends ObservableViewModel {
    private String tel;
    private String pass;
    @Bindable
    public String getTel(){
        return tel;
    }

    public void setTel(String tel){
        this.tel = tel;
        notifyChange();
    }

    @Bindable
    public String getPass(){
        return this.pass;
    }

    public void setPass(String pass){
        this.pass = pass;
        notifyChange();
    }

    @Inject
    BH_Logger bh_logger;
    @Inject
    LoginRepository loginRepository;
    @Inject
    BmloggSharedPreference bmloggSharedPreference;


    @Inject
    public LoginActivity2ViewModel(){
    }

    public void login(){
        if(validString(this.tel) && validString(this.pass))
            bhLoggerMutableLiveData.setValue(new BH_Logger(-1,this.tel,this.pass,null));
        else{
            bhLoggerMutableLiveData.setValue(null);
        }
    }
    private boolean validString(String string){
        return string !=null && !string.isEmpty() ;
    }


    public void initialize(){
        if(bh_logger != null || bh_logger.getNumber() != -1){
            setTel(bh_logger.getTel());
            setPass(bh_logger.getPass());
        }
        else{

        }
    }

    public void save(BH_Logger logger){
        bmloggSharedPreference.writeLogin(logger);
        bh_logger.setNumber(logger.getNumber());
        bh_logger.setTel(logger.getTel());
        bh_logger.setPass(logger.getPass());
        bh_logger.setUrl(logger.getUrl());
    }

    private final MutableLiveData<BH_Logger> bhLoggerMutableLiveData = new MutableLiveData<>();
    private final LiveData<Resource<BH_Logger>> results = Transformations.switchMap(
            bhLoggerMutableLiveData,
            logger->{
                if(logger == null)
                    return AbsentLiveData.Companion.create();
                else
                {
                    return loginRepository.login(logger);
                }
            });

    public LiveData<Resource<BH_Logger>> getResults(){
        return this.results;
    }
}

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
    @Inject
    LoginRepository loginRepository;
    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    private final MutableLiveData<BH_Logger> bhLoggerMutableLiveData= new MutableLiveData<>();
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

    private int number;
    private String tel;
    private String pass;

    @Inject
    public LoginActivity2ViewModel(){
    }

    public MutableLiveData<BH_Logger> getBhLoggerMutableLiveData(){
        return bhLoggerMutableLiveData ;
    }

    public LiveData<Resource<BH_Logger>> getResults(){
        return this.results;
    }
    @Bindable
    public String getTel(){
        return tel;
    }

    public void setTel(String tel){
        this.tel = tel;
    }

    @Bindable
    public String getPass(){
        return this.pass;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public void login(){
        if(validString(this.tel) && validString(this.pass))
            getBhLoggerMutableLiveData().setValue(new BH_Logger(this.number,this.tel,this.pass));
        else{
            getBhLoggerMutableLiveData().setValue(null);
        }
    }
    private boolean validString(String string){
        return string !=null && !string.isEmpty() ;
    }


    public void initialize(){
        BH_Logger bh_logger = bmloggSharedPreference.readLogin();
        if(bh_logger != null){
            setTel(bh_logger.getTel());
            setPass(bh_logger.getPass());
            this.number = bh_logger.getNumber();
        }
        else{
            setTel("11");
            setPass("123432");
        }
    }

    public void save(BH_Logger bh_logger){
        bmloggSharedPreference.writeLogin(bh_logger);
    }
}

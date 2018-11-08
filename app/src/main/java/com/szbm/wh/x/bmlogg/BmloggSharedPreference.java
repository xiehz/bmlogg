package com.szbm.wh.x.bmlogg;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.szbm.wh.x.bmlogg.vo.BH_Logger;

public class BmloggSharedPreference {

    SharedPreferences sharedPreferences;
    final static String id_key ="id";
    final static String user_key = "user";
    final static String pass_key = "pass";

    private BmloggSharedPreference(Builder builder){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(builder.application);
    }
    public final static class Builder{
        Application application;
        public Builder(Application app){
            this.application = app;
        }

        public BmloggSharedPreference build(){
            return new BmloggSharedPreference(this);
        }
    }

    public BH_Logger readLogin(){
        if(sharedPreferences.contains(user_key)&&
                sharedPreferences.contains(user_key)&&
                sharedPreferences.contains(pass_key)){
            int id = -1; String user = null ,pass = null;
            sharedPreferences.getInt(id_key,id);
            sharedPreferences.getString(user_key,user);
            sharedPreferences.getString(pass_key,pass);
            return new BH_Logger(id,user,pass);
        }
        return null;
    }
    public void writeLogin(BH_Logger login){
        sharedPreferences.edit().putInt(id_key,login.getNumber());
        sharedPreferences.edit().putString(user_key,login.getTel());
        sharedPreferences.edit().putString(pass_key,login.getPass());
        sharedPreferences.edit().commit();
    }
}

package com.szbm.wh.x.bmlogg;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.szbm.wh.x.bmlogg.vo.BH_Logger;

public class BmloggSharedPreference {

    SharedPreferences sharedPreferences;
    final static String tag = "com.szbm.wh.x.bmlg";
    final static String id_key ="id";
    final static String user_key = "user";
    final static String pass_key = "pass";
    final static String current_project = "cur_project";

    private BmloggSharedPreference(Builder builder){
        sharedPreferences = builder.application.getSharedPreferences(tag,Context.MODE_PRIVATE);
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
        int id = -1; String user = "" ,pass = "";

        return new BH_Logger(sharedPreferences.getInt(id_key,id)
                ,sharedPreferences.getString(user_key,user),
                sharedPreferences.getString(pass_key,pass));
    }
    public void writeLogin(BH_Logger login){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(id_key,login.getNumber());
        editor.putString(user_key,login.getTel());
        editor.putString(pass_key,login.getPass());
        editor.commit();
    }

    public int readCurrentProject(){
        return sharedPreferences.getInt(current_project,-1);
    }

    public void writeCurrentProject(int project_id){
        sharedPreferences.edit().putInt(current_project,project_id).commit();
    }
}

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
    final static String url_key = "url";
    final static String current_project = "cur_project";
    final static String search_radius = "search_radius";
    final static String logging_depth = "logging_depth";

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
        Long id = -1l; String user = "" ,pass = "",url = null;

        return new BH_Logger(sharedPreferences.getLong(id_key,id)
                ,sharedPreferences.getString(user_key,user),
                sharedPreferences.getString(pass_key,pass),
                sharedPreferences.getString(url_key,url));
    }
    public void writeLogin(BH_Logger login){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(id_key,login.getNumber());
        editor.putString(user_key,login.getTel());
        editor.putString(pass_key,login.getPass());
        editor.putString(url_key,login.getUrl()== null?"":login.getUrl());
        editor.commit();
    }

    public long readCurrentProject(){
        return sharedPreferences.getLong(current_project,-1l);
    }

    public void writeCurrentProject(long project_id){
        sharedPreferences.edit().putLong(current_project,project_id).commit();
    }

    public void writesearch_radius(int ra){
        sharedPreferences.edit().putInt(search_radius,ra).commit();
    }

    public int readsearch_radius(){
        return sharedPreferences.getInt(search_radius,30);
    }

    public float readLoggingDepth(){return sharedPreferences.getFloat(logging_depth,1.0f);}

    public void writeLoggingDepth(float depth){
        sharedPreferences.edit().putFloat(logging_depth,depth);
    }
}

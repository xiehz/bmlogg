package com.szbm.wh.x.bmlogg.pojo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ImageInfo extends BaseObservable {
    public ImageInfo(String t, String u,String n,String desc){
        this.title = t;
        this.url = u;
        this.name = n;
        this.desc = desc;
    }
    String title;
    @Bindable
    public void setTitle(String t){
        this.title = t;
    }
    public String getTitle(){
        return this.title;
    }

    String url;
    @Bindable
    public void setUrl(String u){
        this.url = u;
    }
    public String getUrl(){
        return this.url;
    }

    String name;
    @Bindable
    public void setName(String n){
        this.name = n;
    }
    public String getName(){
        return this.name;
    }

    String desc;
    @Bindable
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

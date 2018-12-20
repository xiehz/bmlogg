package com.szbm.wh.x.bmlogg.util;

public class StringPreder {
    public static boolean isNullOrEmpty(String v){
        if(v == null) return true;
        else{
            if(v.trim().isEmpty())
                return true;
            else
                return false;
        }
    }
}

package com.szbm.wh.x.bmlogg.binding;

import android.content.Context;
import android.widget.EditText;

import com.szbm.wh.x.bmlogg.R;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseMethod;

public class Converter {

    @InverseMethod("stringToFloat")
    public static  String floatToString(Context context,Float value){
        if(value != null){
            return context.getString(R.string.ftoS4,value);
        }
        else{
            return null;
        }
    }

    public static Float stringToFloat(Context context,String value){
        if(value == null) return null;
        try{
            Float r  = Float.parseFloat(value);
            return r;
        }catch (Exception e)
        {
            return null;
        }
    }

    @InverseMethod("stringToDouble")
    public static  String doubleToString(Context context,Double value){
        if(value != null){
            return context.getString(R.string.ftoS4,value);
        }
        else{
            return null;
        }
    }

    public static Double stringToDouble(Context context,String value){
        if(value == null) return null;
        try{
            Double r  = Double.parseDouble(value);
            return r;
        }catch (Exception e)
        {
            return null;
        }
    }


    @InverseMethod("stringToInt")
    public static  String intToString(Context context,Integer value){
        if(value != null){
            return context.getString(R.string.itoS,value);
        }
        else{
            return null;
        }
    }

    public static Integer stringToInt(Context context,String value){
        if(value == null) return null;
        try{
            Integer r  = Integer.parseInt(value);
            return r;
        }catch (Exception e)
        {
            return null;
        }
    }

}

package com.techease.ramdan.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.techease.ramdan.R;

/**
 * Created by eapple on 18/12/2018.
 */

public class GeneralUtils {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static Fragment connectFragmentWithoutBack(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        return fragment;
    }

    public static Fragment connectFragmentWithBack(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("").commit();
        return fragment;
    }

    public static Fragment connectHomeFragment(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,fragment).commit();
        return fragment;
    }

    public static Fragment connectHomeFragmentWithBack(Context context,Fragment fragment){
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,fragment).addToBackStack("back").commit();
        return fragment;
    }

    public static SharedPreferences.Editor putBooleanValueInEditor(Context context, String key, boolean value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value).commit();
        return editor;
    }

    public static SharedPreferences.Editor putStringValueInEditor(Context context, String key, String value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
        return editor;
    }



    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("ssc", 0);
    }

    public static String getSehriTime(Context context) {
        return getSharedPreferences(context).getString("sehri_time","");
    }
    public static String getIftarTime(Context context) {
        return getSharedPreferences(context).getString("iftar_time","");
    }


    public static boolean reatailerLogin(Context context){
        return getSharedPreferences(context).getBoolean("retailer_loggedIn",false);
    }

}

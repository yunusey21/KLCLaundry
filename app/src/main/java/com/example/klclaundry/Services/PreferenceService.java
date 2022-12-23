package com.example.klclaundry.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceService {
    public final String PREF_NAME = "USERNAME";
    private final SharedPreferences sharedPreferences;

    public PreferenceService(Context cnt) {
        sharedPreferences = cnt.getSharedPreferences(PREF_NAME,cnt.MODE_PRIVATE);
    }
    public void push(String key, String value) {
        SharedPreferences.Editor edt = this.sharedPreferences.edit();
        edt.putString(key, value);
        edt.apply();
    }

    public String  get(String value, String def) {
        return this.sharedPreferences.getString(value,def);

    }

    public void pushInt(String key, int value) {
        SharedPreferences.Editor edt = this.sharedPreferences.edit();
        edt.putInt(key,value);
        edt.apply();

    }

    public int getInt(String key, int def) {
        return this.sharedPreferences.getInt(key,def);
    }


}

package com.romickid.simpbook.database;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.content.Context;

/**
 * 保存用户信息类
 */
public class UserPreference {
    private static SharedPreferences mUserPreferences;
    private static final String USER_PREFERENCE = "user_preference";
    /**
     * 初始化保存接口
     */
    public static SharedPreferences ensureIntializePreference(Context context) {
        if (mUserPreferences == null) {
            mUserPreferences =context.getSharedPreferences(USER_PREFERENCE, 0);
        }
        return mUserPreferences;
    }
    /**
     * 保存
     */
    public static void save(Context context, String key, String value) {
        Editor editor = ensureIntializePreference(context).edit();
        editor.putString(key, value);
        editor.commit(); 
    }

    /**
     * 读取
     */
    public static String read(Context context, String key, String defaultvalue) {
        return ensureIntializePreference(context).getString(key, defaultvalue);
    }
    
}

package com.romickid.simpbook.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Other {
    /**
     * 关闭软键盘
     *
     * @param activity default
     */
    public static void turnoffKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&& activity.getCurrentFocus()!=null){
            if ( activity.getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow( activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 设置toast显示信息
     *
     * @param message 待显示的信息
     * @param context default
     * @param length 显示时间
     */
    public static void displayToast(String message, Context context, int length) {
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
        if (length != 1)
            Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

}

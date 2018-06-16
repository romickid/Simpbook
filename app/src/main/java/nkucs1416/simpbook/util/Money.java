package nkucs1416.simpbook.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class Money {
    /**
     * 为EditText设置金额的输入格式
     *
     * @param editText 输入金额的editText
     */
    public static void setEditTextDecimalScheme(EditText editText) {
        final EditText et = editText;
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        et.setText(s);
                        et.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et.setText(s);
                    et.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et.setText(s.subSequence(0, 1));
                        et.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * 为TextView显示正确的金额形式
     *
     * @param textView 显示金额的textView
     * @param money 金额
     */
    public static void setTextViewDecimalMoney(TextView textView, float money) {
        textView.setText(String.format(Locale.ROOT, "%.2f", money));
    }

    /**
     * 获取EditText存储的金额信息
     *
     * @param editText 待获取的EditText
     */
    public static float getEditTextMoney(EditText editText) {
        return Float.parseFloat(editText.getText().toString());
    }

    /**
     * 为EditText显示正确的金额形式
     *
     * @param editText 显示金额的EditText
     * @param money 金额
     */
    public static void setEditTextDecimalMoney(EditText editText, float money) {
        editText.setText(String.format(Locale.ROOT, "%.2f", money));
    }

}

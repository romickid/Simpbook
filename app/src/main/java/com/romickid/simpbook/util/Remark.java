package com.romickid.simpbook.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.romickid.simpbook.R;

import static com.romickid.simpbook.util.Other.turnoffKeyboard;

public class Remark {
    /**
     * 构建输入备注的dialog
     *
     * @param textViewRemark 显示备注的textView
     * @param context        default
     * @param activity       default
     * @return dialog
     */
    public static Dialog createDialogRemark(
            final TextView textViewRemark,
            final Context context,
            final Activity activity
    ) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, 3);
        View viewRemarkDialog = View.inflate(activity, R.layout.dialog_remark, null);
        final EditText editTextDialog = viewRemarkDialog.findViewById(R.id.dremark_edittext);

        editTextDialog.setText(textViewRemark.getText());

        builder.setTitle("备注");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textViewRemark.setText(editTextDialog.getText());
                turnoffKeyboard(activity);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                turnoffKeyboard(activity);
            }
        });

        return builder.create();
    }

}

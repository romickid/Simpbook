package com.romickid.simpbook.statement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.romickid.simpbook.R;

class ViewHolderStatementRecordDefault extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageViewBackground;
    Context context;


    /**
     * StatementRecordDefault的Holder, 与istatementdefault相关
     *
     * @param view default
     */
    ViewHolderStatementRecordDefault(View view) {
        super(view);
        context = view.getContext();
        imageView = view.findViewById(R.id.irecorddefault_color);
        textViewText = view.findViewById(R.id.irecorddefault_text);
        textViewMoney = view.findViewById(R.id.irecorddefault_money);
        imageViewBackground = view.findViewById(R.id.irecorddefault_background);
    }

}

package com.romickid.simpbook.statement;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.romickid.simpbook.R;

class ViewHolderStatementDate extends RecyclerView.ViewHolder {
    TextView textViewText;


    /**
     * StatementDate的Holder, 与istatementdate相关
     *
     * @param view default
     */
    ViewHolderStatementDate(View view) {
        super(view);
        textViewText = view.findViewById(R.id.istatementdate_text);
    }

}

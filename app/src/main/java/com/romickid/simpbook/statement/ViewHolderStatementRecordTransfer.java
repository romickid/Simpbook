package com.romickid.simpbook.statement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.romickid.simpbook.R;

class ViewHolderStatementRecordTransfer extends RecyclerView.ViewHolder {
    TextView textViewTextFrom;
    TextView textViewTextTo;
    ImageView imageViewFrom;
    TextView textViewMoney;
    ImageView imageViewBackground;
    Context context;


    /**
     * StatementRecordTransfer的Holder, 与istatementtransfer相关
     *
     * @param view default
     */
    ViewHolderStatementRecordTransfer(View view) {
        super(view);
        context = view.getContext();
        textViewTextFrom = view.findViewById(R.id.irecordtransfer_textfrom);
        textViewTextTo = view.findViewById(R.id.irecordtransfer_textto);
        imageViewFrom = view.findViewById(R.id.irecordtransfer_colorfrom);
        textViewMoney = view.findViewById(R.id.irecordtransfer_money);
        imageViewBackground = view.findViewById(R.id.irecordtransfer_background);
    }

}

package nkucs1416.simpbook.account;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderAccountSummarize extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;


    /**
     * AccountSummarize的Holder, 与iaccountsummarize相关
     *
     * @param view default
     */
    ViewHolderAccountSummarize(View view) {
        super(view);
        textViewText = view.findViewById(R.id.iaccountsummarize_text);
        textViewMoney = view.findViewById(R.id.iaccountsummarize_money);
    }

}

package nkucs1416.simpbook.account;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nkucs1416.simpbook.R;

public class ViewHolderAccountSummarize extends RecyclerView.ViewHolder {
    public TextView textViewText;
    public TextView textViewMoney;


    /**
     * AccountSummarize的Holder, 与iaccountsummarize相关
     *
     * @param view default
     */
    public ViewHolderAccountSummarize(View view) {
        super(view);
        textViewText = view.findViewById(R.id.iaccountsummarize_text);
        textViewMoney = view.findViewById(R.id.iaccountsummarize_money);
    }

}

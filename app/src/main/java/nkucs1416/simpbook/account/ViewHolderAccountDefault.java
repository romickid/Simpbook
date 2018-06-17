package nkucs1416.simpbook.account;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderAccountDefault extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageViewColor;
    ImageView imageViewBackground;
    Context context;


    /**
     * AccountElement的Holder, 与iaccountdefault相关
     *
     * @param view default
     */
    ViewHolderAccountDefault(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.iaccountdefault_text);
        textViewMoney = view.findViewById(R.id.iaccountdefault_money);
        imageViewColor = view.findViewById(R.id.iaccountdefault_color);
        imageViewBackground = view.findViewById(R.id.iaccountdefault_background);
    }

}

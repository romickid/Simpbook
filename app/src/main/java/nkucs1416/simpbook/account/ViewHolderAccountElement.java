package nkucs1416.simpbook.account;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderAccountElement extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageViewColor;
    ImageView imageViewBackground;
    Context context;


    /**
     * AccountElement的Holder, 与iaccount相关
     *
     * @param view default
     */
    ViewHolderAccountElement(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.iaccountelement_text);
        textViewMoney = view.findViewById(R.id.iaccountelement_money);
        imageViewColor = view.findViewById(R.id.iaccountelement_color);
        imageViewBackground = view.findViewById(R.id.iaccountelement_background);
    }

}

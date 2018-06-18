package nkucs1416.simpbook.account;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

public class ViewHolderAccountDefault extends RecyclerView.ViewHolder {
    public TextView textViewText;
    public TextView textViewMoney;
    public ImageView imageViewColor;
    public ImageView imageViewBackground;
    public Context context;


    /**
     * AccountElement的Holder, 与iaccountdefault相关
     *
     * @param view default
     */
    public ViewHolderAccountDefault(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.iaccountdefault_text);
        textViewMoney = view.findViewById(R.id.iaccountdefault_money);
        imageViewColor = view.findViewById(R.id.iaccountdefault_color);
        imageViewBackground = view.findViewById(R.id.iaccountdefault_background);
    }

}

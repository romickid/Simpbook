package nkucs1416.simpbook.fragments.Collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderCollectionDefault extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageViewColor;
    ImageView imageViewBackground;
    Context context;


    /**
     * CollectionDefault的Holder, 与irecorddefault相关
     *
     * @param view default
     */
    ViewHolderCollectionDefault(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.irecorddefault_text);
        textViewMoney = view.findViewById(R.id.irecorddefault_money);
        imageViewColor = view.findViewById(R.id.irecorddefault_color);
        imageViewBackground = view.findViewById(R.id.irecorddefault_background);
    }

}

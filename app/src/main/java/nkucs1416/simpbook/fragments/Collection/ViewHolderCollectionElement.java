package nkucs1416.simpbook.fragments.Collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderCollectionElement extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageViewColor;
    ImageView imageViewBackground;
    Context context;


    /**
     * CollectionElement的Holder, 与icollectionelement相关
     *
     * @param view default
     */
    ViewHolderCollectionElement(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.icollectionelement_text);
        textViewMoney = view.findViewById(R.id.icollectionelement_money);
        imageViewColor = view.findViewById(R.id.icollectionelement_color);
        imageViewBackground = view.findViewById(R.id.icollectionelement_background);
    }

}

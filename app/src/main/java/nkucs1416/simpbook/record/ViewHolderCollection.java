package nkucs1416.simpbook.record;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderCollection extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageView;


    ViewHolderCollection(View view) {
        super(view);
        textViewText = (TextView) view.findViewById(R.id.icollection_text);
        textViewMoney = (TextView) view.findViewById(R.id.icollection_money);
        imageView = (ImageView) view.findViewById(R.id.icollection_color);
    }

}

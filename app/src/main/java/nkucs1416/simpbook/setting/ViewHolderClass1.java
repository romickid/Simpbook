package nkucs1416.simpbook.setting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderClass1 extends RecyclerView.ViewHolder {
    TextView textViewText;
    ImageView imageViewColor;
    ImageView imageViewEdit;
    ImageView imageViewDelete;
    ImageView imageViewBackground;


    public ViewHolderClass1(View view) {
        super(view);
        textViewText = view.findViewById(R.id.iclasselement_text);
        imageViewColor = view.findViewById(R.id.iclasselement_color);
        imageViewEdit = view.findViewById(R.id.iclasselement_iconedit);
        imageViewDelete = view.findViewById(R.id.iclasselement_icondelete);
        imageViewBackground = view.findViewById(R.id.iclasselement_background);
    }

}

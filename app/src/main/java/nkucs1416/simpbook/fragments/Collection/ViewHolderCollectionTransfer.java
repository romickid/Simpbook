package nkucs1416.simpbook.fragments.Collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderCollectionTransfer extends RecyclerView.ViewHolder {
    TextView textViewTextFrom;
    TextView textViewTextTo;
    ImageView imageViewFrom;
    TextView textViewMoney;
    ImageView imageViewBackground;
    Context context;


    /**
     * CollectionTransfer的Holder, 与irecordtransfer相关
     *
     * @param view default
     */
    ViewHolderCollectionTransfer(View view) {
        super(view);
        context = view.getContext();
        textViewTextFrom = view.findViewById(R.id.irecordtransfer_textfrom);
        textViewTextTo = view.findViewById(R.id.irecordtransfer_textto);
        imageViewFrom = view.findViewById(R.id.irecordtransfer_colorfrom);
        textViewMoney = view.findViewById(R.id.irecordtransfer_money);
        imageViewBackground = view.findViewById(R.id.irecordtransfer_background);
    }

}

package nkucs1416.simpbook.statement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;

/**
 *
 */
class ViewHolderStatementElement extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageView;
    ImageView imageViewBackground;
    Context context;


    /**
     * StatementElement的Holder, 与istatement相关
     *
     * @param view default
     */
    ViewHolderStatementElement(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.istatement_text);
        textViewMoney = view.findViewById(R.id.istatement_money);
        imageView = view.findViewById(R.id.istatement_color);
        imageViewBackground = view.findViewById(R.id.istatement_background);
    }


}

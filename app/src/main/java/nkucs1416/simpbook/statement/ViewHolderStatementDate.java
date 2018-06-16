package nkucs1416.simpbook.statement;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderStatementDate extends RecyclerView.ViewHolder {
    TextView textViewText;


    /**
     * StatementDate的Holder, 与istatementdate相关
     *
     * @param view default
     */
    ViewHolderStatementDate(View view) {
        super(view);
        textViewText = view.findViewById(R.id.istatementdate_text);
    }

}

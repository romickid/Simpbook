package nkucs1416.simpbook.fragments.Collection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nkucs1416.simpbook.R;

class ViewHolderCollectionSummarize extends RecyclerView.ViewHolder {
    TextView textViewText;


    /**
     * CollectionSummarize的Holder, 与icollectionsummerize相关
     *
     * @param view default
     */
    ViewHolderCollectionSummarize(View view) {
        super(view);
        textViewText = view.findViewById(R.id.icollectionsummerize_text);
    }

}

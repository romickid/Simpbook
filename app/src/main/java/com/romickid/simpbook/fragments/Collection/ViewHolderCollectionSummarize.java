package com.romickid.simpbook.fragments.Collection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.romickid.simpbook.R;

class ViewHolderCollectionSummarize extends RecyclerView.ViewHolder {
    TextView textViewText;


    /**
     * CollectionSummarize的Holder, 与icollectionsummarize相关
     *
     * @param view default
     */
    ViewHolderCollectionSummarize(View view) {
        super(view);
        textViewText = view.findViewById(R.id.icollectionsummarize_text);
    }

}

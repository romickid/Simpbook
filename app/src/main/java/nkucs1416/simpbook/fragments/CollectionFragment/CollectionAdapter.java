package nkucs1416.simpbook.fragments.CollectionFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.edit.EditActivity;
import nkucs1416.simpbook.record.RecordActivity;
import nkucs1416.simpbook.util.CollectionElement;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listCollectionObjects;
    private Context context;

    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的CollectionObjects列表
     *
     * @param tContext 传入的Context
     * @param tListCollectionAdapter 传入的CollectionObjects列表
     */
    CollectionAdapter(Context tContext, ArrayList<HashMap<String, Object>> tListCollectionAdapter) {
        super();
        this.context = tContext;
        this.listCollectionObjects = tListCollectionAdapter;
    }

    /**
     * 根据不同的viewType构建不同的ViewHolder
     *
     * @param parent default
     * @param viewType 1:CollectionElement, 0:CollectionSummarize
     * @return viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_collection_summerize, parent, false);
                return new CollectionSummarizeViewHolder(view);
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_collection_element, parent, false);
                return new CollectionElementViewHolder(view);
        }
        return null;
    }

    /**
     * 根据不同的位置, 为holder构建不同的实例
     *
     * @param holder 传入的holder实例
     * @param position 位置(不同位置viewType不同)
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                CollectionSummarizeViewHolder collectionSummarizeViewHolder = (CollectionSummarizeViewHolder) holder;
                CollectionSummarize collectionSummarize = (CollectionSummarize)listCollectionObjects.get(position).get("object");

                final String text0 = collectionSummarize.getText();

                collectionSummarizeViewHolder.textViewText.setText(text0);

                break;
            case 1:
                CollectionElementViewHolder collectionElementViewHolder = (CollectionElementViewHolder) holder;
                CollectionElement collectionElement = (CollectionElement)listCollectionObjects.get(position).get("object");

                final String text1 = "abc";
                final String money1 = "1.0";
                final int color1 = R.drawable.ic_lens_blue_a400_24dp;

                final ImageView imageViewBackground1 = collectionElementViewHolder.imageViewBackground;
                final Context context1 = collectionElementViewHolder.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, RecordActivity.class);
                        intent.putExtra("type","expense");
                        context1.startActivity(intent);
                    }
                });

                collectionElementViewHolder.textViewText.setText(text1);
                collectionElementViewHolder.textViewMoney.setText(money1);
                collectionElementViewHolder.imageViewColor.setImageResource(color1);

                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:CollectionElement, 0:CollectionSummarize, -1:CollectionSplitLine)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listCollectionObjects.get(position).get("type");
    }

    /**
     * 获取Objects数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listCollectionObjects.size();
    }

}


/**
 * CollectionElement的Holder, 与icollection相关
 */
class CollectionElementViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageViewColor;
    ImageView imageViewBackground;
    Context context;

    CollectionElementViewHolder(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.icollectionelement_text);
        textViewMoney = view.findViewById(R.id.icollectionelement_money);
        imageViewColor = view.findViewById(R.id.icollectionelement_color);
        imageViewBackground = view.findViewById(R.id.icollectionelement_background);
    }

}


/**
 * CollectionSummarize的Holder, 与icollectionsummerize相关
 */
class CollectionSummarizeViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;

    CollectionSummarizeViewHolder(View view) {
        super(view);
        textViewText = view.findViewById(R.id.icollectionsummerize_text);
    }
}



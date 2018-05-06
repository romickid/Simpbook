package nkucs1416.simpbook.record;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import nkucs1416.simpbook.R;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private ArrayList<Map<String,Object>> listMap;
    private Context context;

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_collection, parent, false);

        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Map<String,Object> map = listMap.get(position);
        String text = (String) map.get("text");
        String money = (String) map.get("money");
        int color = (int) map.get("color");

        holder.textViewText.setText(text);
        holder.textViewMoney.setText(money);
        holder.imageView.setImageResource(color);
    }

    @Override
    public int getItemCount() {
        return listMap.size();
    }

    public CollectionAdapter(Context mContext, ArrayList<Map<String,Object>> mlistMap) {
        super();
        this.context = mContext;
        this.listMap = mlistMap;
    }

}


class RecyclerHolder extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageView;

    public RecyclerHolder(View view) {
        super(view);
        textViewText = (TextView) view.findViewById(R.id.icollection_text);
        textViewMoney = (TextView) view.findViewById(R.id.icollection_money);
        imageView = (ImageView) view.findViewById(R.id.icollection_color);
    }
}


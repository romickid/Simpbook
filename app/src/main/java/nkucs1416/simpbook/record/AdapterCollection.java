package nkucs1416.simpbook.record;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

import nkucs1416.simpbook.R;

public class AdapterCollection extends RecyclerView.Adapter<ViewHolderCollection> {
    private ArrayList<Map<String,Object>> listMaps;
    private Context context;


    @NonNull
    @Override
    public ViewHolderCollection onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_collection, parent, false);

        return new ViewHolderCollection(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCollection holder, int position) {
        Map<String,Object> map = listMaps.get(position);
        String text = (String) map.get("text");
        String money = (String) map.get("money");
        int color = (int) map.get("color");

        holder.textViewText.setText(text);
        holder.textViewMoney.setText(money);
        holder.imageView.setImageResource(color);
    }

    @Override
    public int getItemCount() {
        return listMaps.size();
    }

    public AdapterCollection(Context tContext, ArrayList<Map<String,Object>> tListMaps) {
        super();
        this.context = tContext;
        this.listMaps = tListMaps;
    }

}

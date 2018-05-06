package nkucs1416.simpbook.statement;

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

public class StatementAdapter extends RecyclerView.Adapter<StatementViewHolder> {
    private ArrayList<Map<String,Object>> listMap;
    private Context context;

    @NonNull
    @Override
    public StatementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_statement, parent, false);

        return new StatementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatementViewHolder holder, int position) {
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

    public StatementAdapter(Context mContext, ArrayList<Map<String,Object>> mlistMap) {
        super();
        this.context = mContext;
        this.listMap = mlistMap;
    }
}

class StatementViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageView;

    public StatementViewHolder(View view) {
        super(view);
        textViewText = (TextView) view.findViewById(R.id.istatement_text);
        textViewMoney = (TextView) view.findViewById(R.id.istatement_money);
        imageView = (ImageView) view.findViewById(R.id.istatement_color);
    }
}
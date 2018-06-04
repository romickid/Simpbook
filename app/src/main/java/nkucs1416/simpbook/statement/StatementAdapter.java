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
import nkucs1416.simpbook.util.StatementElement;

public class StatementAdapter extends RecyclerView.Adapter<StatementViewHolder> {
    private ArrayList<StatementElement> listStatementElements;
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
        StatementElement statementElement = listStatementElements.get(position);
        int color = statementElement.getColor();
        String text = statementElement.getText();
        Float money = statementElement.getMoney();

        holder.textViewText.setText(text);
        holder.textViewMoney.setText(money.toString());
        holder.imageView.setImageResource(color);
    }

    @Override
    public int getItemCount() {
        return listStatementElements.size();
    }

    StatementAdapter(Context mContext, ArrayList<StatementElement> tlistStatementElement) {
        super();
        this.context = mContext;
        this.listStatementElements = tlistStatementElement;
    }
}

class StatementViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageView;

    StatementViewHolder(View view) {
        super(view);
        textViewText = (TextView) view.findViewById(R.id.istatement_text);
        textViewMoney = (TextView) view.findViewById(R.id.istatement_money);
        imageView = (ImageView) view.findViewById(R.id.istatement_color);
    }
}
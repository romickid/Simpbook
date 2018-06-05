package nkucs1416.simpbook.statement;

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
import nkucs1416.simpbook.util.StatementRecord;

import static nkucs1416.simpbook.util.Date.getStrDate;

public class StatementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listStatementObjects;
    private Context context;

    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的AccountObjects列表
     *
     * @param tContext 传入的Context
     * @param tListStatementObjects 传入的StatementObjects列表
     */
    StatementAdapter(Context tContext, ArrayList<HashMap<String, Object>> tListStatementObjects) {
        super();
        this.context = tContext;
        this.listStatementObjects = tListStatementObjects;
    }

    /**
     * 根据不同的viewType构建不同的ViewHolder
     *
     * @param parent default
     * @param viewType 1:StatementRecord, 0:StatementDate
     * @return viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement_date, parent, false);
                return new StatementDateViewHolder(view);
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement, parent, false);
                return new StatementElementViewHolder(view);
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
                StatementDateViewHolder statementDateViewHolder = (StatementDateViewHolder) holder;
                StatementDate statementDate = (StatementDate)listStatementObjects.get(position).get("object");

                final String text0 = getStrDate(statementDate.getDate());

                statementDateViewHolder.textViewText.setText(text0);

                break;
            case 1:
                StatementElementViewHolder statementElementViewHolder = (StatementElementViewHolder) holder;
                StatementRecord statementRecord = (StatementRecord)listStatementObjects.get(position).get("object");

                final int color1 = R.drawable.ic_lens_blue_a400_24dp;
                final String text1 = "abc";
                final String money1 = statementRecord.getStrMoney();

                final ImageView imageViewBackground1 = statementElementViewHolder.imageViewBackground;
                final Context context1 = statementElementViewHolder.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, EditActivity.class);
                        intent.putExtra("type","expense");
                        context1.startActivity(intent);
                    }
                });

                statementElementViewHolder.imageView.setImageResource(color1);
                statementElementViewHolder.textViewText.setText(text1);
                statementElementViewHolder.textViewMoney.setText(money1);

                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:AccountElement, 0:AccountSummarize)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listStatementObjects.get(position).get("isElement");
    }

    /**
     * 获取Objects数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listStatementObjects.size();
    }

}


/**
 * StatementElement的Holder, 与istatement相关
 */
class StatementElementViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageView;
    ImageView imageViewBackground;
    Context context;

    StatementElementViewHolder(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.istatement_text);
        textViewMoney = view.findViewById(R.id.istatement_money);
        imageView = view.findViewById(R.id.istatement_color);
        imageViewBackground = view.findViewById(R.id.istatement_background);
    }
}


/**
 * StatementDate的Holder, 与istatementdate相关
 */
class StatementDateViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;

    StatementDateViewHolder(View view) {
        super(view);
        textViewText = view.findViewById(R.id.istatementdate_text);
    }
}
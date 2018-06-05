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
import java.util.HashMap;

import nkucs1416.simpbook.R;

import static nkucs1416.simpbook.util.Date.getStrDate;

public class StatementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listStatementObjects;
    private Context context;

    // RecyclerView.ViewHolder相关
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
     * @param viewType 1:StatementElement, 0:StatementDate
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
        String text = null;
        String money = null;
        Integer color = null;
        switch (getItemViewType(position)) {
            case 0:
                StatementDateViewHolder statementDateViewHolder = (StatementDateViewHolder) holder;
                StatementDate statementDate = (StatementDate)listStatementObjects.get(position).get("object");

                text = getStrDate(statementDate.getDate());

                statementDateViewHolder.textViewText.setText(text);
                break;
            case 1:
                StatementElementViewHolder statementElementViewHolder = (StatementElementViewHolder) holder;
                StatementElement statementElement = (StatementElement)listStatementObjects.get(position).get("object");

                color = statementElement.getColor();
                text = statementElement.getText();
                money = statementElement.getStrMoney();

                statementElementViewHolder.textViewText.setText(text);
                statementElementViewHolder.textViewMoney.setText(money);
                statementElementViewHolder.imageView.setImageResource(color);
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

    StatementElementViewHolder(View view) {
        super(view);
        textViewText = (TextView) view.findViewById(R.id.istatement_text);
        textViewMoney = (TextView) view.findViewById(R.id.istatement_money);
        imageView = (ImageView) view.findViewById(R.id.istatement_color);
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
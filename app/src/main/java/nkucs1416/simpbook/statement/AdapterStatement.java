package nkucs1416.simpbook.statement;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.edit.ActivityEdit;
import nkucs1416.simpbook.util.StatementRecord;

import static nkucs1416.simpbook.util.Date.getStrDate;

public class AdapterStatement extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listStatementObjects;
    private Context context;


    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的AccountObjects列表
     *
     * @param tContext 传入的Context
     * @param tListStatementObjects 传入的StatementObjects列表
     */
    AdapterStatement(Context tContext, ArrayList<HashMap<String, Object>> tListStatementObjects) {
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
                return new ViewHolderStatementDate(view);
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_statement_record, parent, false);
                return new ViewHolderStatementElement(view);
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
                ViewHolderStatementDate viewHolderStatementDate = (ViewHolderStatementDate) holder;
                StatementDate statementDate = (StatementDate)listStatementObjects.get(position).get("object");

                final String text0 = getStrDate(statementDate.getDate());

                viewHolderStatementDate.textViewText.setText(text0);

                break;
            case 1:
                ViewHolderStatementElement viewHolderStatementElement = (ViewHolderStatementElement) holder;
                StatementRecord statementRecord = (StatementRecord)listStatementObjects.get(position).get("object");

                final int color1 = R.drawable.ic_lens_blue_a400_24dp;
                final String text1 = "abc";
                final String money1 = statementRecord.getStrMoney();

                final ImageView imageViewBackground1 = viewHolderStatementElement.imageViewBackground;
                final Context context1 = viewHolderStatementElement.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, ActivityEdit.class);
                        intent.putExtra("type","expense");
                        context1.startActivity(intent);
                    }
                });

                viewHolderStatementElement.imageView.setImageResource(color1);
                viewHolderStatementElement.textViewText.setText(text1);
                viewHolderStatementElement.textViewMoney.setText(money1);

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

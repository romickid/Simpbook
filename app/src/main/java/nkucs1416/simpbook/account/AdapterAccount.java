package nkucs1416.simpbook.account;

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
import nkucs1416.simpbook.statement.ActivityStatement;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Money.setTextViewMoneyDecimal;

public class AdapterAccount extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listAccountObjects;
    private Context context;


    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的AccountObjects列表
     *
     * @param tContext 传入的Context
     * @param tListAccountObjects 传入的AccountObjects列表
     */
    AdapterAccount(Context tContext, ArrayList<HashMap<String, Object>> tListAccountObjects) {
        super();
        this.context = tContext;
        this.listAccountObjects = tListAccountObjects;
    }

    /**
     * 根据不同的viewType构建不同的ViewHolder
     *
     * @param parent default
     * @param viewType 1:AccountElement, 0:AccountSummarize, -1:AccountSplitLine
     * @return viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_account_summerize, parent, false);
                return new ViewHolderAccountSummarize(view);
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_account_element, parent, false);
                return new ViewHolderAccountElement(view);
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
                ViewHolderAccountSummarize viewHolderAccountSummarize = (ViewHolderAccountSummarize) holder;
                AccountSummarize accountSummarize = (AccountSummarize)listAccountObjects.get(position).get("object");

                final String text0 = accountSummarize.getText();
                final float money0 = accountSummarize.getMoney();

                viewHolderAccountSummarize.textViewText.setText(text0);
                setTextViewMoneyDecimal(viewHolderAccountSummarize.textViewMoney, money0);

                break;

            case 1:
                ViewHolderAccountElement viewHolderAccountElement = (ViewHolderAccountElement) holder;
                AccountElement accountElement = (AccountElement)listAccountObjects.get(position).get("object");

                final int colorIcon1 = getColorIcon(accountElement.getColorId());
                final String text1 = accountElement.getText();
                final float money1 = accountElement.getMoney();

                final ImageView imageViewBackground1 = viewHolderAccountElement.imageViewBackground;
                final Context context1 = viewHolderAccountElement.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, ActivityStatement.class);
                        intent.putExtra("account",text1);
                        context1.startActivity(intent);
                    }
                });

                viewHolderAccountElement.textViewText.setText(text1);
                setTextViewMoneyDecimal(viewHolderAccountElement.textViewMoney, money1);
                viewHolderAccountElement.imageViewColor.setImageResource(colorIcon1);

                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:AccountElement, 0:AccountSummarize, -1:AccountSplitLine)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listAccountObjects.get(position).get("type");
    }

    /**
     * 获取Objects数量
     *
     * @return 数量
     */
    @Override
    public int getItemCount() {
        return listAccountObjects.size();
    }

}

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
import nkucs1416.simpbook.util.Account;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Money.setTextViewDecimalMoney;

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
     * @param viewType 1:AccountDefault, 2:AccountSummarize
     * @return viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_account_default, parent, false);
                return new ViewHolderAccountDefault(view);
            case 2:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_account_summarize, parent, false);
                return new ViewHolderAccountSummarize(view);
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
            case 1:
                ViewHolderAccountDefault viewHolderAccountDefault = (ViewHolderAccountDefault) holder;
                final Account account = (Account)listAccountObjects.get(position).get("Object");

                final int colorIcon1 = getColorIcon(account.getColor());
                final String text1 = account.getName();
                final float money1 = account.getMoney();

                final ImageView imageViewBackground1 = viewHolderAccountDefault.imageViewBackground;
                final Context context1 = viewHolderAccountDefault.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, ActivityStatement.class);
                        intent.putExtra("StatementFilterDate", "Default");
                        intent.putExtra("StatementFilterAccount", String.valueOf(account.getId()));
                        context1.startActivity(intent);
                    }
                });

                viewHolderAccountDefault.textViewText.setText(text1);
                setTextViewDecimalMoney(viewHolderAccountDefault.textViewMoney, money1);
                viewHolderAccountDefault.imageViewColor.setImageResource(colorIcon1);
                break;

            case 2:
                ViewHolderAccountSummarize viewHolderAccountSummarize = (ViewHolderAccountSummarize) holder;
                AccountSummarize accountSummarize = (AccountSummarize)listAccountObjects.get(position).get("Object");

                final String text2 = accountSummarize.getText();
                final float money2 = accountSummarize.getMoney();

                viewHolderAccountSummarize.textViewText.setText(text2);
                setTextViewDecimalMoney(viewHolderAccountSummarize.textViewMoney, money2);
                break;
        }
    }

    /**
     * 获取View的类型
     *
     * @param position 数组的位置
     * @return 类型(1:AccountDefault, 2:AccountSummarize)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listAccountObjects.get(position).get("AccountObjectViewType");
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

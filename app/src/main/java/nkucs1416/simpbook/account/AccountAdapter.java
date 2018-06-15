package nkucs1416.simpbook.account;

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
import nkucs1416.simpbook.statement.StatementActivity;

import static nkucs1416.simpbook.util.Color.getColorIcon;
import static nkucs1416.simpbook.util.Money.setTextViewMoneyDecimal;

public class AccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listAccountObjects;
    private Context context;

    // RecyclerView.Adapter相关
    /**
     * 构造函数, 读取需要绘制的AccountObjects列表
     *
     * @param tContext 传入的Context
     * @param tListAccountObjects 传入的AccountObjects列表
     */
    AccountAdapter(Context tContext, ArrayList<HashMap<String, Object>> tListAccountObjects) {
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
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_account_summerize, parent, false);
                return new AccountSummarizeViewHolder(view);
            case 1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_account_element, parent, false);
                return new AccountElementViewHolder(view);
            case -1:
                view = LayoutInflater.from(context)
                        .inflate(R.layout.item_splitline, parent, false);
                return new SplitLineViewHolder(view);
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
                AccountSummarizeViewHolder accountSummarizeViewHolder = (AccountSummarizeViewHolder) holder;
                AccountSummarize accountSummarize = (AccountSummarize)listAccountObjects.get(position).get("object");

                final String text0 = accountSummarize.getText();
                final float money0 = accountSummarize.getMoney();

                accountSummarizeViewHolder.textViewText.setText(text0);
                setTextViewMoneyDecimal(accountSummarizeViewHolder.textViewMoney, money0);

                break;

            case 1:
                AccountElementViewHolder accountElementViewHolder = (AccountElementViewHolder) holder;
                AccountElement accountElement = (AccountElement)listAccountObjects.get(position).get("object");

                final int colorIcon1 = getColorIcon(accountElement.getColorId());
                final String text1 = accountElement.getText();
                final float money1 = accountElement.getMoney();

                final ImageView imageViewBackground1 = accountElementViewHolder.imageViewBackground;
                final Context context1 = accountElementViewHolder.context;

                imageViewBackground1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context1, StatementActivity.class);
                        intent.putExtra("account",text1);
                        context1.startActivity(intent);
                    }
                });

                accountElementViewHolder.textViewText.setText(text1);
                setTextViewMoneyDecimal(accountElementViewHolder.textViewMoney, money1);
                accountElementViewHolder.imageViewColor.setImageResource(colorIcon1);

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


/**
 * AccountElement的Holder, 与iaccount相关
 */
class AccountElementViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;
    ImageView imageViewColor;
    ImageView imageViewBackground;
    Context context;

    AccountElementViewHolder(View view) {
        super(view);
        context = view.getContext();
        textViewText = view.findViewById(R.id.iaccountelement_text);
        textViewMoney = view.findViewById(R.id.iaccountelement_money);
        imageViewColor = view.findViewById(R.id.iaccountelement_color);
        imageViewBackground = view.findViewById(R.id.iaccountelement_background);
    }

}


/**
 * AccountSummarize的Holder, 与iaccountsummerize相关
 */
class AccountSummarizeViewHolder extends RecyclerView.ViewHolder {
    TextView textViewText;
    TextView textViewMoney;

    AccountSummarizeViewHolder(View view) {
        super(view);
        textViewText = view.findViewById(R.id.iaccountsummerize_text);
        textViewMoney = view.findViewById(R.id.iaccountsummerize_money);
    }
}


/**
 * AccountSplitLine的Holder, 与isplitline相关
 */
class SplitLineViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;

    SplitLineViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.isplitline_imageview);
    }
}
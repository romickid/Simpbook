package nkucs1416.simpbook.account;

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

public class AccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HashMap<String, Object>> listAccountObjects;
    private Context context;

    // RecycleView.Adapter相关
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
     * @param viewType 1:AccountElement, 0:AccountSummarize
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
                        .inflate(R.layout.item_account, parent, false);
                return new AccountElementViewHolder(view);
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
                AccountSummarizeViewHolder accountSummarizeViewHolder = (AccountSummarizeViewHolder) holder;
                AccountSummarize accountSummarize = (AccountSummarize)listAccountObjects.get(position).get("object");

                text = accountSummarize.getText();
                money = accountSummarize.getStrMoney();

                accountSummarizeViewHolder.textViewText.setText(text);
                accountSummarizeViewHolder.textViewMoney.setText(money);
                break;
            case 1:
                AccountElementViewHolder accountElementViewHolder = (AccountElementViewHolder) holder;
                AccountElement accountElement = (AccountElement)listAccountObjects.get(position).get("object");

                color = accountElement.getColor();
                text = accountElement.getText();
                money = accountElement.getStrMoney();

                accountElementViewHolder.textViewText.setText(text);
                accountElementViewHolder.textViewMoney.setText(money);
                accountElementViewHolder.imageView.setImageResource(color);
                break;
        }
    }

    /**
     * 获取ItemView的类型
     *
     * @param position 数组的位置
     * @return 类型(1:AccountElement, 0:AccountSummarize)
     */
    @Override
    public int getItemViewType(int position) {
        return (int)listAccountObjects.get(position).get("isElement");
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
    ImageView imageView;


    AccountElementViewHolder(View view) {
        super(view);
        textViewText = (TextView) view.findViewById(R.id.iaccount_text);
        textViewMoney = (TextView) view.findViewById(R.id.iaccount_money);
        imageView = (ImageView) view.findViewById(R.id.iaccount_color);
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
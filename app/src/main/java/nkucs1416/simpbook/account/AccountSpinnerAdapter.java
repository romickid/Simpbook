package nkucs1416.simpbook.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.util.Account;

public class AccountSpinnerAdapter extends BaseAdapter {
    private ArrayList<Account> listAccount;
    private Context context;

    /**
     * 构造函数, 读取需要绘制的Account列表
     * @param tcontext 传入的Context
     * @param tlistAccount 传入的Account列表
     */
    public AccountSpinnerAdapter(Context tcontext, ArrayList<Account> tlistAccount) {
        super();
        this.listAccount = tlistAccount;
        this.context = tcontext;
    }

    /**
     * 获取Account的数量
     * @return 数量
     */
    @Override
    public int getCount() {
        return listAccount.size();
    }

    /**
     * 获取指定位置的Account的实例
     * @return 实例
     */
    @Override
    public Account getItem(int position) {
        return listAccount.get(position);
    }

    /**
     * 获取指定位置的Account的Id
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return listAccount.get(position).getId();
    }

    /**
     * 获取Account的绘制形式(view)
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner,null);
        TextView textView = (TextView) view.findViewById(R.id.ispinner_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.ispinner_color);

        Account Account = listAccount.get(position);
        String text = Account.getName();
        int color = Account.getColor();

        textView.setText(text);
        imageView.setImageResource(color);

        return view;
    }
}

package nkucs1416.simpbook.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nkucs1416.simpbook.R;

import static nkucs1416.simpbook.util.Color.getColorIcon;

public class SpinnerAdapterAccount extends BaseAdapter {
    private ArrayList<Account> listAccounts;
    private Context context;

    /**
     * 构造函数, 读取需要绘制的Account列表
     * @param tContext 传入的Context
     * @param tListAccounts 传入的Account列表
     */
    public SpinnerAdapterAccount(Context tContext, ArrayList<Account> tListAccounts) {
        super();
        this.listAccounts = tListAccounts;
        this.context = tContext;
    }

    /**
     * 获取Account的数量
     * @return 数量
     */
    @Override
    public int getCount() {
        return listAccounts.size();
    }

    /**
     * 获取指定位置的Account的实例
     * @return 实例
     */
    @Override
    public Account getItem(int position) {
        return listAccounts.get(position);
    }

    /**
     * 获取指定位置的Account的Id
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return listAccounts.get(position).getId();
    }

    /**
     * 获取Account的绘制形式(view)
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner,null);
        TextView textView = view.findViewById(R.id.ispinner_text);
        ImageView imageView = view.findViewById(R.id.ispinner_color);

        Account Account = listAccounts.get(position);
        String text = Account.getName();
        int colorId = Account.getColor();
        int colorIcon = getColorIcon(colorId);

        textView.setText(text);
        imageView.setImageResource(colorIcon);

        return view;
    }

}

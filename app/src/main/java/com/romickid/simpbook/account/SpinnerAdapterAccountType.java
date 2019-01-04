package com.romickid.simpbook.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.romickid.simpbook.R;

import static com.romickid.simpbook.account.AccountType.*;

public class SpinnerAdapterAccountType extends BaseAdapter {
    private ArrayList<Integer> listAccountTypes;
    private Context context;


    /**
     * 构造函数, 读取需要绘制的AccountType列表
     *
     * @param tContext          传入的Context
     * @param tListAccountTypes 传入的AccountType列表
     */
    public SpinnerAdapterAccountType(Context tContext, ArrayList<Integer> tListAccountTypes) {
        super();
        this.listAccountTypes = tListAccountTypes;
        this.context = tContext;
    }

    /**
     * 获取AccountType的数量
     *
     * @return 数量
     */
    @Override
    public int getCount() {
        return listAccountTypes.size();
    }

    /**
     * 获取指定位置的AccountType的实例
     *
     * @return 实例
     */
    @Override
    public Integer getItem(int position) {
        return listAccountTypes.get(position);
    }

    /**
     * 获取指定位置的AccountType的Id
     *
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return listAccountTypes.get(position);
    }

    /**
     * 获取AccountType的绘制形式(view)
     *
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner, null);
        TextView textViewText = view.findViewById(R.id.ispinner_text);
        ImageView imageViewAccountType = view.findViewById(R.id.ispinner_color);

        int accountTypeId = listAccountTypes.get(position);
        int accountTypeIcon = getAccountTypeIcon(accountTypeId);
        String text = getAccountTypeName(accountTypeId);

        textViewText.setText(text);
        imageViewAccountType.setImageResource(accountTypeIcon);

        return view;
    }

}

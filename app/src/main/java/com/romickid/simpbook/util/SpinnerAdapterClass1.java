package com.romickid.simpbook.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.romickid.simpbook.R;

import static com.romickid.simpbook.util.Color.getColorIcon;

public class SpinnerAdapterClass1 extends BaseAdapter {
    private ArrayList<Class1> listClass1s;
    private Context context;


    /**
     * 构造函数, 读取需要绘制的Class1列表
     *
     * @param tContext 传入的Context
     * @param tListClass1s 传入的Class1列表
     */
    public SpinnerAdapterClass1(Context tContext, ArrayList<Class1> tListClass1s) {
        super();
        this.listClass1s = tListClass1s;
        this.context = tContext;
    }


    /**
     * 获取Class1的数量
     *
     * @return 数量
     */
    @Override
    public int getCount() {
        return listClass1s.size();
    }

    /**
     * 获取指定位置的Class1的实例
     *
     * @return 实例
     */
    @Override
    public Class1 getItem(int position) {
        return listClass1s.get(position);
    }

    /**
     * 获取指定位置的Class1的Id
     *
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return listClass1s.get(position).getId();
    }

    /**
     * 获取Class1的绘制形式(view)
     *
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner,null);
        TextView textView = view.findViewById(R.id.ispinner_text);
        ImageView imageView = view.findViewById(R.id.ispinner_color);

        Class1 class1 = listClass1s.get(position);
        String text = class1.getName();
        int colorId = class1.getColor();
        int color = getColorIcon(colorId);

        textView.setText(text);
        imageView.setImageResource(color);

        return view;
    }

}

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

public class Class1SpinnerAdapter extends BaseAdapter {
    private ArrayList<Class1> listClass1;
    private Context context;
    
    /**
     * 构造函数, 读取需要绘制的Class1列表
     * @param tcontext 传入的Context
     * @param tlistClass1 传入的Class1列表
     */
    public Class1SpinnerAdapter(Context tcontext, ArrayList<Class1> tlistClass1) {
        super();
        this.listClass1 = tlistClass1;
        this.context = tcontext;
    }

    /**
     * 获取Class1的数量
     * @return 数量
     */
    @Override
    public int getCount() {
        return listClass1.size();
    }

    /**
     * 获取指定位置的Class1的实例
     * @return 实例
     */
    @Override
    public Class1 getItem(int position) {
        return listClass1.get(position);
    }

    /**
     * 获取指定位置的Class1的Id
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return listClass1.get(position).getId();
    }

    /**
     * 获取Class1的绘制形式(view)
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner,null);
        TextView textView = (TextView) view.findViewById(R.id.ispinner_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.ispinner_color);

        Class1 class1 = listClass1.get(position);
        String text = class1.getName();
        int color = class1.getColor();

        textView.setText(text);
        imageView.setImageResource(color);

        return view;
    }
}

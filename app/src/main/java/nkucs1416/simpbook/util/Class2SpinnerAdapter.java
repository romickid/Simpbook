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

public class Class2SpinnerAdapter extends BaseAdapter {
    private ArrayList<Class2> listClass2;
    private Context context;

    /**
     * 构造函数, 读取需要绘制的Class2列表
     * @param tcontext 传入的Context
     * @param tlistClass2 传入的Class2列表
     */
    public Class2SpinnerAdapter(Context tcontext, ArrayList<Class2> tlistClass2) {
        super();
        this.listClass2 = tlistClass2;
        this.context = tcontext;
    }

    /**
     * 获取Class2的数量
     * @return 数量
     */
    @Override
    public int getCount() {
        return listClass2.size();
    }

    /**
     * 获取指定位置的Class2的实例
     * @return 实例
     */
    @Override
    public Class2 getItem(int position) {
        return listClass2.get(position);
    }

    /**
     * 获取指定位置的Class2的Id
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return listClass2.get(position).getId();
    }

    /**
     * 获取Class2的绘制形式(view)
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner,null);
        TextView textView = (TextView) view.findViewById(R.id.ispinner_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.ispinner_color);

        Class2 Class2 = listClass2.get(position);
        String text = Class2.getName();
        int color = Class2.getColor();

        textView.setText(text);
        imageView.setImageResource(color);

        return view;
    }
}

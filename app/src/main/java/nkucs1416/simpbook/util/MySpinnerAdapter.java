package nkucs1416.simpbook.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import nkucs1416.simpbook.R;

public class MySpinnerAdapter extends BaseAdapter {
    private ArrayList<Map<String,Object>> listMap;
    private Context context;
    
    /**
     * 构造函数, 读取需要绘制的Spinner变量
     * @param tcontext
     * @param tlistMap
     */
    public MySpinnerAdapter(Context tcontext, ArrayList<Map<String,Object>> tlistMap) {
        super();
        this.listMap = tlistMap;
        this.context = tcontext;
    }

    /**
     * 获取Spinner子项的数量
     * @return 数量
     */
    @Override
    public int getCount() {
        return listMap.size();
    }

    /**
     * 获取Spinner子项的实例
     * @return 实例
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * 获取Spinner子项的Id
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 获取Spinner子项的绘制形式(view)
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner,null);
        TextView textView = (TextView) view.findViewById(R.id.ispinner_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.ispinner_color);

        Map<String,Object> map = listMap.get(position);
        String text = (String) map.get("text");
        int color = (int) map.get("color");
        textView.setText(text);
        imageView.setImageResource(color);

        return view;
    }
}

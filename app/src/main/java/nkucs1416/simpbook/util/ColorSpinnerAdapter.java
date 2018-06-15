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

import static nkucs1416.simpbook.util.Color.*;

public class ColorSpinnerAdapter extends BaseAdapter {
    private ArrayList<Integer> listColor;
    private Context context;

    /**
     * 构造函数, 读取需要绘制的Color列表
     * @param tContext 传入的Context
     * @param tListColors 传入的Color列表
     */
    public ColorSpinnerAdapter(Context tContext, ArrayList<Integer> tListColors) {
        super();
        this.listColor = tListColors;
        this.context = tContext;
    }

    /**
     * 获取Color的数量
     * @return 数量
     */
    @Override
    public int getCount() {
        return listColor.size();
    }

    /**
     * 获取指定位置的Color的实例
     * @return 实例
     */
    @Override
    public Integer getItem(int position) {
        return listColor.get(position);
    }

    /**
     * 获取指定位置的Color的Id
     * @return Id
     */
    @Override
    public long getItemId(int position) {
        return listColor.get(position);
    }

    /**
     * 获取Color的绘制形式(view)
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_spinner,null);
        TextView textViewText = view.findViewById(R.id.ispinner_text);
        ImageView imageViewColor = view.findViewById(R.id.ispinner_color);

        int colorId = listColor.get(position);
        int colorIcon = getColorIcon(colorId);
        String text = getColorName(colorId);

        textViewText.setText(text);
        imageViewColor.setImageResource(colorIcon);

        return view;
    }
}

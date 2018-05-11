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

    public MySpinnerAdapter(Context mContext, ArrayList<Map<String,Object>> mlistMap) {
        super();
        this.listMap = mlistMap;
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return listMap.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

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

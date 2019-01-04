package com.romickid.simpbook.util;

import java.util.ArrayList;

import com.romickid.simpbook.R;

public class Color {
    /**
     * id范围 1-7
     * 分别指代 红橙黄绿青蓝紫
     */
    private static int maxColorIndex = 7;

    /**
     * 获取ColorIcon实例(int类型id)
     *
     * @param colorId colorId
     * @return icon实例
     */
    public static int getColorIcon(int colorId) {
        switch (colorId) {
            case 1:
                return R.drawable.ic_lens_red_a400_36dp;
            case 2:
                return R.drawable.ic_lens_orange_a400_36dp;
            case 3:
                return R.drawable.ic_lens_yellow_a400_36dp;
            case 4:
                return R.drawable.ic_lens_green_a400_36dp;
            case 5:
                return R.drawable.ic_lens_cyan_a400_36dp;
            case 6:
                return R.drawable.ic_lens_blue_a400_36dp;
            case 7:
                return R.drawable.ic_lens_purple_a400_36dp;
        }
        return 0;
    }

    /**
     * 获取Color名称
     *
     * @param colorId colorId
     * @return 名称
     */
    public static String getColorName(int colorId) {
        switch (colorId) {
            case 1:
                return "Red";
            case 2:
                return "Orange";
            case 3:
                return "Yellow";
            case 4:
                return "Green";
            case 5:
                return "Cyan";
            case 6:
                return "Blue";
            case 7:
                return "Purple";
        }
        return null;
    }

    /**
     * 获取ListColorIds, 通过maxColorIndex定义
     *
     * @return listColorIds
     */
    public static ArrayList<Integer> getListColorIds() {
        ArrayList<Integer> listColor = new ArrayList<Integer>();
        for(int i = 1; i <= maxColorIndex; i++) {
            listColor.add(i);
        }

        return listColor;
    }

}

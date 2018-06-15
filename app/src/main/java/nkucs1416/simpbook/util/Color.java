package nkucs1416.simpbook.util;

import java.util.ArrayList;

import nkucs1416.simpbook.R;

public class Color {
    private static int maxColorIndex = 7;

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

    public static ArrayList<Integer> getListColorIds() {
        ArrayList<Integer> listColor = new ArrayList<Integer>();
        for(int i = 1; i <= maxColorIndex; i++) {
            listColor.add(i);
        }

        return listColor;
    }
}

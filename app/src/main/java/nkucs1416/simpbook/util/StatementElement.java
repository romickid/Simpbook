package nkucs1416.simpbook.util;

public class StatementElement {
    private int color;
    private String text;
    private float money;

    public StatementElement(int tColor, String tText, float tMoney) {
        color = tColor;
        text = tText;
        money = tMoney;
    }

    public int getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public float getMoney() {
        return money;
    }
}

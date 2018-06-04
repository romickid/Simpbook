package nkucs1416.simpbook.util;

public class StatementElement {
    private Integer color;
    private String text;
    private Float money;

    public StatementElement(Integer tColor, String tText, Float tMoney) {
        color = tColor;
        text = tText;
        money = tMoney;
    }

    public Integer getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public Float getMoney() {
        return money;
    }
}

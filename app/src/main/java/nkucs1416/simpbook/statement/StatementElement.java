package nkucs1416.simpbook.statement;

public class StatementElement {
    private int color;
    private String text;
    private float money;

    private int id;

    /**
     * 构建StatementElement实例
     *
     * @param tColor 标识颜色
     * @param tText 名称
     * @param tMoney 金额
     * @param tId id
     */
    public StatementElement(int tColor, String tText, float tMoney, int tId) {
        color = tColor;
        text = tText;
        money = tMoney;
        id = tId;
    }

    /**
     * 获取标识颜色
     *
     * @return 标识颜色
     */
    public int getColor() {
        return color;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getText() {
        return text;
    }

    /**
     * 获取金额
     *
     * @return 金额
     */
    public float getMoney() {
        return money;
    }

    /**
     * 获取字符串的金额表示
     *
     * @return 金额
     */
    public String getStrMoney() {
        Float fmoney = money;
        return fmoney.toString();
    }

}

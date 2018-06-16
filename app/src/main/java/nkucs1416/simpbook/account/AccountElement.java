package nkucs1416.simpbook.account;

public class AccountElement {
    private int colorId;
    private String text;
    private float money;
    private int id;


    /**
     * 构建AccountElement实例
     *
     * @param tColor 标识颜色
     * @param tText 名称
     * @param tMoney 金额
     * @param tId id
     */
    AccountElement(int tColor, String tText, float tMoney, int tId) {
        colorId = tColor;
        text = tText;
        money = tMoney;
        id = tId;
    }

    /**
     * 获取标识颜色
     *
     * @return 标识颜色
     */
    public int getColorId() {
        return colorId;
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
     * 获取id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

}

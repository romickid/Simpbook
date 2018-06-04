package nkucs1416.simpbook.account;

public class AccountElement {
    private int color;
    private String text;
    private float money;

    private boolean isAsset;
    private int id;

    /**
     * 构建AccountElement实例
     *
     * @param tColor 标识颜色
     * @param tText 名称
     * @param tMoney 金额
     * @param tIsAsset 是否是资产账户
     * @param tId id
     */
    public AccountElement(int tColor, String tText, float tMoney, boolean tIsAsset, int tId) {
        color = tColor;
        text = tText;
        money = tMoney;
        isAsset = tIsAsset;
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

    /**
     * 判断是否是资产账户
     *
     * @return bool
     */
    public boolean getIsAsset() {
        return isAsset;
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

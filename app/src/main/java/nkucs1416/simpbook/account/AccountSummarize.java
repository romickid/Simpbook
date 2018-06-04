package nkucs1416.simpbook.account;

public class AccountSummarize {
    private String text;
    private Float money;

    /**
     * 构建AccountSummarize实例
     *
     * @param tText 名称
     * @param tMoney 金额
     */
    public AccountSummarize(String tText, float tMoney) {
        text = tText;
        money = tMoney;
    }

    /**
     * 构建空的实例, 将其作为占位使用
     */
    public AccountSummarize() {
        text = "";
        money = null;
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
        if (money != null) {
            Float fmoney = money;
            return fmoney.toString();
        }
        else {
            return "";
        }
    }
}

package com.romickid.simpbook.account;

public class AccountSummarize {
    private String text;
    private Float money;


    /**
     * 构建AccountSummarize实例
     *
     * @param tText  名称
     * @param tMoney 金额
     */
    public AccountSummarize(String tText, float tMoney) {
        text = tText;
        money = tMoney;
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

}

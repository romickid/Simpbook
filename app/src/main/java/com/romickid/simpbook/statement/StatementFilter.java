package com.romickid.simpbook.statement;

import com.romickid.simpbook.util.Account;
import com.romickid.simpbook.util.Class1;
import com.romickid.simpbook.util.Class2;
import com.romickid.simpbook.util.Date;

class StatementFilter {
    private Class1 class1;
    private Class2 class2;
    private Account account;
    private Date dateFrom;
    private Date dateTo;


    /**
     * 构造函数, 传入筛选信息
     *
     * @param tClass1   class1
     * @param tClass2   class2
     * @param tAccount  account
     * @param tDateFrom dateFrom
     * @param tDateTo   dateTo
     */
    StatementFilter(
            Class1 tClass1,
            Class2 tClass2,
            Account tAccount,
            Date tDateFrom,
            Date tDateTo) {
        class1 = tClass1;
        class2 = tClass2;
        account = tAccount;
        dateFrom = tDateFrom;
        dateTo = tDateTo;
    }

    /**
     * 获取Class1
     *
     * @return class1
     */
    public Class1 getClass1() {
        return class1;
    }

    /**
     * 获取Class2
     *
     * @return class2
     */
    public Class2 getClass2() {
        return class2;
    }

    /**
     * 获取Account
     *
     * @return account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * 获取筛选的起始日期
     *
     * @return 起始日期
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * 获取筛选的结束日期
     *
     * @return 结束日期
     */
    public Date getDateTo() {
        return dateTo;
    }

}

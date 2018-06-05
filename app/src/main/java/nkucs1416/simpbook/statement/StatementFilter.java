package nkucs1416.simpbook.statement;

import nkucs1416.simpbook.util.Date;

class StatementFilter {
    private int class1Id;
    private int class2Id;
    private int accountId;
    private Date dateFrom;
    private Date dateTo;

    /**
     * 构造函数, 传入筛选信息
     *
     * @param tClass1Id class1Id
     * @param tClass2Id class2Id
     * @param tAccountId accountId
     * @param tDateFrom dateFrom
     * @param tDateTo dateTo
     */
    StatementFilter(
            int tClass1Id,
            int tClass2Id,
            int tAccountId,
            Date tDateFrom,
            Date tDateTo) {
        class1Id = tClass1Id;
        class2Id = tClass2Id;
        accountId = tAccountId;
        dateFrom = tDateFrom;
        dateTo = tDateTo;
    }

    /**
     * 获取Class1的Id
     *
     * @return Id
     */
    public int getClass1Id() {
        return class1Id;
    }

    /**
     * 获取Class2的Id
     *
     * @return Id
     */
    public int getClass2Id() {
        return class2Id;
    }

    /**
     * 获取Account的Id
     *
     * @return Id
     */
    public int getAccountId() {
        return accountId;
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

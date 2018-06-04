package nkucs1416.simpbook.statement;

import java.util.Map;

import nkucs1416.simpbook.util.Date;

class StatementFilter {
    private int class1Id;
    private int class2Id;
    private int accountId;
    private Date dateFrom;
    private Date dateTo;

    /**
     * 构造函数, 传入筛选信息
     * @param tclass1Id class1Id
     * @param tclass2Id class2Id
     * @param taccountId accountId
     * @param tdateFrom dateFrom
     * @param tdateTo dateTo
     */
    StatementFilter(
            int tclass1Id,
            int tclass2Id,
            int taccountId,
            Date tdateFrom,
            Date tdateTo) {
        class1Id = tclass1Id;
        class2Id = tclass2Id;
        accountId = taccountId;
        dateFrom = tdateFrom;
        dateTo = tdateTo;
    }

    /**
     * 获取Class1的Id
     * @return Id
     */
    public int getClass1Id() {
        return class1Id;
    }

    /**
     * 获取Class2的Id
     * @return Id
     */
    public int getClass2Id() {
        return class2Id;
    }

    /**
     * 获取Account的Id
     * @return Id
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * 获取筛选的起始日期
     * @return 起始日期
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * 获取筛选的结束日期
     * @return 结束日期
     */
    public Date getDateTo() {
        return dateTo;
    }
}

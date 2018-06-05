package nkucs1416.simpbook.util;

public class Collection {
    private int id;
    private int accountId;
    private float money;
    private int type;
    private Date date;
    private String remark;
    private Integer class1Id;
    private Integer class2Id;
    private Integer toAccountId;


    /**
     * 构建Collection的实例
     *
     * @param tId id
     * @param tAccountId 账户id
     * @param tMoney 金额
     * @param tType 类型(1:支出, 2:收入, 3:转账)
     * @param tDate 日期
     * @param tRemark 备注
     * @param tClass1Id 一级分类id
     * @param tClass2Id 二级分类id
     */
    public Collection(
            int tId, int tAccountId, float tMoney, int tType, Date tDate, String tRemark,
            int tClass1Id, int tClass2Id) {
        id = tId;
        accountId = tAccountId;
        money = tMoney;
        type = tType;
        date = tDate;
        remark = tRemark;
        class1Id = tClass1Id;
        class2Id = tClass2Id;
    }

    /**
     * 构建Collection的实例
     *
     * @param tId id
     * @param tAccountId 账户id
     * @param tMoney 金额
     * @param tType 类型(1:支出, 2:收入, 3:转账)
     * @param tDate 日期
     * @param tRemark 备注
     * @param tToAccountId 发送账户id
     */
    public Collection(
            int tId, int tAccountId, float tMoney, int tType, Date tDate, String tRemark,
            int tToAccountId) {
        id = tId;
        accountId = tAccountId;
        money = tMoney;
        type = tType;
        date = tDate;
        remark = tRemark;
        toAccountId = tToAccountId;
    }


    /**
     * 获取Id
     *
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * 获取账户Id
     *
     * @return 账户Id
     */
    public int getAccountId() {
        return accountId;
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
        Float fmoney = getMoney();
        return fmoney.toString();
    }

    /**
     * 获取类型(1:支出, 2:收入, 3:转账)
     *
     * @return 类型
     */
    public int getType() {
        return type;
    }

    /**
     * 获取日期
     *
     * @return 日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 获取一级分类id
     *
     * @return 一级分类id
     */
    public Integer getClass1Id() {
        return class1Id;
    }

    /**
     * 获取二级分类id
     *
     * @return 二级分类id
     */
    public Integer getClass2Id() {
        return class2Id;
    }

    /**
     * 获取发送账户id
     *
     * @return 发送账户id
     */
    public Integer getToAccountId() {
        return toAccountId;
    }

}
package nkucs1416.simpbook.util;

public class Account {
    private int id;
    private String name;
    private float money;
    private int color;


    /**
     * 构建一个Account实例
     *
     * @param tId id
     * @param tName 名称
     * @param tMoney 金额
     * @param tColor 标识颜色
     */
    public Account(int tId, String tName, float tMoney, int tColor) {
        id = tId;
        name = tName;
        money = tMoney;
        color = tColor;
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
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
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
     * 获取标识颜色
     *
     * @return 标识颜色
     */
    public int getColor() {
        return color;
    }

}

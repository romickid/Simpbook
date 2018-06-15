package nkucs1416.simpbook.util;

public class Class2 {
    private int id;
    private String name;
    private int color;
    private int status;
    private int fatherId;


    /**
     * 构造一个Class2实例
     *
     * @param tId Id
     * @param tName 名称
     * @param tColor 标识颜色
     * @param tFatherId 所属类1Id
     * @param tStatus 状态
     */
    public Class2(int tId, String tName, int tColor,int tFatherId,int tStatus) {
        id = tId;
        name = tName;
        color = tColor;
        status = tStatus;
        fatherId = tFatherId;
    }

    /**
     * 构造一个Class2实例
     *
     * @param tId Id
     * @param tName 名称
     * @param tColor 标识颜色
     */
    public Class2(int tId, String tName, int tColor) {
        id = tId;
        name = tName;
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
     * 获取标识颜色
     *
     * @return 标识颜色
     */
    public int getColor() {
        return color;
    }

    /**
     * 获取状态
     *
     * @return 标识状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 获取所属类1Id
     *
     * @return 所属类1Id
     */
    public int getFatherId() {
        return fatherId;
    }

}

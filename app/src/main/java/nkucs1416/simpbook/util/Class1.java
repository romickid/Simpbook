package nkucs1416.simpbook.util;

public class Class1 {
    private int id;
    private String name;
    private int color;
    private int status;


    /**
     * 构建一个Class1实例
     *
     * @param tId id
     */
    public Class1(int tId) {
        id = tId;
    }

    /**
     * 构建一个Class1实例
     *
     * @param tName 名称
     * @param tColor 标识颜色
     */
    public Class1(String tName, int tColor) {
        id = -1;
        name = tName;
        color = tColor;
    }

    /**
     * 构建一个Class1实例
     *
     * @param tId id
     * @param tName 名称
     * @param tColor 标识颜色
     */
    public Class1(int tId, String tName, int tColor) {
        id = tId;
        name = tName;
        color = tColor;
    }

    /**
     * 构建一个Class1实例
     *
     * @param tId id
     * @param tName 名称
     * @param tColor 标识颜色
     * @param tStatus 状态
     */
    public Class1(int tId, String tName, int tColor, int tStatus) {
        id = tId;
        name = tName;
        color = tColor;
        status = tStatus;
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
     * @return 状态
     */
    public int getStatus() {
        return status;
    }

}

package nkucs1416.simpbook.util;

public class Class1 {
    private int id;
    private String name;
    private int color;
    private int type; // 1->支出分类 2->收入分类
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
     * @param tType 类型
     */
    public Class1(String tName, int tColor, int tType) {
        id = -1;
        name = tName;
        color = tColor;
        type = tType;
    }

    /**
     * 构建一个Class1实例
     *
     * @param tId id
     * @param tName 名称
     * @param tColor 标识颜色
     * @param tType 类型
     */
    public Class1(int tId, String tName, int tColor, int tType) {
        id = tId;
        name = tName;
        color = tColor;
        type = tType;
    }

    /**
     * 构建一个Class1实例
     *
     * @param tId id
     * @param tName 名称
     * @param tColor 标识颜色
     * @param tType 类型
     * @param tStatus 状态
     */
    public Class1(int tId, String tName, int tColor, int tStatus, int tType) {
        id = tId;
        name = tName;
        color = tColor;
        type = tType;
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
     * 获取类型
     *
     * @return 类型
     */
    public int getType() {
        return type;
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

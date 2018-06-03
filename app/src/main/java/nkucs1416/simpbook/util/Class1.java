package nkucs1416.simpbook.util;

public class Class1 {
    private String name;
    private int color;
    private int id;

    /**
     * 构造一个Class1实例
     * @param tname 名称
     * @param tcolor 标识颜色
     * @param tid Id
     */
    public Class1(String tname, int tcolor, int tid) {
        name = tname;
        color = tcolor;
        id = tid;
    }

    /**
     * 获取名称
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取标识颜色
     * @return 标识颜色
     */
    public int getColor() {
        return color;
    }

    /**
     * 获取Id
     * @return Id
     */
    public int getId() {
        return id;
    }
}

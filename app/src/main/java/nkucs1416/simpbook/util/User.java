package nkucs1416.simpbook.util;

public class User {
    private int id;
    private String name;
    private String email;

    /**
     * 构建User的一个实例
     *
     * @param tId id
     * @param tName 名称
     * @param tEmail email
     */
    User(int tId, String tName, String tEmail) {
        id = tId;
        name = tName;
        email = tEmail;
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
     * 获取Email
     *
     * @return Email
     */
    public String getEmail() {
        return email;
    }
    
}

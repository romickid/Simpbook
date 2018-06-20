package nkucs1416.simpbook.util;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;


    /**
     * 构建User的一个实例
     *
     * @param tId id
     * @param tName 名称
     * @param tEmail email
     * @param tPassword password
     */
    public User(int tId, String tName, String tEmail, String tPassword) {
        id = tId;
        name = tName;
        email = tEmail;
        password = tPassword;
    }

    /**
     * 构建User的一个实例
     *
     * @param tName 名称
     * @param tEmail email
     * @param tPassword password
     */
    public User(String tName, String tEmail, String tPassword) {
        name = tName;
        email = tEmail;
        password = tPassword;
    }

    /**
     * 构建User的一个实例
     *
     * @param tEmail email
     * @param tPassword password
     */
    public User(String tEmail, String tPassword) {
        email = tEmail;
        password = tPassword;
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

    /**
     * 获取Password
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }
    
}

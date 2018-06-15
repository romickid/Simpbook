package nkucs1416.simpbook.database;

import android.content.Context;

import nkucs1416.simpbook.network.KeyConstance;
import nkucs1416.simpbook.network.Register;
import nkucs1416.simpbook.network.Login;

import nkucs1416.simpbook.util.User;


/**
 * 处理用户数据库类
 */
public class UserDb {

    Context context;

    /**
     * 新建一个实例
     */
    public UserDb(Context context_instance) {
        context = context_instance;
    }

    /**
     * 创建用户
     */
    public void createUser(String name, String password, String email) {
        Register register = new Register(context);
        register.createUser(name, password, email);
    }
    /**
     * 用户登录
     */
    public void loginUser(String email, String password) {
        Login login = new Login(context);
        login.userLogin(email, password);
    }
    /**
     * 获取用户信息
     */
    public User getUserInfo() {
        String userAccount = UserPreference.read(KeyConstance.IS_USER_ACCOUNT, null);
        String userName = UserPreference.read(KeyConstance.IS_USER_NAME, null);
        String userPassword = UserPreference.read(KeyConstance.IS_USER_PASSWORD, null);
        String userid = UserPreference.read(KeyConstance.IS_USER_ID, null);
        int id = Integer.valueOf(userid).intValue();
        User user = new User(id ,userName, userAccount, userPassword);
        return user;
    }

    public String getUserToken() {
        String token = UserPreference.read(KeyConstance.IS_TOKEN, null);
        return token;
    }
}

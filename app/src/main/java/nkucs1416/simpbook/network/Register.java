package nkucs1416.simpbook.network;


import android.content.Context;

import org.json.JSONObject;
import org.json.JSONException;

import nkucs1416.simpbook.database.UserPreference;


/**
 * 处理用户注册接口类
 */
public class Register implements HttpResponeCallBack {

    private Context context;

    private String password;

    /**
     * 构建一个Register实例
     *
     * @param instance 运行环境实例
     */
    public Register(Context instance) {
        context = instance;
    }
    /**
     * 创建用户接口调用
     *
     * @param nickStr 昵称
     * @param passwordStr 密码
     * @param emailStr 邮箱
     */
    public void createUser(String nickStr, String passwordStr, String emailStr) {
        password = passwordStr;
        RequestApiData.getInstance().getRegistData(nickStr, emailStr, passwordStr,
                Register.this, context);
    }

    /**
     * 响应开始回调函数
     *
     * @param apiName 接口api名
     */
    @Override
    public void onResponeStart(String apiName) {
        
    }

    /**
     * 响应成功回调函数
     *
     * @param apiName 接口api名
     * @param object 返回对象
     */
    @Override
    public void onSuccess(String apiName, Object object) {
        // TODO Auto-generated method stub
        //注册接口
        if (UrlConstance.KEY_REGIST_INFO.equals(apiName)) {
            if (object != null && object instanceof JSONObject) {
                JSONObject info = (JSONObject) object;
                try {
                    String ret = info.getInt("ret") + "";
                    //请求成功
                    if (ret.equals(Constant.KEY_SUCCESS)) {
                        UserPreference.save(context, KeyConstance.IS_USER_ID, "" + info.getInt("userId"));
                        UserPreference.save(context, KeyConstance.IS_USER_NAME, info.getString("username"));
                        UserPreference.save(context, KeyConstance.IS_USER_ACCOUNT, info.getString("email"));
                        UserPreference.save(context, KeyConstance.IS_USER_PASSWORD, password);

                    } else {
                        // return baseUser;
                        String errode = info.getString("errode");
                        String msg = info.getString("msg");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 响应失败回调函数
     *
     * @param apiName 接口api名
     * @param t 异常
     * @param errorNo 错误码
     * @param strMsg 错误信息
     */
    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
       
    }
}

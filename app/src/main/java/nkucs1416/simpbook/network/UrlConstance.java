package nkucs1416.simpbook.network;

/**
 * 处理网络的参数常量类
 */

public class UrlConstance {

   //验证身份字段
   public static final String ACCESSTOKEN_KEY ="accesstoken";

   //签约公钥，即客户端与服务器协商订的一个公钥
   public static final String PUBLIC_KEY ="*.simpbook.com";

   //服务器端URL链接
   public static final String APP_URL = "http://139.162.45.117:8081/api/";
   
   //注册用户接口
   public static final String KEY_REGIST_INFO ="createUser";
   
   //登录用户接口
   public static final String KEY_LOGIN_INFO ="userLogin";

   //更新服务器account接口
   public static final String KEY_UPDATE_ACCOUNT_INFO ="updateAccount";

   //更新服务器category接口
   public static final String KEY_UPDATE_CATEGORY_INFO ="updateCategory";

   //更新服务器subcategory接口
   public static final String KEY_UPDATE_SUBCATEGORY_INFO ="updateSubcategory";

   //更新服务器record接口
   public static final String KEY_UPDATE_RECORD_INFO ="updateRecord";

   //更新服务器template接口
   public static final String KEY_UPDATE_TEMPLATE_INFO ="updateTemplate";

   //更新客户端所有数据接口
   public static final String KEY_UPDATE_ALL_INFO ="synchAll";
   
}

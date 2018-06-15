package nkucs1416.simpbook.network;

import nkucs1416.simpbook.database.SubcategoryDb;
import android.content.Context;

import nkucs1416.simpbook.util.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;


/**
 * 处理更新服务器subcategory接口类
 */

public class UpdateSubcategory implements HttpResponeCallBack {

    private Context context;

    private SQLiteDatabase db;


    /**
     * 构建一个UpdateSubcategory实例
     *
     * @param subcategoryList subcategory数据
     * @param token 身份验证
     * @param instance 运行环境实例
     * @param db_instance 数据库实例
     */
    public UpdateSubcategory(ArrayList<Class2> subcategoryList, String token, Context instance, SQLiteDatabase db_instance) {
        context = instance;
        db = db_instance;
        RequestApiData.getInstance().getUpdateSubcategoryData(subcategoryList, token,
                UpdateSubcategory.this, context);
    }

     /**
     * 响应开始回调函数
     *
     * @param apiName 接口api名
     */
    @Override
    public void onResponeStart(String apiName) {
        // TODO Auto-generated method stub

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
        if (UrlConstance.KEY_UPDATE_SUBCATEGORY_INFO.equals(apiName)) {
            if (object != null && object instanceof JSONObject) {
                JSONObject info = (JSONObject) object;
                try {
                    String ret = info.getInt("ret") + "";
                    //请求成功
                    if (ret.equals(Constant.KEY_SUCCESS)) {
                        JSONArray subcategoryIdList = info.getJSONArray("subcategoryIdList");
                        int[] subcategoryId = new int[subcategoryIdList.length()];
                        int[] isDelete = new int[subcategoryIdList.length()];
                        for(int i = 0;i<subcategoryIdList.length();i++) {
                            JSONObject subcategory = subcategoryIdList.getJSONObject(i);
                            subcategoryId[i] = subcategory.getInt("subcategoryId");
                            isDelete[i] = subcategory.getInt("isDelete");
                        }
                        SubcategoryDb subcategoryDb = new SubcategoryDb(db);
                        subcategoryDb.updateSubcategoryStatus(subcategoryId, isDelete);

                    } else {
                        String errode = info.getString("errcode");
                        String msg = info.getString("msg");
                    }
                } catch (JSONException e) {

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
        // Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
        System.out.println(strMsg);
    }
}

package nkucs1416.simpbook.network;

import nkucs1416.simpbook.database.RecordDb;
import android.content.Context;

import nkucs1416.simpbook.util.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;


/**
 * 处理更新服务器record接口类
 */
public class UpdateRecord implements HttpResponeCallBack {

    private Context context;

    private SQLiteDatabase db;


    /**
     * 构建一个UpdateRecord实例
     *
     * @param recordList record数据
     * @param token 身份验证
     * @param instance 运行环境实例
     * @param db_instance 数据库实例
     */
    public UpdateRecord(ArrayList<Record> recordList, String token, Context instance, SQLiteDatabase db_instance) {
        context = instance;
        db = db_instance;
        RequestApiData.getInstance().getUpdateRecordData(recordList, token,
                UpdateRecord.this, context);
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
        if (UrlConstance.KEY_UPDATE_RECORD_INFO.equals(apiName)) {
            if (object != null && object instanceof JSONObject) {
                JSONObject info = (JSONObject) object;
                try {
                    String ret = info.getInt("ret") + "";
                    //请求成功
                    if (ret.equals(Constant.KEY_SUCCESS)) {
                        JSONArray recordIdList = info.getJSONArray("recordIdList");
                        int[] recordId = new int[recordIdList.length()];
                        int[] isDelete = new int[recordIdList.length()];
                        for(int i = 0;i<recordIdList.length();i++) {
                            JSONObject record = recordIdList.getJSONObject(i);
                            recordId[i] = record.getInt("recordId");
                            isDelete[i] = record.getInt("isDelete");
                        }

                        RecordDb recordDb = new RecordDb(db);
                        recordDb.updateRecordStatus(recordId, isDelete);

                    } else {
                        // return baseUser;
                        // Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
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

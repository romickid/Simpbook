package nkucs1416.simpbook.network;

import android.content.Context;

import nkucs1416.simpbook.util.*;
import nkucs1416.simpbook.database.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;


/**
 * 处理更新客户端所有数据接口类
 */
public class SynchAllData implements HttpResponeCallBack {

    private Context context;

    private SQLiteDatabase db;

    private UserListener userListener;


    /**
     * 构建一个SynchAllData实例
     *
     * @param token 身份验证
     * @param instance 运行环境实例
     * @param db_instance 数据库实例
     */
    public SynchAllData(String token, Context instance, SQLiteDatabase db_instance) {
        context = instance;
        db = db_instance;
        userListener = (UserListener)instance;
        RequestApiData.getInstance().getUpdateAllData(token,
                SynchAllData.this, context);
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
        if (UrlConstance.KEY_UPDATE_ALL_INFO.equals(apiName)) {
            if (object != null && object instanceof JSONObject) {
                JSONObject info = (JSONObject) object;
                try {
                    String ret = info.getInt("ret") + "";
                    //请求成功
                    if (ret.equals(Constant.KEY_SUCCESS)) {
                        JSONArray accountList = info.getJSONArray("accountList");
                        JSONArray categoryList = info.getJSONArray("categoryList");
                        JSONArray subcategoryList = info.getJSONArray("subcategoryList");
                        JSONArray recordList = info.getJSONArray("recordList");
                        JSONArray templateList = info.getJSONArray("templateList");
                        ArrayList<Account> accountArrayList = new ArrayList();
                        for(int i = 0;i<accountList.length();i++) {
                            JSONObject account = accountList.getJSONObject(i);
                            int accountId = account.getInt("accountId");
                            String accountName = account.getString("accountName");
                            int accountColor = account.getInt("accountColor");
                            float accountMoney = (float) account.getDouble("accountMoney");
                            int accountType = account.getInt("accountType");
                            int status = account.getInt("status");
                            accountArrayList.add(new Account(accountId, accountName, accountMoney, accountColor, accountType, status));
                        }
                        AccountDb accountDb = new AccountDb(db);
                        accountDb.updateAccountData(accountArrayList);
                        ArrayList<Class1> categoryArrayList = new ArrayList();
                        for(int i = 0;i<categoryList.length();i++) {
                            JSONObject category = categoryList.getJSONObject(i);
                            int categoryId = category.getInt("categoryId");
                            String categoryName = category.getString("categoryName");
                            int categoryColor = category.getInt("categoryColor");
                            int categoryType = category.getInt("categoryType");
                            int status = category.getInt("status");
                            categoryArrayList.add(new Class1(categoryId, categoryName, categoryColor, categoryType, status));
                        }
                        CategoryDb categoryDb = new CategoryDb(db);
                        categoryDb.updateCategoryData(categoryArrayList);
                        ArrayList<Class2> subcategoryArrayList = new ArrayList();
                        for(int i = 0;i<subcategoryList.length();i++) {
                            JSONObject subcategory = subcategoryList.getJSONObject(i);
                            int subcategoryId = subcategory.getInt("subcategoryId");
                            String subcategoryName = subcategory.getString("subcategoryName");
                            int subcategoryColor = subcategory.getInt("subcategoryColor");
                            int fatherId = subcategory.getInt("fatherId");
                            int status = subcategory.getInt("status");
                            subcategoryArrayList.add(new Class2(subcategoryId, subcategoryName, subcategoryColor, fatherId, status));
                        }
                        SubcategoryDb subcategoryDb = new SubcategoryDb(db);
                        subcategoryDb.updateSubcategoryData(subcategoryArrayList);
                        ArrayList<Record> recordArrayList = new ArrayList();
                        for(int i = 0;i<recordList.length();i++) {
                            JSONObject record = recordList.getJSONObject(i);
                            int recordId = record.getInt("recordId");
                            int accountId = record.getInt("accountId");
                            int categoryId = record.getInt("categoryId");
                            int subcategoryId = record.getInt("subcategoryId");
                            int accountToId = record.getInt("accountToId");
                            float recordMoney = (float) record.getDouble("recordMoney");
                            int recordTime = record.getInt("recordTime");
                            Utils util = new Utils();
                            Date time = util.switchTimeToDate(recordTime);
                            String recordRemark = record.getString("recordRemark");
                            int recordType = record.getInt("recordType");
                            int status = record.getInt("status");
                            recordArrayList.add(new Record(recordId, accountId, recordMoney, recordType, time, recordRemark, categoryId, subcategoryId, accountToId, status));
                        }
                        RecordDb recordDb = new RecordDb(db);
                        recordDb.updateRecordtData(recordArrayList);
                        ArrayList<Collection> templateArrayList = new ArrayList();
                        for(int i = 0;i<templateList.length();i++) {
                            JSONObject template = templateList.getJSONObject(i);
                            int templateId = template.getInt("templateId");
                            int accountId = template.getInt("accountId");
                            int categoryId = template.getInt("categoryId");
                            int subcategoryId = template.getInt("subcategoryId");
                            int accountToId = template.getInt("accountToId");
                            float templateMoney = (float) template.getDouble("templateMoney");
                            int templateTime = template.getInt("templateTime");
                            Utils util = new Utils();
                            Date time = util.switchTimeToDate(templateTime);
                            String templateRemark = template.getString("templateRemark");
                            int templateType = template.getInt("templateType");
                            int status = template.getInt("status");
                            templateArrayList.add(new Collection(templateId, accountId, templateMoney, templateType, time, templateRemark, categoryId, subcategoryId, accountToId, status));
                        }
                        TemplateDb templateDb = new TemplateDb(db);
                        templateDb.updateTemplateData(templateArrayList);
                        userListener.OnUserDownloadSuccess();
                    } else {
                        // return baseUser;
                        // Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        String errcode = info.getString("errcode");
                        String msg = info.getString("msg");
                        userListener.OnUserDownloadFail();
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

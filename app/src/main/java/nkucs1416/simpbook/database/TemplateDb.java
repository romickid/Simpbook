package nkucs1416.simpbook.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import nkucs1416.simpbook.network.UpdateTemplate;
import nkucs1416.simpbook.util.Collection;
import nkucs1416.simpbook.util.Date;

import nkucs1416.simpbook.network.Utils;

import java.util.ArrayList;

/**
 * 处理数据库template接口类
 */
public class TemplateDb {

    private SQLiteDatabase db;
    /**
     * 创建一个templateDb实例
     * @param db_instance
     */
    public TemplateDb(SQLiteDatabase db_instance) {
        db = db_instance;
    }
    /**
     * 插入一条template数据
     *
     * @param account_id 账户id
     * @param template_type 种类
     * @param template_money 金额
     * @param template_note 备注
     * @param category_id 一级分类id
     * @param subcategory_id 二级分类id
     * @param account_toId 发送账户id
     * @param template_time 日期
     *
     * @return "成功" or "UNKNOW ERROR"
     */
    private String insertTemplate(int  account_id, int template_type, float template_money,
                                  String template_note, int category_id, int subcategory_id,
                                  int account_toId, Date template_time) {

        ContentValues values = new ContentValues();

        Utils util = new Utils();

        int datetime = util.switchDatetoTime(template_time);

        values.put("template_accoundID", account_id);
        values.put("template_money", template_money);
        values.put("template_type", template_type);
        values.put("template_note", template_note);
        values.put("template_categoryID", category_id);
        values.put("template_subcategoryID", subcategory_id);
        values.put("template_accountToID", account_toId);
        values.put("template_time", datetime);
        values.put("status", 0);
        values.put("anchor", 0);

        long result = db.insert("c_template", null, values);

        if (result > -1)
            return "SUCCESS";
        else return "UNKNOW ERROR";

    }


    /**
     * 插入一条template数据
     *
     * @param collection 模版实例
     *
     * @return "SUCCESS" or "UNKNOW ERROR"
     */
    public String insertTemplate(Collection collection) {
        return insertTemplate(collection.getAccountId(), collection.getType(), collection.getMoney(), collection.getRemark(), collection.getClass1Id(),
                collection.getClass2Id(), collection.getToAccountId(), collection.getDate());
    }

    /**
     * 更新template状态数据
     *
     * @param templateIdList 数据
     * @param isdelete 是否删除
     *
     *
     */
    public void updateTemplateStatus(int[] templateIdList, int[] isdelete) {
        int length = templateIdList.length;
        for(int i = 0;i < length;i++) {
            ContentValues values = new ContentValues();
            if(isdelete[i] == 0) {
                values.put("status", 2);
            } else {
                values.put("status", -2);
            }
            db.update("c_template", values, "template_id = ?", new String[]{templateIdList[i]+""});
        }
    }
    /**
     * 更新从后端同步过来的template数据
     *
     * @param templateArrayList 数据
     *
     */
    public void updateTemplateData(ArrayList<Collection> templateArrayList) {
        deleteAllLocalData();
        int length = templateArrayList.size();
        for(int i = 0;i < length;i++) {
            Collection template = templateArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put("template_id", template.getId());
            values.put("template_type", template.getType());
            Utils util = new Utils();
            values.put("template_time", util.switchDatetoTime(template.getDate()));
            values.put("template_money", template.getMoney());
            values.put("template_note", template.getRemark());
            values.put("template_accountID", template.getAccountId());
            values.put("template_categoryID", template.getClass1Id());
            values.put("template_subcategoryID", template.getClass2Id());
            values.put("template_accountToID", template.getToAccountId());
            values.put("template_accountID", template.getAccountId());
            values.put("status", template.getStatus());
            db.insert("c_template", null, values);
        }
    }

    /**
     * 删除所有template数据
     *
     */
    private void deleteAllLocalData() {
        String DELETE_ALL = "delete from c_template";
        db.execSQL(DELETE_ALL);
    }


    /**
     * 更新一条template数据
     *
     * @param collection 模版实例
     *
     * @return "SUCCESS" or "UNKNOW ERROR"
     */
    public String updateTemplate(Collection collection) {
        return updateTemplate(collection.getId(), collection.getAccountId(), collection.getType(), collection.getMoney(), collection.getRemark(),
        collection.getClass1Id(),collection.getClass2Id(),collection.getToAccountId(), collection.getDate());
    }
    /**
     * 更新一条template数据
     *
     * @param template_id 模版Id
     * @param account_id 账户id
     * @param template_type 种类
     * @param template_money 金额
     * @param template_note 备注
     * @param category_id 一级分类id
     * @param subcategory_id 二级分类id
     * @param account_toId 发送账户id
     * @param template_time 日期
     *
     * @return "成功" or ""
     */
    private String updateTemplate(int template_id, int  account_id, int template_type, float template_money,
                                  String template_note, int category_id, int subcategory_id,
                                  int account_toId, Date template_time) {

        Utils util = new Utils();

        int datetime = util.switchDatetoTime(template_time);

        ContentValues values = new ContentValues();

        values.put("template_accountID", account_id);
        values.put("template_type", template_type);
        values.put("template_money", template_money);
        values.put("template_note", template_note);
        values.put("template_categoryID", category_id);
        values.put("template_subcategoryID", subcategory_id);
        values.put("template_accountToID", account_toId);
        values.put("template_time", datetime);
        values.put("status", 1);
        values.put("anchor", 0);
        int result = db.update("c_template", values, "template_id = ?", new String[]{template_id+""});

        if (result > 0)
            return "成功";
        else return "修改失败";
    }

    /**
     * 删除一条template数据
     *
     * @param collection 模版实例
     *
     * @return "成功" or "删除失败"
     */
    public String deleteTemplate(Collection collection) {
        return deleteTemplate(collection.getId());
    }
    /**
     * 删除一条template数据
     *
     * @param template_id 模版Id
     *
     * @return "成功" or "删除失败"
     */
    public String deleteTemplate(int template_id) {
        ContentValues values = new ContentValues();
        values.put("status", -1);
        values.put("anchor", 0);
        int result = db.update("c_template", values, "template_id = ?", new String[]{template_id+""});

        if (result > 0)
            return "成功";
        else return "删除失败";
    }

    /**
     * 查看是否有关联的template数据
     *
     * @param table_name 表名
     * @param seg_id 字段id
     *
     * @return 有： 1，没有：0
     */
    public boolean isHaveTemplate(String table_name, int seg_id) {
        try {
            if (table_name == "c_account") {
                int account_id = seg_id;
                Cursor cursor = db.query("c_template", new String[]{"template_accountID"},
                        "template_accountID = ? AND status > -1", new String[]{account_id + ""},
                        null, null, null);
                int count = cursor.getCount();
                if (count > 0)
                    return true;
                else return false;
            } else if (table_name == "c_category") {
                int category_id = seg_id;
                Cursor cursor = db.query("c_template", new String[]{"template_categoryID"},
                        "template_categoryID = ? AND status > -1", new String[]{category_id + ""},
                        null, null, null);
                int count = cursor.getCount();
                if (count > 0)
                    return true;
                else return false;
            } else if (table_name == "c_subcategory") {
                int subcategory_id = seg_id;
                Cursor cursor = db.query("c_template", new String[]{"template_subcategoryID"},
                        "template_subcategoryID = ? AND status > -1", new String[]{subcategory_id + ""},
                        null, null, null);
                int count = cursor.getCount();
                if (count > 0)
                    return true;
                else return false;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    /**
     * 根据id返回template数据
     *
     * @param template_id 模版id
     * @return Collection数组
     */
    public ArrayList<Collection> getTemplateListById (int template_id) {
        Cursor cursor = db.query("c_template", null, "template_id = ?",
                new String[]{template_id+""}, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Collection> templateArray = new ArrayList();
        for (int i=0;i<count;i++) {

            int accountIdIndex = cursor.getColumnIndex("template_accountID");
            int  template_accountId = cursor.getInt(accountIdIndex);

            int moneyIndex = cursor.getColumnIndex("template_money");
            float template_money = cursor.getFloat(moneyIndex);

            int typeIndex = cursor.getColumnIndex("template_type");
            int template_type = cursor.getInt(typeIndex);

            int noteIndex = cursor.getColumnIndex("template_note");
            String template_note = cursor.getString(noteIndex);

            int categoryIDIndex = cursor.getColumnIndex("template_categoryID");
            int template_categoryID = cursor.getInt(categoryIDIndex);

            int subcategoryIdIndex = cursor.getColumnIndex("template_subcategoryID");
            int template_subcategoryId = cursor.getInt(subcategoryIdIndex);

            int accountToIdIndex = cursor.getColumnIndex("template_accountToID");
            int template_accountToId = cursor.getInt(accountToIdIndex);

            int timeIndex = cursor.getColumnIndex("template_time");
            int template_time = cursor.getInt(timeIndex);

            Utils util = new Utils();

            Date datetime = util.switchTimetoDate(template_time);


            if (typeIndex == -1) {
                Collection template = new Collection(template_id, template_accountId, template_money, template_type, datetime, template_note, template_accountToId);
                templateArray.add(template);
            }
            else {
                Collection template = new Collection(template_id, template_accountId, template_money, template_type, datetime, template_note, template_categoryID, template_subcategoryId);
                templateArray.add(template);
            }
            cursor.moveToNext();
        }
        return templateArray;
    }

    /**
     * 返回所有的template数据
     *
     *
     * @return Collection数组
     */
    public ArrayList<Collection> templateList () {
        Cursor cursor = db.query("c_template", null, "status > -1",
                null, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Collection> templateArray = new ArrayList();
        for (int i=0;i<count;i++) {

            int idIndex = cursor.getColumnIndex("template_id");
            int  template_id = cursor.getInt(idIndex);

            int accountIdIndex = cursor.getColumnIndex("template_accountID");
            int  template_accountId = cursor.getInt(accountIdIndex);

            int moneyIndex = cursor.getColumnIndex("template_money");
            float template_money = cursor.getFloat(moneyIndex);

            int typeIndex = cursor.getColumnIndex("template_type");
            int template_type = cursor.getInt(typeIndex);

            int noteIndex = cursor.getColumnIndex("template_note");
            String template_note = cursor.getString(noteIndex);

            int categoryIDIndex = cursor.getColumnIndex("template_categoryID");
            int template_categoryID = cursor.getInt(categoryIDIndex);

            int subcategoryIdIndex = cursor.getColumnIndex("template_subcategoryID");
            int template_subcategoryId = cursor.getInt(subcategoryIdIndex);

            int accountToIdIndex = cursor.getColumnIndex("template_accountToID");
            int template_accountToId = cursor.getInt(accountToIdIndex);

            int timeIndex = cursor.getColumnIndex("template_time");
            int template_time = cursor.getInt(timeIndex);

            Utils util = new Utils();

            Date datetime = util.switchTimetoDate(template_time);


            if (typeIndex == -1) {
                Collection template = new Collection(template_id, template_accountId, template_money, template_type, datetime, template_note, template_accountToId);
                templateArray.add(template);
            }
            else {
                Collection template = new Collection(template_id, template_accountId, template_money, template_type, datetime, template_note, template_categoryID, template_subcategoryId);
                templateArray.add(template);
            }
            cursor.moveToNext();
        }
        return templateArray;
    }

    /**
     * 返回所有需要更新的template数据
     *
     *
     * @return Collection 数组
     */
    public ArrayList<Collection> templateListUpdate () {
        Cursor cursor = db.query("c_template", null, "status > -2 AND status < 2",
                null, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Collection> templateArray = new ArrayList();
        for (int i=0;i<count;i++) {

            int idIndex = cursor.getColumnIndex("template_id");
            int  template_id = cursor.getInt(idIndex);

            int accountIdIndex = cursor.getColumnIndex("template_accountID");
            int  template_accountId = cursor.getInt(accountIdIndex);

            int moneyIndex = cursor.getColumnIndex("template_money");
            float template_money = cursor.getFloat(moneyIndex);

            int typeIndex = cursor.getColumnIndex("template_type");
            int template_type = cursor.getInt(typeIndex);

            int noteIndex = cursor.getColumnIndex("template_note");
            String template_note = cursor.getString(noteIndex);

            int categoryIDIndex = cursor.getColumnIndex("template_categoryID");
            int template_categoryID = cursor.getInt(categoryIDIndex);

            int subcategoryIdIndex = cursor.getColumnIndex("template_subcategoryID");
            int template_subcategoryId = cursor.getInt(subcategoryIdIndex);

            int accountToIdIndex = cursor.getColumnIndex("template_accountToID");
            int template_accountToId = cursor.getInt(accountToIdIndex);

            int timeIndex = cursor.getColumnIndex("template_time");
            int template_time = cursor.getInt(timeIndex);

            int statusIndex = cursor.getColumnIndex("status");
            int status = cursor.getInt(statusIndex);

            Utils util = new Utils();

            Date datetime = util.switchTimetoDate(template_time);

            Collection template = new Collection(template_id, template_accountId, template_money, template_type, datetime, template_note, template_categoryID, template_subcategoryId, template_accountToId, status);

            templateArray.add(template);

            cursor.moveToNext();
        }
        return templateArray;
    }

    private void printTemplate(ArrayList<Collection> templateArray) {
        System.out.println("test template print ***************");
        for (int i = 0;i < templateArray.size();i++) {
            Collection template  = templateArray.get(i);
            int id = template.getId();
            int accountId = template.getAccountId();
            float money = template.getMoney();
            Date time = template.getDate();

            Utils util = new Utils();

            int datetime = util.switchDatetoTime(time);
            int type = template.getType();
            String note = template.getRemark();
            if (type == -1) {
                int accountToId = template.getToAccountId();
                System.out.println(id +" "+ accountId +" "+money +" "+type+" "+datetime+" "+accountToId+" "+note);
            }
            else {
                int categoryId = template.getClass1Id();
                int subcategoryId = template.getClass2Id();
                System.out.println(id +" "+ accountId +" "+ categoryId +" "+ subcategoryId+" "+money +" "+type+" "+datetime+" "+note);
            }
        }
    }

    /**
     * 调用同步服务器端template的接口
     *
     * @param context 运行时环境
     *
     */
    public void synchDataBackend(Context context) {
        UserDb userDb = new UserDb(context);
        String token = userDb.getUserToken();
        UpdateTemplate updateTemplate = new UpdateTemplate(templateListUpdate(), token, context, db);
    }
}

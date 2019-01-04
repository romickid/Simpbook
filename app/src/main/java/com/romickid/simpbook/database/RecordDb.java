package com.romickid.simpbook.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.romickid.simpbook.util.Account;
import com.romickid.simpbook.util.Class1;
import com.romickid.simpbook.util.Class2;
import com.romickid.simpbook.util.Date;
import com.romickid.simpbook.util.Record;
import com.romickid.simpbook.util.Utils;

import java.util.ArrayList;

/**
 * 处理数据库record接口类
 */
public class RecordDb {

    private SQLiteDatabase db;

    /**
     * 新建一个recordDb实例
     */
    public RecordDb(SQLiteDatabase db_instance) {
        db = db_instance;
    }

    /**
     * 插入一条record数据
     *
     * @param record
     * @return "成功" or "未知错误"
     */
    public String insertRecord(Record record) {
        return insertRecord(record.getAccountId(), record.getType(), record.getMoney(),
                record.getRemark(), record.getClass1Id(), record.getClass2Id(),
                record.getToAccountId(), record.getDate());
    }

    /**
     * 插入一条record数据
     *
     * @param account_id     账号id
     * @param record_type    种类
     * @param record_money   金额
     * @param record_note    备注
     * @param category_id    一级分类id
     * @param subcategory_id 二级分类id
     * @param account_toId   发送账户id
     * @param record_time    记录时间
     */
    public String insertRecord(int account_id, int record_type, float record_money,
                               String record_note, int category_id, int subcategory_id,
                               int account_toId, Date record_time) {

        Utils util = new Utils();

        int datetime = util.switchDateToTime(record_time);

        ContentValues values = new ContentValues();

        values.put("record_accountID", account_id);
        values.put("record_money", record_money);
        values.put("record_type", record_type);
        values.put("record_note", record_note);
        values.put("record_categoryID", category_id);
        values.put("record_subcategoryID", subcategory_id);
        values.put("record_accountToID", account_toId);
        values.put("record_time", datetime);
        values.put("status", 0);
        values.put("anchor", 0);

        long result = db.insert("c_record", null, values);

        AccountDb accountdb = new AccountDb(db);

        accountdb.updateAccountSum(account_id);

        if (record_type == 3) {
            accountdb.updateAccountSum(account_toId);
        }

        if (result > -1)
            return "成功";
        else return "未知错误";
    }

    /**
     * 更新所有record状态数据
     *
     * @param recordIdList 数据
     * @param isdelete     是否删除
     */

    public void updateRecordStatus(int[] recordIdList, int[] isdelete) {
        int length = recordIdList.length;
        for (int i = 0; i < length; i++) {
            ContentValues values = new ContentValues();
            if (isdelete[i] == 0) {
                values.put("status", 2);
            } else {
                values.put("status", -2);
            }
            db.update("c_record", values, "record_id = ?", new String[]{recordIdList[i] + ""});
        }
    }

    /**
     * 更新后端同步过来的record数据
     *
     * @param recordArrayList 数据
     */
    public void updateRecordtData(ArrayList<Record> recordArrayList) {
        deleteAllLocalData();
        int length = recordArrayList.size();
        for (int i = 0; i < length; i++) {
            Record record = recordArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put("record_id", record.getId());
            values.put("record_type", record.getType());
            Utils util = new Utils();
            values.put("record_time", util.switchDateToTime(record.getDate()));
            values.put("record_money", record.getMoney());
            values.put("record_note", record.getRemark());
            values.put("record_accountID", record.getAccountId());
            values.put("record_categoryID", record.getClass1Id());
            values.put("record_subcategoryID", record.getClass2Id());
            values.put("record_accountToID", record.getToAccountId());
            values.put("record_accountID", record.getAccountId());
            values.put("status", record.getStatus());
            db.insert("c_record", null, values);
        }
    }

    /**
     * 删除所有record数据
     */
    public void deleteAllLocalData() {
        String DELETE_ALL = "delete from c_record";
        db.execSQL(DELETE_ALL);
    }

    /**
     * 更新一条record数据
     *
     * @param record 流水记录实例
     */
    public String updateRecord(Record record) {
        return updateRecord(record.getId(), record.getAccountId(), record.getType(),
                record.getMoney(), record.getRemark(), record.getClass1Id(),
                record.getClass2Id(), record.getToAccountId(), record.getDate());
    }

    private int retrieveRecordAccountId(int record_id) {
        Cursor cursor = db.query("c_record", new String[]{"record_accountID"},
                "record_id = ? ", new String[]{record_id + ""}, null, null, null);
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("record_accountID");
        int id = cursor.getInt(idIndex);
        return id;
    }


    /**
     * 更新一条record数据
     *
     * @param record_id      记录id
     * @param account_id     账号id
     * @param record_type    种类
     * @param record_money   金额
     * @param record_note    备注
     * @param category_id    一级分类id
     * @param subcategory_id 二级分类id
     * @param account_toId   发送账户id
     * @param record_time    记录时间
     */
    private String updateRecord(int record_id, int account_id, int record_type, float record_money,
                                String record_note, int category_id, int subcategory_id,
                                int account_toId, Date record_time) {

        Utils util = new Utils();

        int datetime = util.switchDateToTime(record_time);

        int old_accountId = retrieveRecordAccountId(record_id);

        ContentValues values = new ContentValues();
        values.put("record_accountID", account_id);
        values.put("record_type", record_type);
        values.put("record_money", record_money);
        values.put("record_note", record_note);
        values.put("record_categoryID", category_id);
        values.put("record_subcategoryID", subcategory_id);
        values.put("record_time", datetime);
        values.put("record_accountToID", account_toId);
        values.put("status", 1);
        values.put("anchor", 0);

        int result = db.update("c_record", values, "record_id = ?", new String[]{record_id + ""});

        AccountDb accountdb = new AccountDb(db);

        accountdb.updateAccountSum(account_id);

        if (account_id != old_accountId) {
            accountdb.updateAccountSum(old_accountId);
        }

        if (record_type == 3) {
            accountdb.updateAccountSum(account_toId);
        }

        if (result > 0)
            return "成功";
        else return "未知错误";
    }

    /**
     * 返回accountID
     *
     * @param record_id 记录id
     */
    public int retrieveAccountIdByRecord(int record_id) {
        Cursor cursor = db.query("c_record", new String[]{"record_accountID"},
                "record_id = ? ", new String[]{record_id + ""}, null, null, null);
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("record_accountID");
        int id = cursor.getInt(idIndex);
        return id;
    }

    /**
     * 返回发送账户id
     *
     * @param record_id 记录id
     */

    public int retrieveAccountToIdByRecord(int record_id) {
        Cursor cursor = db.query("c_record", new String[]{"record_accountToID"},
                "record_id = ? ", new String[]{record_id + ""}, null, null, null);
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("record_accountToID");
        int id = cursor.getInt(idIndex);
        return id;
    }

    /**
     * 返回记录种类
     *
     * @param record_id 记录id
     */

    public int retrieveRecordType(int record_id) {
        Cursor cursor = db.query("c_record", new String[]{"record_type"},
                "record_id = ? ", new String[]{record_id + ""}, null, null, null);
        cursor.moveToFirst();
        int typeIndex = cursor.getColumnIndex("record_type");
        int type = cursor.getInt(typeIndex);
        return type;
    }

    /**
     * 删除一条record数据
     *
     * @param record 流水实例
     */

    public String deleteRecord(Record record) {
        return deleteRecord(record.getId());
    }

    /**
     * 删除一条record数据
     *
     * @param record_id 记录id
     */
    public String deleteRecord(int record_id) {
        ContentValues values = new ContentValues();
        values.put("status", -1);
        values.put("anchor", 0);
        int result = db.update("c_record", values, "record_id = ?", new String[]{record_id + ""});

        int account_id = retrieveAccountIdByRecord(record_id);

        AccountDb accountdb = new AccountDb(db);

        accountdb.updateAccountSum(account_id);

        if (retrieveRecordType(record_id) == 3) {
            accountdb.updateAccountSum(retrieveAccountToIdByRecord(record_id));
        }

        if (result > 0)
            return "成功";
        else return "未知错误";
    }

    /**
     * 查找是否有record记录
     *
     * @param table_name 表名
     * @param seg_id     字段id
     */
    public boolean isHaveRecord(String table_name, int seg_id) {
        try {
            if (table_name == "c_account") {
                int account_id = seg_id;
                Cursor cursor = db.query("c_record", new String[]{"record_accountID"},
                        "record_accountID = ? AND record_type != 4 AND status > -1", new String[]{account_id + ""},
                        null, null, null);
                int count = cursor.getCount();
                if (count > 0)
                    return true;
                else return false;
            } else if (table_name == "c_category") {
                int category_id = seg_id;
                Cursor cursor = db.query("c_record", new String[]{"record_categoryID"},
                        "record_categoryID = ? AND status > -1", new String[]{category_id + ""},
                        null, null, null);
                int count = cursor.getCount();
                if (count > 0)
                    return true;
                else return false;
            } else if (table_name == "c_subcategory") {
                int subcategory_id = seg_id;
                Cursor cursor = db.query("c_record", new String[]{"record_subcategoryID"},
                        "record_subcategoryID = ? AND status > -1", new String[]{subcategory_id + ""},
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
     * 将record数据加入数组
     *
     * @param cursor 数据库查询返回游标
     */
    public ArrayList<Record> addRecordList(Cursor cursor) {
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Record> recordArray = new ArrayList();
        for (int i = 0; i < count; i++) {
            int idIndex = cursor.getColumnIndex("record_id");
            int record_id = cursor.getInt(idIndex);

            int accountIdIndex = cursor.getColumnIndex("record_accountID");
            int record_accountId = cursor.getInt(accountIdIndex);

            int moneyIndex = cursor.getColumnIndex("record_money");
            float record_money = cursor.getFloat(moneyIndex);

            int typeIndex = cursor.getColumnIndex("record_type");
            int record_type = cursor.getInt(typeIndex);

            int noteIndex = cursor.getColumnIndex("record_note");
            String record_note = cursor.getString(noteIndex);

            int categoryIDIndex = cursor.getColumnIndex("record_categoryID");
            int record_categoryID = cursor.getInt(categoryIDIndex);

            int subcategoryIdIndex = cursor.getColumnIndex("record_subcategoryID");
            int record_subcategoryId = cursor.getInt(subcategoryIdIndex);

            int accountToIdIndex = cursor.getColumnIndex("record_accountToID");
            int record_accountToId = cursor.getInt(accountToIdIndex);

            int timeIndex = cursor.getColumnIndex("record_time");
            int record_time = cursor.getInt(timeIndex);

            Utils util = new Utils();

            Date datetime = util.switchTimeToDate(record_time);


            Record record;
            if (record_type == 3) {
                record = new Record(record_id, record_accountId, record_money, record_type, datetime, record_note, record_accountToId);
            } else {
                record = new Record(record_id, record_accountId, record_money, record_type, datetime, record_note, record_categoryID, record_subcategoryId);
            }
            recordArray.add(record);

            cursor.moveToNext();
        }
        return recordArray;
    }

    /**
     * 返回所有需要更新的record数据
     */
    public ArrayList<Record> recordListUpdate() {
        Cursor cursor = db.query("c_record", null, "status > -2 AND status < 2",
                null, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Record> recordArray = new ArrayList();
        for (int i = 0; i < count; i++) {
            int idIndex = cursor.getColumnIndex("record_id");
            int record_id = cursor.getInt(idIndex);

            int accountIdIndex = cursor.getColumnIndex("record_accountID");
            int record_accountId = cursor.getInt(accountIdIndex);

            int moneyIndex = cursor.getColumnIndex("record_money");
            float record_money = cursor.getFloat(moneyIndex);

            int typeIndex = cursor.getColumnIndex("record_type");
            int record_type = cursor.getInt(typeIndex);

            int noteIndex = cursor.getColumnIndex("record_note");
            String record_note = cursor.getString(noteIndex);

            int categoryIDIndex = cursor.getColumnIndex("record_categoryID");
            int record_categoryID = cursor.getInt(categoryIDIndex);

            int subcategoryIdIndex = cursor.getColumnIndex("record_subcategoryID");
            int record_subcategoryId = cursor.getInt(subcategoryIdIndex);

            int accountToIdIndex = cursor.getColumnIndex("record_accountToID");
            int record_accountToId = cursor.getInt(accountToIdIndex);

            int timeIndex = cursor.getColumnIndex("record_time");
            int record_time = cursor.getInt(timeIndex);

            int statusIndex = cursor.getColumnIndex("status");
            int status = cursor.getInt(statusIndex);

            Utils util = new Utils();

            Date datetime = util.switchTimeToDate(record_time);

            Record record;

            record = new Record(record_id, record_accountId, record_money, record_type, datetime, record_note, record_categoryID, record_subcategoryId, record_accountToId, status);

            recordArray.add(record);

            cursor.moveToNext();
        }
        return recordArray;
    }

    /**
     * 返回某个流水id的record数据
     */
    public ArrayList<Record> getRecordListById(int record_id) {
        Cursor cursor = db.query("c_record", null, "record_id = ?",
                new String[]{record_id + ""}, null, null, null);
        return addRecordList(cursor);
    }

    /**
     * 返回所有的record数据
     */
    public ArrayList<Record> recordList() {
        Cursor cursor = db.query("c_record", null, "record_type != 4 AND status > -1",
                null, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 返回今天的支出总额
     *
     * @return float 支出
     */
    public float todayExpenseRecordSum() {
        Utils util = new Utils();
        int today = util.switchDateToTime(new Date());
        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 1",
                new String[]{today + "", today + ""}, null, null, "record_time DESC");
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }

    /**
     * 返回今天的收入总额
     *
     * @return float 收入
     */
    public float todayIncomeRecordSum() {
        Utils util = new Utils();
        int today = util.switchDateToTime(new Date());
        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 2",
                new String[]{today + "", today + ""}, null, null, "record_time DESC");
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }

    /**
     * 返回本周的支出总额
     *
     * @return float 支出
     */
    public float weekExpenseRecordSum() {
        Utils util = new Utils();
        int weekBegin = util.getMondayOfThisWeek(new Date());
        int weekEnd = util.getSundayOfThisWeek(new Date());

        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 1",
                new String[]{weekBegin + "", weekEnd + ""}, null, null, "record_time DESC");
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }

    /**
     * 返回本周的收入总额
     *
     * @return float 收入
     */
    public float weekIncomeRecordSum() {
        Utils util = new Utils();
        int weekBegin = util.getMondayOfThisWeek(new Date());
        int weekEnd = util.getSundayOfThisWeek(new Date());

        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 2",
                new String[]{weekBegin + "", weekEnd + ""}, null, null, "record_time DESC");
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }

    /**
     * 返回本月的支出总额
     *
     * @return float 支出
     */
    public float monthExpenseRecordSum() {
        Utils util = new Utils();
        int monthBegin = util.getFirstOfThisMonth(new Date());
        int monthEnd = monthBegin / 100 * 100 + 99;

        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 1",
                new String[]{monthBegin + "", monthEnd + ""}, null, null, "record_time DESC"
        );
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }

    /**
     * 返回本月的收入总额
     *
     * @return float 收入
     */
    public float monthIncomeRecordSum() {
        Utils util = new Utils();
        int monthBegin = util.getFirstOfThisMonth(new Date());
        int monthEnd = monthBegin / 100 * 100 + 99;

        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 2",
                new String[]{monthBegin + "", monthEnd + ""}, null, null, "record_time DESC"
        );
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }

    /**
     * 返回本年的支出总额
     *
     * @return float 支出
     */
    public float yearExpenseRecordSum() {
        Utils util = new Utils();
        int yearBegin = util.getFirstOfThisYear(new Date());
        int yearEnd = yearBegin / 10000 * 10000 + 9999;

        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 1",
                new String[]{yearBegin + "", yearEnd + ""}, null, null, "record_time DESC");
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }

    /**
     * 返回本年的收入总额
     *
     * @return float 支出
     */
    public float yearIncomeRecordSum() {
        Utils util = new Utils();
        int yearBegin = util.getFirstOfThisYear(new Date());
        int yearEnd = yearBegin / 10000 * 10000 + 9999;

        Cursor cursor = db.query(
                "c_record", null, "status > -1 AND record_time >= ? AND record_time <= ? AND record_type = 2",
                new String[]{yearBegin + "", yearEnd + ""}, null, null, "record_time DESC");
        ArrayList<Record> recordList = addRecordList(cursor);
        float sum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            sum += recordList.get(i).getMoney();
        }
        return sum;
    }


    /**
     * 返回某个account的record数据
     *
     * @param account_id 账号id
     */
    public ArrayList<Record> recordList(int account_id) {
        Cursor cursor = db.query("c_record", null, "(record_accountID = ? OR record_accountToID = ?) AND record_type != 4 AND status > -1",
                new String[]{account_id + "", account_id + ""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 返回时间间隔的record数据
     *
     * @param begintime 开始时间
     * @param endtime   结束时间
     */
    public ArrayList<Record> recordList(Date begintime, Date endtime) {

        Utils util = new Utils();

        int time1 = util.switchDateToTime(begintime);
        int time2 = util.switchDateToTime(endtime);
        Cursor cursor = db.query("c_record", null, "record_time >= ? AND record_time <= ? AND record_type != 4 AND status > -1",
                new String[]{time1 + "", time2 + ""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 根据class1、class2、account、开始时间、结束时间返回record数据
     *
     * @param class1    一级分类
     * @param class2    二级分类
     * @param account   账户
     * @param begindate 开始日期
     * @param enddate   结束日期
     */
    public ArrayList<Record> recordList(Class1 class1, Class2 class2, Account account, Date begindate, Date enddate) {
        int begindateInt = new Utils().switchDateToTime(begindate);
        int enddateInt = new Utils().switchDateToTime(enddate);
        String selection = "record_type != 4 AND status > -1 AND record_time >= ? AND record_time <= ?";
        ArrayList<String> selectionArgu = new ArrayList();
        selectionArgu.add(begindateInt + "");
        selectionArgu.add(enddateInt + "");
        if (class1.getId() != -1) {
            selection += " AND record_categoryID = ?";
            selectionArgu.add(class1.getId() + "");

        }
        if (class2.getId() != -1) {
            selection += " AND record_subcategoryID = ?";
            selectionArgu.add(class2.getId() + "");
        }
        if (account.getId() != -1) {
            selection += " AND record_accountID = ?";
            selectionArgu.add(account.getId() + "");
        }
        String[] selectionArguString = new String[selectionArgu.size()];
        for (int i = 0; i < selectionArgu.size(); i++) {
            selectionArguString[i] = selectionArgu.get(i);
        }
        Cursor cursor = db.query("c_record", null, selection, selectionArguString, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 返回某个种类的的record数据
     *
     * @param record_type 种类
     */
    public ArrayList<Record> recordListByType(int record_type) {
        Cursor cursor = db.query("c_record", null, "record_type = ? AND status > -1",
                new String[]{record_type + ""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 返回某个分类的record数据
     *
     * @param category_id 分类id
     */
    public ArrayList<Record> recordListByCate(int category_id) {
        Cursor cursor = db.query("c_record", null, "record_categoryID = ? AND record_type != 4 AND status > -1",
                new String[]{category_id + ""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 返回某个二级分类的record数据
     *
     * @param subcategory_id 二级分类id
     */

    public ArrayList<Record> recordListBySubCate(int subcategory_id) {
        Cursor cursor = db.query("c_record", null, "record_subcategoryID = ? AND record_type != 4 AND status > -1",
                new String[]{subcategory_id + ""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 打印record数据
     *
     * @param recordArray record数组
     */
    public void printRecord(ArrayList<Record> recordArray) {
        System.out.println("test record print ***************");
        for (int i = 0; i < recordArray.size(); i++) {
            Record record = recordArray.get(i);
            int id = record.getId();
            int accountId = record.getAccountId();
            float money = record.getMoney();
            Date time = record.getDate();

            Utils util = new Utils();

            int datetime = util.switchDateToTime(time);
            int type = record.getType();
            String note = record.getRemark();
            if (type == -1) {
                int accountToId = record.getToAccountId();
                System.out.println(id + " " + accountId + " " + money + " " + type + " " + datetime + " " + accountToId + " " + note);
            } else {
                int categoryId = record.getClass1Id();
                int subcategoryId = record.getClass2Id();
                System.out.println(id + " " + accountId + " " + categoryId + " " + subcategoryId + " " + money + " " + type + " " + datetime + " " + note);
            }
        }
    }
}

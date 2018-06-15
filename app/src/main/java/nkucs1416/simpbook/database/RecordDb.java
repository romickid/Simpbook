package nkucs1416.simpbook.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import nkucs1416.simpbook.network.UpdateRecord;
import nkucs1416.simpbook.util.Date;
import nkucs1416.simpbook.util.StatementRecord;

import nkucs1416.simpbook.network.Utils;

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
     * @param account_id 账号id
     * @param record_type 种类
     * @param record_money 金额
     * @param record_note 备注
     * @param category_id 一级分类id
     * @param subcategory_id 二级分类id
     * @param account_toId 发送账户id
     * @param record_time 记录时间
     */
    public String insertRecord(int  account_id, int record_type, float record_money,
                                String record_note, int category_id, int subcategory_id,
                                int account_toId, Date record_time) {

        Utils util = new Utils();

        int datetime = util.switchDatetoTime(record_time);

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

        if(record_type == -1) {
            accountdb.updateAccountSum(account_toId);
        }

        if (result > -1)
            return "SUCCESS";
        else return "UNKNOW ERROR";
    }

    /**
     * 插入一条record数据
     *
     * @param account_id 账号id
     * @param record_type 种类
     * @param record_money 金额
     * @param record_note 备注
     * @param category_id 一级分类id
     * @param subcategory_id 二级分类id
     * @param record_time 记录时间
     */
    public String insertRecord(int  account_id, int record_type, float record_money,
                                String record_note, int category_id, int subcategory_id,
                                Date record_time) {
        String result = insertRecord(account_id, record_type, record_money, record_note, category_id, subcategory_id, 0, record_time);
        return result;
    }

    /**
     * 插入一条record数据
     *
     * @param account_id 账号id
     * @param record_type 种类
     * @param record_money 金额
     * @param record_note 备注
     * @param account_toId 发送账户id
     * @param record_time 记录时间
     */

    public String insertRecord(int  account_id, int record_type, float record_money,
                                String record_note, int account_toId,
                                Date record_time) {
        String result = insertRecord(account_id, record_type, record_money, record_note, 0, 0, account_toId, record_time);
        return result;
    }

    /**
     * 更新所有record状态数据
     *
     * @param recordIdList 数据
     * @param isdelete 是否删除
     */

    public void updateRecordStatus(int[] recordIdList, int[] isdelete) {
        int length = recordIdList.length;
        for(int i = 0;i < length;i++) {
            ContentValues values = new ContentValues();
            if(isdelete[i] == 0) {
                values.put("status", 2);
            } else {
                values.put("status", -2);
            }
            db.update("c_record", values, "record_id = ?", new String[]{recordIdList[i]+""});
        }
    }

    /**
     * 更新后端同步过来的record数据
     *
     * @param recordArrayList 数据
     */
    public void updateRecordtData(ArrayList<StatementRecord> recordArrayList) {
        deleteAllLocalData();
        int length = recordArrayList.size();
        for(int i = 0;i < length;i++) {
            StatementRecord record = recordArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put("record_id", record.getId());
            values.put("record_type", record.getType());
            Utils util = new Utils();
            values.put("record_time", util.switchDatetoTime(record.getDate()));
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
     *
     */
    private void deleteAllLocalData() {
        String DELETE_ALL = "delete from c_record";
        db.execSQL(DELETE_ALL);
    }

    /**
     * 更新一条record数据
     *
     * @param record_id 记录id
     * @param account_id 账号id
     * @param record_type 种类
     * @param record_money 金额
     * @param record_note 备注
     * @param category_id 一级分类id
     * @param subcategory_id 二级分类id
     * @param account_toId 发送账户id
     * @param record_time 记录时间
     */
    public String updateRecord(int record_id, int account_id, int record_type, float record_money,
                                String record_note, int category_id, int subcategory_id,
                                int account_toId, Date record_time) {

        Utils util = new Utils();

        int datetime = util.switchDatetoTime(record_time);

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

        int result = db.update("c_record", values, "record_id = ?", new String[]{record_id+""});

        AccountDb accountdb = new AccountDb(db);

        accountdb.updateAccountSum(account_id);

        if (record_type == -1) {
            accountdb.updateAccountSum(account_toId);
        }

        if (result > 0)
            return "SUCCESS";
        else return "UNKNOW ERROR";
    }

    /**
     * 插入一条record数据
     *
     * @param record_id 记录id
     * @param account_id 账号id
     * @param record_type 种类
     * @param record_money 金额
     * @param record_note 备注
     * @param category_id 一级分类id
     * @param subcategory_id 二级分类id
     * @param record_time 记录时间
     */
    public String updateRecord(int record_id, int account_id, int record_type, float record_money,
                                String record_note, int category_id, int subcategory_id, Date record_time) {
        String result = updateRecord(record_id, account_id, record_type, record_money, record_note, category_id, subcategory_id, 0, record_time);
        return result;

    }

    /**
     * 插入一条record数据
     *
     * @param record_id 记录id
     * @param account_id 账号id
     * @param record_type 种类
     * @param record_money 金额
     * @param record_note 备注
     * @param account_toId 发送账户id
     * @param record_time 记录时间
     */
    public String updateRecord(int record_id, int account_id, int record_type, float record_money,
                                String record_note, int account_toId, Date record_time) {
        String result = updateRecord(record_id, account_id, record_type, record_money, record_note, 0, 0, account_toId, record_time);
        return result;

    }

    /**
     * 返回accountID
     *
     * @param record_id 记录id
     */
    public int retrieveAccountIdByRecord(int record_id) {
        Cursor cursor = db.query("c_record", new String[]{"record_accountID"},
                "record_id = ? ", new String[]{record_id+""}, null, null, null);
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
                "record_id = ? ", new String[]{record_id+""}, null, null, null);
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
                "record_id = ? ", new String[]{record_id+""}, null, null, null);
        cursor.moveToFirst();
        int typeIndex = cursor.getColumnIndex("record_type");
        int type = cursor.getInt(typeIndex);
        return type;
    }

    /**
     * 删除一条record数据
     *
     * @param record_id 记录id
     *
     */
    public String  deleteRecord(int record_id) {
        ContentValues values = new ContentValues();
        values.put("status", -1);
        values.put("anchor", 0);
        int result = db.update("c_record", values, "record_id = ?", new String[]{record_id+""});

        int account_id = retrieveAccountIdByRecord(record_id);

        AccountDb accountdb = new AccountDb(db);

        accountdb.updateAccountSum(account_id);

        if(retrieveRecordType(record_id) == -1) {
            accountdb.updateAccountSum(retrieveAccountToIdByRecord(record_id));
        }

        if (result > 0)
            return "SUCCESS";
        else return "UNKNOW ERROR";
    }

    /**
     * 查找是否有record记录
     *
     * @param table_name 表名
     * @param seg_id 字段id
     */
    public boolean isHaveRecord(String table_name, int seg_id) {
        try {
            if (table_name == "c_account") {
                int account_id = seg_id;
                Cursor cursor = db.query("c_record", new String[]{"record_accountID"},
                        "record_accountID = ? AND record_type != 4 AND status != -1", new String[]{account_id + ""},
                        null, null, null);
                int count = cursor.getCount();
                if (count > 0)
                    return true;
                else return false;
            } else if (table_name == "c_category") {
                int category_id = seg_id;
                Cursor cursor = db.query("c_record", new String[]{"record_categoryID"},
                        "record_categoryID = ? AND status != -1", new String[]{category_id + ""},
                        null, null, null);
                int count = cursor.getCount();
                if (count > 0)
                    return true;
                else return false;
            } else if (table_name == "c_subcategory") {
                int subcategory_id = seg_id;
                Cursor cursor = db.query("c_record", new String[]{"record_subcategoryID"},
                        "record_subcategoryID = ? AND status != -1", new String[]{subcategory_id + ""},
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
    public ArrayList<StatementRecord> addRecordList (Cursor cursor) {
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<StatementRecord> recordArray = new ArrayList();
        for (int i=0;i<count;i++) {
            int idIndex = cursor.getColumnIndex("record_id");
            int  record_id = cursor.getInt(idIndex);

            int accountIdIndex = cursor.getColumnIndex("record_accountID");
            int  record_accountId = cursor.getInt(accountIdIndex);

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

            Date datetime = util.switchTimetoDate(record_time);

            StatementRecord record;
            if (record_type == -1) {
                record = new StatementRecord(record_id, record_accountId, record_money, record_type, datetime, record_note, record_accountToId);
            }
            else {
                record = new StatementRecord(record_id, record_accountId, record_money, record_type, datetime, record_note, record_categoryID, record_subcategoryId);
            }
            recordArray.add(record);

            cursor.moveToNext();
        }
        return  recordArray;
    }

    /**
     * 返回所有需要更新的record数据
     *
     */
    public ArrayList<StatementRecord> recordListUpdate () {
        Cursor cursor = db.query("c_record", null, "status > -2 AND status < 2",
                null, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<StatementRecord> recordArray = new ArrayList();
        for (int i=0;i<count;i++) {
            int idIndex = cursor.getColumnIndex("record_id");
            int  record_id = cursor.getInt(idIndex);

            int accountIdIndex = cursor.getColumnIndex("record_accountID");
            int  record_accountId = cursor.getInt(accountIdIndex);

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

            Date datetime = util.switchTimetoDate(record_time);

            StatementRecord record;

            record = new StatementRecord(record_id, record_accountId, record_money, record_type, datetime, record_note, record_categoryID, record_subcategoryId, record_accountToId, status);

            recordArray.add(record);

            cursor.moveToNext();
        }
        return  recordArray;
    }
    /**
     * 返回所有的record数据
     *
     */
    public ArrayList<StatementRecord> recordList () {
        Cursor cursor = db.query("c_record", null, "record_type != 4 AND status != -1",
                null, null, null, "record_time DESC");
        return addRecordList(cursor);
    }
    /**
     * 返回某个account的record数据
     *
     * @param account_id 账号id
     */
    public ArrayList<StatementRecord> recordList (int account_id) {
        Cursor cursor = db.query("c_record", null, "(record_accountID = ? OR record_accountToID = ?) AND record_type != 4 AND status != -1",
                new String[]{account_id+"", account_id+""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 返回时间间隔的record数据
     *
     * @param begintime 开始时间
     * @param endtime 结束时间
     */
    public ArrayList<StatementRecord> recordList (Date begintime, Date endtime) {

        Utils util = new Utils();

        int time1 = util.switchDatetoTime(begintime);
        int time2 = util.switchDatetoTime(endtime);
        Cursor cursor = db.query("c_record", null, "record_time >= ? AND record_time <= ? AND record_type != 4 AND status != -1",
                new String[]{time1+"", time2+""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }
    /**
     * 返回某个种类的的record数据
     *
     * @param record_type 种类
     */
    public ArrayList<StatementRecord> recordListByType (int record_type) {
        Cursor cursor = db.query("c_record", null, "record_type = ? AND status != -1",
                new String[]{record_type+""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }
    /**
     * 返回某个分类的record数据
     *
     * @param category_id 分类id
     */
    public ArrayList<StatementRecord> recordListByCate (int category_id) {
        Cursor cursor = db.query("c_record", null, "record_categoryID = ? AND record_type != 4 AND status != -1",
                new String[]{category_id+""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }

    /**
     * 返回某个二级分类的record数据
     *
     * @param subcategory_id 二级分类id
     *
     */

    public ArrayList<StatementRecord> recordListBySubCate (int subcategory_id) {
        Cursor cursor = db.query("c_record", null, "record_subcategoryID = ? AND record_type != 4 AND status != -1",
                new String[]{subcategory_id+""}, null, null, "record_time DESC");
        return addRecordList(cursor);
    }
    /**
     * 打印record数据
     *
     * @param recordArray record数组
     */
    public void printRecord(ArrayList<StatementRecord> recordArray) {
        System.out.println("test record print ***************");
        for (int i = 0;i < recordArray.size();i++) {
            StatementRecord record  = recordArray.get(i);
            int id = record.getId();
            int accountId = record.getAccountId();
            float money = record.getMoney();
            Date time = record.getDate();

            Utils util = new Utils();

            int datetime = util.switchDatetoTime(time);
            int type = record.getType();
            String note = record.getRemark();
            if (type == -1) {
                int accountToId = record.getToAccountId();
                System.out.println(id +" "+ accountId +" "+money +" "+type+" "+datetime+" "+accountToId+" "+note);
            }
            else {
                int categoryId = record.getClass1Id();
                int subcategoryId = record.getClass2Id();
                System.out.println(id +" "+ accountId +" "+ categoryId +" "+ subcategoryId+" "+money +" "+type+" "+datetime+" "+note);
            }
        }
    }

    /**
     * 调用更新后端record数据接口
     *
     * @param context 运行时环境
     */
    public void synchDataBackend(Context context) {
        UserDb userDb = new UserDb(context);
        String token = userDb.getUserToken();
        UpdateRecord updateRecord = new UpdateRecord(recordListUpdate(), token, context, db);
    }
}

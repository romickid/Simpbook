package com.romickid.simpbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.romickid.simpbook.util.Account;
import com.romickid.simpbook.util.Date;

import java.util.ArrayList;

/**
 * 处理数据库account接口类
 */
public class AccountDb {

    private SQLiteDatabase db;

    /**
     * 构建一个AccountDb实例
     *
     * @param db_instance 数据库实例
     */
    public AccountDb(SQLiteDatabase db_instance) {
        db = db_instance;
    }

    /**
     * 插入一条account数据
     *
     * @param account 账户名
     * @return "成功" or "账户名重复" or "未知错误"
     */
    public String insertAccount(Account account) {
        return insertAccount(account.getName(),account.getColor(),  account.getMoney(), account.getType());
    }

    /**
     * 插入一条account数据
     *
     * @param account_name 账户名
     * @param account_color 账户颜色
     * @param account_sum 账户金额
     */
    private String insertAccount(String account_name, int account_color, float account_sum, int account_type) {
        try {
            Cursor cursor = db.query("c_account", new String[]{"account_name"}, "account_name = ? AND status > -1",
                    new String[]{account_name}, null, null, null);
            int count = cursor.getCount();
            if (count > 0)
                return "账户名重复";

            ContentValues values = new ContentValues();
            values.put("account_name", account_name);
            values.put("account_color", account_color);
            values.put("account_type", account_type);
            values.put("status", 0);
            values.put("anchor", 0);
            long result = db.insert("c_account", null, values);

            Date now = new Date();

            DbUtils dbutil = new DbUtils(db);

            if(account_sum != 0) {
                int accountId = dbutil.retrieveID("c_account", account_name);

                RecordDb recorddb = new RecordDb(db);

                recorddb.insertRecord(accountId, 4, account_sum, "", 0, 0, 0, now);
                updateAccountSum(accountId);
            }
            if (result > -1)
                return "成功";
            else return "未知错误";
        } catch (SQLException e) {
            return "未知错误";
        }
    }

    public String updateAccount(Account account) {
        return updateAccount(account.getId(), account.getName(), account.getColor(), account.getMoney(), account.getType());
    }

    /**
     * 更新一条account数据
     *
     * @param account_id 账户id
     * @param account_name 账户名
     * @param account_color 账户颜色
     * @param account_sum 账户金额
     */
    private String updateAccount(int account_id, String account_name, int account_color, float account_sum, int account_type) {
        try {
            Cursor cursor = db.query("c_account", new String[]{"account_name"},
                    "account_name = ? AND status > -1 AND account_id != ?",
                    new String[]{account_name, account_id+""}, null, null, null);
            int count = cursor.getCount();
            if (count > 0)
                return "账户名重复";

            ContentValues values = new ContentValues();
            values.put("account_name", account_name);
            values.put("account_color", account_color);
            values.put("account_type", account_type);
            values.put("status", 1);
            values.put("anchor", 0);
            int result = db.update("c_account", values, "account_id = ?", new String[]{account_id + ""});

            Date now = new Date();

            float old_account_sum = retrieveAccountSum(account_id);

            RecordDb recorddb = new RecordDb(db);

            if(old_account_sum != account_sum) {
                recorddb.insertRecord(account_id, 4, account_sum-old_account_sum, "", 0, 0, 0, now);
                updateAccountSum(account_id);
            }


            if (result > 0)
                return "成功";
            else return "未知错误";
        } catch (SQLException e) {
            return "未知错误";
        }

    }

    /**
     * 更新一条account的金额数据
     *
     * @param account_id 账户id
     *
     */
    public void updateAccountSum(int account_id) {
        Cursor cursor = db.query("c_record", new String[]{"record_type", "SUM(record_money) AS money"}, "record_accountID = ? AND status > -1",
                new String[] {account_id+""}, "record_type", null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        float account_sum = 0;
        for (int i=0;i<count;i++) {
            int typeIndex = cursor.getColumnIndex("record_type");
            int type = cursor.getInt(typeIndex);
            int moneyIndex = cursor.getColumnIndex("money");
            float account_money = cursor.getFloat(moneyIndex);
            if (type == 2) {
                account_sum += account_money;
            } else if (type == 1) {
                account_sum -= account_money;
            } else if (type == 3) {
                account_sum -= account_money;
            } else if (type == 4) {
                account_sum += account_money;
            }
            cursor.moveToNext();
        }

        cursor = db.query("c_record", new String[]{"record_type", "SUM(record_money) AS money"},
                "record_accountToID = ? AND record_type = 3 AND status > -1",
                new String[] {account_id+""}, null, null, null);
        cursor.moveToFirst();
        int moneyIndex = cursor.getColumnIndex("money");
        float account_money = cursor.getFloat(moneyIndex);
        account_sum += account_money;

        ContentValues values = new ContentValues();
        values.put("account_sum", account_sum);
        //参数依次是表名，修改后的值，where条件，以及约束，如果不指定三四两个参数，会更改所有行
        db.update("c_account", values, "account_id = ?", new String[]{account_id+""});
    }

    /**
     * 更新所有account Status数据
     *
     * @param accountIdList 所选账户id
     * @param isdelete 是否删除
     */

    public void updateAccountStatus(int[] accountIdList, int[] isdelete) {
        int length = accountIdList.length;
        for(int i = 0;i < length;i++) {
            ContentValues values = new ContentValues();
            if(isdelete[i] == 0) {
                values.put("status", 2);
            } else {
                values.put("status", -2);
            }
            db.update("c_account", values, "account_id = ?", new String[]{accountIdList[i]+""});
        }
    }


    /**
     * 更新所有account数据
     *
     * @param accountArrayList 账户数据
     */
    public void updateAccountData(ArrayList<Account> accountArrayList) {
        deleteAllLocalData();
        int length = accountArrayList.size();
        for(int i = 0;i < length;i++) {
            Account account = accountArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put("account_id", account.getId());
            values.put("account_name", account.getName());
            values.put("account_color", account.getColor());
            values.put("account_sum", account.getMoney());
            values.put("account_type", account.getType());
            values.put("status", account.getStatus());
            db.insert("c_account", null, values);
        }
    }

    /**
     * 删除所有account数据
     *
     */
    public void deleteAllLocalData() {
        String DELETE_ALL = "delete from c_account";
        db.execSQL(DELETE_ALL);
    }

    /**
     * 取回一条account的金额数据
     *
     * @param account_id 账户id
     */
    public float retrieveAccountSum(int account_id) {
        Cursor cursor = db.query("c_account", new String[]{"account_id" , "account_sum"}, "account_id = ?",
                new String[]{account_id+""}, null, null, null);
        cursor.moveToFirst();
        int sumIndex = cursor.getColumnIndex("account_sum");
        float sum = cursor.getFloat(sumIndex);
        return sum;
    }
    /**
     * 删除一条account数据
     *
     * @param account 账户
     */
    public String deleteAccount(Account account) {
        return deleteAccount(account.getId());
    }


    /**
     * 删除一条account数据
     *
     * @param account_id 账户id
     */
    private String deleteAccount(int account_id) {
        try {

            RecordDb recordDb = new RecordDb(db);

            TemplateDb templateDb = new TemplateDb(db);

            boolean access1 = recordDb.isHaveRecord("c_account", account_id);
            if (access1)
                return "该账户下有流水记录";
            boolean access2 = templateDb.isHaveTemplate("c_account", account_id);
            if (access2)
                return "该账户下有模版记录";

            ContentValues values = new ContentValues();
            values.put("status", -1);
            values.put("anchor", 0);
            int result = db.update("c_account", values, "account_id = ?", new String[]{account_id + ""});

            if (result > 0)
                return "成功";
            else return "未知错误";
        } catch (SQLException e) {
            return "未知错误";
        }
    }

    /**
     * 根据id返回account数据
     * @param account_id 账户Id
     */
    public ArrayList<Account> getAccountListById (int account_id) {
        Cursor cursor = db.query("c_account", null,
                "account_id = ?", new String[]{account_id+""},
                null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Account> accountArray = new ArrayList();
        for (int i=0;i<count;i++){
            int nameIndex = cursor.getColumnIndex("account_name");
            String  account_name = cursor.getString(nameIndex);
            int sumIndex = cursor.getColumnIndex("account_sum");
            float account_sum = cursor.getFloat(sumIndex);
            int colorIndex = cursor.getColumnIndex("account_color");
            int account_color = cursor.getInt(colorIndex);
            int typeIndex = cursor.getColumnIndex("account_type");
            int account_type = cursor.getInt(typeIndex);
            Account account = new Account(account_id, account_name, account_sum, account_color, account_type);
            accountArray.add(account);
            cursor.moveToNext();
        }
        return accountArray;
    }

    /**
     * 返回所有account数据
     *
     */
    public ArrayList<Account> accountList () {
        Cursor cursor = db.query("c_account", null,
                "status > -1", null,
                null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Account> accountArray = new ArrayList();
        for (int i=0;i<count;i++){
            int nameIndex = cursor.getColumnIndex("account_name");
            String  account_name = cursor.getString(nameIndex);
            int sumIndex = cursor.getColumnIndex("account_sum");
            float account_sum = cursor.getFloat(sumIndex);
            int idIndex = cursor.getColumnIndex("account_id");
            int account_id = cursor.getInt(idIndex);
            int colorIndex = cursor.getColumnIndex("account_color");
            int account_color = cursor.getInt(colorIndex);
            int typeIndex = cursor.getColumnIndex("account_type");
            int account_type = cursor.getInt(typeIndex);
            Account account = new Account(account_id, account_name, account_sum, account_color, account_type);
            accountArray.add(account);
            cursor.moveToNext();
        }
        return accountArray;
    }

    /**
     * 返回所有需要更新account的数据
     *
     */
    public ArrayList<Account> accountListUpdate() {
        Cursor cursor = db.query("c_account", null,
                "status < 2 AND status > -2", null,
                null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Account> accountArray = new ArrayList();
        for (int i=0;i<count;i++){
            int nameIndex = cursor.getColumnIndex("account_name");
            String  account_name = cursor.getString(nameIndex);
            int sumIndex = cursor.getColumnIndex("account_sum");
            float account_sum = cursor.getFloat(sumIndex);
            int idIndex = cursor.getColumnIndex("account_id");
            int account_id = cursor.getInt(idIndex);
            int colorIndex = cursor.getColumnIndex("account_color");
            int account_color = cursor.getInt(colorIndex);
            int typeIndex = cursor.getColumnIndex("account_type");
            int account_type = cursor.getInt(typeIndex);
            int statusIndex = cursor.getColumnIndex("status");
            int status = cursor.getInt(statusIndex);
            Account account = new Account(account_id, account_name, account_sum, account_color, account_type, status);
            accountArray.add(account);
            cursor.moveToNext();
        }
        return accountArray;
    }
    /**
     * 更新打印account数据
     *
     * @param accountArray 账户id
     */
    public void printAccount(ArrayList<Account> accountArray) {
        System.out.println("test account print ***************");
        for (int i = 0;i < accountArray.size();i++) {
            Account account = accountArray.get(i);
            int id = account.getId();
            String name = account.getName();
            float money = account.getMoney();
            int color = account.getColor();
            System.out.println(id +" "+ name +" "+ money +" "+ color);
        }
    }
}

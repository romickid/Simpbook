package nkucs1416.simpbook.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import nkucs1416.simpbook.network.UpdateSubcategory;
import nkucs1416.simpbook.util.Class2;

import java.util.ArrayList;


/**
 * 处理数据库subcategory接口类
 */

public class SubcategoryDb {

    private SQLiteDatabase db;
    /**
     * 创建一个subcategoryDb实例
     * @param db_instance
     */
    public SubcategoryDb(SQLiteDatabase db_instance) {
        db = db_instance;
    }


    /**
     *  插入一条subcategory数据
     *
     *  @param class2 二级分类实例
     */

    public String insertSubcategory(Class2 class2) {
        return insertSubcategory(class2.getName(), class2.getColor(), class2.getFatherId());
    }
    /**
     *  插入一条subcategory数据
     *
     *  @param subcategory_name 二级分类名
     *  @param subcategory_color 二级分类颜色
     *  @param fatherId 所属一级分类id
     */

    private String insertSubcategory(String  subcategory_name, int subcategory_color, int fatherId) {
        int category_id = fatherId;
        try {
            Cursor cursor = db.query("c_subcategory", new String[]{"subcategory_name"},
                    "subcategory_name = ? AND status > -1 AND subcategory_fatherID = ?",
                    new String[]{subcategory_name, category_id + ""}, null, null, null);
            int count = cursor.getCount();
            if (count > 0)
                return "分类名重复";

            ContentValues values = new ContentValues();
            values.put("subcategory_name", subcategory_name);
            values.put("subcategory_color", subcategory_color);
            values.put("subcategory_fatherID", category_id);
            values.put("status", 0);
            values.put("anchor", 0);
            long result = db.insert("c_subcategory", null, values);

            if (result > -1)
                return "成功";
            else return "未知错误";
        } catch (SQLException e) {
            return "未知错误";
        }
    }
    /**
     * 更新所有subcategory状态
     *
     * @param subcategoryIdList subcategory数据
     * @param isdelete 是否删除
     */
    public void updateSubcategoryStatus(int[] subcategoryIdList, int[] isdelete) {
        int length = subcategoryIdList.length;
        for(int i = 0;i < length;i++) {
            ContentValues values = new ContentValues();
            if(isdelete[i] == 0) {
                values.put("status", 2);
            } else {
                values.put("status", -2);
            }
            db.update("c_subcategory", values, "subcategory_id = ?", new String[]{subcategoryIdList[i]+""});
        }
    }
    /**
     * 更新所有subcategory数据
     *
     * @param subcategoryArrayList subcategory数据
     *
     */
    public void updateSubcategoryData(ArrayList<Class2> subcategoryArrayList) {
        deleteAllLocalData();
        int length = subcategoryArrayList.size();
        for(int i = 0;i < length;i++) {
            Class2 subcategory = subcategoryArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put("subcategory_id", subcategory.getId());
            values.put("subcategory_name", subcategory.getName());
            values.put("subcategory_color", subcategory.getColor());
            values.put("subcategory_fatherID", subcategory.getFatherId());
            values.put("status", subcategory.getStatus());
            db.insert("c_subcategory", null, values);
        }
    }
    /**
     * 删除所有subcategory
     *
     *
     */
    private void deleteAllLocalData() {
        String DELETE_ALL = "delete from c_subcategory";
        db.execSQL(DELETE_ALL);
    }

    /**
     * 更新一条subcategory
     *
     * @param class2
     *
     * @return "成功" or "分类名重复" or "未知错误"
     */
    public String updateSubcategory(Class2 class2) {
        return updateSubcategory(class2.getId(),class2.getName(),class2.getColor(),class2.getFatherId());
    }

    /**
     * 更新一条subcategory
     *
     * @param subcategory_id subcategoryId
     * @param subcategory_name 二级分类名
     * @param subcategory_color 二级分类颜色
     * @param fatherId 一级分类Id
     *
     * @return "成功" or "分类名重复" or "未知错误"
     */
    private String updateSubcategory(int subcategory_id, String subcategory_name, int subcategory_color, int fatherId) {
        int category_id = fatherId;
        try {
            Cursor cursor = db.query("c_subcategory", new String[]{"subcategory_name"},
                    "subcategory_name = ? AND status > -1 AND subcategory_fatherID = ? AND subcategory_id != ?",
                    new String[]{subcategory_name, category_id + "", subcategory_id+""}, null, null, null);
            int count = cursor.getCount();
            if (count > 0)
                return "分类名重复";

            ContentValues values = new ContentValues();
            values.put("subcategory_name", subcategory_name);
            values.put("subcategory_color", subcategory_color);
            values.put("subcategory_fatherID", category_id);
            values.put("status", 1);
            values.put("anchor", 0);
            int result = db.update("c_subcategory", values, "subcategory_id = ?",
                    new String[]{subcategory_id + ""});
            if (result > 0)
                return "成功";
            else return "未知错误";
        } catch (SQLException e) {
            return "未知错误";
        }
    }

    /**
     * 删除一条subcategory
     *
     * @param class2 二级分类实例
     */
    public String  deleteSubcategory(Class2 class2) {
        return deleteSubcategory(class2.getId());
    }
    /**
     * 删除一条subcategory
     *
     * @param subcategory_id 二级分类Id
     */
    private String  deleteSubcategory(int subcategory_id) {
        RecordDb recordDb = new RecordDb(db);
        TemplateDb templateDb = new TemplateDb(db);
        boolean access = recordDb.isHaveRecord("c_subcategory", subcategory_id);
        if (access)
            return "该分类下有流水记录，无法删除";
        boolean access2 = templateDb.isHaveTemplate("c_subcategory", subcategory_id);
        if (access2)
            return "该分类下有模版记录，无法删除";

        ContentValues values = new ContentValues();
        values.put("status", -1);
        values.put("anchor", 0);
        int result = db.update("c_subcategory", values, "subcategory_id = ?", new String[]{subcategory_id+""});

        if (result > 0)
            return "成功";
        else return "未知错误";
    }
    /**
     * 查看是否有子分类
     *
     * @param category_id 一级分类Id
     */
    public boolean isHaveSubcategory (int category_id) {
        try {
            Cursor cursor = db.query("c_subcategory", new String[]{"subcategory_fatherID"},
                    "subcategory_fatherID = ? AND status > -1", new String[]{category_id + ""},
                    null, null, null);
            int count = cursor.getCount();
            if (count > 0)
                return true;
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }
    /**
     * 返回某个一级分类下的所有subcategory
     *
     * @param category_id 一级分类ID
     *
     * @return 二级分类数组
     */
    public ArrayList<Class2> subcategoryList (int category_id) {

        Cursor cursor = db.query("c_subcategory", null,
                "subcategory_fatherID = ? AND status > -1", new String[]{category_id+""},
                null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Class2> subcatagoryArray = new ArrayList();
        for (int i=0;i<count;i++){
            int nameIndex = cursor.getColumnIndex("subcategory_name");
            String  subcategory_name = cursor.getString(nameIndex);
            int colorIndex = cursor.getColumnIndex("subcategory_color");
            int subcategory_color = cursor.getInt(colorIndex);
            int idIndex = cursor.getColumnIndex("subcategory_id");
            int subcategory_id = cursor.getInt(idIndex);
            Class2 subcategory = new Class2(subcategory_id, subcategory_name, subcategory_color);
            subcatagoryArray.add(subcategory);
            cursor.moveToNext();
        }
        return subcatagoryArray;
    }

    /**
     * 返回所有需要更新的subcategory
     *
     *
     */
    public ArrayList<Class2> subcategoryListUpdate () {

        Cursor cursor = db.query("c_subcategory", null,
                "status < 2 AND status > -2",null,
                null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Class2> subcatagoryArray = new ArrayList();
        for (int i=0;i<count;i++){
            int nameIndex = cursor.getColumnIndex("subcategory_name");
            String  subcategory_name = cursor.getString(nameIndex);
            int colorIndex = cursor.getColumnIndex("subcategory_color");
            int subcategory_color = cursor.getInt(colorIndex);
            int idIndex = cursor.getColumnIndex("subcategory_id");
            int subcategory_id = cursor.getInt(idIndex);
            int fatherIdIndex = cursor.getColumnIndex("subcategory_fatherID");
            int fatherId = cursor.getInt(fatherIdIndex);
            int statusIndex = cursor.getColumnIndex("status");
            int status = cursor.getInt(statusIndex);
            Class2 subcategory = new Class2(subcategory_id, subcategory_name, subcategory_color, fatherId, status);
            subcatagoryArray.add(subcategory);
            cursor.moveToNext();
        }
        return subcatagoryArray;
    }

    public void printSubCategory(ArrayList<Class2> subcategoryArray) {
        System.out.println("test subcategory print ***************");
        for (int i = 0;i < subcategoryArray.size();i++) {
            Class2 subcategory  = subcategoryArray.get(i);
            int id = subcategory.getId();
            String name = subcategory.getName();
            int color = subcategory.getColor();
            System.out.println(id +" "+ name +" "+ color);
        }
    }
    /**
     * 调用同步后端subcategory数据的接口
     *
     * @param context 运行时环境
     */
    public void synchDataBackend(Context context) {
        UserDb userDb = new UserDb(context);
        String token = userDb.getUserToken();
        UpdateSubcategory updateSubcategory = new UpdateSubcategory(subcategoryListUpdate(), token, context, db);
    }
}

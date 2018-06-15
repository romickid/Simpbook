package nkucs1416.simpbook.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import nkucs1416.simpbook.network.UpdateCategory;
import nkucs1416.simpbook.util.Class1;

import java.util.ArrayList;

/**
 * 处理数据库category接口类
 */
public class CategoryDb {

    private SQLiteDatabase db;

    /**
     * 构建一个CategoryDb实例
     *
     * @param db_instance 数据库实例
     */
    public CategoryDb(SQLiteDatabase db_instance) {
        db = db_instance;
    }

    /**
     * 插入一条category数据
     *
     * @param category_name 分类名
     * @param category_color 分类颜色
     */
    private String insertCategory(String  category_name, int category_color) {
        try {
            Cursor cursor = db.query("c_category", new String[]{"category_name"}, "category_name = ? AND status != -1",
                    new String[]{category_name}, null, null, null);
            int count = cursor.getCount();
            if (count > 0)
                return "DUPLICATE ERROR";

            ContentValues values = new ContentValues();
            values.put("category_name", category_name);
            values.put("category_color", category_color);
            values.put("status", 0);
            values.put("anchor", 0);
            long result = db.insert("c_category", null, values);

            if (result > -1)
                return "SUCCESS";
            else return "UNKNOW SQL ERROR";
        } catch (SQLException e) {
            return "UNKNOW SQL ERROR";
        }
    }

    /**
     * 更新所有category数据
     *
     * @param categoryIdList 分类IdList
     * @param isdelete 是否删除
     */
    public void updateCategoryStatus(int[] categoryIdList, int[] isdelete) {
        int length = categoryIdList.length;
        for(int i = 0;i < length;i++) {
            ContentValues values = new ContentValues();
            if(isdelete[i] == 0) {
                values.put("status", 2);
            } else {
                values.put("status", -2);
            }
            db.update("c_category", values, "category_id = ?", new String[]{categoryIdList[i]+""});
        }
    }

    /**
     * 更新所有category数据
     *
     * @param categoryArrayList 分类数据
     */
    public void updateCategoryData(ArrayList<Class1> categoryArrayList) {
        deleteAllLocalData();
        int length = categoryArrayList.size();
        for(int i = 0;i < length;i++) {
            Class1 category = categoryArrayList.get(i);
            ContentValues values = new ContentValues();
            values.put("category_id", category.getId());
            values.put("category_name", category.getName());
            values.put("category_color", category.getColor());
            values.put("status", category.getStatus());
            db.insert("c_category", null, values);
        }
    }

    /**
     * 删除所有category数据
     *
     */
    private void deleteAllLocalData() {
        String DELETE_ALL = "delete from c_category";
        db.execSQL(DELETE_ALL);
    }

    /**
     * 更新一条category数据
     *
     * @param category_id 分类id
     * @param category_name 分类名
     * @param category_color 分类颜色
     */

    public String updateCategory(int category_id, String category_name, int category_color) {
        try {
            Cursor cursor = db.query("c_category", new String[]{"category_name"},
                    "category_name = ? AND status != -1 AND category_id != ?",
                    new String[]{category_name, category_id+""}, null, null, null);
            int count = cursor.getCount();
            if (count > 0)
                return "DUPLICATE ERROR";

            ContentValues values = new ContentValues();
            values.put("category_name", category_name);
            values.put("category_color", category_color);
            values.put("status", 1);
            values.put("anchor", 0);
            int result = db.update("c_category", values, "category_id = ?", new String[]{category_id + ""});

            if (result > 0)
                return "SUCCESS";
            else return "UNKNOW SQL ERROR";
        } catch (SQLException e) {
            return "UNKNOW SQL ERROR";
        }
    }

    /**
     * 删除一条category数据
     *
     * @param category_id 分类id
     */
    public String deleteCategory(int category_id) {
        try {

            RecordDb recordDb = new RecordDb(db);
            TemplateDb templateDb = new TemplateDb(db);
            SubcategoryDb subcategoryDb = new SubcategoryDb(db);

            boolean access = recordDb.isHaveRecord("c_category", category_id);
            boolean access2 = subcategoryDb.isHaveSubcategory(category_id);
            boolean access3 = templateDb.isHaveTemplate("c_category", category_id);
            if (access || access2 || access3)
                return "HAVE RECORD ON CATEGORY";


            ContentValues values = new ContentValues();
            values.put("status", -1);
            values.put("anchor", 0);
            int result = db.update("c_category", values, "category_id = ?", new String[]{category_id + ""});

            if (result > 0)
                return "SUCCESS";
            else return "UNKNOW SQL ERROR";
        } catch (SQLException e) {
            return "UNKNOW SQL ERROR";
        }
    }

    /**
     * 返回所有需要更新的category数据
     *
     * @return Class1的ArrayList
     */
    public ArrayList<Class1> categoryListUpdate (){
        Cursor cursor = db.query("c_category", null,
                "status > -2 AND status < 2", null,
                null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Class1> categoryArray = new ArrayList();
        for (int i=0;i<count;i++){
            int idIndex = cursor.getColumnIndex("category_id");
            int category_id= cursor.getInt(idIndex);
            int nameIndex = cursor.getColumnIndex("category_name");
            String  category_name = cursor.getString(nameIndex);
            int colorIndex = cursor.getColumnIndex("category_color");
            int category_color = cursor.getInt(colorIndex);
            int statusIndex = cursor.getColumnIndex("status");
            int status = cursor.getInt(statusIndex);
            Class1 category = new Class1(category_id, category_name, category_color, status);
            categoryArray.add(category);
            cursor.moveToNext();
        }
        return categoryArray;
    }

    /**
     * 返回所有category数据
     *
     * @return Class1的ArrayList
     */
    public ArrayList<Class1> categoryList () {
        Cursor cursor = db.query("c_category", null,
                "status > -1", null,
                null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<Class1> categoryArray = new ArrayList();
        for (int i=0;i<count;i++){
            int idIndex = cursor.getColumnIndex("category_id");
            int category_id= cursor.getInt(idIndex);
            int nameIndex = cursor.getColumnIndex("category_name");
            String  category_name = cursor.getString(nameIndex);
            int colorIndex = cursor.getColumnIndex("category_color");
            int category_color = cursor.getInt(colorIndex);
            Class1 category = new Class1(category_id, category_name, category_color);
            categoryArray.add(category);
            cursor.moveToNext();
        }
        return categoryArray;
    }

    /**
     * 打印category数据
     *
     * @param categoryArray category数据
     */
    private void printCategory(ArrayList<Class1> categoryArray) {
        System.out.println("test category print ***************");
        for (int i = 0;i < categoryArray.size();i++) {
            Class1 category  = categoryArray.get(i);
            int id = category.getId();
            String name = category.getName();
            int color = category.getColor();
            System.out.println(id +" "+ name +" "+ color);
        }
    }

    /**
     * 更新后端category数据
     *
     * @param context 运行时环境
     */
    public void synchDataBackend(Context context) {
        UserDb userDb = new UserDb(context);
        String token = userDb.getUserToken();
        UpdateCategory updateCategory = new UpdateCategory(categoryListUpdate(), token, context, db);
    }
}

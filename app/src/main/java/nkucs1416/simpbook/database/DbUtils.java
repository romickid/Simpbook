package nkucs1416.simpbook.database;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库工具类
 */
public class DbUtils {

    private SQLiteDatabase db;

    /**
     * 创建一个数据库工具实例
     *
     * @param db_instance 数据库实例
     */
    public DbUtils(SQLiteDatabase db_instance) {
        db = db_instance;
    }

    /**
     * 取回id
     *
     * @param table_name 表名
     * @param seg_name 字段名
     */

    public int retrieveID(String table_name, String  seg_name) {
        int idIndex = 0;
        int id_retrieve = 0;
        if (table_name == "c_account") {
            Cursor cursor_name = db.query(table_name, new String[]{"account_name", "account_id"}, "account_name = ?",
                    new String[] {seg_name}, null, null, null);
            cursor_name.moveToFirst();
            idIndex = cursor_name.getColumnIndex("account_id");
            id_retrieve = cursor_name.getInt(idIndex);
        } else if (table_name == "c_category") {
            Cursor cursor_name = db.query(table_name, new String[]{"category_name", "category_id"}, "category_name = ?",
                    new String[] {seg_name}, null, null, null);
            cursor_name.moveToFirst();
            idIndex = cursor_name.getColumnIndex("category_id");
            id_retrieve = cursor_name.getInt(idIndex);
        } else if (table_name == "c_subcategory") {
            Cursor cursor_name = db.query(table_name, new String[]{"subcategory_name", "subcategory_id"}, "subcategory_name = ?",
                    new String[] {seg_name}, null, null, null);
            cursor_name.moveToFirst();
            idIndex = cursor_name.getColumnIndex("subcategory_id");
            id_retrieve = cursor_name.getInt(idIndex);
        }
        return id_retrieve;
    }

    /**
     * 取回名字
     *
     * @param table_name 表名
     * @param seg_id 字段ID
     */
    public String retrieveName(String table_name, int  seg_id) {
        int nameIndex = 0;
        String name_retrieve = "";
        if (table_name == "c_account") {
            Cursor cursor_name = db.query(table_name, new String[]{"account_name", "account_id"}, "account_id = ?",
                    new String[] {seg_id+""}, null, null, null);
            cursor_name.moveToFirst();
            nameIndex = cursor_name.getColumnIndex("account_name");
            name_retrieve = cursor_name.getString(nameIndex);
        } else if (table_name == "c_category") {
            Cursor cursor_name = db.query(table_name, new String[]{"category_name", "category_id"}, "category_id = ?",
                    new String[] {seg_id+""}, null, null, null);
            cursor_name.moveToFirst();
            nameIndex = cursor_name.getColumnIndex("category_name");
            name_retrieve = cursor_name.getString(nameIndex);
        } else if (table_name == "c_subcategory") {
            Cursor cursor_name = db.query(table_name, new String[]{"subcategory_name", "subcategory_id"}, "subcategory_id = ?",
                    new String[] {seg_id+""}, null, null, null);
            cursor_name.moveToFirst();
            nameIndex = cursor_name.getColumnIndex("subcategory_name");
            name_retrieve = cursor_name.getString(nameIndex);
        }
        return name_retrieve;
    }
}

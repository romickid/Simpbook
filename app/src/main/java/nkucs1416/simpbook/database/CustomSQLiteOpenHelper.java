package nkucs1416.simpbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库类
 */
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simpbook.db";//数据库名字
    private static final int DATABASE_VERSION = 8;//数据库版本号
    private static final String CREATE_USER = "create table c_user ("
            + "user_id integer primary key,"
            + "user_name varchar(50) not null, "
            + "user_mail varchar(50) not null, "
            + "status integer,"
            + "anchor integer)";//数据库里的表
    private static final String CREATE_ACCOUNT = "create table c_account ("
            + "account_id integer primary key,"
            + "account_name varchar(50) not null, "
            + "account_sum float ,"
            + "account_color int,"
            + "account_type int,"
            + "status integer,"
            + "anchor integer)";
    private static final String CREATE_TEMPLATE = "create table c_template ("
            + "template_id integer primary key,"
            + "template_accountID integer not null, "
            + "template_money float not null, "
            + "template_type int not null,"
            + "template_note varchar(100),"
            + "template_categoryID integer,"
            + "template_time integer, "
            + "template_subcategoryID integer,"
            + "template_accountToID integer, "
            + "status integer,"
            + "anchor integer)";
    private static final String CREATE_RECORD = "create table c_record ("
            + "record_id integer primary key,"
            + "record_accountID integer not null, "
            + "record_money float not null, "
            + "record_type int not null,"
            + "record_note varchar(100),"
            + "record_categoryID integer,"
            + "record_subcategoryID integer,"
            + "record_time integer not null, "
            + "record_accountToID integer, "
            + "status integer,"
            + "anchor integer)";
    private static final String CREATE_CATEGORY = "create table c_category ("
            + "category_id integer primary key,"
            + "category_name varchar(50) not null, "
            + "category_color int, "
            + "category_type int, "
            + "status integer,"
            + "anchor integer)";
    private static final String CREATE_SUBCATEGORY = "create table c_subcategory ("
            + "subcategory_id integer primary key,"
            + "subcategory_name varchar(50) not null, "
            + "subcategory_fatherID integer not null, "
            + "subcategory_color int, "
            + "status integer,"
            + "anchor integer)";

    /**
     * 创建一个数据库实例
     *
     * @param context 运行时环境
     */
    public CustomSQLiteOpenHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 创建数据库实例
     *
     * @param name 数据库名
     * @param factory
     * @param version 版本
     * @param context 运行时环境
     */
    private CustomSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//调用到SQLiteOpenHelper中
    }

    /**
     * 数据库时的操作
     *
     * @param db 数据库实例
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d(TAG, "onCreate");
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL(CREATE_TEMPLATE);
        db.execSQL(CREATE_RECORD);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(CREATE_SUBCATEGORY);
    }

    /**
     * 数据库版本更新时的操作
     *
     * @param newVersion 新版本号
     * @param oldVersion 旧版本号
     * @param db 数据库实例
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_CATEGORY = "drop table c_category";
//        String DROP_RECORD = "drop table c_record";
        db.execSQL(DROP_CATEGORY);
//            db.execSQL(DROP_RECORD);
        db.execSQL(CREATE_CATEGORY);
//            db.execSQL(CREATE_RECORD);
    }

    public void clearDatabaseBydbName(Context context) {
        context.deleteDatabase("simpbook.db");
    }
}


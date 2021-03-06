package com.romickid.simpbook.setting;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.romickid.simpbook.R;
import com.romickid.simpbook.database.AccountDb;
import com.romickid.simpbook.database.CategoryDb;
import com.romickid.simpbook.database.CustomSQLiteOpenHelper;
import com.romickid.simpbook.database.RecordDb;
import com.romickid.simpbook.database.SubcategoryDb;
import com.romickid.simpbook.database.TemplateDb;

import static com.romickid.simpbook.util.Other.displayToast;

public class ActivitySetting extends AppCompatActivity {
    private Toolbar toolbar;

    private ImageView imageViewClassExpense;
    private ImageView imageViewClassIncome;
    private ImageView imageViewAccount;
    private ImageView imageViewUser;
    private ImageView imageViewDeleteData;

    private SQLiteDatabase sqLiteDatabase;
    private AccountDb accountDb;
    private CategoryDb categoryDb;
    private RecordDb recordDb;
    private SubcategoryDb subcategoryDb;
    private TemplateDb templateDb;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initFindById();
        initToolbar();
        initImageView();
        initDatabase();
    }


    // 初始化相关

    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.setting_toolbar);
        imageViewClassExpense = findViewById(R.id.setting_imageview_classexpense);
        imageViewClassIncome = findViewById(R.id.setting_imageview_classincome);
        imageViewAccount = findViewById(R.id.setting_imageview_account);
        imageViewDeleteData = findViewById(R.id.setting_imageview_deletedata);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化各类显示框
     */
    private void initImageView() {
        initImageViewClassExpense();
        initImageViewClassIncome();
        initImageViewAccount();
        initImageViewDeleteData();
    }

    /**
     * 初始化"支出分类设置"
     */
    private void initImageViewClassExpense() {
        imageViewClassExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivitySetting.this, ActivityClass1Expense.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"收入分类设置"
     */
    private void initImageViewClassIncome() {
        imageViewClassIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivitySetting.this, ActivityClass1Income.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"账户编辑设置"
     */
    private void initImageViewAccount() {
        imageViewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivitySetting.this, ActivityAccountSetting.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"删除所有数据"
     */
    private void initImageViewDeleteData() {
        imageViewDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                accountDb.deleteAllLocalData();
                categoryDb.deleteAllLocalData();
                recordDb.deleteAllLocalData();
                subcategoryDb.deleteAllLocalData();
                templateDb.deleteAllLocalData();
                displayToast("删除成功", getApplicationContext(), 0);
            }
        });
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(this);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        accountDb = new AccountDb(sqLiteDatabase);
        categoryDb = new CategoryDb(sqLiteDatabase);
        recordDb = new RecordDb(sqLiteDatabase);
        subcategoryDb = new SubcategoryDb(sqLiteDatabase);
        templateDb = new TemplateDb(sqLiteDatabase);
    }

}

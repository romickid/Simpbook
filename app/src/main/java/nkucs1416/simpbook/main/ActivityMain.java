package nkucs1416.simpbook.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.account.ActivityAccount;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.RecordDb;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.record.ActivityRecord;
import nkucs1416.simpbook.setting.ActivitySetting;
import nkucs1416.simpbook.statement.ActivityStatement;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.Date;

import static nkucs1416.simpbook.util.Money.setTextViewDecimalMoney;
import static nkucs1416.simpbook.util.Other.displayToast;

public class ActivityMain extends AppCompatActivity {

    private FloatingActionButton buttonRecord;
    private Button buttonAccount;
    private Button buttonCollection;
    private Button buttonStatement;
    private ImageView buttonSetting;

    private ImageView imageViewDay;
    private ImageView imageViewWeek;
    private ImageView imageViewMonth;
    private ImageView imageViewYear;
    private ImageView imageViewTopInfo;

    private TextView textViewDayExpense;
    private TextView textViewDayIncome;
    private TextView textViewWeekExpense;
    private TextView textViewWeekIncome;
    private TextView textViewMonthExpense;
    private TextView textViewMonthIncome;
    private TextView textViewYearExpense;
    private TextView textViewYearIncome;
    private TextView textViewTopInfoExpense;
    private TextView textViewTopInfoRemain;

    private TextView textViewDate;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;
    private AccountDb accountDb;
    private RecordDb recordDb;

    private ArrayList<Class1> listClass1s;
    private ArrayList<Class2> listClass2s;
    private ArrayList<Account> listAccounts;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFindById();
        initButton();
        initImageView();
        initDatabase();
        initData();

        updateData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
        updateData();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        buttonRecord = findViewById(R.id.main_button_record);
        buttonAccount = findViewById(R.id.main_button_account);
        buttonStatement = findViewById(R.id.main_button_statement);
        buttonCollection = findViewById(R.id.main_button_collection);
        buttonSetting = findViewById(R.id.main_button_setting);

        imageViewDay = findViewById(R.id.main_imageview_info1);
        imageViewWeek = findViewById(R.id.main_imageview_info2);
        imageViewMonth = findViewById(R.id.main_imageview_info3);
        imageViewYear = findViewById(R.id.main_imageview_info4);
        imageViewTopInfo = findViewById(R.id.main_imageview_topinfo);

        textViewDayExpense = findViewById(R.id.main_textview_todayexpense);
        textViewDayIncome = findViewById(R.id.main_textview_todayincome);
        textViewWeekExpense = findViewById(R.id.main_textview_weekexpense);
        textViewWeekIncome = findViewById(R.id.main_textview_weekincome);
        textViewMonthExpense = findViewById(R.id.main_textview_monthexpense);
        textViewMonthIncome = findViewById(R.id.main_textview_monthincome);
        textViewYearExpense = findViewById(R.id.main_textview_yearexpense);
        textViewYearIncome = findViewById(R.id.main_textview_yearincome);
        textViewTopInfoExpense = findViewById(R.id.main_textview_topinfoexpense);
        textViewTopInfoRemain = findViewById(R.id.main_textview_topinforemain);

        textViewDate = findViewById(R.id.main_textview_date);
    }

    /**
     * 初始化各类按钮
     */
    private void initButton() {
        initButtonRecord();
        initButtonAccount();
        initButtonStatement();
        initButtonCollection();
        initButtonSetting();
    }

    /**
     * 初始化记录按钮
     */
    private void initButtonRecord() {
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!checkDataValidity())
                    return;
                Intent intent = new Intent(ActivityMain.this, ActivityRecord.class);
                intent.putExtra("RecordType","Expense");
                intent.putExtra("RecordScheme","Insert");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"账户"按钮
     */
    private void initButtonAccount() {
        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivityAccount.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"流水"按钮
     */
    private void initButtonStatement() {
        buttonStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivityStatement.class);
                intent.putExtra("StatementFilterDate","Default");
                intent.putExtra("StatementFilterAccount", "Default");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"模板"按钮
     */
    private void initButtonCollection() {
        buttonCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!checkDataValidity())
                    return;
                Intent intent = new Intent(ActivityMain.this, ActivityRecord.class);
                intent.putExtra("RecordType","Collection");
                intent.putExtra("RecordScheme","Insert");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化设置按钮
     */
    private void initButtonSetting() {
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivitySetting.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化各类显示框
     */
    private void initImageView() {
        initImageViewBasicInfo();
        initImageViewDay();
        initImageViewWeek();
        initImageViewMonth();
        initImageViewYear();
    }

    /**
     * 初始化顶部综合信息
     */
    private void initImageViewBasicInfo() {
        imageViewTopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivityStatement.class);
                intent.putExtra("StatementFilterDate","ThisMonth");
                intent.putExtra("StatementFilterAccount", "Default");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本日收支信息
     */
    private void initImageViewDay() {
        imageViewDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivityStatement.class);
                intent.putExtra("StatementFilterDate","Today");
                intent.putExtra("StatementFilterAccount", "Default");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本周收支信息
     */
    private void initImageViewWeek() {
        imageViewWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivityStatement.class);
                intent.putExtra("StatementFilterDate","ThisWeek");
                intent.putExtra("StatementFilterAccount", "Default");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本月收支信息
     */
    private void initImageViewMonth() {
        imageViewMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivityStatement.class);
                intent.putExtra("StatementFilterDate","ThisMonth");
                intent.putExtra("StatementFilterAccount", "Default");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本年收支信息
     */
    private void initImageViewYear() {
        imageViewYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityMain.this, ActivityStatement.class);
                intent.putExtra("StatementFilterDate","ThisYear");
                intent.putExtra("StatementFilterAccount", "Default");
                startActivity(intent);
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
        recordDb = new RecordDb(sqLiteDatabase);
        class1Db = new CategoryDb(sqLiteDatabase);
        class2Db = new SubcategoryDb(sqLiteDatabase);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        listClass1s = class1Db.categoryList();
        listAccounts = accountDb.accountList();
    }


    // 更新数据相关
    /**
     * 更新数据
     */
    private void updateData() {
        updateDate();
        updateDayData();
        updateWeekData();
        updateMonthData();
        updateYearData();
    }

    /**
     * 更新日期
     */
    private void updateDate() {
        Date today = new Date();
        String strToday = today.getYear() + "/" + today.getMonth() + "/" + today.getDay() + "  " + today.getWeekOfDate();
        textViewDate.setText(strToday);
    }

    /**
     * 更新今日收支数据
     */
    private void updateDayData() {
        setTextViewDecimalMoney(textViewDayExpense, recordDb.todayExpenseRecordSum());
        setTextViewDecimalMoney(textViewDayIncome, recordDb.todayIncomeRecordSum());
    }

    /**
     * 更新本周收支数据
     */
    private void updateWeekData() {
        setTextViewDecimalMoney(textViewWeekExpense, recordDb.weekExpenseRecordSum());
        setTextViewDecimalMoney(textViewWeekIncome, recordDb.weekIncomeRecordSum());
    }

    /**
     * 更新本月收支数据
     */
    private void updateMonthData() {
        float expense = recordDb.monthExpenseRecordSum();
        float income = recordDb.monthIncomeRecordSum();
        setTextViewDecimalMoney(textViewMonthExpense, expense);
        setTextViewDecimalMoney(textViewMonthIncome, income);
        setTextViewDecimalMoney(textViewTopInfoExpense, expense);
        setTextViewDecimalMoney(textViewTopInfoRemain, income - expense);
    }

    /**
     * 更新本年收支数据
     */
    private void updateYearData() {
        setTextViewDecimalMoney(textViewYearExpense, recordDb.yearExpenseRecordSum());
        setTextViewDecimalMoney(textViewYearIncome, recordDb.yearIncomeRecordSum());
    }


    /**
     * 检查数据合法性
     */
    private boolean checkDataValidity() {
        ArrayList<Class1> listClass1Expense = class1Db.getCategoryListByType(1);
        ArrayList<Class1> listClass1Income = class1Db.getCategoryListByType(2);

        if (listClass1Expense.size() == 0) {
            displayToast("不存在支出的一级分类，请在设置中新建一级分类", this, 1);
            return false;
        }
        if (listClass1Income.size() == 0) {
            displayToast("不存在收入的一级分类，请在设置中新建一级分类", this, 1);
            return false;
        }
        if (listAccounts.size() == 0) {
            displayToast("不存在任何账户，请新建账户", this, 1);
            return false;
        }

        return true;
    }

}

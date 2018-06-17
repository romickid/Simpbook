package nkucs1416.simpbook.statement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.database.RecordDb;
import nkucs1416.simpbook.database.SubcategoryDb;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.OnDeleteDataListener;
import nkucs1416.simpbook.util.SpinnerAdapterAccount;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.SpinnerAdapterClass2;
import nkucs1416.simpbook.util.Date;
import nkucs1416.simpbook.util.SpinnerAdapterClass1;
import nkucs1416.simpbook.util.Record;

import static nkucs1416.simpbook.util.Class1.sortListClass1s;
import static nkucs1416.simpbook.util.Date.*;
import static nkucs1416.simpbook.util.Money.setTextViewDecimalMoney;

public class ActivityStatement extends AppCompatActivity implements OnDeleteDataListener {
    private Toolbar toolbar;
    private TextView textViewRemain;
    private TextView textViewIncome;
    private TextView textViewExpense;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonFilter;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;
    private AccountDb accountDb;
    private RecordDb recordDb;

    private ArrayList<Class1> listFilterClass1s;
    private ArrayList<Class2> listFilterClass2s;
    private ArrayList<Account> listFilterAccounts;
    private int class1Id;

    private SpinnerAdapterClass1 adapterFilterClass1;
    private SpinnerAdapterClass2 adapterFilterClass2;
    private SpinnerAdapterAccount adapterFilterAccount;

    private ArrayList<Record> listRecords;
    private ArrayList<HashMap<String, Object>> listStatementObjects;

    private StatementFilter statementFilter;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        initFindById();
        initToolbar();
        initDialog();
        initDatabase();

        updateData();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.statement_toolbar);
        textViewRemain = findViewById(R.id.statement_textview_remain);
        textViewIncome = findViewById(R.id.statement_textview_income);
        textViewExpense = findViewById(R.id.statement_textview_expense);
        recyclerView = findViewById(R.id.statement_recyclerview);
        buttonFilter = findViewById(R.id.statement_button_filter);
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
     * 初始化数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(this);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        class1Db = new CategoryDb(sqLiteDatabase);
        class2Db = new SubcategoryDb(sqLiteDatabase);
        accountDb = new AccountDb(sqLiteDatabase);
        recordDb = new RecordDb(sqLiteDatabase);
    }

    /**
     * 初始化对话框
     */
    private void initDialog() {
        setListenerFilterDialog();
    }


    // 更新显示内容相关
    /**
     * 更新RecycleView的绘制
     */
    private void updateRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterStatement adapterStatement = new AdapterStatement(this, listStatementObjects);
        recyclerView.setAdapter(adapterStatement);
    }

    /**
     * 更新筛选框Spinner数据的绑定
     */
    private void updateFilterSpinnerAdapter() {
        adapterFilterClass1 = new SpinnerAdapterClass1(this, listFilterClass1s);
        adapterFilterClass2 = new SpinnerAdapterClass2(this, listFilterClass2s);
        adapterFilterAccount = new SpinnerAdapterAccount(this, listFilterAccounts);
    }


    // 更新数据相关
    /**
     * 初始化数据
     */
    private void updateData() {
        setDefaultStatementFilter(); // TODO: 6/17/2018

        updateFilterData();
        updateStatement();
    }

    /**
     * 初始化筛选对话框中的数据
     */
    private void updateFilterData() {
        updateListClass1s();
        updateListClass2s();
        updateListAccounts();
        updateFilterSpinnerAdapter();
    }

    /**
     * 更新流水信息
     */
    private void updateStatement() {
        updateListRecords();
        updateListStatementObjects();
        updateRecycleView();
        updateTopInfo();
    }


    // 更新筛选数据相关
    /**
     * 更新所有一级支出分类信息
     */
    private void updateListClass1s() {
        listFilterClass1s = new ArrayList<>();
        ArrayList<Class1> listClass1Expense = class1Db.getCategoryListByType(1);
        ArrayList<Class1> listClass1Income = class1Db.getCategoryListByType(2);
        sortListClass1s(listClass1Expense);
        sortListClass1s(listClass1Income);
        listFilterClass1s.add(getAllDataClass1());
        listFilterClass1s.addAll(listClass1Expense);
        listFilterClass1s.addAll(listClass1Income);

        class1Id = listFilterClass1s.get(0).getId();
    }

    /**
     * 更新所有二级支出分类信息
     */
    private void updateListClass2s() {
        listFilterClass2s = new ArrayList<>();
        listFilterClass2s.add(getAllDataClass2());
        if (class1Id != 0)
            listFilterClass2s.addAll(class2Db.subcategoryList(class1Id));
    }

    /**
     * 更新所有账户信息
     */
    private void updateListAccounts() {
        listFilterAccounts = new ArrayList<>();
        listFilterAccounts.add(getAllDataAccount());
        listFilterAccounts.addAll(accountDb.accountList());
    }

    /**
     * 构建"所有一级分类"的Class1实例
     *
     * @return Class1实例
     */
    private Class1 getAllDataClass1() {
        return new Class1(-1, "所有一级分类", 5, -1);
    }

    /**
     * 构建"所有二级分类"的Class2实例
     *
     * @return Class2实例
     */
    private Class2 getAllDataClass2() {
        return new Class2(-1, "所有二级分类", 5, -1);
    }

    /**
     * 构建"所有账户"的Account实例
     *
     * @return Account实例
     */
    private Account getAllDataAccount() {
        return new Account(-1,"所有账户", 0.0f, 5, -1);
    }


    // 更新流水数据相关
    /**
     * 获取某个Record的RecordObject
     *
     * @param record 待获取的记录
     * @return RecordObject
     */
    private HashMap<String, Object> getRecordObject(Record record) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("StatementObjectViewType", 1); // 1->Record
        hashMap.put("Object", record);
        return hashMap;
    }

    /**
     * 获取某个日期的StatementDateObject
     *
     * @param date 待获取的日期
     * @return StatementDateObject
     */
    private HashMap<String, Object> getStatementDateObject(Date date) {
        StatementDate statementDate = new StatementDate(date);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("StatementObjectViewType", 2); // 2->StatementDate
        hashMap.put("Object", statementDate);
        return hashMap;
    }

    /**
     * 使用筛选参数更新listRecords信息
     */
    private void updateListRecords() {
        listRecords = recordDb.recordList(
                statementFilter.getClass1(),
                statementFilter.getClass2(),
                statementFilter.getAccount(),
                statementFilter.getDateFrom(),
                statementFilter.getDateTo()
        );
    }

    /**
     * 更新listRecords的listStatementObjects
     */
    private void updateListStatementObjects() {
        listStatementObjects = new ArrayList<>();
        Date currentDate = new Date(1900, 1, 1);

        for (Record record: listRecords) {
            if (compareDate(currentDate, record.getDate()) != 0) {
                currentDate = record.getDate();
                listStatementObjects.add(getStatementDateObject(currentDate));
                listStatementObjects.add(getRecordObject(record));
            }
            else {
                listStatementObjects.add(getRecordObject(record));
            }
        }
    }

    /**
     * 更新顶部的总结数据
     */
    private void updateTopInfo() {
        float expense = 0.0f;
        float income = 0.0f;

        for(Record record: listRecords) {
            if (record.getType() == 1) {
                expense += record.getMoney();
            }
            else if (record.getType() == 2) {
                income += record.getMoney();
            }
        }

        setTextViewDecimalMoney(textViewExpense, expense);
        setTextViewDecimalMoney(textViewIncome, income);
        setTextViewDecimalMoney(textViewRemain, income - expense);
    }


    // 更新筛选数据相关
    /**
     * 设置默认的statementFilter
     */
    private void setDefaultStatementFilter() {
        statementFilter = new StatementFilter(
                getAllDataClass1(),
                getAllDataClass2(),
                getAllDataAccount(),
                getDateAdd(new Date(), -90),
                new Date()
        );
    }

    /**
     * 读取Dialog中的信息, 并更新statementFilter
     *
     * @param spinnerClass1    dialog中的spinnerClass1
     * @param spinnerClass2    dialog中的spinnerClass2
     * @param spinnerAccount   dialog中的spinnerAccount
     * @param textViewDateFrom dialog中的textViewDateFrom
     * @param textViewDateTo   dialog中的textViewDateTo
     */
    private void updateStatementFilter(
            Spinner spinnerClass1,
            Spinner spinnerClass2,
            Spinner spinnerAccount,
            TextView textViewDateFrom,
            TextView textViewDateTo
    ) {
        Class1 class1 = (Class1) spinnerClass1.getSelectedItem();
        Class2 class2 = (Class2) spinnerClass2.getSelectedItem();
        Account account = (Account) spinnerAccount.getSelectedItem();
        statementFilter = new StatementFilter(
                class1,
                class2,
                account,
                getDate(textViewDateFrom),
                getDate(textViewDateTo)
        );
    }



    // 筛选Dialog相关
    /**
     * 设置筛选按钮的Listener
     */
    private void setListenerFilterDialog() {
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogFilter().show();
            }
        });
    }

    /**
     * 设置筛选框内, 日期的Listener
     *
     * @param textView 显示日期的textView
     */
    private void setListenerDateDialog(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogDate(textView).show();
            }
        });

    }

    /**
     * 构建筛选的Dialog
     *
     * @return dialog
     */
    private Dialog createDialogFilter() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        View viewRemarkDialog = View.inflate(this, R.layout.dialog_statementfilter, null);

        final Spinner spinnerClass1 = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_class1);
        final Spinner spinnerClass2 = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_class2);
        final Spinner spinnerAccount = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_account);
        final TextView textViewDateFrom = viewRemarkDialog.findViewById(R.id.dstatementfilter_textview_datefrom);
        final TextView textViewDateTo = viewRemarkDialog.findViewById(R.id.dstatementfilter_textview_dateto);

        setListenerSpinnerClass1(spinnerClass1, spinnerClass2, this);
        spinnerClass1.setAdapter(adapterFilterClass1);
        setSelectionSpinnerClass1(spinnerClass1, statementFilter.getClass1());
        spinnerClass2.setAdapter(adapterFilterClass2);
        setSelectionSpinnerClass2(spinnerClass2, statementFilter.getClass2());
        spinnerAccount.setAdapter(adapterFilterAccount);
        setSelectionSpinnerAccount(spinnerAccount, statementFilter.getAccount());

        setDisplayTextViewDate(textViewDateFrom, statementFilter.getDateFrom());
        setListenerDateDialog(textViewDateFrom);
        setDisplayTextViewDate(textViewDateTo, statementFilter.getDateTo());
        setListenerDateDialog(textViewDateTo);

        builder.setTitle("筛选");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateStatementFilter(spinnerClass1, spinnerClass2, spinnerAccount, textViewDateFrom, textViewDateTo);
                updateStatement();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    /**
     * 构建筛选框内, 选择日期的Dialog
     *
     * @param tTextView 显示日期的textView
     * @return 返回Dialog
     */
    private Dialog createDialogDate(TextView tTextView) {
        final TextView textView = tTextView;
        Dialog dialog;
        DatePickerDialog.OnDateSetListener listener;
        Date date = getDate(tTextView);

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTextViewDate(textView, new Date(year, month + 1, dayOfMonth));
            }
        };

        dialog = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, listener, date.getYear(), date.getMonth() - 1, date.getDay());
        return dialog;
    }

    /**
     * 为SpinnerClass1设置与Class2联动的Listener
     *
     * @param spinnerClass1 spinnerClass1
     * @param spinnerClass2 spinnerClass2
     * @param context context
     */
    private void setListenerSpinnerClass1(final Spinner spinnerClass1, final Spinner spinnerClass2, final Context context) {
        spinnerClass1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                class1Id = ((Class1)spinnerClass1.getSelectedItem()).getId();
                updateListClass2s();
                adapterFilterClass2 = new SpinnerAdapterClass2(context, listFilterClass2s);
                spinnerClass2.setAdapter(adapterFilterClass2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    /**
     * 为SpinnerClass1设置对应的Class1实例
     *
     * @param spinnerClass1 spinnerClass1
     * @param class1 class1实例
     */
    private void setSelectionSpinnerClass1(Spinner spinnerClass1, Class1 class1) {
        for(int i = 0; i < listFilterClass1s.size(); i++) {
            if (listFilterClass1s.get(i).getId() == class1.getId()) {
                spinnerClass1.setSelection(i);
                return;
            }
        }
    }

    /**
     * 为SpinnerClass2设置对应的Class2实例
     *
     * @param spinnerClass2 spinnerClass2
     * @param class2 class2实例
     */
    private void setSelectionSpinnerClass2(Spinner spinnerClass2, Class2 class2) {
        for(int i = 0; i < listFilterClass2s.size(); i++) {
            if (listFilterClass2s.get(i).getId() == class2.getId()) {
                spinnerClass2.setSelection(i);
                return;
            }
        }
    }

    /**
     * 为SpinnerAccount设置对应的Account实例
     *
     * @param spinnerAccount spinnerAccount
     * @param account account实例
     */
    private void setSelectionSpinnerAccount(Spinner spinnerAccount, Account account) {
        for(int i = 0; i < listFilterAccounts.size(); i++) {
            if (listFilterAccounts.get(i).getId() == account.getId()) {
                spinnerAccount.setSelection(i);
                return;
            }
        }
    }

    /**
     * 为textViewDate设置对应的Date实例
     *
     * @param textViewDate textView
     * @param date date实例
     */
    private void setDisplayTextViewDate(TextView textViewDate, Date date) {
        setTextViewDate(textViewDate, date);
    }


    // Adapter-Activity数据传递相关
    @Override
    public void OnDeleteData() {
        updateStatement();
    }

}

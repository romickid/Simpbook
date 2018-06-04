package nkucs1416.simpbook.statement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.AccountSpinnerAdapter;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.Class2;
import nkucs1416.simpbook.util.Class2SpinnerAdapter;
import nkucs1416.simpbook.util.Date;
import nkucs1416.simpbook.util.Class1SpinnerAdapter;
import nkucs1416.simpbook.util.StatementElement;

import static nkucs1416.simpbook.util.Date.*;

public class StatementActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewRemain;
    private TextView textViewIncome;
    private TextView textViewExpence;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonFilter;

    private ArrayList<Class1> listFilterClass1;
    private ArrayList<Class2> listFilterClass2;
    private ArrayList<Account> listFilterAccount;

    private Class1SpinnerAdapter adapterFilterClass1;
    private Class2SpinnerAdapter adapterFilterClass2;
    private AccountSpinnerAdapter adapterFilterAccount;

    private ArrayList<StatementElement> liststatementElements;

    private StatementFilter statementFilter;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        initFindById();
        initToolbar();
        initRecycleView();
        initSpinnerAdapter();
        setListenerFilter();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.statement_toolbar);
        textViewRemain = findViewById(R.id.statement_textview_remain);
        textViewIncome = findViewById(R.id.statement_textview_income);
        textViewExpence = findViewById(R.id.statement_textview_expense);
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
     * 初始化RecycleView
     */
    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        demoSetStatementList();

        StatementAdapter statementAdapter = new StatementAdapter(this, liststatementElements);
        recyclerView.setAdapter(statementAdapter);
    }


    // Statement相关
    /**
     * 测试用StatementList
     */
    private void demoSetStatementList() {
        liststatementElements = new ArrayList<StatementElement>();
        StatementElement statementElement = new StatementElement( R.drawable.ic_lens_yellow_a400_24dp, "1", 1.0f);
        liststatementElements.add(statementElement);

        statementElement = new StatementElement( R.drawable.ic_lens_blue_a400_24dp, "2", 2.0f);
        liststatementElements.add(statementElement);

        statementElement = new StatementElement( R.drawable.ic_lens_red_a400_24dp, "3", 3.0f);
        liststatementElements.add(statementElement);
    }

    /**
     * 依据statementFilter的参数, 更新statement列表
     */
    private void updateStatement() {

    }


    // 筛选Dialog相关
    /**
     * 设置筛选按钮的Listener
     */
    private void setListenerFilter() {
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                createDialogFilter().show();
            }
        });
    }

    /**
     * 设置筛选框内, 日期的Listener
     * @param textView 显示日期的textView
     */
    private void setListenerDate(final TextView textView) {
        setTextViewDate(textView, new Date());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                createDialogDate(textView).show();
            }
        });

    }

    /**
     * 构建筛选的Dialog
     *
     * @return
     */
    private Dialog createDialogFilter() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        View viewRemarkDialog = View.inflate(this, R.layout.dialog_statementfilter, null);
        final Spinner spinnerClass1 = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_class1);
        final Spinner spinnerClass2 = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_class2);
        final Spinner spinnerAccount = viewRemarkDialog.findViewById(R.id.dstatementfilter_spinner_account);
        final TextView textViewDateFrom = viewRemarkDialog.findViewById(R.id.dstatementfilter_textview_datefrom);
        final TextView textViewDateTo = viewRemarkDialog.findViewById(R.id.dstatementfilter_textview_dateto);

        spinnerClass1.setAdapter(adapterFilterClass1);
        spinnerClass2.setAdapter(adapterFilterClass2);
        spinnerAccount.setAdapter(adapterFilterAccount);
        setListenerDate(textViewDateFrom);
        setListenerDate(textViewDateTo);

        builder.setTitle("筛选");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateStatementFilter(spinnerClass1, spinnerClass2, spinnerAccount, textViewDateFrom, textViewDateTo);
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
     * @param ttextView 显示日期的textView
     * @return 返回Dialog
     */
    private Dialog createDialogDate(TextView ttextView) {
        final TextView textView = ttextView;
        Dialog dialog = null;
        DatePickerDialog.OnDateSetListener listener = null;
        Date date = new Date();

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
     * 初始化筛选框中,分类和账户的SpinnerAdapter
     */
    private void initSpinnerAdapter() {
        demoSetListClass1();
        demoSetListClass2();
        demoSetListAccount();

        adapterFilterClass1 = new Class1SpinnerAdapter(this, listFilterClass1);
        adapterFilterClass2 = new Class2SpinnerAdapter(this, listFilterClass2);
        adapterFilterAccount = new AccountSpinnerAdapter(this, listFilterAccount);
    }

    /**
     * 测试用ListClass1
     */
    private void demoSetListClass1() {
        listFilterClass1 = new ArrayList<Class1>();
        Class1 class1 = new Class1("1", R.drawable.ic_lens_yellow_a400_24dp, 1);
        listFilterClass1.add(class1);

        class1 = new Class1("2", R.drawable.ic_lens_blue_a400_24dp, 2);
        listFilterClass1.add(class1);

        class1 = new Class1("3", R.drawable.ic_lens_yellow_a400_24dp, 3);
        listFilterClass1.add(class1);
    }

    /**
     * 测试用ListClass2
     */
    private void demoSetListClass2() {
        listFilterClass2 = new ArrayList<Class2>();
        Class2 class2 = new Class2("1", R.drawable.ic_lens_yellow_a400_24dp, 1);
        listFilterClass2.add(class2);

        class2 = new Class2("2", R.drawable.ic_lens_blue_a400_24dp, 2);
        listFilterClass2.add(class2);

        class2 = new Class2("3", R.drawable.ic_lens_yellow_a400_24dp, 3);
        listFilterClass2.add(class2);
    }

    /**
     * 测试用ListAccount
     */
    private void demoSetListAccount() {
        listFilterAccount = new ArrayList<Account>();
        Account account = new Account("1", R.drawable.ic_lens_yellow_a400_24dp, 1);
        listFilterAccount.add(account);

        account = new Account("2", R.drawable.ic_lens_blue_a400_24dp, 2);
        listFilterAccount.add(account);

        account = new Account("3", R.drawable.ic_lens_yellow_a400_24dp, 3);
        listFilterAccount.add(account);
    }


    // 筛选相关
    /**
     * 设置默认的statementFilter
     */
    private void setDefaultStatementFilter() {
        statementFilter = new StatementFilter(
                -1,
                -1,
                -1,
                new Date(),
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
                class1.getId(),
                class2.getId(),
                account.getId(),
                getDate(textViewDateFrom),
                getDate(textViewDateTo)
        );

        updateStatement();
    }

}
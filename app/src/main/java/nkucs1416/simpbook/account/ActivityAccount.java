package nkucs1416.simpbook.account;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.util.Account;

import static nkucs1416.simpbook.account.AccountType.getAccountTypeName;
import static nkucs1416.simpbook.util.Account.getSumMoney;
import static nkucs1416.simpbook.util.Account.sortListAccounts;
import static nkucs1416.simpbook.util.Money.setTextViewMoneyDecimal;

public class ActivityAccount extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewNetAssets;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddAccount;

    private SQLiteDatabase sqLiteDatabase;
    private AccountDb accountDb;

    private ArrayList<HashMap<String, Object>> listAccountObjects;
    private ArrayList<Account> listAccounts;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initFindById();
        initToolbar();
        initButtonAddAccount();
        initDatabase();
        initData();

        initRecycleView();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.account_toolbar);
        textViewNetAssets = findViewById(R.id.account_textview_netassets);
        recyclerView = findViewById(R.id.account_recyclerview);
        buttonAddAccount = findViewById(R.id.account_button_addaccount);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
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
        AdapterAccount adapterAccount = new AdapterAccount(this, listAccountObjects);
        recyclerView.setAdapter(adapterAccount);
    }

    /**
     * 初始化添加Account按钮
     */
    private void initButtonAddAccount() {
        buttonAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityAccount.this, ActivityAccountAdd.class);
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
    }

    /**
     * 初始化数据
     */
    private void initData() {
        updateListAccounts();
        updateListAccountObjects();
        updateNetAssets();
    }


    // 数据相关
    /**
     * 更新所有账户信息
     */
    private void updateListAccounts() {
        listAccounts = accountDb.accountList();
        sortListAccounts(listAccounts);
    }

    /**
     * 更新ListAccountObjects
     */
    private void updateListAccountObjects() {
        listAccountObjects = new ArrayList<>();

        int accountTypeIndex = 0;
        AccountElement accountElement;
        AccountSummarize accountSummarize;
        HashMap<String, Object> hashMap;

        for(Account account: listAccounts) {
            if (account.getType() > accountTypeIndex) {
                // 增加一个Summarize
                accountTypeIndex += 1;
                accountSummarize = new AccountSummarize(getAccountTypeName(accountTypeIndex), getSumMoney(accountTypeIndex, listAccounts));
                hashMap = new HashMap<>();
                hashMap.put("type", 0); // 0->Summarize
                hashMap.put("object", accountSummarize);
                listAccountObjects.add(hashMap);
            }
            // 增加一个Element
            accountElement = new AccountElement(account.getColor(), account.getName(), account.getMoney(), account.getId());
            hashMap = new HashMap<>();
            hashMap.put("type", 1); // 1->Element
            hashMap.put("object", accountElement);
            listAccountObjects.add(hashMap);
        }
    }

    /**
     * 更新净资产信息
     */
    private void updateNetAssets() {
        setTextViewMoneyDecimal(textViewNetAssets, getSumMoney(listAccounts));
    }

}

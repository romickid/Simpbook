package com.romickid.simpbook.account;

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

import com.romickid.simpbook.R;
import com.romickid.simpbook.database.AccountDb;
import com.romickid.simpbook.database.CustomSQLiteOpenHelper;
import com.romickid.simpbook.util.Account;
import com.romickid.simpbook.util.OnDeleteDataListener;

import static com.romickid.simpbook.account.AccountType.getAccountTypeName;
import static com.romickid.simpbook.util.Account.getSumMoney;
import static com.romickid.simpbook.util.Money.setTextViewDecimalMoney;

public class ActivityAccount extends AppCompatActivity implements OnDeleteDataListener {
    private Toolbar toolbar;
    private TextView textViewNetAssets;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAdd;

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
        initButtonAdd();
        initDatabase();
        initData();

        initRecycleView();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        buttonAdd = findViewById(R.id.account_button_addaccount);
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
        AdapterAccount adapterAccount = new AdapterAccount(listAccountObjects, this, this);
        recyclerView.setAdapter(adapterAccount);
    }

    /**
     * 初始化添加Account按钮
     */
    private void initButtonAdd() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivityAccount.this, ActivityAccountEdit.class);
                intent.putExtra("AccountEditScheme", "Insert");
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
    }

    /**
     * 更新ListAccountObjects
     */
    private void updateListAccountObjects() {
        listAccountObjects = new ArrayList<>();

        ArrayList<Account> listAccounts1 = getListAccountsByType(listAccounts, 1);
        if (listAccounts1.size() != 0)
            listAccountObjects.add(getAccountSummarizeObject(listAccounts1, 1));
        listAccountObjects.addAll(getListAccountElementObjects(listAccounts1));

        ArrayList<Account> listAccounts2 = getListAccountsByType(listAccounts, 2);
        if (listAccounts2.size() != 0)
            listAccountObjects.add(getAccountSummarizeObject(listAccounts2, 2));
        listAccountObjects.addAll(getListAccountElementObjects(listAccounts2));
    }

    /**
     * 获取某个账户类型的listAccounts
     *
     * @param listAccounts 待获取的listAccounts
     * @param type 账户类型
     * @return 筛选后的listAccounts
     */
    private ArrayList<Account> getListAccountsByType(ArrayList<Account> listAccounts, int type) {
        ArrayList<Account> listReturn = new ArrayList<>();
        for(Account account: listAccounts) {
            if (account.getType() == type) {
                listReturn.add(account);
            }
        }
        return listReturn;
    }

    /**
     * 获取某个listAccounts的listAccountElementObjects
     *
     * @param listAccounts 待获取的listAccounts
     * @return listAccountElementObjects
     */
    private ArrayList<HashMap<String, Object>> getListAccountElementObjects(ArrayList<Account> listAccounts) {
        ArrayList<HashMap<String, Object>> listReturn = new ArrayList<>();
        HashMap<String, Object> hashMap;

        for(Account account: listAccounts) {
            hashMap = new HashMap<>();
            hashMap.put("AccountObjectViewType", 1); // 1->AccountDefault
            hashMap.put("Object", account);
            listReturn.add(hashMap);
        }
        return listReturn;
    }

    /**
     * 获取某个账户类型的AccountSummarizeObject
     *
     * @param listAccounts 待获取的listAccounts
     * @param type 账户类型
     * @return AccountSummarizeObject
     */
    private HashMap<String, Object> getAccountSummarizeObject(ArrayList<Account> listAccounts, int type) {
        AccountSummarize accountSummarize =
                new AccountSummarize(getAccountTypeName(type), getSumMoney(listAccounts));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("AccountObjectViewType", 2); // 2->AccountSummarize
        hashMap.put("Object", accountSummarize);
        return hashMap;
    }

    /**
     * 更新净资产信息
     */
    private void updateNetAssets() {
        setTextViewDecimalMoney(textViewNetAssets, getSumMoney(listAccounts));
    }


    // Adapter-Activity数据传递相关
    @Override
    public void OnDeleteData() {
        initData();
        initRecycleView();
    }

}

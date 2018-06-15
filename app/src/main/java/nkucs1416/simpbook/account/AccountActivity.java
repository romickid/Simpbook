package nkucs1416.simpbook.account;

import android.content.Intent;
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

public class AccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewNetAssets;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddAccount;

    private ArrayList<HashMap<String, Object>> listAccountObjects;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initFindById();
        initToolbar();
        initRecycleView();
        initButtonAddAccount();
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

        demoAccountObjectsList();

        AccountAdapter accountAdapter = new AccountAdapter(this, listAccountObjects);
        recyclerView.setAdapter(accountAdapter);
    }

    /**
     * 初始化添加Account按钮
     */
    private void initButtonAddAccount() {
        buttonAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(AccountActivity.this, AccountAddActivity.class);
                startActivity(intent);
            }
        });
    }


    // RecyclerView相关
    /**
     * 测试用ListAccountObjects
     */
    private void demoAccountObjectsList() {
        listAccountObjects = new ArrayList<HashMap<String, Object>>();

        HashMap hashMap = null;
        AccountElement accountElement = null;
        AccountSummarize accountSummarize = null;
        Integer type = null;

        type = 0;
        accountSummarize = new AccountSummarize("资产账户", 2.0f);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountSummarize);
        listAccountObjects.add(hashMap);

        type = 1;
        accountElement = new AccountElement( R.drawable.ic_lens_yellow_a400_24dp, "支付宝", 1.0f, true, 1);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountElement);
        listAccountObjects.add(hashMap);

        type = 0;
        accountSummarize = new AccountSummarize("负债账户", 2.0f);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountSummarize);
        listAccountObjects.add(hashMap);

        type = 1;
        accountElement = new AccountElement( R.drawable.ic_lens_yellow_a400_24dp, "信用卡", 1.0f, true, 2);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountElement);
        listAccountObjects.add(hashMap);

        type = 1;
        accountElement = new AccountElement( R.drawable.ic_lens_blue_a400_24dp, "信用卡2", 1.0f, true, 3);
        hashMap = new HashMap<String, Object>();
        hashMap.put("type", type);
        hashMap.put("object", accountElement);
        listAccountObjects.add(hashMap);
    }

}

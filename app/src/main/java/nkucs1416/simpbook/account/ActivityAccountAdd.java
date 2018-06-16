package nkucs1416.simpbook.account;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.SpinnerAdapterColor;

import static nkucs1416.simpbook.account.AccountType.getListAccountTypes;
import static nkucs1416.simpbook.util.Color.*;
import static nkucs1416.simpbook.util.Money.setEditTextDecimalScheme;

public class ActivityAccountAdd extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTextName;
    private Spinner spinnerAccountType;
    private Spinner spinnerAccountColor;
    private EditText editTextMoney;
    private FloatingActionButton buttonAddAccount;

    private SQLiteDatabase sqLiteDatabase;
    private AccountDb accountDb;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountadd);

        initFindById();
        initToolbar();
        initSpinner();
        initMoney();
        initButton();
        initDatabase();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.accountadd_toolbar);
        editTextName = findViewById(R.id.accountadd_edittext_name);
        spinnerAccountType = findViewById(R.id.accoundadd_spinner_accounttype);
        spinnerAccountColor = findViewById(R.id.accoundadd_spinner_color);
        editTextMoney = findViewById(R.id.accountadd_edittext_money);
        buttonAddAccount = findViewById(R.id.accountadd_button_add);
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
     * 初始化Spinner
     */
    private void initSpinner() {
        initSpinnerAccountType();
        initSpinnerAccountColor();
    }

    /**
     * 初始化SpinnerAccountType
     */
    private void initSpinnerAccountType() {
        ArrayList<Integer> listAccountTypes = getListAccountTypes();
        SpinnerAdapterAccountType adapterAccountType = new SpinnerAdapterAccountType(this, listAccountTypes);
        spinnerAccountType.setAdapter(adapterAccountType);
    }

    /**
     * 初始化SpinnerAccountColor
     */
    private void initSpinnerAccountColor() {
        ArrayList<Integer> listAccountColors = getListColorIds();
        SpinnerAdapterColor adapterAccountColor = new SpinnerAdapterColor(this, listAccountColors);
        spinnerAccountColor.setAdapter(adapterAccountColor);
    }

    /**
     * 初始化金额
     */
    private void initMoney() {
        setEditTextDecimalScheme(editTextMoney);
    }

    /**
     * 初始化按钮
     */
    private void initButton() {
        buttonAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String message = insertAccount();
                if (message.equals("成功")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityAccountAdd.this, ActivityAccount.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
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


    // 数据相关
    /**
     * 获取输入的Account数据
     *
     * @return Account实例
     */
    private Account getAccount() {
        String name = editTextName.getText().toString();
        int type = (int)spinnerAccountType.getSelectedItem();
        int color = (int)spinnerAccountColor.getSelectedItem();
        String strMoney = editTextMoney.getText().toString();
        float money = 0.0f;
        if (!strMoney.isEmpty())
            money = Float.parseFloat(strMoney);

        return new Account(name, money, color, type);
    }

    /**
     * 向数据库中保存数据
     */
    private String insertAccount() {
        Account accountSave = getAccount();
        return accountDb.insertAccount(accountSave);
    }

}

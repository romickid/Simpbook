package com.romickid.simpbook.account;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import com.romickid.simpbook.R;
import com.romickid.simpbook.database.AccountDb;
import com.romickid.simpbook.database.CustomSQLiteOpenHelper;
import com.romickid.simpbook.util.Account;
import com.romickid.simpbook.util.SpinnerAdapterColor;

import static com.romickid.simpbook.account.AccountType.getListAccountTypes;
import static com.romickid.simpbook.util.Color.*;
import static com.romickid.simpbook.util.Money.setEditTextDecimalMoney;
import static com.romickid.simpbook.util.Money.setEditTextDecimalScheme;
import static com.romickid.simpbook.util.Other.displayToast;

public class ActivityAccountEdit extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTextName;
    private Spinner spinnerAccountType;
    private Spinner spinnerAccountColor;
    private EditText editTextMoney;
    private FloatingActionButton buttonAddAccount;

    private SQLiteDatabase sqLiteDatabase;
    private AccountDb accountDb;

    private ArrayList<Integer> listAccountTypes;
    private ArrayList<Integer> listAccountColors;

    private String accountEditScheme;
    private int updateAccountId;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountedit);

        initFindById();
        initAccountEditScheme();
        initToolbar();
        initSpinner();
        initMoney();
        initButton();

        initDatabase();
        initData();

        updateDataForUpdateScheme();
    }


    // 初始化相关

    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.accountedit_toolbar);
        editTextName = findViewById(R.id.accountedit_edittext_name);
        spinnerAccountType = findViewById(R.id.accountedit_spinner_accounttype);
        spinnerAccountColor = findViewById(R.id.accountedit_spinner_color);
        editTextMoney = findViewById(R.id.accountedit_edittext_money);
        buttonAddAccount = findViewById(R.id.accountedit_button_add);
    }

    /**
     * 初始化编辑形式: Insert / Update
     */
    private void initAccountEditScheme() {
        accountEditScheme = getIntent().getStringExtra("AccountEditScheme");

        if (accountEditScheme.equals("Update")) {
            updateAccountId = Integer.valueOf(getIntent().getStringExtra("AccountUpdateId"));
        }
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
        listAccountTypes = getListAccountTypes();
        SpinnerAdapterAccountType adapterAccountType = new SpinnerAdapterAccountType(this, listAccountTypes);
        spinnerAccountType.setAdapter(adapterAccountType);
    }

    /**
     * 初始化SpinnerAccountColor
     */
    private void initSpinnerAccountColor() {
        listAccountColors = getListColorIds();
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
                switch (accountEditScheme) {
                    case "Insert":
                        Account accountInsert = getAccountInsert();
                        String message = insertAccount(accountInsert);
                        if (message.equals("成功")) {
                            displayToast(message, getApplicationContext(), 0);
                            finish();
                        } else {
                            displayToast(message, getApplicationContext(), 1);
                        }
                        break;

                    case "Update":
                        Account accountUpdate = getAccountUpdate(updateAccountId);
                        String messageUpdate = updateAccount(accountUpdate);
                        if (messageUpdate.equals("成功")) {
                            displayToast(messageUpdate, getApplicationContext(), 0);
                            finish();
                        } else {
                            displayToast(messageUpdate, getApplicationContext(), 1);
                        }
                        break;
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

    /**
     * 初始化数据
     */
    private void initData() {
        updateListAccountTypes();
        updateListColors();
    }


    // 账户类型相关

    /**
     * 为SpinnerAccountType设置与accountType实例相同的位置
     *
     * @param accountTypeId 需要显示的accountType实例对应的id
     */
    private void setSpinnerPositionAccountTypeById(int accountTypeId) {
        spinnerAccountType.setSelection(accountTypeId - 1);
    }


    // 账户标识色相关

    /**
     * 为SpinnerColor设置与color实例相同的位置
     *
     * @param colorId 需要显示的Color实例对应的id
     */
    private void setSpinnerPositionColorById(int colorId) {
        spinnerAccountColor.setSelection(colorId - 1);
    }


    // 数据相关

    /**
     * 更新标识色信息
     */
    private void updateListAccountTypes() {
        listAccountTypes = getListAccountTypes();
    }

    /**
     * 更新标识色信息
     */
    private void updateListColors() {
        listAccountColors = getListColorIds();
    }


    // 修改数据相关

    /**
     * 获取用于添加的Account数据
     *
     * @return Account实例
     */
    private Account getAccountInsert() {
        String name = editTextName.getText().toString();
        int type = (int) spinnerAccountType.getSelectedItem();
        int color = (int) spinnerAccountColor.getSelectedItem();
        String strMoney = editTextMoney.getText().toString();
        float money = 0.0f;
        if (!strMoney.isEmpty())
            money = Float.parseFloat(strMoney);

        return new Account(name, money, color, type);
    }

    /**
     * 获取用于更新的Account数据
     *
     * @return Account实例
     */
    private Account getAccountUpdate(int id) {
        String name = editTextName.getText().toString();
        int type = (int) spinnerAccountType.getSelectedItem();
        int color = (int) spinnerAccountColor.getSelectedItem();
        String strMoney = editTextMoney.getText().toString();
        float money = 0.0f;
        if (!strMoney.isEmpty())
            money = Float.parseFloat(strMoney);

        return new Account(id, name, money, color, type);
    }

    /**
     * 向数据库中添加数据
     */
    private String insertAccount(Account accountSave) {
        return accountDb.insertAccount(accountSave);
    }

    /**
     * 向数据库中更新数据
     */
    private String updateAccount(Account accountUpdate) {
        return accountDb.updateAccount(accountUpdate);
    }


    // 更新数据相关

    /**
     * 若Scheme为更新状态, 则使用数据更新控件
     */
    private void updateDataForUpdateScheme() {
        if (accountEditScheme.equals("Update")) {
            toolbar.setTitle("账户编辑");
            Account account = accountDb.getAccountListById(updateAccountId).get(0);

            String name = account.getName();
            int accountType = account.getType();
            int colorId = account.getColor();
            float money = account.getMoney();

            editTextName.setText(name);
            setSpinnerPositionAccountTypeById(accountType);
            setSpinnerPositionColorById(colorId);
            setEditTextDecimalMoney(editTextMoney, money);
        }
    }

}

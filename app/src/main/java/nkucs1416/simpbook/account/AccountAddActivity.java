package nkucs1416.simpbook.account;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.AccountDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.util.Account;
import nkucs1416.simpbook.util.ColorSpinnerAdapter;

import static nkucs1416.simpbook.account.AccountType.getListAccountTypes;
import static nkucs1416.simpbook.util.Color.*;

public class AccountAddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTextName;
    private Spinner spinnerAccountType;
    private Spinner spinnerAccountColor;
    private EditText editTextMoney;
    private FloatingActionButton buttonAdd;

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
        buttonAdd = findViewById(R.id.accountadd_button_add);
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
        AccountTypeSpinnerAdapter adapterAccountType = new AccountTypeSpinnerAdapter(this, listAccountTypes);
        spinnerAccountType.setAdapter(adapterAccountType);
    }

    /**
     * 初始化SpinnerAccountColor
     */
    private void initSpinnerAccountColor() {
        ArrayList<Integer> listAccountColors = getListColorIds();
        ColorSpinnerAdapter adapterAccountColor = new ColorSpinnerAdapter(this, listAccountColors);
        spinnerAccountColor.setAdapter(adapterAccountColor);
    }

    /**
     * 初始化金额
     */
    private void initMoney() {
        setEditTextMoneyDecimal();
    }

    /**
     * 初始化按钮
     */
    private void initButton() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String message = saveAccount();
                if (message.equals("SUCCESS")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AccountAddActivity.this, AccountActivity.class);
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
    }


    // 金额相关
    /**
     * 设置金额的格式化(输入框设置为2位小数)
     */
    public void setEditTextMoneyDecimal() {
        editTextMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editTextMoney.setText(s);
                        editTextMoney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editTextMoney.setText(s);
                    editTextMoney.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editTextMoney.setText(s.subSequence(0, 1));
                        editTextMoney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }


    // 数据相关
    /**
     * 获取输入的Account数据
     *
     * @return Account实例
     */
    private Account getAccount() {
        String name = editTextName.getText().toString();
        int type = spinnerAccountType.getId();
        int color = spinnerAccountColor.getId();
        float money = Float.parseFloat(editTextMoney.getText().toString());
        return new Account(name, money, type, color);
    }

    /**
     * 向数据库中保存数据
     */
    private String saveAccount() {
        Account accountSave = getAccount();
        accountDb = new AccountDb(sqLiteDatabase);
        return accountDb.insertAccount(accountSave);
    }

}

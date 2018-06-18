package nkucs1416.simpbook.setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.database.CategoryDb;
import nkucs1416.simpbook.database.CustomSQLiteOpenHelper;
import nkucs1416.simpbook.util.Class1;
import nkucs1416.simpbook.util.SpinnerAdapterColor;

import static nkucs1416.simpbook.util.Color.getListColorIds;
import static nkucs1416.simpbook.util.Other.displayToast;

public class ActivityClass1Income extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAdd;

    private SpinnerAdapterColor spinnerAdapterColor;
    private AdapterClass1 adapterClass1;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;

    private ArrayList<Class1> listClass1s;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        initFindById();
        initToolbar();
        initSpinnerAdapter();
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
        toolbar = findViewById(R.id.class_toolbar);
        recyclerView = findViewById(R.id.class_recyclerview);
        buttonAdd = findViewById(R.id.class_button_add);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("收入分类");

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
        adapterClass1 = new AdapterClass1(this, listClass1s);
        recyclerView.setAdapter(adapterClass1);
    }

    /**
     * 初始化按钮
     */
    private void initButtonAdd() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                createDialogAdd().show();
            }
        });
    }

    /**
     * 初始化SpinnerAdapter
     */
    private void initSpinnerAdapter() {
        ArrayList<Integer> listColors = getListColorIds();
        spinnerAdapterColor = new SpinnerAdapterColor(this, listColors);
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        CustomSQLiteOpenHelper customSQLiteOpenHelper = new CustomSQLiteOpenHelper(this);
        sqLiteDatabase = customSQLiteOpenHelper.getWritableDatabase();
        class1Db = new CategoryDb(sqLiteDatabase);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        updateListClass1s();
    }


    // 数据相关
    /**
     * 更新所有一级收入分类信息
     */
    private void updateListClass1s() {
        listClass1s = class1Db.getCategoryListByType(2);
    }

    /**
     * 获取一个Class1实例
     *
     * @param name 名称
     * @param colorId 颜色id
     * @return 实例
     */
    private Class1 getClass1(String name, int colorId, int type) {
        return new Class1(name, colorId, type);
    }

    /**
     * 向数据库中添加一级分类数据
     *
     * @param class1Insert 添加的一级分类实例
     * @return 操作信息
     */
    private String insertClass1(Class1 class1Insert) {
        return class1Db.insertCategory(class1Insert);
    }


    // 新增收入Class1相关
    /**
     * 构建"新增Class1"的Dialog
     *
     * @return dialog实例
     */
    private Dialog createDialogAdd() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        View viewRemarkDialog = View.inflate(this, R.layout.dialog_class1, null);

        final EditText editText = viewRemarkDialog.findViewById(R.id.dclass1_edittext);
        final Spinner spinnerColor = viewRemarkDialog.findViewById(R.id.dclass1_spinner_color);

        spinnerColor.setAdapter(spinnerAdapterColor);

        builder.setTitle("新增一级分类");
        builder.setView(viewRemarkDialog);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                int colorId = (int)spinnerColor.getSelectedItem();

                if (name.isEmpty()) {
                    displayToast("输入不能为空", getApplicationContext(), 0);
                    dialog.cancel();
                    return;
                }

                Class1 class1 = getClass1(name, colorId, 2); // 2->收入分类

                String message = insertClass1(class1);
                if (message.equals("成功")) {
                    displayToast(message, getApplicationContext(), 0);
                    refreshActivity();
                }
                else {
                    displayToast(message, getApplicationContext(), 1);
                }
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
     * 刷新当前Activity
     */
    private void refreshActivity() {
        finish();
        startActivity(getIntent());
    }

}

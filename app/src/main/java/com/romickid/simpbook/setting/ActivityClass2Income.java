package com.romickid.simpbook.setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import java.util.ArrayList;

import com.romickid.simpbook.R;
import com.romickid.simpbook.database.CategoryDb;
import com.romickid.simpbook.database.CustomSQLiteOpenHelper;
import com.romickid.simpbook.database.SubcategoryDb;
import com.romickid.simpbook.util.Class1;
import com.romickid.simpbook.util.Class2;
import com.romickid.simpbook.util.SpinnerAdapterColor;

import static com.romickid.simpbook.util.Color.getListColorIds;
import static com.romickid.simpbook.util.Other.displayToast;

public class ActivityClass2Income extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAdd;

    private SpinnerAdapterColor spinnerAdapterColor;
    private AdapterClass2 adapterClass2;

    private SQLiteDatabase sqLiteDatabase;
    private CategoryDb class1Db;
    private SubcategoryDb class2Db;

    private ArrayList<Class2> listClass2s;
    private int class1Id;
    private Class1 class1;


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
        toolbar.setTitle("二级收入分类");

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
        adapterClass2 = new AdapterClass2(listClass2s,this);
        recyclerView.setAdapter(adapterClass2);
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
        class2Db = new SubcategoryDb(sqLiteDatabase);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        updateListClass2s();
    }


    // 数据相关
    /**
     * 更新二级分类所属的一级分类信息
     */
    private void updateClass1Id(){
        Intent intent = getIntent();
        class1Id = Integer.valueOf(intent.getStringExtra("class1Id"));
        class1 = class1Db.getCategoryListById(class1Id).get(0);
    }

    /**
     * 更新所有二级收入分类信息
     */
    private void updateListClass2s() {
        updateClass1Id();
        listClass2s = class2Db.subcategoryList(class1Id);
    }

    /**
     * 获取一个Class2实例
     *
     * @param name 名称
     * @param colorId 颜色id
     * @return 实例
     */
    private Class2 getClass2(String name, int colorId, int class1Id) {
        return new Class2(name, colorId, class1Id);
    }

    /**
     * 向数据库中保存数据
     */
    private String saveClass2(Class2 class2Save) {
        return class2Db.insertSubcategory(class2Save);
    }


    // 新增收入Class2相关
    /**
     * 构建"新增Class2"的Dialog
     *
     * @return dialog实例
     */
    private Dialog createDialogAdd() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        View viewRemarkDialog = View.inflate(this, R.layout.dialog_class2add, null);

        final EditText editText = viewRemarkDialog.findViewById(R.id.dclass2add_edittext);
        final TextView textView = viewRemarkDialog.findViewById(R.id.dclass2add_textview_class1);
        final Spinner spinnerColor = viewRemarkDialog.findViewById(R.id.dclass2add_spinner_color);

        textView.setText(class1.getName());
        spinnerColor.setAdapter(spinnerAdapterColor);

        builder.setTitle("新增二级收入分类");
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

                Class2 class2 = getClass2(name, colorId, class1Id);
                String message = saveClass2(class2);

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

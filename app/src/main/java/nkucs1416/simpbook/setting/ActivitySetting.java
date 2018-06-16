package nkucs1416.simpbook.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import nkucs1416.simpbook.R;

public class ActivitySetting extends AppCompatActivity {
    private Toolbar toolbar;

    private ImageView imageViewClassExpense;
    private ImageView imageViewClassIncome;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initFindById();
        initToolbar();
        initImageView();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.setting_toolbar);
        imageViewClassExpense = findViewById(R.id.setting_imageview_classexpense);
        imageViewClassIncome = findViewById(R.id.setting_imageview_classincome);
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
     * 初始化各类显示框
     */
    private void initImageView() {
        initImageViewClassExpense();
    }

    /**
     * 初始化"支出分类设置"信息
     */
    private void initImageViewClassExpense() {
        imageViewClassExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActivitySetting.this, ActivityClass1Expense.class);
                startActivity(intent);
            }
        });
    }

}

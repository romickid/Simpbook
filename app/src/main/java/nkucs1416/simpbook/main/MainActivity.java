package nkucs1416.simpbook.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.account.AccountActivity;
import nkucs1416.simpbook.record.RecordActivity;
import nkucs1416.simpbook.setting.SettingActivity;
import nkucs1416.simpbook.statement.StatementActivity;
import nkucs1416.simpbook.util.Date;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonRecord;
    private Button buttonAccount;
    private Button buttonCollection;
    private Button buttonStatement;
    private ImageView buttonSetting;

    private ImageView imageViewDay;
    private ImageView imageViewWeek;
    private ImageView imageViewMonth;
    private ImageView imageViewYear;
    private ImageView imageViewTopInfo;

    private TextView textViewDate;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFindById();
        initButton();
        initImageView();
        updateData();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        buttonRecord = findViewById(R.id.main_button_record);
        buttonAccount = findViewById(R.id.main_button_account);
        buttonStatement = findViewById(R.id.main_button_statement);
        buttonCollection = findViewById(R.id.main_button_collection);
        buttonSetting = findViewById(R.id.main_button_setting);

        imageViewDay = findViewById(R.id.main_imageview_info1);
        imageViewWeek = findViewById(R.id.main_imageview_info2);
        imageViewMonth = findViewById(R.id.main_imageview_info3);
        imageViewYear = findViewById(R.id.main_imageview_info4);
        imageViewTopInfo = findViewById(R.id.main_imageview_topinfo);

        textViewDate = findViewById(R.id.main_textview_date);
    }

    /**
     * 初始化各类按钮
     */
    private void initButton() {
        initButtonRecord();
        initButtonAccount();
        initButtonStatement();
        initButtonCollection();
        initButtonSetting();
    }

    /**
     * 初始化记录按钮
     */
    private void initButtonRecord() {
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                intent.putExtra("type","expense");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"账户"按钮
     */
    private void initButtonAccount() {
        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"流水"按钮
     */
    private void initButtonStatement() {
        buttonStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, StatementActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化"模板"按钮
     */
    private void initButtonCollection() {
        buttonCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                intent.putExtra("type","collection");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化设置按钮
     */
    private void initButtonSetting() {
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化各类显示框
     */
    private void initImageView() {
        initImageViewBasicInfo();
        initImageViewDay();
        initImageViewWeek();
        initImageViewMonth();
        initImageViewYear();
    }

    /**
     * 初始化顶部综合信息
     */
    private void initImageViewBasicInfo() {
        imageViewTopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, StatementActivity.class);
                intent.putExtra("filter","main_month");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本日收支信息
     */
    private void initImageViewDay() {
        imageViewDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, StatementActivity.class);
                intent.putExtra("filter","main_day");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本周收支信息
     */
    private void initImageViewWeek() {
        imageViewWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, StatementActivity.class);
                intent.putExtra("filter","main_week");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本月收支信息
     */
    private void initImageViewMonth() {
        imageViewMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, StatementActivity.class);
                intent.putExtra("filter","main_month");
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化本年收支信息
     */
    private void initImageViewYear() {
        imageViewYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, StatementActivity.class);
                intent.putExtra("filter","main_year");
                startActivity(intent);
            }
        });
    }


    // 更新数据相关
    /**
     * 更新数据
     */
    private void updateData() {
        updateDate();
    }

    /**
     * 更新日期
     */
    private void updateDate() {
        Date today = new Date();
        String strToday = today.getYear() + "/" + today.getMonth() + "/" + today.getDay() + "  " + today.getWeekOfDate();
        textViewDate.setText(strToday);
    }

}

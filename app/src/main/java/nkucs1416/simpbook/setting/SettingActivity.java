package nkucs1416.simpbook.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import nkucs1416.simpbook.R;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initFindById();
        initToolbar();
    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.setting_toolbar);
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
}

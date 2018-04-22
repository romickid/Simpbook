package nkucs1416.simpbook.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import nkucs1416.simpbook.R;

public class AccountActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initToolbar();
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.account_toolbar);
        toolbar.setTitle("账户");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
}

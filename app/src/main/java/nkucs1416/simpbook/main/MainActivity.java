package nkucs1416.simpbook.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.support.design.widget.FloatingActionButton;
import android.widget.Button;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.account.AccountActivity;
import nkucs1416.simpbook.record.RecordActivity;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonRecord;
    private Button buttonAccount;
    private Button buttonCollection;
    private Button buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFindById();
        initButtonRecord();
        initButtonAccount();
    }

    private void initFindById() {
        buttonRecord = findViewById(R.id.main_button_record);
        buttonAccount = findViewById(R.id.main_button_account);
    }

    private void initButtonRecord() {
        buttonRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            startActivity(intent);
            }
        });
    }

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
}

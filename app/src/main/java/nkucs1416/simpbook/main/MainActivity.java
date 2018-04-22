package nkucs1416.simpbook.main;

import android.accounts.Account;
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

    private FloatingActionButton button_record;
    private Button button_account;
    private Button button_collection;
    private Button button_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtonRecord();
        initButtonAccount();
    }

    private void initButtonRecord() {
        button_record = findViewById(R.id.main_button_record);

        button_record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            startActivity(intent);
            }
        });
    }

    private void initButtonAccount() {
        button_account = findViewById(R.id.main_button_account);

        button_account.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

}

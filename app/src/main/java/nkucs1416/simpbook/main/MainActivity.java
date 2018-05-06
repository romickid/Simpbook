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
import nkucs1416.simpbook.statement.StatementActivity;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonRecord;
    private Button buttonAccount;
    private Button buttonCollection;
    private Button buttonStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFindById();
        initButton();
    }

    private void initFindById() {
        buttonRecord = findViewById(R.id.main_button_record);
        buttonAccount = findViewById(R.id.main_button_account);
        buttonStatement = findViewById(R.id.main_button_statement);
        buttonCollection = findViewById(R.id.main_button_collection);
    }

    private void initButton() {
        initButtonRecord();
        initButtonAccount();
        initButtonStatement();
        initButtonCollection();
    }

    private void initButtonRecord() {
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                intent.putExtra("tabID","1");
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

    private void initButtonCollection() {
        buttonCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                intent.putExtra("tabID","0");
                startActivity(intent);
            }
        });
    }
}

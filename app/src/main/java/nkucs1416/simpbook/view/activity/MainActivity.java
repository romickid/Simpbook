package nkucs1416.simpbook.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.support.design.widget.FloatingActionButton;

import nkucs1416.simpbook.R;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton button_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreateButtonRecord();
    }

    private void onCreateButtonRecord() {
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
}

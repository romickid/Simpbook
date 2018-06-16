package nkucs1416.simpbook.edit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.fragments.FragmentExpense;
import nkucs1416.simpbook.fragments.FragmentIncome;
import nkucs1416.simpbook.fragments.FragmentTransfer;

public class ActivityEdit extends AppCompatActivity implements
        FragmentIncome.OnFragmentInteractionListener,
        FragmentExpense.OnFragmentInteractionListener,
        FragmentTransfer.OnFragmentInteractionListener {

    private Toolbar toolbar;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initFindById();
        initToolbar();
        initFragment();
    }


    // Fragment相关
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    // 初始化相关
    /**
     * 初始化Id
     */
    private void initFindById() {
        toolbar = findViewById(R.id.edit_toolbar);
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
     * 初始化Fragment
     */
    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setCurrentFragment();
    }

    /**
     * 设置显示的Fragment页面(支出/收入/转账)
     */
    private void setCurrentFragment() {
        Intent intent = getIntent();
        String currentType = intent.getStringExtra("RecordType");
        switch (currentType) {
            case "Expense":
                fragment = new FragmentExpense();
                fragmentTransaction.replace(R.id.edit_framelayout, fragment);
                fragmentTransaction.commit();
                break;
            case "Income":
                fragment = new FragmentIncome();
                fragmentTransaction.replace(R.id.edit_framelayout, fragment);
                fragmentTransaction.commit();
                break;
            case "Transfer":
                fragment = new FragmentTransfer();
                fragmentTransaction.replace(R.id.edit_framelayout, fragment);
                fragmentTransaction.commit();
                break;
        }
    }

}

package com.romickid.simpbook.fragments.Collection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.romickid.simpbook.R;
import com.romickid.simpbook.fragments.FragmentExpense;
import com.romickid.simpbook.fragments.FragmentIncome;
import com.romickid.simpbook.fragments.FragmentTransfer;

public class ActivityCollectionAdd extends AppCompatActivity implements
        FragmentIncome.OnFragmentInteractionListener,
        FragmentExpense.OnFragmentInteractionListener,
        FragmentTransfer.OnFragmentInteractionListener,
        FragmentCollectionAdd.OnFragmentInteractionListener {

    private Toolbar toolbar;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectionadd);

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
        toolbar = findViewById(R.id.collectionadd_toolbar);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
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
        String currentType = intent.getStringExtra("CollectionAddType");
        switch (currentType) {
            case "Expense":
                fragment = new FragmentExpense();
                fragmentTransaction.replace(R.id.collectionadd_framelayout, fragment);
                fragmentTransaction.commit();
                break;
            case "Income":
                fragment = new FragmentIncome();
                fragmentTransaction.replace(R.id.collectionadd_framelayout, fragment);
                fragmentTransaction.commit();
                break;
            case "Transfer":
                fragment = new FragmentTransfer();
                fragmentTransaction.replace(R.id.collectionadd_framelayout, fragment);
                fragmentTransaction.commit();
                break;
            case "Default":
                fragment = new FragmentCollectionAdd();
                fragmentTransaction.replace(R.id.collectionadd_framelayout, fragment);
                fragmentTransaction.commit();
                break;
        }
    }

}

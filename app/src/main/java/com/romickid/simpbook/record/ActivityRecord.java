package com.romickid.simpbook.record;

import java.util.*;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;

import com.romickid.simpbook.R;
import com.romickid.simpbook.fragments.Collection.FragmentCollection;
import com.romickid.simpbook.fragments.FragmentExpense;
import com.romickid.simpbook.fragments.FragmentIncome;
import com.romickid.simpbook.fragments.FragmentTransfer;

public class ActivityRecord extends AppCompatActivity implements
        FragmentCollection.OnFragmentInteractionListener,
        FragmentIncome.OnFragmentInteractionListener,
        FragmentExpense.OnFragmentInteractionListener,
        FragmentTransfer.OnFragmentInteractionListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> listFragments;
    private ArrayList<String> listIndicators;
    private PagerAdapterRecord pagerAdapter;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initFindById();
        initToolbar();
        initViewpager();
        initTabLayout();
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
        toolbar = findViewById(R.id.record_toolbar);
        tabLayout = findViewById(R.id.record_tablayout);
        viewPager = findViewById(R.id.record_viewpager);
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
     * 初始化TabLayout
     */
    private void initTabLayout(){
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化Viewpager
     */
    private void initViewpager(){
        listFragments = new ArrayList<>();
        listIndicators = new ArrayList<>();

        listIndicators.add("模板");
        listFragments.add(FragmentCollection.newInstance());
        listIndicators.add("支出");
        listFragments.add(FragmentExpense.newInstance());
        listIndicators.add("收入");
        listFragments.add(FragmentIncome.newInstance());
        listIndicators.add("转账");
        listFragments.add(FragmentTransfer.newInstance());

        pagerAdapter = new PagerAdapterRecord(getSupportFragmentManager());
        pagerAdapter.setFragments(listFragments);
        pagerAdapter.setIndicators(listIndicators);

        viewPager.setAdapter(pagerAdapter);

        setCurrentTab();
    }


    // 其他
    /**
     * 设置默认显示的tab页面(模板/支出/收入/转账)
     */
    private void setCurrentTab() {
        Intent intent = getIntent();
        String currentTab = intent.getStringExtra("RecordType");
        switch (currentTab) {
            case "Collection":
                viewPager.setCurrentItem(0);
                break;
            case "Expense":
                viewPager.setCurrentItem(1);
                break;
            case "Income":
                viewPager.setCurrentItem(2);
                break;
            case "Transfer":
                viewPager.setCurrentItem(3);
                break;
            default:
                viewPager.setCurrentItem(1);
        }
    }

}

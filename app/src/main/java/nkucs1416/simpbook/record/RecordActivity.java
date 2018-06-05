package nkucs1416.simpbook.record;

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

import nkucs1416.simpbook.R;
import nkucs1416.simpbook.fragments.CollectionFragment;
import nkucs1416.simpbook.fragments.ExpenseFragment;
import nkucs1416.simpbook.fragments.IncomeFragment;
import nkucs1416.simpbook.fragments.TranferFragment;

public class RecordActivity extends AppCompatActivity implements
        CollectionFragment.OnFragmentInteractionListener,
        IncomeFragment.OnFragmentInteractionListener,
        ExpenseFragment.OnFragmentInteractionListener,
        TranferFragment.OnFragmentInteractionListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> listFragments;
    private ArrayList<String> listIndicators;
    private RecordPagerAdapter pagerAdapter;


    // Activity相关
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initFindById();
        initToolbar();
        initViewpager();
        initTablayout();
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
     * 初始化Tablayout
     */
    private void initTablayout(){
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化Viewpager
     */
    private void initViewpager(){
        listFragments = new ArrayList<>();
        listIndicators = new ArrayList<>();
        String s = "a"; // todo

        listIndicators.add("模板");
        listFragments.add(CollectionFragment.newInstance(s,s));
        listIndicators.add("支出");
        listFragments.add(ExpenseFragment.newInstance(s,s));
        listIndicators.add("收入");
        listFragments.add(IncomeFragment.newInstance(s,s));
        listIndicators.add("转账");
        listFragments.add(TranferFragment.newInstance(s,s));

        pagerAdapter = new RecordPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setFragments(listFragments);
        pagerAdapter.setIndicators(listIndicators);

        viewPager.setAdapter(pagerAdapter);

        setCurrentTab();
    }


    // 其他
    /**
     * 设置默认显示的tab页面(模板/支出)
     */
    private void setCurrentTab() {
        Intent intent = getIntent();
        String currentTab = intent.getStringExtra("tabID");
        switch (currentTab) {
            case "1":
                viewPager.setCurrentItem(1);
                break;
            case "0":
            default:
                viewPager.setCurrentItem(0);
        }
    }
}




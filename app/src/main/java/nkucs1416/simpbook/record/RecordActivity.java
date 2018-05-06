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

import nkucs1416.simpbook.R;

public class RecordActivity extends AppCompatActivity implements
        RecordCollectionFragment.OnFragmentInteractionListener,
        RecordIncomeFragment.OnFragmentInteractionListener,
        RecordExpenseFragment.OnFragmentInteractionListener,
        RecordTranferFragment.OnFragmentInteractionListener {
    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private ArrayList<Fragment> listFragments;
    private ArrayList<String> listIndicators;
    private RecordPagerAdapter pageradapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initFindById();
        initToolbar();
        initViewpager();
        initTablayout();
    }

    private void initFindById() {
        toolbar = findViewById(R.id.record_toolbar);
        tablayout = findViewById(R.id.record_tablayout);
        viewpager = findViewById(R.id.record_viewpager);
    }

    private void initToolbar(){
        toolbar.setTitle("记录");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initTablayout(){
        tablayout.setupWithViewPager(viewpager);
    }

    private void initViewpager(){
        listFragments = new ArrayList<>();
        listIndicators = new ArrayList<>();
        String s = "a"; // todo

        listIndicators.add("模板");
        listFragments.add(RecordCollectionFragment.newInstance(s,s));
        listIndicators.add("支出");
        listFragments.add(RecordExpenseFragment.newInstance(s,s));
        listIndicators.add("收入");
        listFragments.add(RecordIncomeFragment.newInstance(s,s));
        listIndicators.add("转账");
        listFragments.add(RecordTranferFragment.newInstance(s,s));

        pageradapter = new RecordPagerAdapter(getSupportFragmentManager());
        pageradapter.setFragments(listFragments);
        pageradapter.setIndicators(listIndicators);

        viewpager.setAdapter(pageradapter);

        setCurrentTab();
    }


    private void setCurrentTab() {
        Intent intent = getIntent();
        String currentTab = intent.getStringExtra("tabID");
        switch (currentTab) {
            case "1":
                viewpager.setCurrentItem(1);
                break;
            case "0":
            default:
                viewpager.setCurrentItem(0);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}




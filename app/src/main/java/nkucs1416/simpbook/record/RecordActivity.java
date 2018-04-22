package nkucs1416.simpbook.record;

import java.util.*;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import nkucs1416.simpbook.R;

public class RecordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private ArrayList<Fragment> array_fragments = new ArrayList<>();
    private RecordPagerAdapter myPagerAdapter = new RecordPagerAdapter(getSupportFragmentManager());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initToolbar();
        initTablayout();
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.record_toolbar);
        toolbar.setTitle("记录");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initTablayout(){
        tablayout = findViewById(R.id.record_tablayout);
        // tablayout.setupWithViewPager(viewpager);
    }
}




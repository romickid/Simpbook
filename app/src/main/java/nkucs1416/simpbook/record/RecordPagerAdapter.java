package nkucs1416.simpbook.record;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


class RecordPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> listFragments;
    private ArrayList<String> listIndicators;

    public void setFragments(ArrayList<Fragment> mFragments) {
        listFragments = mFragments;
    }

    public void setIndicators(ArrayList<String> mIndicators) {
        listIndicators = mIndicators;
    }


    public RecordPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int mPosition) {
        Fragment fragment = listFragments.get(mPosition);
        return fragment;
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int mPosition) {
        return listIndicators.get(mPosition);
    }
}
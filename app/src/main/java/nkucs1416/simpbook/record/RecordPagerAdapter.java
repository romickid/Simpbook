package nkucs1416.simpbook.record;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


class RecordPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragmentList = fragments;
    }

    public RecordPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
package nkucs1416.simpbook.record;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


class RecordPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> listFragments;
    private ArrayList<String> listIndicators;

    // PagerAdapter相关
    public RecordPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 获取tab页面的实例
     * @param position
     * @return 实例
     */
    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    /**
     * 获取tab页面的数量
     * @return 数量
     */
    @Override
    public int getCount() {
        return listFragments.size();
    }

    /**
     * 获取tab页面的标题名称
     * @param position tabId
     * @return 标题名称
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return listIndicators.get(position);
    }


    // 其他
    /**
     * 接收从Activity传入的listFragments变量
     * @param tlistFragments 传入的listFragments
     */
    public void setFragments(ArrayList<Fragment> tlistFragments) {
        listFragments = tlistFragments;
    }

    /**
     * 接收从Activity传入的listIndicators变量
     * @param tlistIndicators 传入的listIndicators
     */
    public void setIndicators(ArrayList<String> tlistIndicators) {
        listIndicators = tlistIndicators;
    }

}
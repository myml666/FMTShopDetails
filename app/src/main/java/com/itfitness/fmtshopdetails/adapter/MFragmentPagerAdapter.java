package com.itfitness.fmtshopdetails.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Description: java类作用描述 ：主页Fragment适配器
 * @Author: 作者名：lml
 * @CreateDate: 2018/12/4 11:17
 * @UpdateUser: 更新者：
 * @UpdateDate: 2018/12/4 11:17
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class MFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public List<Fragment> getmFragments() {
        return mFragments;
    }

    public MFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;

    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

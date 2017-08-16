

package com.zhonghe.lib_base.baseui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.Utilm;

import java.util.List;


public class FragmentTabAdapter extends FragmentPagerAdapter {
	private List<FragmentTab> mFragmentTabs;
	
    public FragmentTabAdapter(FragmentManager fm, List<FragmentTab> fragments) {
        super(fm);
        this.mFragmentTabs = fragments;
    }

    @Override
    public BaseUIFragment getItem(int position) {
    	if(UtilList.isNotEmpty(mFragmentTabs)) {
    		return mFragmentTabs.get(position).getFragment();
    	}
    	
    	return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(UtilList.isNotEmpty(mFragmentTabs)) {
            return mFragmentTabs.get(position).getTitle();
        }

        return null;
    }

	@Override
    public int getCount() {
        return UtilList.getCount(mFragmentTabs);
    }
	
	public void refresh(List<FragmentTab> fragments) {
		this.mFragmentTabs = fragments;
		notifyDataSetChanged();
	}
    
}

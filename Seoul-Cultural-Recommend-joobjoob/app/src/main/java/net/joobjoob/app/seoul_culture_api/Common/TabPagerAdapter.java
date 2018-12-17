package net.joobjoob.app.seoul_culture_api.Common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.joobjoob.app.seoul_culture_api.CulturalPickUp.MyCockFragment;
import net.joobjoob.app.seoul_culture_api.CulturalRecommend.RecommendFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return new RecommendFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new MyCockFragment();
            case 3:
                return new MyPageFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
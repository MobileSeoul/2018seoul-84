package net.joobjoob.app.seoul_culture_api;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.joobjoob.app.seoul_culture_api.Common.TabPagerAdapter;
import net.joobjoob.app.seoul_culture_api.Common.BottomNavigationViewHelper;

// comment : 메인
public class MainActivity extends AppCompatActivity {

    // 뷰페이저
    ViewPager pager;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        final BottomNavigationView navigationView = findViewById(R.id.a_main_bnv);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);

        BottomNavigationViewHelper.removeShiftMode(navigationView);

        pager = findViewById(R.id.viewPager_tab);

        pager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), 4));

        pager.setOffscreenPageLimit(4); // 안보이는 페이지 로딩해 놓을 갯수

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }

                navigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    pager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_2:
                    pager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_3:
                    pager.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_4:
                    pager.setCurrentItem(3, false);
                    return true;
            }
            return false;
        }
    };

}

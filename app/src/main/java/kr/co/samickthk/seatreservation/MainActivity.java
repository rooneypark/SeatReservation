package kr.co.samickthk.seatreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import kr.co.samickthk.seatreservation.adapter.ContentsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Context mContext;
    private ViewPager mViewPager;
    private ContentsPagerAdapter mContentsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        mTabLayout = (TabLayout) findViewById(R.id.layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText("예약"));
        mTabLayout.addTab(mTabLayout.newTab().setText("조회"));
        mTabLayout.addTab(mTabLayout.newTab().setText("설정"));

        mViewPager = (ViewPager) findViewById(R.id.pager_content);

        mContentsPagerAdapter = new ContentsPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mContentsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View createTabView(String tabName) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView txtName = (TextView) tabView.findViewById(R.id.txt_name);
        txtName.setText(tabName);
        return tabView;
    }
}

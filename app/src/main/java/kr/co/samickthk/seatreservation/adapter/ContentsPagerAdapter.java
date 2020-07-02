package kr.co.samickthk.seatreservation.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import kr.co.samickthk.seatreservation.view.ListFragment;
import kr.co.samickthk.seatreservation.view.ReservationFragment;
import kr.co.samickthk.seatreservation.view.SettingFragment;

public class ContentsPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;

    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ReservationFragment reservationFragment = ReservationFragment.newInstance("param1", "param2");
                return reservationFragment;
            case 1:
                ListFragment listFragment = ListFragment.newInstance("param1", "param2");
                return listFragment;
            case 2:
                SettingFragment settingFragment = SettingFragment.newInstance("param1", "param2");
                return settingFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}

package com.ahmed.androiddeveloperpracticeproject.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ahmed.androiddeveloperpracticeproject.ui.fragments.FragmentIqLeaders;
import com.ahmed.androiddeveloperpracticeproject.ui.fragments.FragmentLearningLeaders;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
            default:
                return new FragmentLearningLeaders();

            case 1:
                return new FragmentIqLeaders();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}

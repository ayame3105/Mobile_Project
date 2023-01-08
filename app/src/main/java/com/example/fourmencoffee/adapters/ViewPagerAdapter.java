package com.example.fourmencoffee.adapters;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fourmencoffee.HobbyFragment;
import com.example.fourmencoffee.personal.PersonalFragment;
import com.example.fourmencoffee.PriceFragment;
import com.example.fourmencoffee.classify.ClassifyFragment;
import com.example.fourmencoffee.home.HomeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ClassifyFragment();
            case 2:
                return new PriceFragment();
            case 3:
                return new HobbyFragment();
            case 4:
                return new PersonalFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Classify";
                break;
            case 2:
                title = "Price";
                break;
            case 3:
                title = "Hobby";
                break;
            case 4:
                title = "Personal";
                break;
        }
        return title;
    }

}

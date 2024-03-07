package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class viewPageAdapter extends FragmentStatePagerAdapter {
    public viewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new fragment_thongso();
           case 1:
               return new fragment_chedo3();
           case 2:
               return new fragment_chucnang();
           default:
               return new fragment_thongso();
       }



    }

    @Override
    public int getCount() //trave so luong tab
     {
        return 3;
    }
}

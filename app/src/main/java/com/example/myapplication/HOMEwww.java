package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HOMEwww extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homewww);
        anhxa();
        setUpViewPager();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homee:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.gearr:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.nutnhann:
                        mViewPager.setCurrentItem(2);

                        break;

                }
                return true;
            }
        });
    }
    private void setUpViewPager(){
        viewPageAdapter viewPageAdapter = new viewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPageAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.homee).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.gearr).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.nutnhann).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void anhxa()
    {
        navigationView = findViewById(R.id.bottombar);
        mViewPager = findViewById(R.id.viewpager);
    }

}
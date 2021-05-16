package com.app.manoranjan.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.manoranjan.R;
import com.app.manoranjan.fragments.FavouriteFragment;
import com.app.manoranjan.fragments.HomeFragment;
import com.app.manoranjan.fragments.SearchFragment;
import com.app.manoranjan.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Field Declaration
    BottomNavigationView bottomNavigationView;
    Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.nav_home, new HomeFragment());
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragmentMap.get(R.id.nav_home)).commit();
        setBottomNavigationViewOnClickListener();
    }

    private void setBottomNavigationViewOnClickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (bottomNavigationView.getSelectedItemId() == itemId)
                    return false;

                switch (itemId) {
                    case R.id.nav_home:
                        if (fragmentMap.get(R.id.nav_home) == null)
                            fragmentMap.put(R.id.nav_home, new HomeFragment());
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragmentMap.get(R.id.nav_home)).commit();
                        break;

                    case R.id.nav_search:
                        if (fragmentMap.get(R.id.nav_search) == null)
                            fragmentMap.put(R.id.nav_search, new SearchFragment());
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragmentMap.get(R.id.nav_search)).commit();
                        break;

                    case R.id.nav_favourite:
                        if (fragmentMap.get(R.id.nav_favourite) == null)
                            fragmentMap.put(R.id.nav_favourite, new FavouriteFragment());
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragmentMap.get(R.id.nav_favourite)).commit();
                        break;

                    case R.id.nav_settings:
                        if (fragmentMap.get(R.id.nav_settings) == null)
                            fragmentMap.put(R.id.nav_settings, new SettingsFragment());
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragmentMap.get(R.id.nav_settings)).commit();
                        break;
                }
                return true;
            }
        });
    }
}
package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.FragmentMasterListRecipe;
import com.example.android.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    //    public static boolean isTablet = false;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean mTabletMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.tablet_view) != null) {
            mTabletMode = true;
        } else mTabletMode = false;

        if (mTabletMode) {
            FragmentMasterListRecipe fragmentMasterListRecipe = new FragmentMasterListRecipe();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.tablet_view, fragmentMasterListRecipe)
                    .commit();
        } else if (!mTabletMode) {

            FragmentMasterListRecipe fragmentMasterListRecipe = new FragmentMasterListRecipe();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.phone_view, fragmentMasterListRecipe)
                    .commit();
        }
    }
}
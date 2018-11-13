package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.bakingapp.Fragments.FragmentMasterListRecipe;
import com.example.android.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    //    public static boolean isTablet = false;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean twoPane;
    FragmentMasterListRecipe fragmentMasterListRecipe;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private final String FRAGMENT_RECIPE_LIST = "fragmentMasterRecipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View tablet_view = findViewById(R.id.tablet_view);
        View phone_view = findViewById(R.id.phone_view);

        if (savedInstanceState != null) {
            fragmentMasterListRecipe = (FragmentMasterListRecipe)
                    getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPE_LIST);
        } else {
            fragmentMasterListRecipe = new FragmentMasterListRecipe();
            if (tablet_view != null) {
                twoPane = true;
                fragmentMasterListRecipe.setTwoPane(twoPane);
                fragmentManager.beginTransaction()
                        .add(R.id.tablet_view, fragmentMasterListRecipe)
                        .commit();
            } else if (phone_view != null) {
                twoPane = false;
                fragmentMasterListRecipe.setTwoPane(twoPane);
                fragmentManager.beginTransaction()
                        .add(R.id.phone_view, fragmentMasterListRecipe)
                        .commit();
            }
        }
    }
}

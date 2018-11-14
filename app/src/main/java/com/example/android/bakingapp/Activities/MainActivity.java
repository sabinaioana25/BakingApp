package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.bakingapp.Fragments.FragmentMasterListRecipe;
import com.example.android.bakingapp.R;

public class MainActivity extends AppCompatActivity {

// --Commented out by Inspection START (11/14/2018 8:01 PM):
//    //    public static boolean isTablet = false;
//    private final String LOG_TAG = MainActivity.class.getSimpleName();
// --Commented out by Inspection STOP (11/14/2018 8:01 PM)
    FragmentMasterListRecipe fragmentMasterListRecipe;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View tablet_view = findViewById(R.id.tablet_view);
        View phone_view = findViewById(R.id.phone_view);

        if (savedInstanceState != null) {
            String FRAGMENT_RECIPE_LIST = "fragmentMasterRecipe";
            fragmentMasterListRecipe = (FragmentMasterListRecipe)
                    getSupportFragmentManager().findFragmentByTag(FRAGMENT_RECIPE_LIST);
        } else {
            fragmentMasterListRecipe = new FragmentMasterListRecipe();
            boolean twoPane;
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

package com.example.meditena.Buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.meditena.Admin.AdminAddCategoryActivity;
import com.example.meditena.R;
import com.example.meditena.ui.Cart.CartFragment;
import com.example.meditena.ui.favorite.FavoriteFragment;
import com.example.meditena.ui.home.HomeFragment;
import com.example.meditena.ui.search.SearchFragment;
import com.example.meditena.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  FragmentManager fragmentManager =  getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, new CartFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        // Passing each menu ID as a set of I;l,lds because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_notifications,R.id.navigation_settings,
                R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.navigation_cart)
        {

            fragment = new CartFragment();
        }
        else if (id == R.id.navigation_favorite)
        {
            fragment = new FavoriteFragment();
        }
        else if (id == R.id.navigation_home)
        {
            fragment = new HomeFragment();

        }
        else if (id == R.id.navigation_settings)
        {
            fragment = new SettingsFragment();

        }
        else if (id == R.id.fab)
        {
            fragment = new CartFragment();

        }
        else if (id == R.id.navigation_search)
        {

            fragment = new SearchFragment();
        }
        if (fragment != null){
            fragment.getView().setFocusableInTouchMode(true);
            fragment.getView().requestFocus();
            fragment.getView().setOnKeyListener( new View.OnKeyListener()
            {
                @Override
                public boolean onKey( View v, int keyCode, KeyEvent event )
                {
                    if( keyCode == KeyEvent.KEYCODE_BACK )
                    {
                        return true;
                    }
                    return false;
                }
            } );
            FragmentManager fragmentManager =  getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }

        return true;
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}

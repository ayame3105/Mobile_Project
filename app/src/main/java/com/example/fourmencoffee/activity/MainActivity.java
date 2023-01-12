package com.example.fourmencoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fourmencoffee.adapters.ViewPagerAdapter;
import com.example.fourmencoffee.leftnagivationview.CategoryFragment;
import com.example.fourmencoffee.classify.ClassifyFragment;
import com.example.fourmencoffee.home.HomeFragment;
import com.example.fourmencoffee.leftnagivationview.CategoryFragment;
import com.example.fourmencoffee.leftnagivationview.MyCartsFragment;
import com.example.fourmencoffee.leftnagivationview.MyOrdersFragment;
import com.example.fourmencoffee.leftnagivationview.NewProductsFragment;
import com.example.fourmencoffee.leftnagivationview.OffersFragment;
import com.example.fourmencoffee.leftnagivationview.ProfileFragment;
import com.example.fourmencoffee.personal.PersonalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private Button search_button;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;

    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_CLASSIFY = 2;
    private static final int FRAGMENT_PROFILE = 3;
    private static final int FRAGMENT_CATEGORY = 4;
    private static final int FRAGMENT_OFFERS= 5;
    private static final int FRAGMENT_NEW_PRODUCTS = 6;
    private static final int FRAGMENT_MY_ORDERS = 7;
    private static final int FRAGMENT_MY_CARTS = 8;
    private static final int FRAGMENT_PRICE = 9;
    private static final int FRAGMENT_HOBBY =10;
    private static final int FRAGMENT_PERSONAL = 11;

    private int currentFragment = FRAGMENT_HOME;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById (R. id. drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //Xu ly left navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.setCheckedItem(R.id.nav_home);
        bottomNavigationView.getMenu().findItem(R.id.home_tab).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_tab:
//                        viewPager.setCurrentItem(0);
                        openHomeFragment();
                        navigationView.setCheckedItem(R.id.nav_home);
                        break;
                    case R.id.classify_tab:
//                        viewPager.setCurrentItem(1);
                        openClassifyFragment();
                        navigationView.setCheckedItem(R.id.nav_classify);
                        break;
                    case R.id.price_tab:
                        openPriceFragment();
//                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.hobby_tab:
                        openHobbyFragment();
//                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.personal_tab:
                        openPersonalFragment();
//                        viewPager.setCurrentItem(4);
                        navigationView.setCheckedItem(R.id.nav_profile);
                        break;
                }
                return true;
            }
        });






    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.relative_layout_main, fragment);

        fragmentTransaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.nav_home){
            openHomeFragment();
            bottomNavigationView.getMenu().findItem(R.id.home_tab).setChecked(true);
        }
        else if (id == R.id.nav_classify){
            openClassifyFragment();
            bottomNavigationView.getMenu().findItem(R.id.classify_tab).setChecked(true);
        }
        else if (id == R.id.nav_profile){
            openProfileFragment();
            bottomNavigationView.getMenu().findItem(R.id.personal_tab).setChecked(true);
        }
        else if (id == R.id.nav_category){
            openCategoryFragment();
        }
        else if (id == R.id.nav_offers){
            openOffersFragment();
        }
        else if (id == R.id.nav_new_product){
            openNewProductsFragment();
        }
        else if (id == R.id.nav_my_orders){
            openMyOrdersFragment();
        }
        else if (id == R.id.nav_my_cart){
            openMyCartsFragment();
        }
        return true;
    }

    private void openHomeFragment(){
        if (currentFragment != FRAGMENT_HOME){
            replaceFragment(new HomeFragment());
            currentFragment = FRAGMENT_HOME;
        }
    }
    private void openClassifyFragment(){
        if (currentFragment != FRAGMENT_CLASSIFY){
            replaceFragment(new ClassifyFragment());
            currentFragment = FRAGMENT_CLASSIFY;
        }
    }
    private void openProfileFragment(){
        if (currentFragment != FRAGMENT_PROFILE){
            replaceFragment(new ProfileFragment());
            currentFragment = FRAGMENT_PROFILE;
        }
    }
    private void openCategoryFragment(){
        if (currentFragment != FRAGMENT_CATEGORY){
            replaceFragment(new CategoryFragment());
            currentFragment = FRAGMENT_CATEGORY;
        }
    }
    private void openOffersFragment(){
        if (currentFragment != FRAGMENT_OFFERS){
            replaceFragment(new OffersFragment());
            currentFragment = FRAGMENT_OFFERS;
        }
    }
    private void openNewProductsFragment(){
        if (currentFragment != FRAGMENT_NEW_PRODUCTS){
            replaceFragment(new NewProductsFragment());
            currentFragment = FRAGMENT_NEW_PRODUCTS;
        }
    }
    private void openMyOrdersFragment(){
        if (currentFragment != FRAGMENT_MY_ORDERS){
            replaceFragment(new MyOrdersFragment());
            currentFragment = FRAGMENT_MY_ORDERS;
        }
    }
    private void openMyCartsFragment(){
        if (currentFragment != FRAGMENT_MY_CARTS){
            replaceFragment(new MyCartsFragment());
            currentFragment = FRAGMENT_MY_CARTS;
        }
    }
    private void openPriceFragment(){
        if (currentFragment != FRAGMENT_PRICE){
            replaceFragment(new PriceFragment());
            currentFragment = FRAGMENT_PRICE;
        }
    }
    private void openHobbyFragment(){
        if (currentFragment != FRAGMENT_HOBBY){
            replaceFragment(new HobbyFragment());
            currentFragment = FRAGMENT_HOBBY;
        }
    }
    private void openPersonalFragment(){
        if (currentFragment != FRAGMENT_PERSONAL){
            replaceFragment(new PersonalFragment());
            currentFragment = FRAGMENT_PERSONAL;
        }
    }



}
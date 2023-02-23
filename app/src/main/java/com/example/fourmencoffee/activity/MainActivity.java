package com.example.fourmencoffee.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fourmencoffee.PriceFragment;
import com.example.fourmencoffee.R;
import com.example.fourmencoffee.leftnagivationview.CategoryFragment;
import com.example.fourmencoffee.classify.ClassifyFragment;
import com.example.fourmencoffee.home.HomeFragment;
import com.example.fourmencoffee.leftnagivationview.MyCartsFragment;
import com.example.fourmencoffee.leftnagivationview.MyOrdersFragment;
import com.example.fourmencoffee.leftnagivationview.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    public static final int MY_REQUEST_CODE = 10;

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private Button search_button;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    private final ProfileFragment profileFragment = new ProfileFragment();

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK)
                            {
                                Intent intent = result.getData();
                                if (intent == null)
                                    return;
                                Uri uri = intent.getData();
                                profileFragment.setUri(uri);
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media
                                            .getBitmap(getContentResolver(), uri);
                                    profileFragment.setBitmapImageView(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
    private ImageView imgAvatar;
    private TextView tv_name, tv_email;

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
        InitUi();
        showUserInformation();
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
    //                    viewPager.setCurrentItem(2);
                        break;
//                    case R.id.hobby_tab:
//                        openHobbyFragment();
////                        viewPager.setCurrentItem(3);
//                        break;
                    case R.id.personal_tab:
                        openProfileFragment();
 //                       viewPager.setCurrentItem(4);
                        navigationView.setCheckedItem(R.id.nav_profile);
                        break;
                }
                return true;
            }
        });






    }

    private void InitUi(){
        imgAvatar = navigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        tv_name = navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        tv_email = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
    }

    public void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
            return;
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        if (name == null)
            tv_name.setVisibility(View.GONE);
        else
        {
            tv_name.setVisibility(View.VISIBLE);
            tv_name.setText(name);
        }
        tv_email.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.black_profile_avt).into(imgAvatar);

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
//        else if (id == R.id.nav_offers){
//            openOffersFragment();
//        }
//        else if (id == R.id.nav_new_product){
//            openNewProductsFragment();
//        }
        else if (id == R.id.nav_my_orders){
            openMyOrdersFragment();
        }
        else if (id == R.id.nav_my_cart){
            openMyCartsFragment();
        }
        else if (id == R.id.nav_log_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
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
            replaceFragment(profileFragment);
            currentFragment = FRAGMENT_PROFILE;
        }
    }
    private void openCategoryFragment(){
        if (currentFragment != FRAGMENT_CATEGORY){
            replaceFragment(new CategoryFragment());
            currentFragment = FRAGMENT_CATEGORY;
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openGallery();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }
}
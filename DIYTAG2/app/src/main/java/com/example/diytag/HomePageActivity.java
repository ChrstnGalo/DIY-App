package com.example.diytag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ProductFragment productFragment = new ProductFragment();
    BalanceFragment balanceFragment = new BalanceFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    MeFragment meFragment = new MeFragment();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getWindow().setStatusBarColor(ContextCompat.getColor(HomePageActivity.this, R.color.light_black));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId()== R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                }
                if (item.getItemId()== R.id.product) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, productFragment).commit();
                    return true;
                }
                if (item.getItemId()== R.id.balance) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, balanceFragment).commit();
                    return true;
                }
                if (item.getItemId()== R.id.me) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, meFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}

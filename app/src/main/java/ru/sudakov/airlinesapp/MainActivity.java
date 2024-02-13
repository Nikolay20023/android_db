package ru.sudakov.airlinesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.sudakov.airlinesapp.fragments.AboutFragment;
import ru.sudakov.airlinesapp.fragments.BuyFragment;
import ru.sudakov.airlinesapp.fragments.CommentsFragment;
import ru.sudakov.airlinesapp.fragments.ProfileFragment;
import ru.sudakov.airlinesapp.fragments.TimetableFragment;

public class MainActivity extends AppCompatActivity {
    public static final String URL_DB = "jdbc:postgresql://37.128.206.12:5432/vadim_db";

    public static final String USERNAME = "markn";

    public static final String PASSWORD = "markn";

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navAbout) {
                loadFragment(new AboutFragment());
            } else if (itemId == R.id.navBuy) {
                loadFragment(new BuyFragment());
            } else if (itemId == R.id.navTimetable) {
                loadFragment(new TimetableFragment());
            } else if (itemId == R.id.navComments) {
                loadFragment(new CommentsFragment());
            } else if (itemId == R.id.navProfile) {
                loadFragment(new ProfileFragment());
            }
            return true;
        });
        loadFragment(new AboutFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
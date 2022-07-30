package com.sharkit.nextmonday.main_menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.NextMondayActivity;
import com.sharkit.nextmonday.R;

import java.util.Calendar;

public class NavigationMenu extends AppCompatActivity {

    private static Context context;

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar_core);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_settings_24);
        toolbar.setOverflowIcon(drawable);

        context = getApplicationContext();
    }

    @SuppressLint({"NonConstantResourceId", "SimpleDateFormat"})
    public void onMenuItemClick(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.weight_item:
//                navController.navigate(R.id.nav_cal_weight);
                break;
            case R.id.calendar_item:
//                navController.navigate(R.id.nav_cal_calendar);
                break;
            case R.id.ration_item:
//                Bundle bundle = new Bundle();
//                bundle.putString(FRAGMENT_RATION_DATE, new SimpleDateFormat(SHOW_DATE_FORMAT).format(Calendar.getInstance().getTimeInMillis()));
//                navController.navigate(R.id.nav_cal_ration, bundle);
                break;
            case R.id.main_item:
//                navController.navigate(R.id.nav_calculator_main);
                break;
            case R.id.calculator_item:
//                DrawerLayout drawer = findViewById(R.id.drawer_layout);
//                navController.navigate(R.id.nav_calculator_main);
//                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.exit_item:
                exit();
                break;
            case R.id.setting_item:
//                navController.navigate(R.id.nav_settings);
                break;
            case R.id.share_item:
                share();
                break;
            case R.id.rating_item:
                rating();
                break;
        }
    }

    public void diaryMenuClickListener(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.item_diary_calendar:
                navController.navigate(R.id.navigation_diary_calendar);
                break;
            case R.id.item_diary_list_of_task:
                navController.navigate(R.id.navigation_diary_notate);
                break;
            case R.id.item_diary_main:
                listTarget();
                navController.navigate(R.id.navigation_diary_main);
                break;
        }
    }

    private void rating() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void listTarget() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
//        Bundle bundle = new Bundle();
//        bundle.putLong(DATE_FOR_MAIN_DIARY_LIST, Calendar.getInstance().getTimeInMillis());
    }

    private void exit() {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(NavigationMenu.this, gso);
        googleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(NavigationMenu.this, NextMondayActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public static Context getContext() {
        return context;
    }
}

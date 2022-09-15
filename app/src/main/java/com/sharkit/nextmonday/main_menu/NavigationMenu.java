package com.sharkit.nextmonday.main_menu;

import android.annotation.SuppressLint;
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
import com.sharkit.nextmonday.configuration.navigation.CalculatorNavigation;
import com.sharkit.nextmonday.configuration.navigation.MenuDrawerNavigation;

@SuppressLint("NonConstantResourceId")
public class NavigationMenu extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_navigation_menu);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Toolbar toolbar = this.findViewById(R.id.toolbar_core);
        this.setSupportActionBar(toolbar);
        final Drawable drawable = ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.ic_baseline_settings_24);
        toolbar.setOverflowIcon(drawable);

    }

    public void additionalMenuDrawer(final MenuItem item) {
        final MenuDrawerNavigation navigation = MenuDrawerNavigation.getInstance(this);

        switch (item.getItemId()) {
            case R.id.feedback_item:
                navigation.moveToFeedback();
                break;
            case R.id.share_item:
                this.share();
                break;
            case R.id.estimate_item:
                this.rating();
                break;
            case R.id.exit_item:
                this.exit();
                break;
            default:
                throw new RuntimeException("Unexpected value");
        }
    }

    public void diaryMenuClickListener(final MenuItem item) {
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        switch (item.getItemId()) {
            case R.id.item_diary_calendar:
                navController.navigate(R.id.navigation_diary_calendar);
                break;
            case R.id.item_diary_list_of_task:
                navController.navigate(R.id.navigation_diary_notate);
                break;
            case R.id.item_diary_main:
                navController.navigate(R.id.navigation_diary_main);
                break;
            default:
                throw new RuntimeException("Unexpected value");
        }
    }

    public void calculatorMenuClickListener(final MenuItem item) {
        final CalculatorNavigation navigation = CalculatorNavigation.getInstance(this);

        switch (item.getItemId()){
            case R.id.main_item:
                navigation.toMainMenu();
                break;
            case R.id.ration_item:
                navigation.toRationMenu();
                break;
            case R.id.calendar_item:
                navigation.toCalendarMenu();
                break;
            case R.id.weight_item:
                navigation.toWeightMenu();
                break;
            default:
                throw new RuntimeException("Unexpected value");
        }
    }

    private void rating() {
        final String appPackageName = this.getPackageName();
        try {
            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (final android.content.ActivityNotFoundException anfe) {
            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void share() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        this.startActivity(Intent.createChooser(intent, "Share"));
    }

    private void exit() {
        final GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();
        final GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(NavigationMenu.this, gso);
        googleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        this.startActivity(new Intent(NavigationMenu.this, NextMondayActivity.class));
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}

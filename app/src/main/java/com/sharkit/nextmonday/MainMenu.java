package com.sharkit.nextmonday;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.CANCEL;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.CREATE_NEW;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_MAIN_DIARY_LIST;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FOOD_INFO_S;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD_ID;
import static com.sharkit.nextmonday.configuration.constant.BundleVariable.CREATE_NEW_FOOD;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sharkit.nextmonday.db.firestore.calculator.FoodInfoFirebase;
import com.sharkit.nextmonday.entity.calculator.FoodInfo;

import java.util.Calendar;

public class MainMenu extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar_core);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_settings_24);
        toolbar.setOverflowIcon(drawable);
    }
    //new title to share

    @SuppressLint("NonConstantResourceId")
    public void onMenuItemClick(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.weight_item:
                navController.navigate(R.id.nav_cal_weight);
                break;
            case R.id.calendar_item:
                navController.navigate(R.id.nav_cal_calendar);
                break;
            case R.id.ration_item:
                navController.navigate(R.id.nav_cal_ration);
                break;
            case R.id.main_item:
                navController.navigate(R.id.nav_calculator_main);
                break;
            case R.id.calculator_item:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                navController.navigate(R.id.nav_calculator_main);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.exit_item:
                exit();
                break;
            case R.id.setting_item:
                navController.navigate(R.id.nav_settings);
                break;
            case R.id.diary_calendar_item:
                navController.navigate(R.id.nav_calendar);
                break;
            case R.id.dairy_search_item:
                navController.navigate(R.id.nav_search);
                break;
            case R.id.list_target_item:
                listTarget();
                navController.navigate(R.id.nav_diary);
                break;
            case R.id.share_item:
                share();
                break;
            case R.id.rating_item:
                rating();
                break;
        }
    }

    public void rating() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        startActivity(Intent.createChooser(intent, "Share"));
    }

    public void listTarget() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        Bundle bundle = new Bundle();
        bundle.putLong(DATE_FOR_MAIN_DIARY_LIST, Calendar.getInstance().getTimeInMillis());
    }

    public void exit() {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainMenu.this, gso);
        googleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(MainMenu.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result.getContents() != null) {
            FoodInfoFirebase foodInfoFirebase = new FoodInfoFirebase();
            foodInfoFirebase.findFoodById(result.getContents())
                    .addOnSuccessListener(documentSnapshot -> {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(FOOD_INFO_S, documentSnapshot.toObject(FoodInfo.class));
                        Navigation.findNavController(MainMenu.this, R.id.nav_host_fragment).navigate(R.id.nav_cal_add_my_food, bundle);
                    }).addOnFailureListener(e -> AlertExistProduct(result.getContents()));
        } else {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_cal_find_food_by_name);
        }
    }

    private void AlertExistProduct(String code) {
        android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(MainMenu.this, R.style.CustomAlertDialog);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View existProduct = layoutInflater.inflate(R.layout.calculator_alert_exist, null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        dialog.setPositiveButton(CREATE_NEW, (dialog13, which) -> {
            Bundle bundle = new Bundle();
            bundle.putString(FRAGMENT_CREATE_FOOD_ID, code);
            bundle.putString(FRAGMENT_CREATE_FOOD, CREATE_NEW_FOOD);
            navController.navigate(R.id.nav_cal_create_food);
        });
        dialog.setNegativeButton(CANCEL, (dialog12, which) -> navController.navigate(R.id.nav_cal_find_food_by_name));
        dialog.setOnCancelListener(dialog1 -> navController.navigate(R.id.nav_cal_find_food_by_name));
        dialog.setView(existProduct);
        dialog.show();
    }
}
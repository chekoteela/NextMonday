package com.sharkit.nextmonday;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.CANCEL;
import static com.sharkit.nextmonday.configuration.constant.AlertButton.CREATE_NEW;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.DATE_FOR_MAIN_DIARY_LIST;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.FRAGMENT_CREATE_FOOD_ID;
import static com.sharkit.nextmonday.configuration.constant.BundleVariable.CREATE_NEW_FOOD;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sharkit.nextmonday.db.DataBasePFC;

import java.util.Calendar;

public class MainMenu extends AppCompatActivity {

     private AppBarConfiguration mAppBarConfiguration;
     private TextView email, nameProfile;
     private DataBasePFC dataBasePFC;
     private SQLiteDatabase db;
     private BottomNavigationView bar;





    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar_core);
        bar = findViewById(R.id.bar);

        setSupportActionBar(toolbar);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_settings_24);
        toolbar.setOverflowIcon(drawable);

    }



    public void OnClickWeight(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_cal_weight);
    }
    public void OnClickCalculatorCalendar(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_cal_calendar);
    }
    public void OnClickRation(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_cal_ration);
    }
    public void OnClickCalculatorMain(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_calculator_main);
    }

    public void OnClickCalculator(MenuItem item){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_calculator_main);
        drawer.closeDrawer(GravityCompat.START);
    }
    public void OnClickExit(MenuItem item){
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(MainMenu.this,gso);
        googleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(MainMenu.this,MainActivity.class));
        finish();
    }
    public void OnClickSettings(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_settings);
    }
    public void OnClickCalendar(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_calendar);
    }
    public void OnClickSearch(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_search);
    }
    public void OnClickTarget(MenuItem item){
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putLong(DATE_FOR_MAIN_DIARY_LIST, Calendar.getInstance().getTimeInMillis());
        navController.navigate(R.id.nav_diary);
    }
    public void Share(MenuItem item){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        intent.putExtra(Intent.EXTRA_TEXT ,"https://play.google.com/store/apps/details?id=com.sharkit.nextmonday");
        startActivity(Intent.createChooser(intent,"Share"));
    }

    public void Rating(MenuItem item){
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (result.getContents() != null) {
            AlertExistProduct(result.getContents());
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
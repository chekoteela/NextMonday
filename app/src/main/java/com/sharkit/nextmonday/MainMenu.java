package com.sharkit.nextmonday;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sharkit.nextmonday.MySQL.DatabaseHelper;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.Users.Users;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharkit.nextmonday.MySQL.DataBasePFC;
import com.sharkit.nextmonday.variables.LocalDataPFC;
import com.sharkit.nextmonday.variables.PFC_today;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import Fragments.Calendar;

public class MainMenu extends AppCompatActivity {

     AppBarConfiguration mAppBarConfiguration;
     TextView email, nameProfile;
     FirebaseAuth mAuth;
     FirebaseDatabase fdb;
     DatabaseReference users;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sdb;
    final String TAG = "qwerty";

    DataBasePFC dataBasePFC;
    SQLiteDatabase db;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar_core);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        users = fdb.getReference("Users");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        sdb = databaseHelper.getReadableDatabase();
        databaseHelper.onCreate(sdb);
        dataBasePFC = new DataBasePFC(getApplicationContext());
        db = dataBasePFC.getReadableDatabase();
        dataBasePFC.onCreate(db);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_settings_24);
        toolbar.setOverflowIcon(drawable);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_diary, R.id.nav_target, R.id.nav_feedback,R.id.nav_calculator_main)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        UpDateUser();

        Calendar calendar = new Calendar();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        calendar.Week(cal.get(java.util.Calendar.DAY_OF_MONTH),cal.get(java.util.Calendar.MONTH),cal.get(java.util.Calendar.YEAR));

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
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        DayOfWeek.setMillis(calendar.getTimeInMillis());
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
        sdb.close();
        startActivity(new Intent(MainMenu.this,MainActivity.class));
        finish();
    }
    public void OnClickSettings(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_profile);
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
        Calendar calendar = new Calendar();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        calendar.Week(cal.get(java.util.Calendar.DAY_OF_MONTH),cal.get(java.util.Calendar.MONTH),cal.get(java.util.Calendar.YEAR));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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

    public void UpDateUser() {
        users.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Target.setModerator("1");
                email = findViewById(R.id.email);
                nameProfile =findViewById(R.id.nameProfile);
                Users us = snapshot.getValue(Users.class);
                try{

                    Target.setModerator(us.getModerator());
                    email.setText(us.getEmail());
                    nameProfile.setText((us.getName()));
                }catch (NullPointerException e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
            PFC_today.setBar_code(result.getContents());
            ReadFireStore();


        } else {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_cal_find_food_by_name);
            Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
        }

        // додати додаткову провірку на наявність запису в SQL
    }
    private void ReadFireStore(){
         FirebaseFirestore db = FirebaseFirestore.getInstance();
         CollectionReference docRef = db.collection("DB Product");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        docRef.whereEqualTo("bar_code", PFC_today.getBar_code())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(!value.isEmpty()){
                            navController.navigate(R.id.nav_cal_my_food);
                        }else{
                            if (!isExistSQLite(PFC_today.getBar_code())) {
                                PFC_today.setPage("MainMenu.LocalSQLite");
                                navController.navigate(R.id.nav_cal_my_food);
                            }else{
                                AlertExistProduct();
                            }
                        }
                    }
                });

    }

    private void AlertExistProduct() {
        android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(MainMenu.this, R.style.CustomAlertDialog);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View existProduct = layoutInflater.inflate(R.layout.calculator_alert_exist, null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        dialog.setPositiveButton("Создать новый", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NullList();
                navController.navigate(R.id.nav_cal_change_food);
            }
        });
        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navController.navigate(R.id.nav_cal_find_food_by_name);
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                navController.navigate(R.id.nav_cal_find_food_by_name);
            }
        });

        dialog.setView(existProduct);
        dialog.show();

    }

    private boolean isExistSQLite(String code) {
        db = dataBasePFC.getReadableDatabase();

       Cursor query = db.rawQuery("SELECT * FROM " + dataBasePFC.TABLE + " WHERE " + dataBasePFC.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "' AND "
                + dataBasePFC.COLUMN_BAR_CODE + " = '" + code + "'", null);

        return (!query.moveToNext());

    }

    private void NullList() {
        LocalDataPFC.setName(null);
        LocalDataPFC.setPortion(null);
        LocalDataPFC.setCalorie(null);
        LocalDataPFC.setProtein(null);
        LocalDataPFC.setWhey_protein(null);
        LocalDataPFC.setSoy_protein(null);
        LocalDataPFC.setAgg_protein(null);
        LocalDataPFC.setCasein_protein(null);
        LocalDataPFC.setCarbohydrate(null);
        LocalDataPFC.setSimple_carbohydrates(null);
        LocalDataPFC.setComplex_carbohydrate(null);
        LocalDataPFC.setFat(null);
        LocalDataPFC.setSaturated_fat(null);
        LocalDataPFC.setTrans_fat(null);
        LocalDataPFC.setOmega_9(null);
        LocalDataPFC.setOmega_6(null);
        LocalDataPFC.setOmega_3(null);
        LocalDataPFC.setAla(null);
        LocalDataPFC.setDha(null);
        LocalDataPFC.setEpa(null);
        LocalDataPFC.setWatter(null);
        LocalDataPFC.setCellulose(null);
        LocalDataPFC.setSalt(null);
        LocalDataPFC.setCalcium(null);
        LocalDataPFC.setPotassium(null);
    }

}
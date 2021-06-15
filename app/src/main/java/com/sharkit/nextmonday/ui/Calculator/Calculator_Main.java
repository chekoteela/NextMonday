package com.sharkit.nextmonday.ui.Calculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.progress.progressview.ProgressView;
import com.sharkit.nextmonday.MySQL.LinkRation;
import com.sharkit.nextmonday.MySQL.MyWeight;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.variables.MealData;
import com.sharkit.nextmonday.variables.PFC_today;
import com.sharkit.nextmonday.variables.SettingsCalculator;
import com.sharkit.nextmonday.variables.UserMeal;
import com.sharkit.nextmonday.variables.WeightV;

import org.jetbrains.annotations.NotNull;

import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

import kotlin.jvm.internal.markers.KMappedMarker;
import soup.neumorphism.NeumorphCardView;

import static android.icu.lang.UCharacter.IndicPositionalCategory.BOTTOM_AND_RIGHT;
import static android.icu.lang.UCharacter.IndicPositionalCategory.RIGHT;
import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.Gravity.END;
import static android.view.TouchDelegate.ABOVE;
import static android.view.TouchDelegate.BELOW;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Calculator_Main extends Fragment implements View.OnClickListener{
    ImageView add_food, add_watter, add_activity, add_weight, plus;
    ProgressView calorie, protein, fat, carbohydrate, watter;
    TextView percent_calorie, percent_protein, percent_fat, percent_carbohydrate,percent_watter,
            eat_c, eat_calorie, all_calorie, eat_f, eat_fat, all_fat,
    eatC, eat_carbohydrate, all_carbohydrate, eat_p, eat_protein, all_protein,
    drink_w, drink_watter, all_watter, text_w, text_c, text_p, text_f;

    int percent_w, percent_p, percent_ch, percent_c, percent_f;
    FrameLayout c_layout, p_layout, w_layout, f_layout, car_layout;

    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView ration;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();;
    FirebaseDatabase fdb;
    DatabaseReference users;
    LinkRation linkRation;
    SQLiteDatabase sdb,mdb;
    MyWeight myWeight;
    ArrayList<Object> watter_drink;
    LinearLayout linear_orange_xml, linear_cv_top, linear_a1, linear_black_a1, linear_orange_a1,linear_b1,linear_black_b1, linear_orange_b1, linear_a2,
            linear_orange_a2, linear_black_a2, linear_cv_bottom, linear_b2, linear_orange_b2, linear_black_b2, linear_bottom_menu, linear_general;

    float v_watter = 0;

    MenuItem home1;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    DocumentReference collRef;

    Cursor query, cursor;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference docRef;

    int change_weight;


    final String TAG = "qwerty";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.calculator_main, container, false);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        FindViewByID(root);

        linkRation = new LinkRation(getApplicationContext());
        sdb = linkRation.getReadableDatabase();
        linkRation.onCreate(sdb);
        myWeight = new MyWeight(getApplicationContext());
        mdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);
        Onclick(this);


        Adaptive();

        GetWatter();

        home1 = bottomNavigationView.getMenu().findItem(R.id.main);
        home1.setIcon(R.drawable.main_selected);



        add_watter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View existProduct = layoutInflater.inflate(R.layout.calculator_weigth_button_dialog, null);
                EditText weight = existProduct.findViewById(R.id.weight);
                TextView textView = existProduct.findViewById(R.id.text_xml);
                weight.setHint("Мл");
                textView.setText("Добавить воду в мл");
                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(weight.getText())){
                            Toast.makeText(getContext(), "Введите количество випитой воды в мл", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Float.parseFloat(weight.getText().toString()) > 4000 || Float.parseFloat(weight.getText().toString()) < 0){
                            Toast.makeText(getContext(), "Введите корректный количество воды", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        AddWatter(weight.getText().toString());
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_food.getVisibility() == View.GONE){
                    add_food.setVisibility(View.VISIBLE);
                    add_watter.setVisibility(View.VISIBLE);
                    add_weight.setVisibility(View.VISIBLE);
                    plus.setImageResource(R.drawable.plus);
                } else {
                    add_food.setVisibility(View.GONE);
                    add_watter.setVisibility(View.GONE);
                    add_weight.setVisibility(View.GONE);
                    plus.setImageResource(R.drawable.icon_x);
                }
            }
        });

        add_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View existProduct = layoutInflater.inflate(R.layout.calculator_weigth_button_dialog, null);
                EditText weight = existProduct.findViewById(R.id.weight);
                weight.setHint("Кг");
                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(weight.getText())){
                            Toast.makeText(getContext(), "Введите ваш вес в кг", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Float.parseFloat(weight.getText().toString()) > 1000 || Float.parseFloat(weight.getText().toString()) < 10){
                            Toast.makeText(getContext(), "Введите корректный вес", Toast.LENGTH_SHORT).show();
                            return;
                        }
                            WriteDataWeight(weight.getText().toString());
                    }
                });
                dialog.setView(existProduct);
                dialog.show();
            }
        });

        add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PFC_today.setPage("");
                PFC_today.setMealName(null);
                navController.navigate(R.id.nav_cal_find_food_by_name);
            }
        });


        return root;
    }



    private void Adaptive() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //визначення розмірів екрану та створення змінної
        int h = metrics.heightPixels;
        int w = metrics.widthPixels;
        //створення додаткових змінних

//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Значення" + h, Toast.LENGTH_LONG);
//        toast.show();

        //Лог

        if(h >= 2000 ){//Встановлення параметрів FrameLayout
            int p = (int)(h/4.1);
            int c = (int)(h/2);
            int v = (int)(w/4.1);
            LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            c_layout.setLayoutParams(c_params);
            //Встановлення параметрів прогресбара калорії
            FrameLayout.LayoutParams progress_calorie = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, p);
            progress_calorie.setMargins(10,30,10,50);
            progress_calorie.gravity = CENTER;          //центрування в FrameLayout
            calorie.setAnimationDuration(4000);        //встановлення швидкості анімації прогресбара
            calorie.setBackgroundWidth((int)(w / 20)); // встановлення ширини беку прогресбара
            calorie.setProgressWidth((int)(w / 20.2)); //встановлення ширини прогресу прогресбара
            calorie.setLayoutParams(progress_calorie);
            calorie.setPadding(0,0,0,0);
            //Встановлення параметрів Внутрішнього LinearLayout
            FrameLayout.LayoutParams linear_orange = new FrameLayout.LayoutParams(v, h/200);
            linear_orange.gravity = CENTER;
            linear_orange_xml.setLayoutParams(linear_orange);
            //Надання параметрів TextView калорії
            FrameLayout.LayoutParams text_param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            text_param.setMargins(0,h / 20,0,0);
            text_param.gravity = CENTER;  //Центрування
            eat_c.setLayoutParams(text_param);
            //Задання параметрів елементам CardView
            LinearLayout.LayoutParams frame_cv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , c);
            frame_cv.weight = 1;
            frame_cv.gravity = CENTER;
            f_layout.setLayoutParams(frame_cv);
            car_layout.setLayoutParams(frame_cv);
            w_layout.setLayoutParams(frame_cv);
            p_layout.setLayoutParams(frame_cv);
        }
        else if (h <= 2000 && h >= 1000) {//Встановлення параметрів FrameLayout для ммого HuaweiP8-line2017 за розширенням
            int c = (int)(h/3.5);
            int v = (int)(h/4.1);
            LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, c);
            c_layout.setLayoutParams(c_params);
            //Встановлення параметрів прогресбара калорії
            FrameLayout.LayoutParams progress_calorie = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, v);
            progress_calorie.setMargins(0,0,0,0);
            progress_calorie.gravity = CENTER;          //центрування в FrameLayout
            calorie.setAnimationDuration(4000);        //встановлення швидкості анімації прогресбара
            calorie.setBackgroundWidth((int)(w / 20)); // встановлення ширини беку прогресбара
            calorie.setProgressWidth((int)(w / 20.2)); //встановлення ширини прогресу прогресбара
            calorie.setLayoutParams(progress_calorie);
            calorie.setPadding(0,0,0,0);
            //Встановлення параметрів Внутрішнього LinearLayout
            int a = (int)(h/200);
            int s = (int)(w/4.1);
            FrameLayout.LayoutParams linear_orange = new FrameLayout.LayoutParams(s, a);
            linear_orange.gravity = CENTER;
            linear_orange_xml.setLayoutParams(linear_orange);
            //Надання параметрів TextView калорії
            FrameLayout.LayoutParams text_param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            text_param.setMargins(0,h / 20,0,0);
            text_param.gravity = CENTER;  //Центрування
            eat_c.setLayoutParams(text_param);
            //Задання параметрів елементам CardView
            int q = (int)(h/4.4);
            LinearLayout.LayoutParams frame_cv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , q);
            frame_cv.weight = 1;
            frame_cv.gravity = CENTER;
            f_layout.setLayoutParams(frame_cv);
            car_layout.setLayoutParams(frame_cv);
            w_layout.setLayoutParams(frame_cv);
            p_layout.setLayoutParams(frame_cv);
        }
        else if (h <= 1000) {
            //Встановлення параметрів FrameLayout для малих за розширенням телефонів
            LinearLayout.LayoutParams c_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(h / 3.9));
            c_layout.setLayoutParams(c_params);
            //Встановлення параметрів прогресбара калорії
            FrameLayout.LayoutParams progress_calorie = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (h /4.7));
            progress_calorie.setMargins(0,0,0,0);
            progress_calorie.gravity = CENTER;          //центрування в FrameLayout
            calorie.setAnimationDuration(4000);        //встановлення швидкості анімації прогресбара
            calorie.setBackgroundWidth((int)(w / 20.2)); // встановлення ширини беку прогресбара
            calorie.setProgressWidth((int)(w / 20.4)); //встановлення ширини прогресу прогресбара
            calorie.setLayoutParams(progress_calorie);
            calorie.setPadding(0,0,0,0);
            //Встановлення параметрів Внутрішнього LinearLayout
            FrameLayout.LayoutParams linear_orange = new FrameLayout.LayoutParams((int)(w / 4.3), (int)(h / 203));
            linear_orange.gravity = CENTER;
            linear_orange_xml.setLayoutParams(linear_orange);
            //Надання параметрів TextView калорії
            FrameLayout.LayoutParams text_param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            text_param.setMargins(0,h / 20,0,0);
            text_param.gravity = CENTER;  //Центрування
            eat_c.setLayoutParams(text_param);
            //Задання параметрів елементам CardView
            LinearLayout.LayoutParams frame_cv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , (int) (h / 4.5));
            frame_cv.weight = 1;
            frame_cv.gravity = CENTER;
            f_layout.setLayoutParams(frame_cv);
            car_layout.setLayoutParams(frame_cv);
            w_layout.setLayoutParams(frame_cv);
            p_layout.setLayoutParams(frame_cv);

        }

if(h >= 2000){ // створення адаптів для h = 2000
    //Встановлення LinearLayout CardView Top

    LinearLayout.LayoutParams linear_cv_top_par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    linear_cv_top.setLayoutParams(linear_cv_top_par);
    linear_cv_top.setWeightSum(2);
    LinearLayout.LayoutParams frame_par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h/4);
    frame_par.weight = 1;
    f_layout.setLayoutParams(frame_par);
    car_layout.setLayoutParams(frame_par);
    w_layout.setLayoutParams(frame_par);
    p_layout.setLayoutParams(frame_par);
    //Встановелння  параметрів Прогрес бара Жирів
    int c = (int)(w / 3.2);
    NeumorphCardView.LayoutParams neumorphic_progress_bar = new NeumorphCardView.LayoutParams(c , (int) (h / 4.4));
    neumorphic_progress_bar.gravity = CENTER_HORIZONTAL;
    fat.setLayoutParams(neumorphic_progress_bar);
    fat.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    fat.setBackgroundWidth((int)(w / 25)); // встановлення ширини беку прогресбара
    fat.setProgressWidth((int)(w / 25.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout
    FrameLayout.LayoutParams linear_a1_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
    linear_a1_param.setMargins(w / 2,h / 45,w / 2,0);
    linear_a1_param.gravity = CENTER;
    linear_a1.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в а1
    NeumorphCardView.LayoutParams text_param_a1 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    text_param_a1.gravity  = BOTTOM | END;
    text_param_a1.setMargins(0,0,h / 60,h / 30);
    eat_fat.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів а1
    NeumorphCardView.LayoutParams linear_black_a1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
    linear_black_a1_param.gravity  = BOTTOM | END;
    linear_black_a1_param.setMargins(0,0,(int)(h / 12),4);
    linear_black_a1.setLayoutParams(linear_black_a1_param);

    NeumorphCardView.LayoutParams linear_orange_a1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
    linear_orange_a1_param.gravity  = BOTTOM | END;
    linear_orange_a1_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_orange_a1.setLayoutParams(linear_orange_a1_param);

    //Встановелння  параметрів Прогрес бара Вуглеводів
    carbohydrate.setLayoutParams(neumorphic_progress_bar);
    carbohydrate.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    carbohydrate.setBackgroundWidth((int)(w / 26)); // встановлення ширини беку прогресбара
    carbohydrate.setProgressWidth((int)(w / 26.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout b1
//        FrameLayout.LayoutParams linear_b1_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
//        linear_b1_param.setMargins(w / 2,h / 45,w / 2,0);
//        linear_b1_param.gravity = CENTER;
    linear_b1.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в b1
//        NeumorphCardView.LayoutParams text_param_b1 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        text_param_b1.gravity  = BOTTOM | END;
//        text_param_b1.setMargins(0,0,h / 60,h / 30);
    eat_carbohydrate.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів b1
//        NeumorphCardView.LayoutParams linear_black_b1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_black_b1_param.gravity  = BOTTOM | END;
//        linear_black_b1_param.setMargins(0,0,(int)(h / 12),4);
    linear_black_b1.setLayoutParams(linear_black_a1_param);
//        NeumorphCardView.LayoutParams linear_orange_b1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_orange_b1_param.gravity  = BOTTOM | END;
//        linear_orange_b1_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_orange_b1.setLayoutParams(linear_orange_a1_param);

    //Встановлення LinearLayout CardView bottom
//    LinearLayout.LayoutParams linear_cv_bottom_par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(h / 4.4));
//    linear_cv_bottom_par.setMargins(0,0,0,0);
    linear_cv_bottom.setLayoutParams(linear_cv_top_par);
    linear_cv_bottom.setWeightSum(2);

    //Встановелння  параметрів Прогрес бара Білків
    protein.setLayoutParams(neumorphic_progress_bar);
    protein.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    protein.setBackgroundWidth((int)(w / 26)); // встановлення ширини беку прогресбара
    protein.setProgressWidth((int)(w / 26.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout
//        FrameLayout.LayoutParams linear_a2_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
//        linear_a2_param.setMargins(w / 2,h / 45,w / 2,0);
//        linear_a2_param.gravity = CENTER;
    linear_a2.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в а1
//        NeumorphCardView.LayoutParams text_param_a2 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        text_param_a2.gravity  = BOTTOM | END;
//        text_param_a2.setMargins(0,0,h / 60,h / 30);
    eat_protein.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів а1
//        NeumorphCardView.LayoutParams linear_black_a2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_black_a2_param.gravity  = BOTTOM | END;
//        linear_black_a2_param.setMargins(0,0,(int)(h / 12),4);
    linear_black_a2.setLayoutParams(linear_black_a1_param);

//        NeumorphCardView.LayoutParams linear_orange_a2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_orange_a2_param.gravity  = BOTTOM | END;
//        linear_orange_a2_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_orange_a2.setLayoutParams(linear_orange_a1_param);

    //Встановелння  параметрів Прогрес бара Води
    watter.setLayoutParams(neumorphic_progress_bar);
    watter.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    watter.setBackgroundWidth((int)(w / 26)); // встановлення ширини беку прогресбара
    watter.setProgressWidth((int)(w / 26.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout
//        FrameLayout.LayoutParams linear_b2_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
//        linear_b2_param.setMargins(w / 2,h / 45,w / 2,0);
//        linear_b2_param.gravity = CENTER;
    linear_b2.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в а1
//        NeumorphCardView.LayoutParams text_param_b2 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        text_param_b2.gravity  = BOTTOM | END;
//        text_param_b2.setMargins(0,0,h / 60,h / 30);
    eat_protein.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів а1
//        NeumorphCardView.LayoutParams linear_orange_b2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_orange_b2_param.gravity  = BOTTOM | END;
//        linear_orange_b2_param.setMargins(0,0,(int)(h / 12),4);
    linear_orange_b2.setLayoutParams(linear_orange_a1_param);

//        NeumorphCardView.LayoutParams linear_black_b2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_black_b2_param.gravity  = BOTTOM | END;
//        linear_black_b2_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_black_b2.setLayoutParams(linear_black_a1_param);

}else{
    //Встановлення LinearLayout CardView Top
    LinearLayout.LayoutParams linear_cv_top_par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(h / 4.4));
    linear_cv_top.setLayoutParams(linear_cv_top_par);
    linear_cv_top.setWeightSum(2);

    //Встановелння  параметрів Прогрес бара Жирів
    NeumorphCardView.LayoutParams neumorphic_progress_bar = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , (int) (h / 5.4));
    neumorphic_progress_bar.gravity = CENTER_HORIZONTAL;
    fat.setLayoutParams(neumorphic_progress_bar);
    fat.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    fat.setBackgroundWidth((int)(w / 26)); // встановлення ширини беку прогресбара
    fat.setProgressWidth((int)(w / 26.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout
    FrameLayout.LayoutParams linear_a1_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
    linear_a1_param.setMargins(w / 2,h / 45,w / 2,0);
    linear_a1_param.gravity = CENTER;
    linear_a1.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в а1
    NeumorphCardView.LayoutParams text_param_a1 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    text_param_a1.gravity  = BOTTOM | END;
    text_param_a1.setMargins(0,0,h / 60,h / 30);
    eat_fat.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів а1
    NeumorphCardView.LayoutParams linear_black_a1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
    linear_black_a1_param.gravity  = BOTTOM | END;
    linear_black_a1_param.setMargins(0,0,(int)(h / 12),4);
    linear_black_a1.setLayoutParams(linear_black_a1_param);

    NeumorphCardView.LayoutParams linear_orange_a1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
    linear_orange_a1_param.gravity  = BOTTOM | END;
    linear_orange_a1_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_orange_a1.setLayoutParams(linear_orange_a1_param);

    //Встановелння  параметрів Прогрес бара Вуглеводів
    carbohydrate.setLayoutParams(neumorphic_progress_bar);
    carbohydrate.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    carbohydrate.setBackgroundWidth((int)(w / 26)); // встановлення ширини беку прогресбара
    carbohydrate.setProgressWidth((int)(w / 26.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout b1
//        FrameLayout.LayoutParams linear_b1_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
//        linear_b1_param.setMargins(w / 2,h / 45,w / 2,0);
//        linear_b1_param.gravity = CENTER;
    linear_b1.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в b1
//        NeumorphCardView.LayoutParams text_param_b1 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        text_param_b1.gravity  = BOTTOM | END;
//        text_param_b1.setMargins(0,0,h / 60,h / 30);
    eat_carbohydrate.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів b1
//        NeumorphCardView.LayoutParams linear_black_b1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_black_b1_param.gravity  = BOTTOM | END;
//        linear_black_b1_param.setMargins(0,0,(int)(h / 12),4);
    linear_black_b1.setLayoutParams(linear_black_a1_param);
//        NeumorphCardView.LayoutParams linear_orange_b1_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_orange_b1_param.gravity  = BOTTOM | END;
//        linear_orange_b1_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_orange_b1.setLayoutParams(linear_orange_a1_param);

    //Встановлення LinearLayout CardView bottom
    LinearLayout.LayoutParams linear_cv_bottom_par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(h / 4.4));
    linear_cv_bottom_par.setMargins(0,0,0,0);
    linear_cv_bottom.setLayoutParams(linear_cv_top_par);
    linear_cv_bottom.setWeightSum(2);


    //Встановелння  параметрів Прогрес бара Білків
    protein.setLayoutParams(neumorphic_progress_bar);
    protein.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    protein.setBackgroundWidth((int)(w / 26)); // встановлення ширини беку прогресбара
    protein.setProgressWidth((int)(w / 26.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout
//        FrameLayout.LayoutParams linear_a2_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
//        linear_a2_param.setMargins(w / 2,h / 45,w / 2,0);
//        linear_a2_param.gravity = CENTER;
    linear_a2.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в а1
//        NeumorphCardView.LayoutParams text_param_a2 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        text_param_a2.gravity  = BOTTOM | END;
//        text_param_a2.setMargins(0,0,h / 60,h / 30);
    eat_protein.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів а1
//        NeumorphCardView.LayoutParams linear_black_a2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_black_a2_param.gravity  = BOTTOM | END;
//        linear_black_a2_param.setMargins(0,0,(int)(h / 12),4);
    linear_black_a2.setLayoutParams(linear_black_a1_param);

//        NeumorphCardView.LayoutParams linear_orange_a2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_orange_a2_param.gravity  = BOTTOM | END;
//        linear_orange_a2_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_orange_a2.setLayoutParams(linear_orange_a1_param);


    //Встановелння  параметрів Прогрес бара Води
    watter.setLayoutParams(neumorphic_progress_bar);
    watter.setAnimationDuration(5000);        //встановлення швидкості анімації прогресбара
    watter.setBackgroundWidth((int)(w / 26)); // встановлення ширини беку прогресбара
    watter.setProgressWidth((int)(w / 26.2)); //встановлення ширини прогресу прогресбар
    //Встановлення параметрів замальованого оранжевого Linear Layout
//        FrameLayout.LayoutParams linear_b2_param = new FrameLayout.LayoutParams((int)(w / 3), (int)(h / 200));
//        linear_b2_param.setMargins(w / 2,h / 45,w / 2,0);
//        linear_b2_param.gravity = CENTER;
    linear_b2.setLayoutParams(linear_a1_param);
    //Встановлення параметрів тексту спожиті в а1
//        NeumorphCardView.LayoutParams text_param_b2 = new NeumorphCardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        text_param_b2.gravity  = BOTTOM | END;
//        text_param_b2.setMargins(0,0,h / 60,h / 30);
    eat_protein.setLayoutParams(text_param_a1);
    //Встановлення розмірів кругів координатів а1
//        NeumorphCardView.LayoutParams linear_orange_b2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_orange_b2_param.gravity  = BOTTOM | END;
//        linear_orange_b2_param.setMargins(0,0,(int)(h / 12),4);
    linear_orange_b2.setLayoutParams(linear_orange_a1_param);

//        NeumorphCardView.LayoutParams linear_black_b2_param = new NeumorphCardView.LayoutParams((int)(h / 64), (int)(h / 64));
//        linear_black_b2_param.gravity  = BOTTOM | END;
//        linear_black_b2_param.setMargins(0,0,(int)(h / 12),h / 28);
    linear_black_b2.setLayoutParams(linear_black_a1_param);
}

        //Встановлення  параметрів  нижнього меню
        LinearLayout.LayoutParams bottom_menu_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h / 20);
        bottom_menu_params.setMargins(10,10,10,10);
        linear_bottom_menu.setWeightSum(4);
        linear_bottom_menu.setLayoutParams(bottom_menu_params);

        //Адаптація тексту під розмір екранів
        if (h > 2001) {
            all_carbohydrate.setTextSize(14);
            all_protein.setTextSize(14);
            all_watter.setTextSize(14);
            eat_protein.setTextSize(14);
            eat_p.setTextSize(14);
            eat_carbohydrate.setTextSize(14);
            eat_c.setTextSize(14);
            drink_watter.setTextSize(14);
            drink_w.setTextSize(14);
            eat_f.setTextSize(14);
            eat_fat.setTextSize(14);
            all_fat.setTextSize(14);

            text_p.setTextSize(16);
            text_f.setTextSize(16);
            text_c.setTextSize(16);
            text_w.setTextSize(16);

            eat_c.setTextSize(32);
            percent_calorie.setTextSize(32);
            percent_fat.setTextSize(32);
            percent_watter.setTextSize(32);
            percent_carbohydrate.setTextSize(32);
            percent_protein.setTextSize(32);
        } else if (h < 2000 && h > 1000) {
            eat_f.setTextSize(12);
            eat_fat.setTextSize(12);
            all_fat.setTextSize(12);
            all_carbohydrate.setTextSize(12);
            all_protein.setTextSize(12);
            all_watter.setTextSize(12);
            eat_protein.setTextSize(12);
            eat_p.setTextSize(12);
            eat_carbohydrate.setTextSize(12);
            eat_c.setTextSize(12);
            drink_watter.setTextSize(12);
            drink_w.setTextSize(12);
            text_p.setTextSize(12);
            text_f.setTextSize(12);
            text_c.setTextSize(12);
            text_w.setTextSize(12);
            eat_c.setTextSize(22);
            percent_calorie.setTextSize(22);
            percent_fat.setTextSize(22);
            percent_watter.setTextSize(22);
            percent_carbohydrate.setTextSize(22);
            percent_protein.setTextSize(22);
        } else if (h <= 1000) {
            all_carbohydrate.setTextSize(10);
            all_protein.setTextSize(10);
            all_watter.setTextSize(10);
            eat_protein.setTextSize(10);
            eat_p.setTextSize(10);
            eat_carbohydrate.setTextSize(10);
            eat_c.setTextSize(10);
            drink_watter.setTextSize(10);
            drink_w.setTextSize(10);
            eat_f.setTextSize(10);
            eat_fat.setTextSize(10);
            all_fat.setTextSize(10);
            text_p.setTextSize(12);
            text_f.setTextSize(12);
            text_c.setTextSize(12);
            text_w.setTextSize(12);
            eat_c.setTextSize(16);
            percent_calorie.setTextSize(16);
            percent_fat.setTextSize(16);
            percent_watter.setTextSize(16);
            percent_carbohydrate.setTextSize(16);
            percent_protein.setTextSize(16);
        }

    }

    private void GetWatter() {
        watter_drink = new ArrayList<>();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = Calendar.getInstance();

        collRef = fb.collection("Users/" + mAuth.getCurrentUser().getUid() + "/DrinkWatter")
        .document(dateFormat.format(calendar.getTimeInMillis()));
        collRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (int i = 0; i < documentSnapshot.getData().size(); i++){
                        watter_drink.add(documentSnapshot.get(String.valueOf(i)));
                    }
                }

                for (int i = 0; i <watter_drink.size(); i++){
                    v_watter +=  Float.parseFloat(String.valueOf(watter_drink.get(i)));
                }
                SynchronizedPFC();
                SumMealNutrition();
            }
        });
    }

    private void AddWatter(String w) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = Calendar.getInstance();

        collRef = fb.collection("Users/" + mAuth.getCurrentUser().getUid() + "/DrinkWatter")
                .document(dateFormat.format(calendar.getTimeInMillis()));

        watter_drink.add(w);
        Map <String, Object> map = new HashMap<>();
        for (int i = 0; i < watter_drink.size(); i ++){
            map.put(String.valueOf(i), watter_drink.get(i));
        }
        collRef.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_calculator_main);
            }
        });

    }

    private void GetWeight() {

        cursor = mdb.rawQuery("SELECT * FROM " + myWeight.TABLE + " WHERE " + myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'", null);

        cursor.moveToLast();
        try {

            PFC_today.setWeight(cursor.getString(2));
        }catch (CursorIndexOutOfBoundsException e) {}
    }

    private void WriteDataWeight(String s) {

        myWeight = new MyWeight(getApplicationContext());
        sdb = myWeight.getReadableDatabase();
        myWeight.onCreate(sdb);

        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        query = sdb.rawQuery("SELECT * FROM " + myWeight.TABLE + " WHERE " + myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "'", null);

        query.moveToLast();

        WeightV weight = new WeightV();
        weight.setDate(calendar.getTimeInMillis());
        weight.setWeight(s);

        try {
            weight.setChange(String.format( Locale.ROOT,"%.1f",(Float.parseFloat(s) - Float.parseFloat(query.getString(2)))));
        }catch (CursorIndexOutOfBoundsException e){
            weight.setChange(String.valueOf(0));
        }

        try {
            sdb.execSQL("INSERT INTO " + myWeight.TABLE + " VALUES ('" + mAuth.getCurrentUser().getUid() + "','" +
                    calendar.getTimeInMillis() + "','" +
                    s + "','" + weight.getChange() + "','" +
                    dateFormat.format(calendar.getTimeInMillis()) +
                    "');");

            CollectionReference colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MyWeight");
            colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(weight);

        }catch (SQLiteConstraintException e){

            try {
                query.moveToPosition(query.getCount() -2);
                weight.setChange(String.valueOf(Float.parseFloat(s) - Float.parseFloat(query.getString(2))));

            }catch (CursorIndexOutOfBoundsException exception){
            }

            try {
                weight.setChange(String.format( Locale.ROOT,"%.1f",(Float.parseFloat(s) - Float.parseFloat(query.getString(2)))));
            }catch (CursorIndexOutOfBoundsException ex){
                weight.setChange(String.valueOf(0));
            }

            sdb.execSQL("UPDATE " + myWeight.TABLE + " SET " +
                    myWeight.COLUMN_WEIGHT + " = '" + s + "'," +
                    myWeight.COLUMN_CHANGE + " = '" + weight.getChange()
                    + "' WHERE " +
                    myWeight.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "' AND " +
                    myWeight.COLUMN_DATE + " = '" + dateFormat.format(calendar.getTimeInMillis()) + "'" );
            CollectionReference colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MyWeight");
            colRef.document(dateFormat.format(calendar.getTimeInMillis())).set(weight);

        }


    }


    @SuppressLint("DefaultLocale")
    private void WriteText() {

        eat_c.setText(String.format("%.0f", PFC_today.getCalorie_eat()));
        eat_calorie.setText(String.format("%.0f", PFC_today.getCalorie_eat()));
        all_calorie.setText(String.format("%.0f", PFC_today.getCalorie()));

        eat_f.setText(String.format("%.1f", PFC_today.getFat_eat()));
        eat_fat.setText(String.format("%.1f", PFC_today.getFat_eat()));
        all_fat.setText(String.format("%.1f", PFC_today.getFat()));

        eatC.setText(String.format("%.1f", PFC_today.getCarbohydrate_eat()));
        eat_carbohydrate.setText(String.format("%.1f", PFC_today.getCarbohydrate_eat()));
        all_carbohydrate.setText(String.format("%.1f", PFC_today.getCarbohydrate()));

        eat_p.setText(String.format("%.1f", PFC_today.getProtein_eat()));
        eat_protein.setText(String.format("%.1f", PFC_today.getProtein_eat()));
        all_protein.setText(String.format("%.1f", PFC_today.getProtein()));

        drink_w.setText(String.format("%.2f", PFC_today.getWatter_drink()));
        drink_watter.setText(String.format("%.2f", PFC_today.getWatter_drink()));
        all_watter.setText(String.format("%.2f", PFC_today.getWatter()));

    }



    private void SynchronizedRation() {
        sdb = linkRation.getReadableDatabase();
        docRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/Meal");
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    MealData mealData = queryDocumentSnapshot.toObject(MealData.class);


                    try {
                        sdb.execSQL("INSERT INTO " + linkRation.TABLE + " VALUES ('" +
                                mAuth.getCurrentUser().getUid() + "','" +
                                mealData.getDate() + "','" +
                                mealData.getCode() + "','" +
                                mealData.getMeal() + "','" +
                                mealData.getNumber() + "','" +
                                mealData.getDate_millis() +
                                "');");
                    }catch (SQLiteConstraintException e){}
                }
            }
        });
    }

    private void ProgressMath() {

        percent_c = ProgressBar(PFC_today.getCalorie_eat(), PFC_today.getCalorie());
        percent_w = ProgressBar(PFC_today.getWatter_drink(), PFC_today.getWatter());
        percent_ch = ProgressBar(PFC_today.getCarbohydrate_eat(), PFC_today.getCarbohydrate());
        percent_f = ProgressBar(PFC_today.getFat_eat(), PFC_today.getFat());
        percent_p = ProgressBar(PFC_today.getProtein_eat(), PFC_today.getProtein());
        WriteText();
        ProgressBarDraw();
    }

    @SuppressLint("SetTextI18n")
    private void ProgressBarDraw() {
        percent_calorie.setText(percent_c + "%");
        percent_carbohydrate.setText(percent_ch + "%");
        percent_watter.setText(percent_w + "%");
        percent_fat.setText(percent_f + "%");
        percent_protein.setText(percent_p + "%");


        calorie.setProgress((float)percent_c / 100);
        carbohydrate.setProgress((float)percent_ch / 100);
        protein.setProgress((float)percent_p / 100);
        fat.setProgress((float)percent_f / 100);
        watter.setProgress((float)percent_w / 100);
    }

    public int ProgressBar(float before, float after) {

        int a = 0;
        if (before != 0) {
            a = (int) ((int) (100 * before) / after);
        }
        return a;
    }

    private void SynchronizedPFC() {
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        users = fdb.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/MyTarget");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SettingsCalculator settingsCalculator = snapshot.getValue(SettingsCalculator.class);
                try {
                    PFC_today.setCurrent_weight(String.valueOf(settingsCalculator.getDesired_weight()));
                    PFC_today.setWeight(String.valueOf(settingsCalculator.getWeight()));
                    PFC_today.setWatter(settingsCalculator.getWatter());
                    PFC_today.setProtein(settingsCalculator.getProtein());
                    PFC_today.setFat(settingsCalculator.getFat());
                    PFC_today.setCarbohydrate(settingsCalculator.getCarbohydrate());
                    PFC_today.setCalorie(settingsCalculator.getCalorie());

                    if (settingsCalculator.getVar().equals("Manual")){
                        GetWeight();

                        PFC_today.setProtein(PFC_today.getProtein() * Float.parseFloat(PFC_today.getWeight()));
                        PFC_today.setCarbohydrate(PFC_today.getCarbohydrate() * Float.parseFloat(PFC_today.getWeight()));
                        PFC_today.setFat(PFC_today.getFat() * Float.parseFloat(PFC_today.getWeight()));
                        PFC_today.setWatter(PFC_today.getWatter() * Float.parseFloat(PFC_today.getWeight()) / 1000);

                        PFC_today.setCalorie((PFC_today.getProtein() * 4) + (PFC_today.getFat() * 9) + (PFC_today.getCarbohydrate() * 4));

                    }
                    if (settingsCalculator.getVar().equals("Auto")){
                        GetWeight();

                        switch (settingsCalculator.getTarget()){
                            case "lose_weight":
                                change_weight = -300;
                                break;
                            case "gain_weight":
                                change_weight = 300;
                                break;
                            case "hold_the_weight":
                                change_weight = 0;
                                break;
                        }

                        if (Float.parseFloat(PFC_today.getCurrent_weight()) > Float.parseFloat(PFC_today.getWeight())
                                && !settingsCalculator.getTarget().equals("gain_weight")){
                            Map<String, Object> map = new HashMap<>();
                            map.put("target", "gain_weight");
                            users.updateChildren(map);
                            change_weight = 300;
                        }
                        if (Float.parseFloat(PFC_today.getCurrent_weight()) < Float.parseFloat(PFC_today.getWeight())
                                && !settingsCalculator.getTarget().equals("lose_weight")){
                            Map<String, Object> map = new HashMap<>();
                            map.put("target", "lose_weight");
                            users.updateChildren(map);
                            change_weight = -300;
                        }
                        if (Float.parseFloat(PFC_today.getCurrent_weight()) == Float.parseFloat(PFC_today.getWeight())
                                && !settingsCalculator.getTarget().equals("hold_the_weight")){
                            Map<String, Object> map = new HashMap<>();
                            map.put("target", "hold_the_weight");
                            users.updateChildren(map);
                            change_weight = 0;
                        }

                        if(settingsCalculator.getFormula().equals("muffin") && settingsCalculator.getSex().equals("female")){
                            PFC_today.setCalorie((int) ((((10 * Float.parseFloat(PFC_today.getWeight())) + (6.25 * settingsCalculator.getHeight()) -
                                    (5 * settingsCalculator.getAge()) - 161) * settingsCalculator.getActivity()) + change_weight));

                        }
                        if (settingsCalculator.getFormula().equals("muffin") && settingsCalculator.getSex().equals("female")){
                            PFC_today.setCalorie((int) ((((10 * Float.parseFloat(PFC_today.getWeight())) + (6.25 * settingsCalculator.getHeight()) -
                                    (5 * settingsCalculator.getAge()) + 5) * settingsCalculator.getActivity()) + change_weight));
                        }
                        if (settingsCalculator.getFormula().equals("harrison") && settingsCalculator.getSex().equals("male")){
                            PFC_today.setCalorie((int) ((((9.247 * Float.parseFloat(PFC_today.getWeight())) + (3.098 * settingsCalculator.getHeight()) -
                                    (4.330 * settingsCalculator.getAge()) + 447.593) * settingsCalculator.getActivity()) + change_weight));

                        }
                        if(settingsCalculator.getFormula().equals("harrison") && settingsCalculator.getSex().equals("male")){
                            PFC_today.setCalorie((int) ((((13.397 * Float.parseFloat(PFC_today.getWeight())) + (4.799 * settingsCalculator.getHeight()) -
                                    (5.677 * settingsCalculator.getAge()) + 88.362) * settingsCalculator.getActivity()) + change_weight));

                        }


                    }
                }catch (NullPointerException e){
                    ProgressBarMathNull();
                }

                ProgressMath();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ProgressBarMathNull() {
        PFC_today.setCalorie(PFC_today.getCalorie_eat());
        PFC_today.setWatter(PFC_today.getWatter_drink());
        PFC_today.setProtein(PFC_today.getProtein_eat());
        PFC_today.setFat(PFC_today.getFat_eat());
        PFC_today.setCarbohydrate(PFC_today.getCarbohydrate_eat());

    }

    public void SumMealNutrition(){
        linkRation = new LinkRation(getApplicationContext());
        sdb = linkRation.getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        UserMeal userMeal = new UserMeal();
        userMeal.setCalorie("0");
        userMeal.setWatter("0");
        userMeal.setCarbohydrate("0");
        userMeal.setProtein("0");
        userMeal.setFat("0");
        query = sdb.rawQuery("SELECT * FROM " + linkRation.TABLE + " WHERE " +
                linkRation.COLUMN_ID + " = '" + mAuth.getCurrentUser().getUid() + "' AND " +
                linkRation.COLUMN_DATE + " = '" + dateFormat.format(calendar.getTimeInMillis()) + "'", null);
        while (query.moveToNext()){
            WritePFC_Today(userMeal);
        }
        PFC_today.setWatter_drink(Float.parseFloat(userMeal.getWatter()) + (v_watter / 1000));
        PFC_today.setCalorie_eat(Float.parseFloat(userMeal.getCalorie()));
        PFC_today.setProtein_eat(Float.parseFloat(userMeal.getProtein()));
        PFC_today.setFat_eat(Float.parseFloat(userMeal.getFat()));
        PFC_today.setCarbohydrate_eat(Float.parseFloat(userMeal.getCarbohydrate()));

    }

    @SuppressLint("DefaultLocale")
    private void WritePFC_Today(UserMeal userMeal) {
        try {

            userMeal.setCalorie(String.valueOf(Float.parseFloat(userMeal.getCalorie()) + Float.parseFloat(query.getString(7)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));

            userMeal.setProtein(String.valueOf(Float.parseFloat(userMeal.getProtein()) + Float.parseFloat(query.getString(8)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));

            userMeal.setFat(String.valueOf(Float.parseFloat(userMeal.getFat()) + Float.parseFloat(query.getString(16)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));

            userMeal.setCarbohydrate(String.valueOf(Float.parseFloat(userMeal.getCarbohydrate()) + Float.parseFloat(query.getString(13)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4))));

            userMeal.setWatter(String.valueOf(Float.parseFloat(userMeal.getWatter()) + Float.parseFloat(query.getString(27)) /
                    Float.parseFloat(query.getString(6)) * Float.parseFloat(query.getString(4)) / 1000));


        }catch (NumberFormatException e){
        }

    }


    private void FindViewByID(View root) {

        text_c = root.findViewById(R.id.text_carb);
        text_f = root.findViewById(R.id.text_fat);
        text_p = root.findViewById(R.id.text_protein);
        text_w = root.findViewById(R.id.text_watter);

        c_layout = root.findViewById(R.id.c_xml);
        f_layout = root.findViewById(R.id.f_xml);
        p_layout = root.findViewById(R.id.p_xml);
        car_layout = root.findViewById(R.id.car_xml);
        w_layout = root.findViewById(R.id.w_xml);

        bottomNavigationView = root.findViewById(R.id.bar);

        ration = root.findViewById(R.id.ration);

        add_food = root.findViewById(R.id.add_food_xml);
        add_weight = root.findViewById(R.id.add_weight_xml);
        add_watter = root.findViewById(R.id.add_watter_xml);
        plus = root.findViewById(R.id.plus_xml);


        eat_c = root.findViewById(R.id.eat_c);
        eat_calorie = root.findViewById(R.id.eat_calorie);
        all_calorie = root.findViewById(R.id.all_calorie);
        eat_f = root.findViewById(R.id.eat_f);
        eat_fat = root.findViewById(R.id.eat_fat);
        all_fat = root.findViewById(R.id.all_fat);
        eatC = root.findViewById(R.id.eatC);
        eat_carbohydrate = root.findViewById(R.id.eat_carbohydrate);
        all_carbohydrate = root.findViewById(R.id.all_carbohydrate);
        eat_p = root.findViewById(R.id.eat_p);
        eat_protein = root.findViewById(R.id.eat_protein);
        all_protein = root.findViewById(R.id.all_protein);
        drink_w = root.findViewById(R.id.drink_w);
        drink_watter = root.findViewById(R.id.drink_watter);
        all_watter = root.findViewById(R.id.all_watter);

        calorie = root.findViewById(R.id.calorie);
        protein = root.findViewById(R.id.protein);
        fat = root.findViewById(R.id.fat);
        carbohydrate = root.findViewById(R.id.carbohydrate);
        watter = root.findViewById(R.id.watter);

        percent_calorie = root.findViewById(R.id.percent_calorie);
        percent_protein = root.findViewById(R.id.percent_protein);
        percent_fat = root.findViewById(R.id.percent_fat);
        percent_carbohydrate = root.findViewById(R.id.percent_carbohydrate);
        percent_watter = root.findViewById(R.id.percent_watter);
        ///
        linear_orange_xml = root.findViewById(R.id.linear_orange_top_xml);
        linear_cv_top = root.findViewById(R.id.linear_cv_top_xml);
        linear_a1 = root.findViewById(R.id.a1);
        linear_black_a1 = root.findViewById(R.id.black_linear_a1);
        linear_orange_a1 = root.findViewById(R.id.orange_linear_a1);
        linear_b1 = root.findViewById(R.id.b1);
        linear_black_b1 = root.findViewById(R.id.black_linear_b1);
        linear_orange_b1 = root.findViewById(R.id.orange_linear_b1);
        linear_a2 = root.findViewById(R.id.a2);
        linear_black_a2 = root.findViewById(R.id.black_linear_a2);
        linear_orange_a2 = root.findViewById(R.id.orange_linear_a2);
        linear_cv_bottom = root.findViewById(R.id.linear_cv_bottom_xml);
        linear_b2 = root.findViewById(R.id.b2);
        linear_black_b2 = root.findViewById(R.id.black_linear_b2);
        linear_orange_b2 = root.findViewById(R.id.orange_linear_b2);
        linear_bottom_menu = root.findViewById(R.id.linearr);
        linear_general = root.findViewById(R.id.linear_dima);
    }
    private void Onclick(Calculator_Main i) {
        all_calorie.setOnClickListener(i);
        eat_carbohydrate.setOnClickListener(i);
        eat_protein.setOnClickListener(i);
        eat_fat.setOnClickListener(i);
        eat_calorie.setOnClickListener(i);
        drink_watter.setOnClickListener(i);
        all_carbohydrate.setOnClickListener(i);
        all_fat.setOnClickListener(i);
        all_protein.setOnClickListener(i);
        all_watter.setOnClickListener(i);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View v) {
        String hint_eat = "За сегодня вам нужно употребать ";
        String hint = "За сегодня вы употребили ";
        switch (v.getId()){
            case R.id.all_calorie:
                hint_eat += all_calorie.getText().toString() + " ккл";
                Toast.makeText(getContext(), hint_eat, Toast.LENGTH_SHORT).show();
                break;
            case R.id.eat_calorie:
                hint += eat_calorie.getText().toString() + " ккл";
                Toast.makeText(getContext(), hint, Toast.LENGTH_SHORT).show();
                break;
            case R.id.eat_fat:
                hint += eat_fat.getText().toString() + " гр жиров";
                Toast.makeText(getContext(), hint, Toast.LENGTH_SHORT).show();
                break;
            case R.id.eat_protein:
                hint += eat_protein.getText().toString() + " гр белков";
                Toast.makeText(getContext(), hint, Toast.LENGTH_SHORT).show();
                break;
            case R.id.eat_carbohydrate:
                hint += eat_carbohydrate.getText().toString() + " гр углеводов";
                Toast.makeText(getContext(), hint, Toast.LENGTH_SHORT).show();
                break;
            case R.id.drink_watter:
                hint += drink_watter.getText().toString() + " л воды";
                Toast.makeText(getContext(), hint, Toast.LENGTH_SHORT).show();
                break;
            case R.id.all_protein:
                hint_eat += all_protein.getText().toString() + " гр белков";
                Toast.makeText(getContext(), hint_eat, Toast.LENGTH_SHORT).show();
                break;
            case R.id.all_carbohydrate:
                hint_eat += all_carbohydrate.getText().toString() + " гр углеводов";
                Toast.makeText(getContext(), hint_eat, Toast.LENGTH_SHORT).show();
                break;
            case R.id.all_fat:
                hint_eat += all_fat.getText().toString() + " гр жиров";
                Toast.makeText(getContext(), hint_eat, Toast.LENGTH_SHORT).show();
                break;
            case R.id.all_watter:
                hint_eat += all_watter.getText().toString() + " л воды";
                Toast.makeText(getContext(), hint_eat, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
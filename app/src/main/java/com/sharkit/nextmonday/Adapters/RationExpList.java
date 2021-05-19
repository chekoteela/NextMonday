package com.sharkit.nextmonday.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;
import com.sharkit.nextmonday.MySQL.DataBasePFC;
import com.sharkit.nextmonday.MySQL.LinkRation;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.DayOfWeek;
import com.sharkit.nextmonday.ui.Calculator.FindMyFood;
import com.sharkit.nextmonday.variables.DataPFC;
import com.sharkit.nextmonday.variables.LocalDataPFC;
import com.sharkit.nextmonday.variables.MealData;
import com.sharkit.nextmonday.variables.PFC_today;
import com.sharkit.nextmonday.variables.UserMeal;

import org.jetbrains.annotations.NotNull;

import java.security.acl.LastOwnerException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class RationExpList extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<UserMeal>> mGroups;
    private ArrayList<Object> mMeal;
    private Context mContext;

    final String TAG = "qwerty";
    LinkRation linkRation;
    SQLiteDatabase sdl;

    TextView calorie, weight, protein, carbohydrate, name_food, fat,
    all_calorie, all_protein, all_carbohydrate, all_fat, meal;
    ImageView plus;
    LinearLayout lin_for_long_press, parent_item;



    ArrayList<UserMeal> list;

    public RationExpList(ArrayList<Object> mMeal , ArrayList<ArrayList<UserMeal>> mGroups, Context mContext) {
        this.mGroups = mGroups;
        this.mContext = mContext;
        this.mMeal = mMeal;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calculator_food_parent_list, null);
        }
        FindView(convertView);

        UserMeal userMeal = new UserMeal();

        parent_item.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                Calendar calendar = Calendar.getInstance();
                Calendar calendar1 = Calendar.getInstance();
                calendar.setTimeInMillis(DayOfWeek.getMillis());

                menu.add("Изменить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (mGroups.get(groupPosition).isEmpty()) {
                        ChangeRationItem(groupPosition);
                        }else {
                            Toast.makeText(mContext,"Невозможно переименовать прием, пока в нем есть добавлиные продукты", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.add("Удалить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) &&
                                calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) &&
                                calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH)){

                            if (mGroups.get(groupPosition).isEmpty()) {
                                DeleteRationItem(groupPosition);
                            }else {
                                Toast.makeText(mContext,"Невозможно удалить прием, пока в нем есть добавлиные продукты", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
            }
        });

        userMeal.setCalorie("0");
        userMeal.setProtein("0");
        userMeal.setWatter("0");
        userMeal.setFat("0");
        userMeal.setCarbohydrate("0");
        userMeal.setWhey_protein("0");
        userMeal.setSoy_protein("0");
        userMeal.setAgg_protein("0");
        userMeal.setCasein_protein("0");
        userMeal.setSaturated_fat("0");
        userMeal.setTrans_fat("0");
        userMeal.setOmega_9("0");
        userMeal.setOmega_6("0");
        userMeal.setOmega_3("0");
        userMeal.setAla("0");
        userMeal.setDha("0");
        userMeal.setEpa("0");
        userMeal.setSimple_carbohydrates("0");
        userMeal.setComplex_carbohydrate("0");
        userMeal.setCellulose("0");
        userMeal.setSalt("0");
        userMeal.setCalcium("0");
        userMeal.setPotassium("0");
        list = new ArrayList<>();

        for (int i = 0; i < mGroups.get(groupPosition).size(); i++){

            SumEatNutrition(i,groupPosition,userMeal);

        }
        all_calorie.setText(String.format("%.0f",Float.parseFloat(userMeal.getCalorie())));
        all_fat.setText(String.format("%.1f",Float.parseFloat(userMeal.getFat())));
        all_protein.setText(String.format("%.1f",Float.parseFloat(userMeal.getProtein())));
        all_carbohydrate.setText(String.format("%.1f",Float.parseFloat(userMeal.getCarbohydrate())));

        meal.setText(String.valueOf(mMeal.get(groupPosition)));

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PFC_today.setPage("Plus");
                OnClickPlus(plus, groupPosition);
            }
        });

        return convertView;
    }

    private void DeleteRationItem(int groupPosition) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference users = db.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/Meal");
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        CollectionReference colRef = fdb.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MealInfo");

        mMeal.remove(groupPosition);
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        data.put(String.valueOf(groupPosition), FieldValue.delete());
        users.child(String.valueOf(groupPosition)).removeValue();
        colRef.document(dateFormat.format(DayOfWeek.getMillis())).update(data);

        for (int i = 0; i < mMeal.size(); i++){
            map.put(String.valueOf(i), mMeal.get(i));
        }

        colRef.document(dateFormat.format(DayOfWeek.getMillis())).set(map);
        users.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_cal_ration);
            }
        });


    }

    private void ChangeRationItem(int groupPosition) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseFirestore fdb = FirebaseFirestore.getInstance();
        DatabaseReference users = db.getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Setting/Calculator/Meal");
        CollectionReference colRef = fdb.collection("Users/" + mAuth.getCurrentUser().getUid() + "/MealInfo");
        AlertDialog.Builder dialog = new AlertDialog.Builder((Activity)mContext, R.style.CustomAlertDialog);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View select = inflater.inflate(R.layout.calculator_alert_add_meal, null);

        EditText new_meal = select.findViewById(R.id.meal);

        dialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Map<String, Object> data = new HashMap<>();
                data.put(String.valueOf(groupPosition), new_meal.getText().toString());
                colRef.document(dateFormat.format(DayOfWeek.getMillis())).update(data);
                    users.updateChildren(data, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                            NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
                            navController.navigate(R.id.nav_cal_ration);
                        }
                    });
            }
        });

        dialog.setView(select);
        dialog.show();


    }

    public void OnClickPlus(View v, int position){
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        PFC_today.setPage(String.valueOf(mMeal.get(position)));
        navController.navigate(R.id.nav_cal_find_food_by_name);
    }
    @SuppressLint("DefaultLocale")
    private void SumEatNutrition(int i, int groupPosition, UserMeal userMeal) {

        try {
            userMeal.setCalorie(String.valueOf ( Float.parseFloat(userMeal.getCalorie()) +  (Float.parseFloat( mGroups.get(groupPosition).get(i).getCalorie()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber())));

            userMeal.setProtein(String.valueOf ( Float.parseFloat(userMeal.getProtein()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getProtein()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setWatter(String.valueOf (Float.parseFloat(userMeal.getWatter()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getWatter()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setFat(String.valueOf (Float.parseFloat(userMeal.getFat()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getFat()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setCarbohydrate(String.valueOf (Float.parseFloat(userMeal.getCarbohydrate()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getCarbohydrate()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setWhey_protein(String.valueOf (Float.parseFloat(userMeal.getWhey_protein()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getWhey_protein()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setSoy_protein(String.valueOf (Float.parseFloat(userMeal.getSoy_protein()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getSoy_protein()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setAgg_protein(String.valueOf (Float.parseFloat(userMeal.getAgg_protein()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getAgg_protein()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setCasein_protein(String.valueOf (Float.parseFloat(userMeal.getCasein_protein()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getCasein_protein()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setSaturated_fat(String.valueOf (Float.parseFloat(userMeal.getSaturated_fat()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getSaturated_fat()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setTrans_fat(String.valueOf (Float.parseFloat(userMeal.getTrans_fat()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getTrans_fat()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setOmega_9(String.valueOf (Float.parseFloat(userMeal.getOmega_9()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getOmega_9()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setOmega_6(String.valueOf (Float.parseFloat(userMeal.getOmega_6()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getOmega_6()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setOmega_3(String.valueOf (Float.parseFloat(userMeal.getOmega_3()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getOmega_3()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setAla(String.valueOf (Float.parseFloat(userMeal.getAla()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getAla()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setDha(String.valueOf (Float.parseFloat(userMeal.getDha()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getDha()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setEpa(String.valueOf (Float.parseFloat(userMeal.getEpa()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getEpa()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setSimple_carbohydrates(String.valueOf (Float.parseFloat(userMeal.getSimple_carbohydrates()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getSimple_carbohydrates()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setComplex_carbohydrate(String.valueOf (Float.parseFloat(userMeal.getComplex_carbohydrate()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getComplex_carbohydrate()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setCellulose(String.valueOf (Float.parseFloat(userMeal.getCellulose()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getCellulose()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setSalt(String.valueOf (Float.parseFloat(userMeal.getSalt()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getSalt()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setCalcium(String.valueOf (Float.parseFloat(userMeal.getCalcium()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getCalcium()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

            userMeal.setPotassium(String.valueOf (Float.parseFloat(userMeal.getPotassium()) +  Float.parseFloat( mGroups.get(groupPosition).get(i).getPotassium()) /
                    Float.parseFloat(mGroups.get(groupPosition).get(i).getPortion()) * mGroups.get(groupPosition).get(i).getNumber()));

        }catch (NumberFormatException e){

        }
    }



    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calculator_food_item_list, null);
        }

        FindView(convertView);

        name_food.setText(mGroups.get(groupPosition).get(childPosition).getName());
        weight.setText(mGroups.get(groupPosition).get(childPosition).getNumber() + " gram");

        carbohydrate.setText("Carbohydrate: " + String.format("%.1f", Float.parseFloat( mGroups.get(groupPosition).get(childPosition).getCarbohydrate()) /
                Float.parseFloat(mGroups.get(groupPosition).get(childPosition).getPortion()) * mGroups.get(groupPosition).get(childPosition).getNumber()));

        protein.setText("Protein: " + String.format("%.1f", Float.parseFloat( mGroups.get(groupPosition).get(childPosition).getProtein()) /
                Float.parseFloat(mGroups.get(groupPosition).get(childPosition).getPortion()) * mGroups.get(groupPosition).get(childPosition).getNumber()));

        fat.setText("Fat: " + String.format("%.1f", Float.parseFloat( mGroups.get(groupPosition).get(childPosition).getFat()) /
                Float.parseFloat(mGroups.get(groupPosition).get(childPosition).getPortion()) * mGroups.get(groupPosition).get(childPosition).getNumber()));

        calorie.setText((String.format("%.0f", Float.parseFloat( mGroups.get(groupPosition).get(childPosition).getCalorie()) /
                Float.parseFloat(mGroups.get(groupPosition).get(childPosition).getPortion()) * mGroups.get(groupPosition).get(childPosition).getNumber())) + " калл");

        lin_for_long_press.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Изменить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
                        PFC_today.setPage("Update");
                        WriteLocal(groupPosition, childPosition);
                        navController.navigate(R.id.nav_cal_my_food);
                        return true;
                    }
                });
                menu.add("Удалить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DeleteData(groupPosition, childPosition);
                        return true;
                    }
                });
            }
        });

        return convertView;
    }

    private void DeleteData(int groupPosition, int childPosition) {
        linkRation = new LinkRation(getApplicationContext());
        sdl = linkRation.getReadableDatabase();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        CollectionReference colRef = db.collection("Users/" + mAuth.getCurrentUser().getUid() + "/Meal");
        colRef.document(String.valueOf(mGroups.get(groupPosition).get(childPosition).getDate_millis())).delete();
        sdl.execSQL("DELETE FROM " + linkRation.TABLE + " WHERE " + linkRation.COLUMN_DATE_MILLIS + " = '" + mGroups.get(groupPosition).get(childPosition).getDate_millis() + "'");

        NavController navController = Navigation.findNavController((Activity)mContext, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_cal_ration);
    }

    private void WriteLocal(int groupPosition, int childPosition) {
        LocalDataPFC.setBar_code(mGroups.get(groupPosition).get(childPosition).getCode());
        LocalDataPFC.setPortion(mGroups.get(groupPosition).get(childPosition).getPortion());
        LocalDataPFC.setNumber(mGroups.get(groupPosition).get(childPosition).getNumber());
        LocalDataPFC.setPotassium(mGroups.get(groupPosition).get(childPosition).getPotassium());
        LocalDataPFC.setSalt(mGroups.get(groupPosition).get(childPosition).getSalt());
        LocalDataPFC.setCalcium(mGroups.get(groupPosition).get(childPosition).getCalcium());
        LocalDataPFC.setCellulose(mGroups.get(groupPosition).get(childPosition).getCellulose());
        LocalDataPFC.setWatter(mGroups.get(groupPosition).get(childPosition).getWatter());
        LocalDataPFC.setCasein_protein(mGroups.get(groupPosition).get(childPosition).getCasein_protein());
        LocalDataPFC.setAgg_protein(mGroups.get(groupPosition).get(childPosition).getAgg_protein());
        LocalDataPFC.setSoy_protein(mGroups.get(groupPosition).get(childPosition).getSoy_protein());
        LocalDataPFC.setWhey_protein(mGroups.get(groupPosition).get(childPosition).getWhey_protein());
        LocalDataPFC.setProtein(mGroups.get(groupPosition).get(childPosition).getProtein());
        LocalDataPFC.setComplex_carbohydrate(mGroups.get(groupPosition).get(childPosition).getComplex_carbohydrate());
        LocalDataPFC.setSimple_carbohydrates(mGroups.get(groupPosition).get(childPosition).getSimple_carbohydrates());
        LocalDataPFC.setCarbohydrate(mGroups.get(groupPosition).get(childPosition).getCarbohydrate());
        LocalDataPFC.setEpa(mGroups.get(groupPosition).get(childPosition).getEpa());
        LocalDataPFC.setDha(mGroups.get(groupPosition).get(childPosition).getDha());
        LocalDataPFC.setAla(mGroups.get(groupPosition).get(childPosition).getAla());
        LocalDataPFC.setOmega_3(mGroups.get(groupPosition).get(childPosition).getOmega_3());
        LocalDataPFC.setOmega_6(mGroups.get(groupPosition).get(childPosition).getOmega_6());
        LocalDataPFC.setOmega_9(mGroups.get(groupPosition).get(childPosition).getOmega_9());
        LocalDataPFC.setTrans_fat(mGroups.get(groupPosition).get(childPosition).getTrans_fat());
        LocalDataPFC.setSaturated_fat(mGroups.get(groupPosition).get(childPosition).getSaturated_fat());
        LocalDataPFC.setFat(mGroups.get(groupPosition).get(childPosition).getFat());
        LocalDataPFC.setCalorie(mGroups.get(groupPosition).get(childPosition).getCalorie());
        LocalDataPFC.setName(mGroups.get(groupPosition).get(childPosition).getName());
        LocalDataPFC.setDate_millis(mGroups.get(groupPosition).get(childPosition).getDate_millis());
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void FindView(View convertView) {

        fat = convertView.findViewById(R.id.fat);
        calorie = convertView.findViewById(R.id.calorie);
        carbohydrate = convertView.findViewById(R.id.carbohydrate);
        protein = convertView.findViewById(R.id.protein);
        name_food = convertView.findViewById(R.id.name_product);
        weight = convertView.findViewById(R.id.weight_food);
        plus = convertView.findViewById(R.id.plus);
        parent_item = convertView.findViewById(R.id.parent_item);


        lin_for_long_press = convertView.findViewById(R.id.liner_for_long_press);
        all_fat = convertView.findViewById(R.id.all_fat);
        all_protein = convertView.findViewById(R.id.all_protein);
        all_calorie = convertView.findViewById(R.id.all_calorie);
        all_carbohydrate = convertView.findViewById(R.id.all_carbohydrate);
        meal = convertView.findViewById(R.id.meal_name);

    }
}

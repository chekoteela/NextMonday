package Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.MessageFromUsers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static com.sharkit.nextmonday.MainActivity.isValidEmail;

public class Feedback extends Fragment {
    FirebaseAuth mAuth;
    FirebaseDatabase fdb;
    DatabaseReference users;
    int pos = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int h = metrics.heightPixels;


        EditText name = root.findViewById(R.id.name);
        EditText mail = root.findViewById(R.id.email);
        EditText textSMS = root.findViewById(R.id.text_sms);
        Spinner spinner = root.findViewById(R.id.spinner);
        Button send = root.findViewById(R.id.send_sms);

        LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(-1,h/16);
        l_params.setMargins(h/50,h/85,h/50,h/85);
        name.setLayoutParams(l_params);
        mail.setLayoutParams(l_params);
        send.setLayoutParams(l_params);
        LinearLayout.LayoutParams l_params1 = new LinearLayout.LayoutParams(-1,h/6);
        l_params1.setMargins(h/50,h/85,h/50,h/85);
        textSMS.setLayoutParams(l_params1);


        mAuth = FirebaseAuth.getInstance();
        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mail.setText(mAuth.getCurrentUser().getEmail());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.spinner_feedback,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidEmail(mail.getText())){
                    Toast.makeText(getContext(),"Введите коректный формат почты",Toast.LENGTH_SHORT).show();

                    return;
                }

                if(TextUtils.isEmpty(name.getText())){
                    Toast.makeText(getContext(),"Введите ваше имя",Toast.LENGTH_SHORT).show();

                    return;
                }

                if(TextUtils.isEmpty(textSMS.getText())){
                    Toast.makeText(getContext(),"Введите ваше сообщение",Toast.LENGTH_SHORT).show();

                    return;
                }
                if(!hasConnection(getContext())){
                    Toast.makeText(getContext(),"Проверьте подключение к интернету",Toast.LENGTH_SHORT).show();

                    return;
                }

                WritToFirebase(pos, name.getText().toString(), mail.getText().toString(), textSMS.getText().toString());
            }
        });

        return root;
    }

    private void WritToFirebase( int position, String name, String mail, String text) {
        Calendar calendar = Calendar.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        String time = calendar.get(Calendar.DAY_OF_WEEK) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR)+
                " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " " + mAuth.getCurrentUser().getUid();

        MessageFromUsers message = new MessageFromUsers();

        message.setMail(mail);
        message.setName(name);
        message.setText_massage(text);

        if(position == 0) {
            users = fdb.getReference("MessagesFromUsers/Errors/" + time);
            users.setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(),"Ваше сообщение в ближайшее время будет рассмотрено",Toast.LENGTH_SHORT).show();

                   navController.navigate(R.id.nav_diary);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Проверьте подключение к интернету или попробуйте позже",Toast.LENGTH_SHORT).show();

                }
            });
        }
        if(position == 1) {

            users = fdb.getReference("MessagesFromUsers/Advise/" + time);
            users.setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(),"Ваше сообщение в ближайшее время будет рассмотрено",Toast.LENGTH_SHORT).show();

                    navController.navigate(R.id.nav_diary);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Проверьте подключение к интернету или попробуйте позже",Toast.LENGTH_SHORT).show();

                }
            });
        }
        if(position == 2) {
            Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
            users = fdb.getReference("MessagesFromUsers/Question/" + time);
            users.setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(),"Ваше сообщение в ближайшее время будет рассмотрено",Toast.LENGTH_SHORT).show();

                    navController.navigate(R.id.nav_diary);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Проверьте подключение к интернету или попробуйте позже",Toast.LENGTH_SHORT).show();

                }
            });
        }
        if(position == 3) {
            users = fdb.getReference("MessagesFromUsers/Like/" + time);
            users.setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(),"Ваше сообщение в ближайшее время будет рассмотрено",Toast.LENGTH_SHORT).show();

                    navController.navigate(R.id.nav_diary);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Проверьте подключение к интернету или попробуйте позже",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
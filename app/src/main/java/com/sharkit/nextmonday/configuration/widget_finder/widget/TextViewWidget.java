package com.sharkit.nextmonday.configuration.widget_finder.widget;

import android.view.View;
import android.widget.TextView;

import com.sharkit.nextmonday.R;

public class TextViewWidget {

    private final View view;

    public TextViewWidget(View view){
        this.view = view;
    }

    public TextView getForgotPassword() {
        return view.findViewById(R.id.forgot_password_xml);
    }

    public TextView getPolicyText() {
        return view.findViewById(R.id.policy_text_xml);
    }

    public TextView getDayName(){
        return view.findViewById(R.id.day_name_xml);
    }

    public TextView getMonth(){
        return view.findViewById(R.id.month_xml);
    }

    public TextView getDayNumber(){
        return view.findViewById(R.id.day_number_xml);
    }

    public TextView getTextTask(){
        return view.findViewById(R.id.text_task_xml);
    }
    public TextView getTimeTask(){
        return view.findViewById(R.id.time_task_xml);
    }
}

package com.sharkit.nextmonday.configuration.widget_finder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sharkit.nextmonday.R;

import lombok.Getter;

@SuppressLint("StaticFieldLeak")
@Getter
public final class WidgetContainer {

    private final View view;
    private final RegisterMenuWidget registerMenuWidget;
    private final AuthorisationMenuWidget authorisationMenuWidget;
    private final TaskCreatorWidget taskCreatorWidget;

    private WidgetContainer(View view) {
        this.view = view;
        this.registerMenuWidget = new RegisterMenuWidget();
        this.authorisationMenuWidget = new AuthorisationMenuWidget();
        this.taskCreatorWidget = new TaskCreatorWidget();
    }

    public static WidgetContainer newInstance(View view) {
        return new WidgetContainer(view);
    }

    @Getter
    public class RegisterMenuWidget {

        private final EditText userName;
        private final EditText userLastName;
        private final EditText email;
        private final EditText password;
        private final Button createAccount;
        private final Button signIn;
        private final CheckBox policy;
        private final TextView policyText;

        public RegisterMenuWidget() {
            this.userName = view.findViewById(R.id.user_name_id);
            this.email = view.findViewById(R.id.email_id);
            this.password = view.findViewById(R.id.password_id);
            this.signIn = view.findViewById(R.id.sign_in_id);
            this.policy = view.findViewById(R.id.policy_id);
            this.createAccount = view.findViewById(R.id.create_account_id);
            this.userLastName = view.findViewById(R.id.user_last_name_id);
            this.policyText = view.findViewById(R.id.policy_text_id);
        }
    }

    @Getter
    public class AuthorisationMenuWidget {

        private final Button createAccount;
        private final Button signIn;
        private final EditText email;
        private final EditText password;
        private final ImageView google;

        public AuthorisationMenuWidget() {
            this.password = view.findViewById(R.id.password_id);
            this.google = view.findViewById(R.id.google_id);
            this.email = view.findViewById(R.id.email_id);
            this.signIn = view.findViewById(R.id.sign_in_id);
            this.createAccount = view.findViewById(R.id.create_account_id);

        }
    }

    @Getter
    public class TaskCreatorWidget {

        private final EditText nameOfTask;
        private final SwitchMaterial takeTime;
        private final SwitchMaterial repeat;
        private final EditText description;
        private final Button create;
        private final AdView adView;
        private final RepeatersWidget repeatersWidget;

        public TaskCreatorWidget() {

            this.nameOfTask = view.findViewById(R.id.name_of_task_id);
            this.takeTime = view.findViewById(R.id.take_time_id);
            this.repeat = view.findViewById(R.id.repeat_id);
            this.description = view.findViewById(R.id.description_id);
            this.create = view.findViewById(R.id.create_id);
            this.adView = view.findViewById(R.id.ad_view_id);

            this.repeatersWidget = new RepeatersWidget();
        }

        @Getter
        public class RepeatersWidget {

            private final EveryDayWidget everyDayWidget;
            private final RadioGroup radioGroup;
            private final LinearLayout checkBoxList;
            private final RadioButton selectDay;
            private final RadioButton everyDay;

            public RepeatersWidget() {
                this.selectDay = view.findViewById(R.id.select_day_id);
                this.everyDay = view.findViewById(R.id.repeat_everyday_id);
                this.radioGroup = view.findViewById(R.id.repeat_radio_group_id);
                this.checkBoxList = view.findViewById(R.id.checkbox_list_id);

                this.everyDayWidget = new EveryDayWidget();
            }

            @Getter
            public class EveryDayWidget {

                private final CheckBox monday;
                private final CheckBox tuesday;
                private final CheckBox wednesday;
                private final CheckBox thursday;
                private final CheckBox friday;
                private final CheckBox saturday;
                private final CheckBox sunday;

                public EveryDayWidget() {
                    this.monday = view.findViewById(R.id.monday_id);
                    this.tuesday = view.findViewById(R.id.tuesday_id);
                    this.wednesday = view.findViewById(R.id.wednesday_id);
                    this.thursday = view.findViewById(R.id.thursday_id);
                    this.friday = view.findViewById(R.id.friday_id);
                    this.saturday = view.findViewById(R.id.saturday_id);
                    this.sunday = view.findViewById(R.id.sunday_id);
                }
            }

        }
    }

}

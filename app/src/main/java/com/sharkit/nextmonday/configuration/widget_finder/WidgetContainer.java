package com.sharkit.nextmonday.configuration.widget_finder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sharkit.nextmonday.R;

import lombok.Getter;

@SuppressLint("StaticFieldLeak")
public final class WidgetContainer {

    private final View view;

    private WidgetContainer(View view) {
        this.view = view;
    }

    public RegisterMenuWidget getRegisterMenuWidget() {
        return new RegisterMenuWidget();
    }

    public AuthorisationMenuWidget getAuthorisationMenuWidget() {
        return new AuthorisationMenuWidget();
    }

    public TaskCreatorWidget getTaskCreatorWidget() {
        return new TaskCreatorWidget();
    }

    public DiaryMainWidget getDiaryMainWidget() {
        return new DiaryMainWidget();
    }

    public static WidgetContainer newInstance(View view) {
        return new WidgetContainer(view);
    }

    @Getter
    public class DiaryMainWidget {

        private final ExpandableListView expandableListView;
        private final DiaryMainParentWidget parentWidget;

        public DiaryMainWidget() {
            this.expandableListView = view.findViewById(R.id.diary_main_list_id);

            this.parentWidget = new DiaryMainParentWidget();
        }

        @Getter
        public class DiaryMainParentWidget {

            private final DiaryMainChildWidget childWidget;
            private final TextView dayName;
            private final TextView dayNumber;
            private final TextView monthName;
            private final TextView completedTask;
            private final TextView allTask;
            private final ProgressBar taskProgress;
            private final ImageView create;

            public DiaryMainParentWidget() {
                this.dayName = view.findViewById(R.id.day_name_id);
                this.dayNumber = view.findViewById(R.id.day_number_id);
                this.monthName = view.findViewById(R.id.month_name_id);
                this.completedTask = view.findViewById(R.id.completed_task_id);
                this.allTask = view.findViewById(R.id.all_tasks_id);
                this.taskProgress = view.findViewById(R.id.task_progress_id);
                this.create = view.findViewById(R.id.create_id);

                this.childWidget = new DiaryMainChildWidget();
            }

            @Getter
            public class DiaryMainChildWidget {

                private final CheckBox getByTask;
                private final TextView textTask;
                private final TextView timeTask;

                public DiaryMainChildWidget() {
                    this.getByTask = view.findViewById(R.id.complete_task_id);
                    this.textTask = view.findViewById(R.id.text_task_id);
                    this.timeTask = view.findViewById(R.id.time_task_id);
                }
            }
        }

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

package com.sharkit.nextmonday.configuration.widget_finder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.sharkit.nextmonday.R;

import lombok.Getter;

@SuppressLint("StaticFieldLeak")
public final class WidgetContainer {

    private final View view;

    private WidgetContainer(View view) {
        this.view = view;
    }

    public Dialog getDialog(){
        return new Dialog();
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

    public DiaryCalendarWidget getDiaryCalendarWidget() {
        return new DiaryCalendarWidget();
    }

    public DiaryUpdateTaskWidget getDiaryUpdateTaskWidget() {
        return new DiaryUpdateTaskWidget();
    }

    public DiaryNotateWidget getDiaryNotateWidget() {
        return new DiaryNotateWidget();
    }

    public static WidgetContainer newInstance(View view) {
        return new WidgetContainer(view);
    }

    @Getter
    public class DiaryNotateWidget {
        private final EditText search;
        private final ListView notateList;
        private final Button add;
        private final FolderItemWidget folderItemWidget;
        private final NotateItemWidget notateItemWidget;

        public DiaryNotateWidget() {
            this.folderItemWidget = new FolderItemWidget();
            this.notateItemWidget = new NotateItemWidget();

            this.search = view.findViewById(R.id.search_id);
            this.notateList = view.findViewById(R.id.diary_main_list_id);
            this.add = view.findViewById(R.id.add_id);
        }

        @Getter
        public class FolderItemWidget {
            private final TextView name;
            private final TextView folderType;
            private final LinearLayout parentItem;

            public FolderItemWidget() {
                this.name = view.findViewById(R.id.name_id);
                this.folderType = view.findViewById(R.id.folder_type_id);
                this.parentItem = view.findViewById(R.id.folder_parent_id);
            }
        }

        @Getter
        public class NotateItemWidget {
            private final TextView name;
            private final TextView notateWidget;
            private final LinearLayout parentItem;

            public NotateItemWidget() {
                this.name = view.findViewById(R.id.name_id);
                this.notateWidget = view.findViewById(R.id.type_of_notate_id);
                this.parentItem = view.findViewById(R.id.notate_parent_id);
            }
        }
    }
    
    @Getter
    public class DiaryUpdateTaskWidget {

        private final EditText nameOfTask;
        private final EditText description;
        private final TextView listOfDays;
        private final TextView currentAlarm;
        private final SwitchMaterial takeTime;
        private final SwitchMaterial repeat;
        private final Button save;

        public DiaryUpdateTaskWidget() {
            this.nameOfTask = view.findViewById(R.id.name_of_task_id);
            this.listOfDays = view.findViewById(R.id.list_of_days_id);
            this.currentAlarm = view.findViewById(R.id.rang_time_id);
            this.takeTime = view.findViewById(R.id.take_time_id);
            this.repeat = view.findViewById(R.id.repeat_id);
            this.description = view.findViewById(R.id.description_id);
            this.save = view.findViewById(R.id.save_id);
        }
    }

    @Getter
    public class DiaryCalendarWidget {

        private final CalendarView calendarView;
        private final TextView dateText;

        public DiaryCalendarWidget() {
            this.calendarView = view.findViewById(R.id.calendar_id);
            this.dateText = view.findViewById(R.id.date_id);
        }
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

                private final RelativeLayout childItem;
                private final CheckBox getByTask;
                private final TextView textTask;
                private final TextView timeTask;

                public DiaryMainChildWidget() {
                    this.getByTask = view.findViewById(R.id.complete_task_id);
                    this.textTask = view.findViewById(R.id.text_task_id);
                    this.timeTask = view.findViewById(R.id.time_task_id);
                    this.childItem = view.findViewById(R.id.child_item_id);
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
        private final TextView forgotPassword;

        public AuthorisationMenuWidget() {
            this.password = view.findViewById(R.id.password_id);
            this.google = view.findViewById(R.id.google_id);
            this.email = view.findViewById(R.id.email_id);
            this.signIn = view.findViewById(R.id.sign_in_id);
            this.createAccount = view.findViewById(R.id.create_account_id);
            this.forgotPassword = view.findViewById(R.id.forgot_password_id);
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

        public TaskCreatorWidget() {

            this.nameOfTask = view.findViewById(R.id.name_of_task_id);
            this.takeTime = view.findViewById(R.id.take_time_id);
            this.repeat = view.findViewById(R.id.repeat_id);
            this.description = view.findViewById(R.id.description_id);
            this.create = view.findViewById(R.id.create_id);
            this.adView = view.findViewById(R.id.ad_view_id);
        }

    }

    @Getter
    public class Dialog {

        private final RepeatersWidget repeatersWidget;
        private final DialogChangeSubjectWidget dialogChangeSubjectWidget;
        private final DialogDeleteSubjectWidget dialogDeleteSubjectWidget;
        private final DialogCreateNotateWidget dialogCreateNotateWidget;

        public Dialog() {
            this.repeatersWidget = new RepeatersWidget();
            this.dialogChangeSubjectWidget = new DialogChangeSubjectWidget();
            this.dialogDeleteSubjectWidget = new DialogDeleteSubjectWidget();
            this.dialogCreateNotateWidget = new DialogCreateNotateWidget();
        }

        @Getter
        public class DialogCreateNotateWidget {

            private final TextInputEditText name;
            private final Spinner typeOfKeeping;
            private final Spinner typeOfNotate;
            private final Button create;

            public DialogCreateNotateWidget() {
                this.name = view.findViewById(R.id.name_id);
                this.typeOfKeeping = view.findViewById(R.id.type_of_keeping_id);
                this.typeOfNotate = view.findViewById(R.id.type_of_notate_id);
                this.create = view.findViewById(R.id.create_id);
            }
        }

        @Getter
        public class DialogChangeSubjectWidget {
             private final Button changeAll;
             private final Button changeOne;

            public DialogChangeSubjectWidget() {
                this.changeAll = view.findViewById(R.id.change_all_id);
                this.changeOne = view.findViewById(R.id.change_one_id);
            }
        }
        @Getter
        public class DialogDeleteSubjectWidget {
             private final Button deleteAll;
             private final Button deleteOne;

            public DialogDeleteSubjectWidget() {
                this.deleteAll = view.findViewById(R.id.delete_all_id);
                this.deleteOne = view.findViewById(R.id.delete_one_id);
            }
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

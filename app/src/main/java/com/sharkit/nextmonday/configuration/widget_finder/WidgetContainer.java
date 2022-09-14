package com.sharkit.nextmonday.configuration.widget_finder;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.sharkit.nextmonday.R;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class WidgetContainer {

    private final View view;

    public Dialog getDialog(){
        return new Dialog();
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

    public DiaryNotateRecipeWidget getDiaryNotateRecipeWidget() {
        return new DiaryNotateRecipeWidget();
    }

    public DiaryPurchaseWidget getDiaryPurchaseWidget() {
        return new DiaryPurchaseWidget();
    }

    public static WidgetContainer newInstance(final View view) {
        return new WidgetContainer(view);
    }

    @Getter
    public class DiaryPurchaseWidget {
        private final FloatingActionButton addPurchase;
        private final EditText name;
        private final EditText description;
        private final ListView purchaseList;
        private final Button save;
        private final PurchaseItemWidget purchaseItemWidget;

        public DiaryPurchaseWidget() {
            this.name = WidgetContainer.this.view.findViewById(R.id.edit_name_id);
            this.description = WidgetContainer.this.view.findViewById(R.id.edit_description_id);
            this.purchaseList = WidgetContainer.this.view.findViewById(R.id.purchase_list_id);
            this.save = WidgetContainer.this.view.findViewById(R.id.save_id);
            this.addPurchase = WidgetContainer.this.view.findViewById(R.id.add_purchase_id);

            this.purchaseItemWidget = new PurchaseItemWidget();
        }

        @Getter
        public class PurchaseItemWidget {
            private final TextView name;
            private final TextView description;
            private final LinearLayout item;
            private final CheckBox complete;

            public PurchaseItemWidget() {
                this.name = WidgetContainer.this.view.findViewById(R.id.purchase_name_id);
                this.description = WidgetContainer.this.view.findViewById(R.id.purchase_description_id);
                this.item = WidgetContainer.this.view.findViewById(R.id.purchase_item_id);
                this.complete = WidgetContainer.this.view.findViewById(R.id.complete_item_id);
            }
        }
    }

    @Getter
    public class DiaryNotateRecipeWidget {
        private final FloatingActionButton addFood;
        private final EditText name;
        private final EditText description;
        private final ImageView recipeImage;
        private final ListView recipeList;
        private final Button save;
        private final Button searchImage;
        private final RecipeItemWidget recipeItemWidget;

        public DiaryNotateRecipeWidget() {
            this.name = WidgetContainer.this.view.findViewById(R.id.edit_name_id);
            this.description = WidgetContainer.this.view.findViewById(R.id.edit_description_id);
            this.recipeImage = WidgetContainer.this.view.findViewById(R.id.recipe_image_id);
            this.recipeList = WidgetContainer.this.view.findViewById(R.id.recipe_list_id);
            this.save = WidgetContainer.this.view.findViewById(R.id.save_id);
            this.addFood = WidgetContainer.this.view.findViewById(R.id.add_food_id);
            this.searchImage = WidgetContainer.this.view.findViewById(R.id.search_image_id);

            this.recipeItemWidget = new RecipeItemWidget();
        }

        @Getter
        public class RecipeItemWidget {
            private final TextView name;
            private final TextView description;
            private final LinearLayout item;

            public RecipeItemWidget() {
                this.name = WidgetContainer.this.view.findViewById(R.id.food_name_id);
                this.description = WidgetContainer.this.view.findViewById(R.id.food_description_id);
                this.item = WidgetContainer.this.view.findViewById(R.id.recipe_item_id);
            }
        }
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

            this.search = WidgetContainer.this.view.findViewById(R.id.search_id);
            this.notateList = WidgetContainer.this.view.findViewById(R.id.diary_main_list_id);
            this.add = WidgetContainer.this.view.findViewById(R.id.add_id);
        }

        @Getter
        public class FolderItemWidget {
            private final TextView name;
            private final TextView folderType;
            private final LinearLayout parentItem;

            public FolderItemWidget() {
                this.name = WidgetContainer.this.view.findViewById(R.id.name_id);
                this.folderType = WidgetContainer.this.view.findViewById(R.id.folder_type_id);
                this.parentItem = WidgetContainer.this.view.findViewById(R.id.folder_parent_id);
            }
        }

        @Getter
        public class NotateItemWidget {
            private final TextView name;
            private final TextView notateWidget;
            private final LinearLayout parentItem;

            public NotateItemWidget() {
                this.name = WidgetContainer.this.view.findViewById(R.id.name_id);
                this.notateWidget = WidgetContainer.this.view.findViewById(R.id.type_of_notate_id);
                this.parentItem = WidgetContainer.this.view.findViewById(R.id.notate_parent_id);
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
            this.nameOfTask = WidgetContainer.this.view.findViewById(R.id.name_of_task_id);
            this.listOfDays = WidgetContainer.this.view.findViewById(R.id.list_of_days_id);
            this.currentAlarm = WidgetContainer.this.view.findViewById(R.id.rang_time_id);
            this.takeTime = WidgetContainer.this.view.findViewById(R.id.take_time_id);
            this.repeat = WidgetContainer.this.view.findViewById(R.id.repeat_id);
            this.description = WidgetContainer.this.view.findViewById(R.id.description_id);
            this.save = WidgetContainer.this.view.findViewById(R.id.save_id);
        }
    }

    @Getter
    public class DiaryCalendarWidget {

        private final CalendarView calendarView;
        private final TextView dateText;

        public DiaryCalendarWidget() {
            this.calendarView = WidgetContainer.this.view.findViewById(R.id.calendar_id);
            this.dateText = WidgetContainer.this.view.findViewById(R.id.date_id);
        }
    }

    @Getter
    public class DiaryMainWidget {

        private final ExpandableListView expandableListView;
        private final DiaryMainParentWidget parentWidget;

        public DiaryMainWidget() {
            this.expandableListView = WidgetContainer.this.view.findViewById(R.id.diary_main_list_id);

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
                this.dayName = WidgetContainer.this.view.findViewById(R.id.day_name_id);
                this.dayNumber = WidgetContainer.this.view.findViewById(R.id.day_number_id);
                this.monthName = WidgetContainer.this.view.findViewById(R.id.month_name_id);
                this.completedTask = WidgetContainer.this.view.findViewById(R.id.completed_task_id);
                this.allTask = WidgetContainer.this.view.findViewById(R.id.all_tasks_id);
                this.taskProgress = WidgetContainer.this.view.findViewById(R.id.task_progress_id);
                this.create = WidgetContainer.this.view.findViewById(R.id.create_id);

                this.childWidget = new DiaryMainChildWidget();
            }

            @Getter
            public class DiaryMainChildWidget {

                private final RelativeLayout childItem;
                private final CheckBox getByTask;
                private final TextView textTask;
                private final TextView timeTask;

                public DiaryMainChildWidget() {
                    this.getByTask = WidgetContainer.this.view.findViewById(R.id.complete_task_id);
                    this.textTask = WidgetContainer.this.view.findViewById(R.id.text_task_id);
                    this.timeTask = WidgetContainer.this.view.findViewById(R.id.time_task_id);
                    this.childItem = WidgetContainer.this.view.findViewById(R.id.child_item_id);
                }
            }
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

            this.nameOfTask = WidgetContainer.this.view.findViewById(R.id.name_of_task_id);
            this.takeTime = WidgetContainer.this.view.findViewById(R.id.take_time_id);
            this.repeat = WidgetContainer.this.view.findViewById(R.id.repeat_id);
            this.description = WidgetContainer.this.view.findViewById(R.id.description_id);
            this.create = WidgetContainer.this.view.findViewById(R.id.create_id);
            this.adView = WidgetContainer.this.view.findViewById(R.id.ad_view_id);
        }

    }

    @Getter
    public class Dialog {

        private final RepeatersWidget repeatersWidget;
        private final DialogChangeSubjectWidget dialogChangeSubjectWidget;
        private final DialogDeleteSubjectWidget dialogDeleteSubjectWidget;
        private final DialogCreateNotateWidget dialogCreateNotateWidget;
        private final TemplateAddItemWidget templateAddItemWidget;

        public Dialog() {
            this.repeatersWidget = new RepeatersWidget();
            this.dialogChangeSubjectWidget = new DialogChangeSubjectWidget();
            this.dialogDeleteSubjectWidget = new DialogDeleteSubjectWidget();
            this.dialogCreateNotateWidget = new DialogCreateNotateWidget();
            this.templateAddItemWidget = new TemplateAddItemWidget();
        }

        @Getter
        public class TemplateAddItemWidget {
            private final EditText name;
            private final EditText description;

            public TemplateAddItemWidget() {
                this.name = WidgetContainer.this.view.findViewById(R.id.food_name_id);
                this.description = WidgetContainer.this.view.findViewById(R.id.food_description_id);
            }
        }

        @Getter
        public class DialogCreateNotateWidget {

            private final TextInputEditText name;
            private final Spinner typeOfKeeping;
            private final Spinner typeOfNotate;
            private final Button create;

            public DialogCreateNotateWidget() {
                this.name = WidgetContainer.this.view.findViewById(R.id.name_id);
                this.typeOfKeeping = WidgetContainer.this.view.findViewById(R.id.type_of_keeping_id);
                this.typeOfNotate = WidgetContainer.this.view.findViewById(R.id.type_of_notate_id);
                this.create = WidgetContainer.this.view.findViewById(R.id.create_id);
            }
        }

        @Getter
        public class DialogChangeSubjectWidget {
             private final Button changeAll;
             private final Button changeOne;

            public DialogChangeSubjectWidget() {
                this.changeAll = WidgetContainer.this.view.findViewById(R.id.change_all_id);
                this.changeOne = WidgetContainer.this.view.findViewById(R.id.change_one_id);
            }
        }
        @Getter
        public class DialogDeleteSubjectWidget {
             private final Button deleteAll;
             private final Button deleteOne;

            public DialogDeleteSubjectWidget() {
                this.deleteAll = WidgetContainer.this.view.findViewById(R.id.delete_all_id);
                this.deleteOne = WidgetContainer.this.view.findViewById(R.id.delete_one_id);
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
                this.selectDay = WidgetContainer.this.view.findViewById(R.id.select_day_id);
                this.everyDay = WidgetContainer.this.view.findViewById(R.id.repeat_everyday_id);
                this.radioGroup = WidgetContainer.this.view.findViewById(R.id.repeat_radio_group_id);
                this.checkBoxList = WidgetContainer.this.view.findViewById(R.id.checkbox_list_id);

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
                    this.monday = WidgetContainer.this.view.findViewById(R.id.monday_id);
                    this.tuesday = WidgetContainer.this.view.findViewById(R.id.tuesday_id);
                    this.wednesday = WidgetContainer.this.view.findViewById(R.id.wednesday_id);
                    this.thursday = WidgetContainer.this.view.findViewById(R.id.thursday_id);
                    this.friday = WidgetContainer.this.view.findViewById(R.id.friday_id);
                    this.saturday = WidgetContainer.this.view.findViewById(R.id.saturday_id);
                    this.sunday = WidgetContainer.this.view.findViewById(R.id.sunday_id);
                }
            }

        }
    }

}

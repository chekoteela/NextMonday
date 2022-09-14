package com.sharkit.nextmonday.main_menu.diary.dialog;

import static com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer.toDiaryTaskDTO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.domain.DiaryTask;
import com.sharkit.nextmonday.main_menu.diary.entity.DiaryTaskDTO;

@SuppressLint("InflateParams")
public class DialogChangeTask {

    public void showDialogUpdate(final Context context, final DiaryTask diaryTask) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_change_item, null);
        final WidgetContainer.Dialog.DialogChangeSubjectWidget widget = WidgetContainer.newInstance(view).getDialog().getDialogChangeSubjectWidget();
        final DiaryTaskDTO diaryTaskDTO = toDiaryTaskDTO(diaryTask);
        final NextMondayDatabase db = NextMondayDatabase.getInstance(context);

        widget.getChangeAll().setOnClickListener(v -> {
            db.dairyTaskDAO().deleteAllByGroupId(diaryTaskDTO.getGroupId());
            db.dairyTaskDAO().create(diaryTaskDTO);
            dialog.dismiss();
            this.moveToDiaryMain(context);
        });

        widget.getChangeOne().setOnClickListener(v -> {
            db.dairyTaskDAO().updateOne(diaryTaskDTO);
            dialog.dismiss();
            this.moveToDiaryMain(context);
        });

        dialog.setView(view);
        dialog.show();
    }

    public void showDialogDelete(final Context context, final DiaryTask diaryTask) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_delete_item, null);
        final WidgetContainer.Dialog.DialogDeleteSubjectWidget widget = WidgetContainer.newInstance(view).getDialog().getDialogDeleteSubjectWidget();
        final DiaryTaskDTO diaryTaskDTO = toDiaryTaskDTO(diaryTask);
        final NextMondayDatabase db = NextMondayDatabase.getInstance(context);

        if (diaryTask.getRepeats() == null) {
            db.dairyTaskDAO().deleteOne(diaryTaskDTO);
            return;
        }

        widget.getDeleteAll().setOnClickListener(v -> {
            db.dairyTaskDAO().deleteAllByGroupId(diaryTaskDTO.getGroupId());
            dialog.dismiss();
            this.moveToDiaryMain(context);
        });

        widget.getDeleteOne().setOnClickListener(v -> {
            db.dairyTaskDAO().deleteOne(diaryTaskDTO);
            dialog.dismiss();
            this.moveToDiaryMain(context);
        });

        dialog.setView(view);
        dialog.show();
    }

    private void moveToDiaryMain(final Context context) {
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_main);
    }
}

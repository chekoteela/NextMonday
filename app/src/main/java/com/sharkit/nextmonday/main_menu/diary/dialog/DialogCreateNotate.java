package com.sharkit.nextmonday.main_menu.diary.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.NotateType;
import com.sharkit.nextmonday.main_menu.diary.enums.TemplateType;
import com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer;

import java.util.Objects;

@SuppressLint("InflateParams")
public class DialogCreateNotate {

    private static final String TAG = DiaryTaskTransformer.class.getCanonicalName();

    public void showCreateNotateDialog(Context context) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_create_noto, null);
        final WidgetContainer.Dialog.DialogCreateNotateWidget widget = WidgetContainer.newInstance(view).getDialog().getDialogCreateNotateWidget();

        widget.getCreate().setOnClickListener(v -> {
            Notate notate = Notate.builder()
                    .notateType(getNotateType(widget.getTypeOfNotate().getSelectedItemPosition()))
                    .templateType(getTemplateType(widget.getTypeOfKeeping().getSelectedItemPosition()))
                    .name(Objects.requireNonNull(widget.getName().getText()).toString())
                    .build();

            notate.getTemplateType().create(context, notate);

            dialog.dismiss();
        });

        dialog.setView(view);
        dialog.show();
    }

    private TemplateType getTemplateType(int position) {
        switch (position) {
            case 0:
                return TemplateType.FOLDER;
            case 1:
                return TemplateType.FILE;
            default:
                Log.e(TAG, "Unsupported value");
                throw new RuntimeException();
        }
    }

    private NotateType getNotateType(int position) {
        switch (position) {
            case 0:
                return NotateType.RECIPE;
            case 1:
                return NotateType.LIST_OF_PURCHASE;
            case 2:
                return NotateType.OTHER;
            default:
                Log.e(TAG, "Unsupported value");
                throw new RuntimeException();
        }
    }
}

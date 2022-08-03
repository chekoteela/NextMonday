package com.sharkit.nextmonday.main_menu.diary.dialog;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_FOLDER_ID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.NotateAdaptor;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.enums.NotateType;
import com.sharkit.nextmonday.main_menu.diary.enums.TemplateType;
import com.sharkit.nextmonday.main_menu.diary.transformer.DiaryTaskTransformer;

import java.util.List;
import java.util.Objects;

@SuppressLint("InflateParams")
public class DialogCreateNotate {

    private static final String TAG = DiaryTaskTransformer.class.getCanonicalName();

    public void showCreateNotateDialog(Context context, Long parentId, NotateAdaptor adaptor, List<Notate> notates) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_create_noto, null);
        final WidgetContainer.Dialog.DialogCreateNotateWidget widget = WidgetContainer.newInstance(view).getDialog().getDialogCreateNotateWidget();

        widget.getCreate().setOnClickListener(v -> {
            Notate notate = Notate.builder()
                    .parentFolderId(parentId)
                    .notateType(getNotateType(widget.getTypeOfNotate().getSelectedItemPosition()))
                    .templateType(getTemplateType(widget.getTypeOfKeeping().getSelectedItemPosition()))
                    .name(Objects.requireNonNull(widget.getName().getText()).toString())
                    .build();

            notate.getTemplateType().create(context, notate);
            notates.add(notate);
            adaptor.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.setView(view);
        dialog.show();
    }

    private void refreshPage(Long parentFolderId, Context context) {
        Bundle bundle = new Bundle();
        bundle.putLong(DIARY_NOTATE_FOLDER_ID, parentFolderId);
        Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate, bundle);
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

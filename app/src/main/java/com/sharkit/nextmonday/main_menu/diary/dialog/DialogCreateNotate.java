package com.sharkit.nextmonday.main_menu.diary.dialog;

import static com.sharkit.nextmonday.main_menu.diary.enums.NotateType.getNotateTypeById;
import static com.sharkit.nextmonday.main_menu.diary.enums.TemplateType.getTemplateTypeById;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

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

    public void showCreateNotateDialog(final Context context, final Long parentId, final NotateAdaptor adaptor, final List<Notate> notates, final NotateType parentType) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_create_noto, null);
        final WidgetContainer.Dialog.DialogCreateNotateWidget widget = WidgetContainer.newInstance(view).getDialog().getDialogCreateNotateWidget();

        widget.getTypeOfNotate().setSelection(parentType.getId());

        if (!parentType.equals(NotateType.OTHER)) {
            widget.getTypeOfNotate().setVisibility(View.GONE);
        }

        widget.getCreate().setOnClickListener(v -> {
            final Notate notate = Notate.builder()
                    .parentFolderId(parentId)
                    .notateType(getNotateTypeById(widget.getTypeOfNotate().getSelectedItemPosition()))
                    .templateType(getTemplateTypeById(widget.getTypeOfKeeping().getSelectedItemPosition()))
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

}

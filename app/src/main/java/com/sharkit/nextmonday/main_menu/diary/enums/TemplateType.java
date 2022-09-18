package com.sharkit.nextmonday.main_menu.diary.enums;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_FOLDER_ID;
import static com.sharkit.nextmonday.main_menu.diary.transformer.FolderTemplateTransformer.toFolderTemplateDTO;
import static com.sharkit.nextmonday.main_menu.diary.transformer.NotateTransformer.toNotateDTO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.configuration.widget.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.NotateAdaptor;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.template.FolderTemplate;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.ITemplateAction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@SuppressLint("InflateParams")
@RequiredArgsConstructor
public enum TemplateType implements ITemplateAction {

    FOLDER(0) {
        @Override
        public void create(final Context context, final Notate notate) {
            final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
            db.runInTransaction(() -> {

                notate.setTemplateId(db.folderTemplateDAO().create(toFolderTemplateDTO(new FolderTemplate())));
                db.notateDAO().create(toNotateDTO(notate));
            });
        }

        @Override
        public View getView(final Context context) {
            return LayoutInflater.from(context).inflate(R.layout.diary_noto_folder_item, null);
        }

        @Override
        public void setAction(final View view, final Notate notate) {
            final WidgetContainer.DiaryNotateWidget.FolderItemWidget folderWidget = WidgetContainer.newInstance(view).getDiaryNotateWidget().getFolderItemWidget();

            folderWidget.getName().setText(notate.getName());
            folderWidget.getFolderType().setText(notate.getNotateType().getName(view.getContext()));

            folderWidget.getParentItem().setOnClickListener(v -> this.moveToFolder(notate.getTemplateId(), view.getContext()));

        }

        private void moveToFolder(final Long folderId, final Context context) {

            Log.i(TAG, "move to folder with folderId: " + folderId);

            final Bundle bundle = new Bundle();
            bundle.putLong(DIARY_NOTATE_FOLDER_ID, folderId);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate, bundle);
        }
    },

    FILE(1) {
        @Override
        public void create(final Context context, final Notate notate) {
            final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
            db.runInTransaction(() -> {
                notate.setTemplateId(notate.getNotateType().createTemplate(db));
                db.notateDAO().create(toNotateDTO(notate));
            });
        }

        @Override
        public View getView(final Context context) {
            return LayoutInflater.from(context).inflate(R.layout.diary_noto_notate_item, null);
        }

        @Override
        public void setAction(final View view, final Notate notate) {
            final WidgetContainer.DiaryNotateWidget.NotateItemWidget notateWidget = WidgetContainer.newInstance(view).getDiaryNotateWidget().getNotateItemWidget();

            notateWidget.getName().setText(notate.getName());
            notateWidget.getNotateWidget().setText(notate.getNotateType().getName(view.getContext()));

            notateWidget.getParentItem().setOnClickListener(v -> notate.getNotateType().moveToFile(view.getContext(), notate));
        }
    };

    @Getter
    private final Integer id;
    private static final String TAG = NotateAdaptor.class.getCanonicalName();

    public static TemplateType getTemplateTypeById(final int id) {
        switch (id) {
            case 0:
                return TemplateType.FOLDER;
            case 1:
                return TemplateType.FILE;
            default:
                Log.e(TAG, "Unsupported value");
                throw new RuntimeException();
        }
    }
}

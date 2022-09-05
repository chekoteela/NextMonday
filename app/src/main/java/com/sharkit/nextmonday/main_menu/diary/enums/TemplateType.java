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
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
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
        public void create(Context context, Notate notate) {
            final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
            db.runInTransaction(() -> {

                notate.setTemplateId(db.folderTemplateDAO().create(toFolderTemplateDTO(new FolderTemplate())));
                db.notateDAO().create(toNotateDTO(notate));
            });
        }

        @Override
        public View getView(Context context) {
            return LayoutInflater.from(context).inflate(R.layout.diary_noto_folder_item, null);
        }

        @Override
        public void setAction(View view, Notate notate) {
            final WidgetContainer.DiaryNotateWidget.FolderItemWidget folderWidget = WidgetContainer.newInstance(view).getDiaryNotateWidget().getFolderItemWidget();

            folderWidget.getName().setText(notate.getName());
            folderWidget.getFolderType().setText(notate.getNotateType().getName(view.getContext()));

            folderWidget.getParentItem().setOnClickListener(v -> moveToFolder(notate.getTemplateId(), view.getContext()));

        }

        private void moveToFolder(Long folderId, Context context) {

            Log.i(TAG, "move to folder with folderId: " + folderId);

            Bundle bundle = new Bundle();
            bundle.putLong(DIARY_NOTATE_FOLDER_ID, folderId);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate, bundle);
        }
    },

    FILE(1) {
        @Override
        public void create(Context context, Notate notate) {
            final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
            db.runInTransaction(() -> {
                notate.setTemplateId(notate.getNotateType().createTemplate(db));
                db.notateDAO().create(toNotateDTO(notate));
            });
        }

        @Override
        public View getView(Context context) {
            return LayoutInflater.from(context).inflate(R.layout.diary_noto_notate_item, null);
        }

        @Override
        public void setAction(View view, Notate notate) {
            final WidgetContainer.DiaryNotateWidget.NotateItemWidget notateWidget = WidgetContainer.newInstance(view).getDiaryNotateWidget().getNotateItemWidget();

            notateWidget.getName().setText(notate.getName());
            notateWidget.getNotateWidget().setText(notate.getNotateType().getName(view.getContext()));

            notateWidget.getParentItem().setOnClickListener(v -> notate.getNotateType().moveToFile(view.getContext(), notate));
        }
    };

    @Getter
    private final Integer id;
    private static final String TAG = NotateAdaptor.class.getCanonicalName();

    public static TemplateType getTemplateTypeById(int id) {
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

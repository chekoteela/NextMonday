package com.sharkit.nextmonday.main_menu.diary.enums;

import static com.sharkit.nextmonday.main_menu.diary.transformer.FolderTemplateTransformer.toFolderTemplateDTO;
import static com.sharkit.nextmonday.main_menu.diary.transformer.NotateTemplateTransformer.toNotateTemplateDTO;
import static com.sharkit.nextmonday.main_menu.diary.transformer.NotateTransformer.toNotateDTO;

import android.content.Context;

import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.domain.FolderTemplate;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.NotateTemplate;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.ITemplateAction;

public enum TemplateType implements ITemplateAction {

    FILE{
        @Override
        public void create(Context context, Notate notate) {
            final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
            db.runInTransaction(() -> {
                notate.setTemplateId(db.notateTemplateDAO().create(toNotateTemplateDTO(new NotateTemplate())));
                db.notateDAO().create(toNotateDTO(notate));
            });
        }
    },
    FOLDER {
        @Override
        public void create(Context context, Notate notate) {
            final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
            db.runInTransaction(() -> {
                notate.setTemplateId(db.folderTemplateDAO().create(toFolderTemplateDTO(new FolderTemplate())));
                db.notateDAO().create(toNotateDTO(notate));
            });
        }
    }
}

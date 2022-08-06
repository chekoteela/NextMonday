package com.sharkit.nextmonday.main_menu.diary.enums;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_ID;
import static com.sharkit.nextmonday.main_menu.diary.transformer.NotateTemplateTransformer.toNotateTemplateDTO;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.domain.template.RecipeTemplate;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.IActionNotateType;

import java.io.Serializable;

public enum NotateType implements IActionNotateType, Serializable {

    RECIPE{
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_recipe);
        }

        @Override
        public void moveToFile(Context context, Long templateId) {
            Bundle bundle = new Bundle();
            bundle.putLong(DIARY_NOTATE_ID, templateId);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate_recipe, bundle);
        }

        @Override
        public Long createTemplate(NextMondayDatabase db) {
            return db.notateTemplateDAO().create(toNotateTemplateDTO(new RecipeTemplate()));
        }
    },
    LIST_OF_PURCHASE {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_list_of_purchases);
        }

        @Override
        public void moveToFile(Context context, Long templateId) {

        }

        @Override
        public Long createTemplate(NextMondayDatabase db) {
            return null;
        }
    },
    OTHER {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_other);
        }

        @Override
        public void moveToFile(Context context, Long templateId) {

        }

        @Override
        public Long createTemplate(NextMondayDatabase db) {
            return null;
        }
    }
}

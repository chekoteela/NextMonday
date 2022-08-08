package com.sharkit.nextmonday.main_menu.diary.enums;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE;
import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeTemplateTransformer.toRecipeTemplateDTO;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.RecipeTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseTemplateDTO;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.IActionNotateType;

import java.io.Serializable;

public enum NotateType implements IActionNotateType, Serializable {

    RECIPE {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_recipe);
        }

        @Override
        public void moveToFile(Context context, Notate notate) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DIARY_NOTATE, notate);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate_recipe, bundle);
        }

        @Override
        public Long createTemplate(NextMondayDatabase db) {
            return db.recipeTemplateDAO().create(toRecipeTemplateDTO(new RecipeTemplate()));
        }
    },
    LIST_OF_PURCHASE {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_list_of_purchases);
        }

        @Override
        public void moveToFile(Context context, Notate notate) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DIARY_NOTATE, notate);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate_purchase, bundle);
        }

        @Override
        public Long createTemplate(NextMondayDatabase db) {
            return db.purchaseTemplateDAO().create(new PurchaseTemplateDTO());
        }
    },
    OTHER {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_other);
        }

        @Override
        public void moveToFile(Context context, Notate notate) {

        }

        @Override
        public Long createTemplate(NextMondayDatabase db) {
            return null;
        }
    }
}

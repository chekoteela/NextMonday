package com.sharkit.nextmonday.main_menu.diary.enums;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE;
import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeTemplateTransformer.toRecipeTemplateDTO;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.adapter.NotateAdaptor;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.RecipeTemplate;
import com.sharkit.nextmonday.main_menu.diary.entity.purchase.PurchaseTemplateDTO;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.IActionNotateType;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotateType implements IActionNotateType, Serializable {

    OTHER(0) {
        @Override
        public String getName(final Context context) {
            return context.getString(R.string.text_view_other);
        }

        @Override
        public void moveToFile(final Context context, final Notate notate) {
            Log.i(TAG, "move to notate: " + notate);

        }

        @Override
        public Long createTemplate(final NextMondayDatabase db) {
            return null;
        }
    },

    RECIPE(1) {
        @Override
        public String getName(final Context context) {
            return context.getString(R.string.text_view_recipe);
        }

        @Override
        public void moveToFile(final Context context, final Notate notate) {
            Log.i(TAG, "move to notate: " + notate);

            final Bundle bundle = new Bundle();
            bundle.putSerializable(DIARY_NOTATE, notate);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate_recipe, bundle);
        }

        @Override
        public Long createTemplate(final NextMondayDatabase db) {
            return db.recipeTemplateDAO().create(toRecipeTemplateDTO(new RecipeTemplate()));
        }
    },
    LIST_OF_PURCHASE(2) {
        @Override
        public String getName(final Context context) {
            return context.getString(R.string.text_view_list_of_purchases);
        }

        @Override
        public void moveToFile(final Context context, final Notate notate) {
            Log.i(TAG, "move to notate: " + notate);

            final Bundle bundle = new Bundle();
            bundle.putSerializable(DIARY_NOTATE, notate);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate_purchase, bundle);
        }

        @Override
        public Long createTemplate(final NextMondayDatabase db) {
            return db.purchaseTemplateDAO().create(new PurchaseTemplateDTO());
        }
    };

    @Getter
    private final Integer id;
    private static final String TAG = NotateAdaptor.class.getCanonicalName();

    public static NotateType getNotateTypeById(final int id) {

        switch (id) {
            case 0:
                return NotateType.OTHER;
            case 1:
                return NotateType.RECIPE;
            case 2:
                return NotateType.LIST_OF_PURCHASE;
            default:
                Log.e(TAG, "Unsupported value");
                throw new RuntimeException();
        }
    }
}

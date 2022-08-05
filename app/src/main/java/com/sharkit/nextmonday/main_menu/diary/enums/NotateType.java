package com.sharkit.nextmonday.main_menu.diary.enums;

import android.app.Activity;
import android.content.Context;

import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.IActionNotateType;

import java.io.Serializable;

public enum NotateType implements IActionNotateType, Serializable {

    RECIPE{
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_recipe);
        }

        @Override
        public void moveToFile(Context context) {
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate_recipe);
        }
    },
    LIST_OF_PURCHASE {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_list_of_purchases);
        }

        @Override
        public void moveToFile(Context context) {

        }
    },
    OTHER {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_other);
        }

        @Override
        public void moveToFile(Context context) {

        }
    }
}

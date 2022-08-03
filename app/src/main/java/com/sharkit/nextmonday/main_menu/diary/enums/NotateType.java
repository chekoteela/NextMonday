package com.sharkit.nextmonday.main_menu.diary.enums;

import android.content.Context;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.main_menu.diary.enums.impl.IActionNotateType;

import java.io.Serializable;

public enum NotateType implements IActionNotateType, Serializable {

    RECIPE{
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_recipe);
        }
    },
    LIST_OF_PURCHASE {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_list_of_purchases);
        }
    },
    OTHER {
        @Override
        public String getName(Context context) {
            return context.getString(R.string.text_view_other);
        }
    }
}

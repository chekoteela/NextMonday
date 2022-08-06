package com.sharkit.nextmonday.main_menu.diary.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

@SuppressLint("InflateParams")
public class DialogAddRecipeFood {

    public void show(Context context) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_add_recipe_food, null);
        final WidgetContainer.Dialog.RecipeAddFoodWidget widget = WidgetContainer.newInstance(view).getDialog().getRecipeAddFoodWidget();

        dialog.setButton(1, "save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.setView(view);
    }
}

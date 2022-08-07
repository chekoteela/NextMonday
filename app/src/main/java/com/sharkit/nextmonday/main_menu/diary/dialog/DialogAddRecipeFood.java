package com.sharkit.nextmonday.main_menu.diary.dialog;

import static com.sharkit.nextmonday.main_menu.diary.transformer.RecipeItemTransformer.toRecipeItemDTO;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.RecipeAdapter;
import com.sharkit.nextmonday.main_menu.diary.domain.template.RecipeItem;

import java.util.List;

@SuppressLint("InflateParams")
public class DialogAddRecipeFood {

    public void show(Context context, Long templateId, List<RecipeItem> recipeItems, RecipeAdapter recipeAdapter) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_add_recipe_food, null);
        final WidgetContainer.Dialog.RecipeAddFoodWidget widget = WidgetContainer.newInstance(view).getDialog().getRecipeAddFoodWidget();
        final NextMondayDatabase db = NextMondayDatabase.getInstance(context);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.button_add), (parent, which) -> {
            RecipeItem item = RecipeItem.builder()
                    .description(widget.getDescription().getText().toString())
                    .name(widget.getName().getText().toString())
                    .templateId(templateId)
                    .build();
            db.recipeItemDAO().save(toRecipeItemDTO(item));
            recipeItems.add(item);
            recipeAdapter.notifyDataSetChanged();
            parent.dismiss();
        });

        dialog.setView(view);
        dialog.show();
    }
}

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

import lombok.RequiredArgsConstructor;

@SuppressLint("InflateParams")
@RequiredArgsConstructor
public class DialogRecipeFood {

    private final Context context;
    private final Long templateId;
    private final List<RecipeItem> recipeItems;
    private final RecipeAdapter recipeAdapter;
    private WidgetContainer.Dialog.RecipeAddFoodWidget widget;

    public void createItem() {
        final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
        final AlertDialog dialog = showDialog();
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
        dialog.show();
    }

    public void changeItem(RecipeItem item, int position){
        final NextMondayDatabase db = NextMondayDatabase.getInstance(context);
        final AlertDialog dialog = showDialog();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.button_change), (parent, which) -> {
            item.setDescription(widget.getDescription().getText().toString());
            item.setName(widget.getName().getText().toString());

            db.recipeItemDAO().update(toRecipeItemDTO(item));

            recipeItems.set(position, item);
            recipeAdapter.notifyDataSetChanged();
            parent.dismiss();
        });
        dialog.show();

    }

    private AlertDialog showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_diary_recipe_food, null);

        widget = WidgetContainer.newInstance(view).getDialog().getRecipeAddFoodWidget();
        dialog.setView(view);
        return dialog;
    }
}

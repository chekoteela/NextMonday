package com.sharkit.nextmonday.main_menu.diary.dialog;

import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeItemTransformer.toRecipeItemDTO;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.configuration.widget.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.RecipeAdapter;
import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.RecipeItem;

import java.util.List;

import lombok.RequiredArgsConstructor;

@SuppressLint("InflateParams")
@RequiredArgsConstructor
public class DialogRecipeFood {

    private final Context context;
    private final Long templateId;
    private final List<RecipeItem> recipeItems;
    private final RecipeAdapter recipeAdapter;
    private WidgetContainer.Dialog.TemplateAddItemWidget widget;

    public void createItem() {
        final NextMondayDatabase db = NextMondayDatabase.getInstance(this.context);
        final AlertDialog dialog = this.showDialog();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, this.context.getString(R.string.button_add), (parent, which) -> {
            final RecipeItem item = RecipeItem.builder()
                    .description(this.widget.getDescription().getText().toString())
                    .name(this.widget.getName().getText().toString())
                    .templateId(this.templateId)
                    .build();
            db.recipeItemDAO().save(toRecipeItemDTO(item));
            this.recipeItems.add(item);
            this.recipeAdapter.notifyDataSetChanged();
            parent.dismiss();
        });
        dialog.show();
    }

    public void changeItem(final RecipeItem item, final int position){
        final NextMondayDatabase db = NextMondayDatabase.getInstance(this.context);
        final AlertDialog dialog = this.showDialog();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, this.context.getString(R.string.button_change), (parent, which) -> {
            item.setDescription(this.widget.getDescription().getText().toString());
            item.setName(this.widget.getName().getText().toString());

            db.recipeItemDAO().update(toRecipeItemDTO(item));

            this.recipeItems.set(position, item);
            this.recipeAdapter.notifyDataSetChanged();
            parent.dismiss();
        });
        dialog.show();

    }

    private AlertDialog showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this.context).create();
        final View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_diary_recipe_food, null);

        this.widget = WidgetContainer.newInstance(view).getDialog().getTemplateAddItemWidget();
        dialog.setView(view);
        return dialog;
    }
}

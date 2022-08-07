package com.sharkit.nextmonday.main_menu.diary.adapter;

import static com.sharkit.nextmonday.main_menu.diary.transformer.RecipeItemTransformer.toRecipeItemDTO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogRecipeFood;
import com.sharkit.nextmonday.main_menu.diary.domain.template.RecipeItem;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecipeAdapter extends BaseAdapter {

    private final List<RecipeItem> items;
    private final Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.diary_noto_recipe_item, null);

        final WidgetContainer.DiaryNotateRecipeWidget.RecipeItemWidget widget = WidgetContainer.newInstance(convertView).getDiaryNotateRecipeWidget().getRecipeItemWidget();

        widget.getDescription().setText(items.get(position).getDescription());
        widget.getName().setText(items.get(position).getName());
        widget.getItem().setOnCreateContextMenuListener((menu, v, menuInfo) -> createMenuListener(menu, context, position));
        return convertView;
    }

    private void createMenuListener(ContextMenu menu, Context context,  int position) {
        menu.add(context.getString(R.string.button_change))
                .setOnMenuItemClickListener(item -> {
                    new DialogRecipeFood(context, items.get(position).getTemplateId(), items, RecipeAdapter.this)
                            .changeItem(items.get(position), position);
                    return true; });
        menu.add(context.getString(R.string.button_delete))
                .setOnMenuItemClickListener(item -> {
                    NextMondayDatabase.getInstance(context).recipeItemDAO().delete(toRecipeItemDTO(items.get(position)));
                    items.remove(position);
                    notifyDataSetChanged();
                    return true; });
    }
}

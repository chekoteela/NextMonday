package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_ID;
import static com.sharkit.nextmonday.main_menu.diary.transformer.RecipeTransformer.toRecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.RecipeAdapter;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogAddRecipeFood;
import com.sharkit.nextmonday.main_menu.diary.domain.template.Recipe;

public class NotateRecipeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Long templateId = requireArguments().getLong(DIARY_NOTATE_ID);
        final View view = inflater.inflate(R.layout.diary_noto_recipe, container, false);
        final WidgetContainer.DiaryNotateRecipeWidget widget = WidgetContainer.newInstance(view).getDiaryNotateRecipeWidget();
        final NextMondayDatabase db = NextMondayDatabase.getInstance(getContext());
        final Recipe recipe = toRecipe(db.recipeDAO().findById(templateId));
        final RecipeAdapter recipeAdapter = new RecipeAdapter(recipe.getRecipeItems(), getContext());

        widget.getRecipeList().setAdapter(recipeAdapter);
        widget.getAddFood().setOnClickListener(v -> new DialogAddRecipeFood().show(getContext(), templateId, recipe.getRecipeItems(),recipeAdapter));

        return view;
    }
}

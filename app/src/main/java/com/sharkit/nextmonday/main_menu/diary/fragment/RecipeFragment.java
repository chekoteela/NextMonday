package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE;
import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_FOLDER_ID;
import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeTransformer.toRecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.RecipeAdapter;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogRecipeFood;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.Recipe;
import com.sharkit.nextmonday.main_menu.diary.domain.template.recipe.RecipeTemplate;

public class RecipeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Notate notate = (Notate) requireArguments().getSerializable(DIARY_NOTATE);
        final View view = inflater.inflate(R.layout.diary_noto_recipe, container, false);
        final WidgetContainer.DiaryNotateRecipeWidget widget = WidgetContainer.newInstance(view).getDiaryNotateRecipeWidget();
        final NextMondayDatabase db = NextMondayDatabase.getInstance(getContext());
        final Recipe recipe = toRecipe(db.recipeTemplateDAO().findById(notate.getTemplateId()));
        final RecipeAdapter recipeAdapter = new RecipeAdapter(recipe.getRecipeItems(), getContext());

        widget.getName().setText(notate.getName());
        widget.getDescription().setText(recipe.getRecipeTemplate().getDescription());
        widget.getRecipeList().setAdapter(recipeAdapter);
        widget.getAddFood().setOnClickListener(v -> new DialogRecipeFood(getContext(), notate.getTemplateId(), recipe.getRecipeItems(), recipeAdapter).createItem());
        widget.getSave().setOnClickListener(v -> saveChanges(widget, recipe.getRecipeTemplate(), db, notate));
        return view;
    }

    private void saveChanges(WidgetContainer.DiaryNotateRecipeWidget widget, RecipeTemplate template, NextMondayDatabase db, Notate notate) {
        template.setDescription(widget.getDescription().getText().toString());
        db.notateDAO().updateName(widget.getName().getText().toString(), notate.getId());

        Bundle bundle = new Bundle();
        bundle.putLong(DIARY_NOTATE_FOLDER_ID, notate.getParentFolderId());
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate, bundle);
    }
}

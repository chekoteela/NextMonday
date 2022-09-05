package com.sharkit.nextmonday.main_menu.diary.fragment;

import static android.app.Activity.RESULT_OK;
import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE;
import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_FOLDER_ID;
import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeTemplateTransformer.toRecipeTemplateDTO;
import static com.sharkit.nextmonday.main_menu.diary.transformer.recipe.RecipeTransformer.toRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.sharkit.nextmonday.main_menu.diary.service.ImageStorageService;

import java.util.Optional;
import java.util.UUID;

public class RecipeFragment extends Fragment {

    private WidgetContainer.DiaryNotateRecipeWidget widget;
    private NextMondayDatabase db;
    private Recipe recipe;
    private ImageStorageService imageStorageService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Notate notate = (Notate) requireArguments().getSerializable(DIARY_NOTATE);
        final View view = inflater.inflate(R.layout.diary_noto_recipe, container, false);

        this.imageStorageService = new ImageStorageService(requireContext());
        this.db = NextMondayDatabase.getInstance(getContext());
        this.recipe = toRecipe(this.db.recipeTemplateDAO().findById(notate.getTemplateId()));
        final RecipeAdapter recipeAdapter = new RecipeAdapter(recipe.getRecipeItems(), getContext());

        this.widget = WidgetContainer.newInstance(view).getDiaryNotateRecipeWidget();
        this.widget.getName().setText(notate.getName());
        this.widget.getDescription().setText(recipe.getRecipeTemplate().getDescription());
        this.widget.getRecipeList().setAdapter(recipeAdapter);
        this.widget.getAddFood().setOnClickListener(v -> new DialogRecipeFood(getContext(), notate.getTemplateId(), recipe.getRecipeItems(), recipeAdapter).createItem());
        this.widget.getSearchImage().setOnClickListener(v -> downloadNewImageAndDeleteOld());
        this.widget.getSave().setOnClickListener(v -> saveChanges(recipe.getRecipeTemplate(), db, notate));

        this.imageStorageService.downloadFile(Optional.ofNullable(recipe.getRecipeTemplate().getImageCod()).orElse(""),
                widget.getRecipeImage());
        return view;
    }

    private void downloadNewImageAndDeleteOld() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);
    }

    private void saveChanges(RecipeTemplate template, NextMondayDatabase db, Notate notate) {
        template.setDescription(this.widget.getDescription().getText().toString());
        db.recipeTemplateDAO().update(toRecipeTemplateDTO(template));
        db.notateDAO().updateName(this.widget.getName().getText().toString(), notate.getId());

        Bundle bundle = new Bundle();
        bundle.putLong(DIARY_NOTATE_FOLDER_ID, notate.getParentFolderId());
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate, bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            final String imageCode = UUID.randomUUID().toString();

            this.imageStorageService.delete(recipe.getRecipeTemplate().getImageCod());
            this.widget.getRecipeImage().setImageURI(data.getData());
            this.db.recipeTemplateDAO().updateImageCod(imageCode, recipe.getRecipeTemplate().getTemplateId());
            this.imageStorageService.upload(data.getData(), imageCode);
        }
    }

}

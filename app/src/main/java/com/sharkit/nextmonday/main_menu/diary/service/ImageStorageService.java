package com.sharkit.nextmonday.main_menu.diary.service;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.sharkit.nextmonday.R;

public class ImageStorageService {

    private final FirebaseStorage storage;
    private final Context context;
    private final String pathToImage;

    private final String imageCode;

    public ImageStorageService(final Context context) {
        this.storage = FirebaseStorage.getInstance();
        this.context = context;
        this.pathToImage = context.getString(R.string.path_to_recipe_image);
        this.imageCode = context.getString(R.string.path_variable_image_code);
    }

    public void delete(final String imageCod) {
        storage.getReference(pathToImage.replace(imageCode, imageCod))
                .delete();
    }

    public void upload(final Uri imageUri, final String imageCod) {
        storage.getReference(pathToImage.replace(imageCode, imageCod)).putFile(imageUri);
    }

    public void downloadFile(final String imageCod, final ImageView imageView) {
        storage.getReference(pathToImage.replace(imageCode, imageCod))
                .getDownloadUrl()
                .addOnSuccessListener(uri -> Glide.with(context)
                        .load(uri)
                        .into(imageView)
                        .getRequest());
    }
}

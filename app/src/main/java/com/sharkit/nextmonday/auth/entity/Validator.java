package com.sharkit.nextmonday.auth.entity;

import android.content.Context;
import android.widget.Toast;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Validator {

    private String messageText;
    private Boolean valid;

    public void throwToastMessage(Context context) {
        Toast.makeText(context, getMessageText(), Toast.LENGTH_SHORT).show();
    }
}

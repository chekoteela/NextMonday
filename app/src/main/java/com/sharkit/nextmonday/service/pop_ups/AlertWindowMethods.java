package com.sharkit.nextmonday.service.pop_ups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public abstract class AlertWindowMethods {
    public abstract AlertDialogChooseDateForAlarm createAlertDialog(Context context);
    public abstract AlertDialogChooseDateForAlarm createAlertDialog(Context context, int themeId);
    protected abstract void findViewAlertDialog(View root);
    protected abstract void activity(AlertDialog.Builder dialog);
}

package com.sharkit.nextmonday.configuration.constant;

import android.content.Context;
import android.widget.Toast;

import com.sharkit.nextmonday.R;

public class ToastMessage {

    public static void NOT_CONNECTION(Context context) {
        toastMessage(context, R.string.not_connection_to_internet);
    }
    public static void ERROR_AUTHORISE(Context context) {
        toastMessage(context, R.string.error_authorise);
    }
    public static void EMAIL_OR_PASS_FAIL(Context context) {
        toastMessage(context, R.string.fail_email_or_pass);
    }
    public static void PASSWORDS_NOT_THE_SAME(Context context) {
        toastMessage(context, R.string.password_is_not_the_same);
    }
    public static void SUCCESSFUL_UPDATE(Context context) {
        toastMessage(context, R.string.successful_update);
    }
    public static void AGREE_WITH_POLICY(Context context) {
        toastMessage(context, R.string.agree_with_privacy_policy);
    }
    public static void USER_WITH_EMAIL_EXIST(Context context) {
        toastMessage(context, R.string.successful_update);
    }
    public static void IS_NULL_SPINNER_POSITION(Context context) {
        toastMessage(context, R.string.successful_update);
    }

    private static void toastMessage(Context context, int idRes){
        Toast.makeText(context, context.getString(idRes), Toast.LENGTH_SHORT).show();
    }


    protected static final String TOO_BIG_VALUE = "Too big value";
    protected static final String TOO_SMALL_VALUE = "Too small value";
    protected static final String IS_NOT_VALID_DESIRED_WEIGHT = "Is not valid desired weight";

}

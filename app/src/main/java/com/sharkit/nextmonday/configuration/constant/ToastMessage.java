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

    private static void toastMessage(Context context, int idRes){
        Toast.makeText(context, context.getString(idRes), Toast.LENGTH_SHORT).show();
    }

    public static final String CHECK_YOUR_EMAIL = "Проверте вашу почту";
    public static final String EMAIL_NOT_FOUND = "Почта не найдена";
    public static final String ERROR_PAST_DATE = "Вы не можете добавить задачу задним числом";
    public static final String ERROR_PAST_TIME = "Вы не можете задать прошедшее время";
    public static final String SEND_FEEDBACK = "Отзыв отправлен";
    public static final String PASSWORD_NOT_CORRECT = "Введите правельный пароль";
    public static final String EMAIL_UPDATED = "Почта обновлена";
    public static final String CREATE_NEW_MEAL = "Прийом їжі створений";
    public static final String UPDATE_WEIGHT = "Current weight is updated";

}

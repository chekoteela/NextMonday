package com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions;

import android.annotation.SuppressLint;
import android.content.Context;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.WidgetSize;

public enum MainActivityDimmension implements WidgetSize {

    BUTTON {
        private Context context;
        private int HEIGHT;
        private int WIDTH;

        @Override
        public WidgetSize setContext(Context context) {
            this.context = context;
            HEIGHT = context.getResources().getDisplayMetrics().heightPixels;//2392
            WIDTH = context.getResources().getDisplayMetrics().widthPixels;//1440
            return this;
        }

        @Override
        public int getHeight() {
            return (int) (HEIGHT / getCoefficient(R.dimen.main_activity_button_height_coefficient));
        }

        @Override
        public int getWidth() {
            return (int) (WIDTH / getCoefficient(R.dimen.main_activity_button_width_coefficient));
        }

        @Override
        public int getMarginTop() {
            return (int) (HEIGHT / getCoefficient(R.dimen.main_activity_button_margin_top_coefficient));
        }

        @Override
        public int getMarginBottom() {
            return (int) (HEIGHT / getCoefficient(R.dimen.main_activity_button_margin_bottom_coefficient));
        }

        @Override
        public int getMarginLeft() {
            return (int) (WIDTH / getCoefficient(R.dimen.main_activity_button_margin_left_coefficient));
        }

        @Override
        public int getMarginRight() {
            return (int) (WIDTH / getCoefficient(R.dimen.main_activity_button_margin_right_coefficient));
        }

        @SuppressLint("ResourceType")
        private float getCoefficient(int idRes) {
            return Float.parseFloat(context.getResources().getString(idRes));
        }
    },

    EDIT_TEXT {
        private Context context;
        private int HEIGHT;
        private int WIDTH;

        @Override
        public WidgetSize setContext(Context context) {
            this.context = context;
            HEIGHT = context.getResources().getDisplayMetrics().heightPixels;//2392
            WIDTH = context.getResources().getDisplayMetrics().widthPixels;//1440
            return this;
        }

        @Override
        public int getHeight() {
            return (int) (HEIGHT / getCoefficient(R.dimen.main_actyvity_edit_text_height_coefficient));
        }

        @Override
        public int getWidth() {
            return (int) (WIDTH / getCoefficient(R.dimen.main_actyvity_edit_text_width_coefficient));
        }

        @Override
        public int getMarginTop() {
            return (int) (HEIGHT / getCoefficient(R.dimen.main_actyvity_edit_text_margin_top_coefficient));
        }

        @Override
        public int getMarginBottom() {
            return (int) (HEIGHT / getCoefficient(R.dimen.main_actyvity_edit_text_margin_bottom_coefficient));
        }

        @Override
        public int getMarginLeft() {
            return (int) (WIDTH / getCoefficient(R.dimen.main_actyvity_edit_text_margin_left_coefficient));
        }

        @Override
        public int getMarginRight() {
            return (int) (WIDTH / getCoefficient(R.dimen.main_actyvity_edit_text_margin_right_coefficient));
        }

        @SuppressLint("ResourceType")
        private float getCoefficient(int idRes) {
            return Float.parseFloat(context.getResources().getString(idRes));
        }
    }

}

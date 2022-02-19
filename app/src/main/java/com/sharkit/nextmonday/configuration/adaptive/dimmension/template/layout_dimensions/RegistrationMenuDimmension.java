package com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions;

import android.content.Context;

import com.sharkit.nextmonday.configuration.adaptive.dimmension.WidgetSize;

public enum RegistrationMenuDimmension implements WidgetSize {

    BUTTON{
        @Override
        public WidgetSize setContext(Context context) {
            return null;
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getMarginTop() {
            return 0;
        }

        @Override
        public int getMarginBottom() {
            return 0;
        }

        @Override
        public int getMarginLeft() {
            return 0;
        }

        @Override
        public int getMarginRight() {
            return 0;
        }
    }

}

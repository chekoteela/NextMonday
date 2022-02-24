package com.sharkit.nextmonday.configuration.adaptive.dimmension.template;

import com.sharkit.nextmonday.configuration.adaptive.dimmension.WidgetSize;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions.Dimension;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions.MainActivityDimension;

public enum TemplateAdaptive implements TemplateWidgetSize{

    MAIN_ACTIVITY {
        @Override
        public WidgetSize getWidgetSize(Dimension dimension) {
            return MainActivityDimension.valueOf(dimension.name());
        }
    },
    REGISTRATION_MENU {
        @Override
        public WidgetSize getWidgetSize(Dimension dimension) {
            return MainActivityDimension.valueOf(dimension.name());
        }
    }

}

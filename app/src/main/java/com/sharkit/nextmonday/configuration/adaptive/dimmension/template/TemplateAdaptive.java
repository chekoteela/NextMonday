package com.sharkit.nextmonday.configuration.adaptive.dimmension.template;

import com.sharkit.nextmonday.configuration.adaptive.dimmension.WidgetSize;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions.Dimmension;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions.MainActivityDimmension;

public enum TemplateAdaptive implements TemplateWidgetSize{

    MAIN_ACTIVITY {
        @Override
        public WidgetSize getWidgetSize(Dimmension dimmension) {
            return MainActivityDimmension.valueOf(dimmension.name());
        }
    },
    REGISTRATION_MENU {
        @Override
        public WidgetSize getWidgetSize(Dimmension dimmension) {
            return MainActivityDimmension.valueOf(dimmension.name());
        }
    }

}

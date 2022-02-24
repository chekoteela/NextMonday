package com.sharkit.nextmonday.configuration.adaptive.dimmension.template;

import com.sharkit.nextmonday.configuration.adaptive.dimmension.WidgetSize;
import com.sharkit.nextmonday.configuration.adaptive.dimmension.template.layout_dimensions.Dimension;

public interface TemplateWidgetSize {

    WidgetSize getWidgetSize(Dimension dimension);
}

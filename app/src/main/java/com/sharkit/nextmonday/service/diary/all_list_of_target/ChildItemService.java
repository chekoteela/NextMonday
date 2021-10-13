package com.sharkit.nextmonday.service.diary.all_list_of_target;

import android.view.View;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ChildItemService implements LayoutService {
    private final String description;
    private TextView desc;

    public ChildItemService(String description) {
        this.description = description;
    }

    @Override
    public LayoutService writeToField() {
        desc.setText(description);
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        desc = root.findViewById(R.id.text_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        return this;
    }
}

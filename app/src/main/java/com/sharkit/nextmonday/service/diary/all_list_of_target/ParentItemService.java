package com.sharkit.nextmonday.service.diary.all_list_of_target;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.db.firestore.diary.DiaryFirestore;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.service.builder.LayoutService;

import java.text.SimpleDateFormat;

public class ParentItemService implements LayoutService {
    private final ChildItemTargetDTO itemTargetDTO;
    private CheckBox checkBox;
    private TextView date, time, text;
    private Context context;

    public ParentItemService(ChildItemTargetDTO itemTargetDTO) {
        this.itemTargetDTO = itemTargetDTO;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public LayoutService writeToField() {
        date.setText(new SimpleDateFormat("dd.MM.yyyy").format(itemTargetDTO.getDate()));
        time.setText(new SimpleDateFormat("HH:mm").format(itemTargetDTO.getDate()));
        text.setText(itemTargetDTO.getText());
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        checkBox = root.findViewById(R.id.completeTarget_xml);
        date = root.findViewById(R.id.date_xml);
        time = root.findViewById(R.id.time_xml);
        text = root.findViewById(R.id.text_xml);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        TargetDataService service = new TargetDataService(context);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            new DiaryFirestore().updateStatus(isChecked, itemTargetDTO.getDate());
            service.setCheckedTarget(itemTargetDTO.getDate(), isChecked);

        });
        return this;
    }
}

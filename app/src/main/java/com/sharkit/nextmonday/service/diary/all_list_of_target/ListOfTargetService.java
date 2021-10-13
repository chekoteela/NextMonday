package com.sharkit.nextmonday.service.diary.all_list_of_target;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.adapter.diary.AllListOfItem;
import com.sharkit.nextmonday.db.sqlite.diary.TargetDataService;
import com.sharkit.nextmonday.service.builder.LayoutService;

public class ListOfTargetService implements LayoutService {
    private EditText find;
    private ExpandableListView listOfItem;
    private Context context;
    private TargetDataService service;

    @Override
    public LayoutService writeToField() {
        return this;
    }

    @Override
    public LayoutService findById(View root) {
        context = root.getContext();
        find = root.findViewById(R.id.find_xml);
        listOfItem = root.findViewById(R.id.list_of_item_xml);
        service = new TargetDataService(context);
        return this;
    }

    @Override
    public LayoutService setAdaptive() {
        return this;
    }

    @Override
    public LayoutService activity() {
        setAdapter();
        find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!find.getText().toString().equals("")) {
                    setAdapterFromName(s.toString());
                }else {
                    setAdapter();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return this;
    }

    private void setAdapterFromName(String tag) {
        listOfItem.setAdapter(new AllListOfItem(context, service.getChildFromName(tag)));
    }

    private void setAdapter() {
        listOfItem.setAdapter(new AllListOfItem(context, service.getChildItem()));
    }
}

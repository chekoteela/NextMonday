package com.sharkit.nextmonday.ui.Diary;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.Adapters.DiaryListFind;
import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.Users.MyTarget;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DiaryFindAllTarget extends Fragment {
    private EditText text;
    private ExpandableListView listView;
    private ArrayList<MyTarget> targets;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dairy_three, container, false);
        findView(root);
        writeAllList();
        find();
        return root;
    }

    private void find() {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(text.getText())){
                    writeAllList();
                }else {
                    findForName(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void findForName(String text) {
        targets = new ArrayList<>();
        TargetData targetData = new TargetData(getContext());
        targetData.findItemForName(text, targets);

        DiaryListFind adapter = new DiaryListFind(getContext(), targets);
        listView.setAdapter(adapter);
    }

    private void writeAllList() {
        targets = new ArrayList<>();
        TargetData targetData = new TargetData(getContext());
        targetData.findAllComplete(targets);

        DiaryListFind adapter = new DiaryListFind(getContext(), targets);
        listView.setAdapter(adapter);
    }

    private void findView(View root) {
        text = root.findViewById(R.id.find_xml);
        listView = root.findViewById(R.id.listFind);
    }
}

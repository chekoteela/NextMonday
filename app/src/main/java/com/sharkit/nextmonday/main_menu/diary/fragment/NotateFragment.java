package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_FOLDER_ID;
import static com.sharkit.nextmonday.main_menu.diary.transformer.NotateTransformer.toNotates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.NotateAdaptor;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogCreateNotate;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;

import java.util.List;
import java.util.Optional;

public class NotateFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_noto, container, false);
        final WidgetContainer.DiaryNotateWidget widget = WidgetContainer.newInstance(view).getDiaryNotateWidget();
        final NextMondayDatabase db = NextMondayDatabase.getInstance(getContext());

        final Long parentId = Optional.ofNullable(getArguments())
                .map(arg -> arg.getLong(DIARY_NOTATE_FOLDER_ID))
                .orElse(0L);

        final List<Notate> notate = toNotates(db.notateDAO().getAllNotateByParentFolderId(parentId));
        final NotateAdaptor adaptor = new NotateAdaptor(notate, getContext());

        widget.getNotateList().setAdapter(adaptor);

        widget.getAdd().setOnClickListener(v -> new DialogCreateNotate()
                .showCreateNotateDialog(getContext(), parentId, adaptor, notate));

        return view;
    }
}

package com.sharkit.nextmonday.main_menu.diary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseItem;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseAdapter extends BaseAdapter {

    private final List<PurchaseItem> items;
    private final Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PurchaseItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.diary_purchase_item, null);
        final WidgetContainer.DiaryPurchaseWidget.PurchaseItemWidget widget = WidgetContainer.newInstance(convertView).getDiaryPurchaseWidget().getPurchaseItemWidget();
        final PurchaseItem item = getItem(position);

        widget.getComplete().setChecked(item.getComplete());
        widget.getDescription().setText(item.getDescription());
        widget.getName().setText(item.getName());

        return convertView;
    }
}

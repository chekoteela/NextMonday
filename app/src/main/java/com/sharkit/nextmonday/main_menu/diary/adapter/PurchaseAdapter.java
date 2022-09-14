package com.sharkit.nextmonday.main_menu.diary.adapter;

import static com.sharkit.nextmonday.main_menu.diary.transformer.purchase.PurchaseItemTransformer.toPurchaseItemDTO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogPurchaseItem;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseItem;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseAdapter extends BaseAdapter {

    private final List<PurchaseItem> items;
    private final Context context;

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public PurchaseItem getItem(final int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(this.context).inflate(R.layout.diary_purchase_item, null);
        final WidgetContainer.DiaryPurchaseWidget.PurchaseItemWidget widget = WidgetContainer.newInstance(convertView).getDiaryPurchaseWidget().getPurchaseItemWidget();
        final NextMondayDatabase db = NextMondayDatabase.getInstance(this.context);

        widget.getComplete().setChecked(this.getItem(position).getStatus());
        widget.getDescription().setText(this.getItem(position).getDescription());
        widget.getName().setText(this.getItem(position).getName());
        widget.getItem().setOnCreateContextMenuListener((menu, v, menuInfo) -> this.createMenuListener(menu, this.context, position));
        widget.getComplete().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed())
                db.purchaseItemDAO().updateStatus(isChecked, this.getItem(position).getId());
        });
        return convertView;
    }

    private void createMenuListener(final ContextMenu menu, final Context context, final int position) {
        menu.add(context.getString(R.string.button_change))
                .setOnMenuItemClickListener(item -> {
                    new DialogPurchaseItem(context, this.items.get(position).getTemplateId(), this.items, PurchaseAdapter.this)
                            .changeItem(this.items.get(position), position);
                    return true;
                });
        menu.add(context.getString(R.string.button_delete))
                .setOnMenuItemClickListener(item -> {
                    NextMondayDatabase.getInstance(context).purchaseItemDAO().delete(toPurchaseItemDTO(this.items.get(position)));
                    this.items.remove(position);
                    this.notifyDataSetChanged();
                    return true;
                });
    }
}

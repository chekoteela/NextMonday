package com.sharkit.nextmonday.main_menu.diary.dialog;

import static com.sharkit.nextmonday.main_menu.diary.transformer.purchase.PurchaseItemTransformer.toPurchaseItemDTO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.configuration.widget.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.PurchaseAdapter;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseItem;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DialogPurchaseItem {

    private final Context context;
    private final Long templateId;
    private final List<PurchaseItem> purchaseItems;
    private final PurchaseAdapter purchaseAdapter;
    private WidgetContainer.Dialog.TemplateAddItemWidget widget;

    public void createItem() {
        final NextMondayDatabase db = NextMondayDatabase.getInstance(this.context);
        final AlertDialog dialog = this.showDialog();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, this.context.getString(R.string.button_add), (parent, which) -> {
            final PurchaseItem item = PurchaseItem.builder()
                    .status(Boolean.FALSE)
                    .description(this.widget.getDescription().getText().toString())
                    .name(this.widget.getName().getText().toString())
                    .templateId(this.templateId)
                    .build();
            db.purchaseItemDAO().create(toPurchaseItemDTO(item));
            this.purchaseItems.add(item);
            this.purchaseAdapter.notifyDataSetChanged();
            parent.dismiss();
        });
        dialog.show();
    }

    public void changeItem(final PurchaseItem item, final int position){
        final NextMondayDatabase db = NextMondayDatabase.getInstance(this.context);
        final AlertDialog dialog = this.showDialog();
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, this.context.getString(R.string.button_change), (parent, which) -> {
            item.setDescription(this.widget.getDescription().getText().toString());
            item.setName(this.widget.getName().getText().toString());

            db.purchaseItemDAO().update(toPurchaseItemDTO(item));

            this.purchaseItems.set(position, item);
            this.purchaseAdapter.notifyDataSetChanged();
            parent.dismiss();
        });
        dialog.show();

    }

    private AlertDialog showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this.context).create();
        final View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_diary_recipe_food, null);

        this.widget = WidgetContainer.newInstance(view).getDialog().getTemplateAddItemWidget();
        dialog.setView(view);
        return dialog;
    }
}

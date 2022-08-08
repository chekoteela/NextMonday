package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE;
import static com.sharkit.nextmonday.main_menu.diary.transformer.purchase.PurchaseTransformer.toPurchase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.PurchaseAdapter;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogPurchaseItem;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.Purchase;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseItem;

public class PurchaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_notate_purchase_fragment, container, false);
        final WidgetContainer.DiaryPurchaseWidget widget = WidgetContainer.newInstance(view).getDiaryPurchaseWidget();
        final Notate notate = (Notate) requireArguments().getSerializable(DIARY_NOTATE);
        final NextMondayDatabase db = NextMondayDatabase.getInstance(getContext());
        final Purchase purchase = toPurchase(db.purchaseTemplateDAO().findById(notate.getTemplateId()));
        final PurchaseAdapter purchaseAdapter = new PurchaseAdapter(purchase.getItems(), getContext());

        widget.getPurchaseList().setAdapter(purchaseAdapter);
        widget.getName().setText(notate.getName());
        widget.getDescription().setText(purchase.getTemplate().getText());
        widget.getAddPurchase().setOnClickListener(v -> new DialogPurchaseItem(getContext(), notate.getTemplateId(), purchase.getItems(), purchaseAdapter).createItem());
        return view;
    }
}

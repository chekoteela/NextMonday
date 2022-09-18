package com.sharkit.nextmonday.main_menu.diary.fragment;

import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE;
import static com.sharkit.nextmonday.main_menu.diary.configuration.DiaryBundleTag.DIARY_NOTATE_FOLDER_ID;
import static com.sharkit.nextmonday.main_menu.diary.transformer.purchase.PurchaseTemplateTransformer.toPurchaseTemplateDTO;
import static com.sharkit.nextmonday.main_menu.diary.transformer.purchase.PurchaseTransformer.toPurchase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sharkit.nextmonday.R;
import com.sharkit.nextmonday.configuration.database.NextMondayDatabase;
import com.sharkit.nextmonday.main_menu.diary.configuration.widget.WidgetContainer;
import com.sharkit.nextmonday.main_menu.diary.adapter.PurchaseAdapter;
import com.sharkit.nextmonday.main_menu.diary.dialog.DialogPurchaseItem;
import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.Purchase;
import com.sharkit.nextmonday.main_menu.diary.domain.template.purchase.PurchaseTemplate;

public class PurchaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diary_notate_purchase_fragment, container, false);
        final WidgetContainer.DiaryPurchaseWidget widget = WidgetContainer.newInstance(view).getDiaryPurchaseWidget();
        final Notate notate = (Notate) this.requireArguments().getSerializable(DIARY_NOTATE);
        final NextMondayDatabase db = NextMondayDatabase.getInstance(this.getContext());
        final Purchase purchase = toPurchase(db.purchaseTemplateDAO().findById(notate.getTemplateId()));
        final PurchaseAdapter purchaseAdapter = new PurchaseAdapter(purchase.getItems(), this.getContext());

        widget.getPurchaseList().setAdapter(purchaseAdapter);
        widget.getName().setText(notate.getName());
        widget.getDescription().setText(purchase.getTemplate().getText());
        widget.getAddPurchase().setOnClickListener(v -> new DialogPurchaseItem(this.getContext(), notate.getTemplateId(), purchase.getItems(), purchaseAdapter).createItem());
        widget.getSave().setOnClickListener(v -> this.saveChanges(widget, purchase.getTemplate(), db, notate));
        return view;
    }

    private void saveChanges(final WidgetContainer.DiaryPurchaseWidget widget, final PurchaseTemplate template, final NextMondayDatabase db, final Notate notate) {
        template.setText(widget.getDescription().getText().toString());
        db.purchaseTemplateDAO().update(toPurchaseTemplateDTO(template));
        db.notateDAO().updateName(widget.getName().getText().toString(), notate.getId());

        final Bundle bundle = new Bundle();
        bundle.putLong(DIARY_NOTATE_FOLDER_ID, notate.getParentFolderId());
        Navigation.findNavController(this.requireActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_diary_notate, bundle);
    }
}

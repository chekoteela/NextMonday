package com.sharkit.nextmonday.configuration.blocker.impl;

import android.view.MenuItem;
import android.view.View;

import com.sharkit.nextmonday.configuration.blocker.BlockerActionI;

import java.util.Arrays;

public class BlockService implements BlockerActionI {

    @Override
    public void visibility(final View... views) {
        Arrays.stream(views).forEach(view -> view.setVisibility(View.GONE));
    }

    @Override
    public void visibility(final MenuItem... menuItems) {
        Arrays.stream(menuItems).forEach(menuItem -> menuItem.setVisible(Boolean.FALSE));
    }

    @Override
    public void enabling(final View... views) {
        Arrays.stream(views).forEach(view -> view.setEnabled(Boolean.FALSE));
    }

    @Override
    public void enabling(final MenuItem... menuItems) {
        Arrays.stream(menuItems).forEach(menuItem -> menuItem.setEnabled(Boolean.FALSE));
    }
}

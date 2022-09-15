package com.sharkit.nextmonday.configuration.blocker.impl;

import android.view.MenuItem;
import android.view.View;

import com.sharkit.nextmonday.configuration.blocker.BlockerActionI;

import java.util.Arrays;

public class UnblockService implements BlockerActionI {

    @Override
    public void visibility(final View... views) {
        Arrays.stream(views).forEach(view -> view.setVisibility(View.VISIBLE));
    }

    @Override
    public void visibility(final MenuItem... menuItems) {
        Arrays.stream(menuItems).forEach(menuItem -> menuItem.setVisible(Boolean.TRUE));
    }

    @Override
    public void enabling(final View... views) {
        Arrays.stream(views).forEach(view -> view.setEnabled(Boolean.TRUE));
    }

    @Override
    public void enabling(final MenuItem... menuItems) {
        Arrays.stream(menuItems).forEach(menuItem -> menuItem.setEnabled(Boolean.TRUE));
    }
}

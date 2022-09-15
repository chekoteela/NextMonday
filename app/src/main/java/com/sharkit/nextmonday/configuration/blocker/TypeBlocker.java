package com.sharkit.nextmonday.configuration.blocker;

import android.view.MenuItem;
import android.view.View;

import com.sharkit.nextmonday.configuration.blocker.impl.BlockService;
import com.sharkit.nextmonday.configuration.blocker.impl.UnblockService;

public enum TypeBlocker implements BlockerActionI {

    BLOCK,
    UNBLOCK;

    @Override
    public void visibility(final View... views) {
        this.getService().visibility(views);
    }

    @Override
    public void enabling(final View... views) {
        this.getService().enabling(views);
    }

    @Override
    public void visibility(final MenuItem... menuItems) {
        this.getService().visibility(menuItems);
    }

    @Override
    public void enabling(final MenuItem... menuItems) {
        this.getService().enabling(menuItems);
    }

    private BlockerActionI getService() {
        switch (this) {
            case BLOCK:
                return new BlockService();
            case UNBLOCK:
            default:
                return new UnblockService();
        }
    }

}

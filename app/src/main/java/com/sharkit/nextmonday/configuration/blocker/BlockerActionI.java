package com.sharkit.nextmonday.configuration.blocker;

import android.view.MenuItem;
import android.view.View;

public interface BlockerActionI {

    void visibility(View... views);

    void enabling(View... views);

    void visibility(MenuItem... menuItems);

    void enabling(MenuItem... menuItems);
}

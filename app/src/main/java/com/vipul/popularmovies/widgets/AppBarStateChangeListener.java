package com.vipul.popularmovies.widgets;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;

import com.vipul.popularmovies.utils.Utils;

/**
 * Created by HP-HP on 20-05-2016.
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onExpanded(appBarLayout);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onCollapsed(appBarLayout);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onIdle(appBarLayout);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onExpanded(AppBarLayout appBarLayout);
    public abstract void onCollapsed(AppBarLayout appBarLayout);
    public abstract void onIdle(AppBarLayout appBarLayout);

}

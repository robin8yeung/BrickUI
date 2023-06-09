package com.seewo.brick.params;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static com.seewo.brick.params.CollapseMode.COLLAPSE_MODE_OFF;
import static com.seewo.brick.params.CollapseMode.COLLAPSE_MODE_PARALLAX;
import static com.seewo.brick.params.CollapseMode.COLLAPSE_MODE_PIN;

import androidx.annotation.IntDef;
import androidx.annotation.RestrictTo;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@RestrictTo(LIBRARY_GROUP)
@IntDef({COLLAPSE_MODE_OFF, COLLAPSE_MODE_PIN, COLLAPSE_MODE_PARALLAX})
@Retention(RetentionPolicy.SOURCE)
public @interface CollapseMode {
    int COLLAPSE_MODE_OFF = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF;
    int COLLAPSE_MODE_PARALLAX = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX;
    int COLLAPSE_MODE_PIN = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN;
}

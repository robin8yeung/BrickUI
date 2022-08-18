package com.seewo.brick;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.Gravity;

import androidx.annotation.NonNull;

public class JavaDrawableUtils {
   private JavaDrawableUtils() {}

   /**
    * 由于Kotlin编译成以下语句，而DrawableWrapper在 sdkVersion 23 才支持，导致低版本设备崩溃
    *
    * var21[1] = (Drawable)(progressType == 1 ? (DrawableWrapper)(new ClipDrawable(progressDrawable, 8388611, 1)) : (DrawableWrapper)(new ScaleDrawable(progressDrawable, 8388611, 1.0F, -1.0F)));
    */
   @NonNull
   public static Drawable getDrawableWrapper(Drawable innerDrawable, int type) {
      if (type == 1) {
         return getClipDrawable(innerDrawable);
      } else {
         return getScaleDrawable(innerDrawable);
      }
   }

   @NonNull
   public static ScaleDrawable getScaleDrawable(Drawable innerDrawable) {
      return new ScaleDrawable(innerDrawable, Gravity.START, 1f, -1f);
   }

   @NonNull
   public static ClipDrawable getClipDrawable(Drawable innerDrawable) {
      return new ClipDrawable(innerDrawable, Gravity.START, ClipDrawable.HORIZONTAL);
   }
}

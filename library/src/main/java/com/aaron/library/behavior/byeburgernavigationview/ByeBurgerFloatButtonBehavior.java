package com.aaron.library.behavior.byeburgernavigationview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Behavior for Float Button
 * Created by wing on 11/8/16.
 */

public class ByeBurgerFloatButtonBehavior extends ByeBurgerBehavior {

  private ScaleAnimateHelper mAnimateHelper;

  public ByeBurgerFloatButtonBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);

  }



  @Override
  public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                                int dx, int dy, int[] consumed) {

    if (isFirstMove) {
      isFirstMove = false;
      mAnimateHelper = ScaleAnimateHelper.get(child);
    }
    if (Math.abs(dy) > mTouchSlop) {
      if (dy < 0) {
        if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_HIDE) {
          mAnimateHelper.show();
        }
      } else if (dy > 0) {
        if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_SHOW) {
          mAnimateHelper.hide();
        }
      }
    }
  }
}

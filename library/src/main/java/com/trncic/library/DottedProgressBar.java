package com.trncic.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by igortrncic on 6/18/15.
 *
 * @modified by fangler at 5/8/15
 */
public class DottedProgressBar extends View {
  private static final int COLOR_ACTIVE = Color.parseColor("#68DE67");
  private static final int COLOR_EMPTY = Color.WHITE;
  private static final int DEFAULT_DOT_SIZE = 5;
  private static final int DEFAULT_DOT_SPACE = 10;

  private float mDotSize;
  private float mSpacing;
  private int mJumpingSpeed;
  private int mEmptyDotsColor;
  private int mActiveDotColor;
  private Drawable mActiveDot;
  private Drawable mInactiveDot;

  private boolean isInProgress;
  private boolean isActiveDrawable = false;
  private boolean isInactiveDrawable = false;

  private int mActiveDotIndex;

  private int mNumberOfDots;
  private Paint mPaint;
  private int mPaddingLeft;
  private Handler mHandler;
  private Runnable mRunnable = new Runnable() {
    @Override
    public void run() {
      if (mNumberOfDots != 0)
        mActiveDotIndex = (mActiveDotIndex + 1) % mNumberOfDots;
      DottedProgressBar.this.invalidate();
      mHandler.postDelayed(mRunnable, mJumpingSpeed);
    }
  };

  public DottedProgressBar(Context context) {
    this(context, null);
  }

  public DottedProgressBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public DottedProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }


  private void init(Context context, AttributeSet attrs) {
    isInProgress = false;
    mHandler = new Handler();
    isActiveDrawable = false;
    isInactiveDrawable = false;
    mActiveDotColor = COLOR_ACTIVE;
    mEmptyDotsColor = COLOR_EMPTY;
    mDotSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DOT_SIZE, getResources().getDisplayMetrics());
    mSpacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DOT_SPACE, getResources().getDisplayMetrics());
    mActiveDotIndex = 0;
    mJumpingSpeed = 500;
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Paint.Style.FILL);

    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(
          attrs,
          R.styleable.DottedProgressBar,
          0, 0);
      TypedValue value = new TypedValue();
      a.getValue(R.styleable.DottedProgressBar_activeDot, value);
      if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
        // It's a color
        mActiveDotColor = getResources().getColor(value.resourceId);
      } else if (value.type == TypedValue.TYPE_STRING) {
        // It's a reference, hopefully to a drawable
        isActiveDrawable = true;
        mActiveDot = getResources().getDrawable(value.resourceId);
      }

      isInactiveDrawable = false;
      a.getValue(R.styleable.DottedProgressBar_inactiveDot, value);
      if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
        // It's a color
        mEmptyDotsColor = getResources().getColor(value.resourceId);
      } else if (value.type == TypedValue.TYPE_STRING) {
        // It's a reference, hopefully to a drawable
        isInactiveDrawable = true;
        mInactiveDot = getResources().getDrawable(value.resourceId);
      }
      mDotSize = a.getDimensionPixelSize(R.styleable.DottedProgressBar_dotSize, (int) mDotSize);
      mSpacing = a.getDimensionPixelSize(R.styleable.DottedProgressBar_spacing, (int) mSpacing);

      mActiveDotIndex = a.getInteger(R.styleable.DottedProgressBar_activeDotIndex, 0);
      mJumpingSpeed = a.getInt(R.styleable.DottedProgressBar_jumpingSpeed, 500);
      a.recycle();
    }
  }

  public void setSpace(int space){
    mSpacing = space;
    requestLayout();
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    for (int i = 0; i < mNumberOfDots; i++) {
      int x = (int) (getPaddingLeft() + mPaddingLeft + mSpacing / 2 + i * (mSpacing + mDotSize));
      if (isInactiveDrawable) {
        mInactiveDot.setBounds(x, getPaddingTop(), (int) (x + mDotSize), getPaddingTop() + (int) mDotSize);
        mInactiveDot.draw(canvas);
      } else {
        mPaint.setColor(mEmptyDotsColor);
        canvas.drawCircle(x + mDotSize / 2,
            getPaddingTop() + mDotSize / 2, mDotSize / 2, mPaint);
      }
    }
    if (isInProgress) {
      int x = (int) (getPaddingLeft() + mPaddingLeft + mSpacing / 2 + mActiveDotIndex * (mSpacing + mDotSize));
      if (isActiveDrawable) {
        mActiveDot.setBounds(x, getPaddingTop(), (int) (x + mDotSize), getPaddingTop() + (int) mDotSize);
        mActiveDot.draw(canvas);
      } else {
        mPaint.setColor(mActiveDotColor);
        canvas.drawCircle(x + mDotSize / 2,
            getPaddingTop() + mDotSize / 2, mDotSize / 2, mPaint);
      }
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

    int widthWithoutPadding = parentWidth - getPaddingLeft() - getPaddingRight();

    int calculatedHeight = getPaddingTop() + getPaddingBottom() + (int) mDotSize;
    Log.d("onMeasure", calculatedHeight + "");
    this.setMeasuredDimension(parentWidth, calculatedHeight);
    mNumberOfDots = calculateDotsNumber(widthWithoutPadding);
  }

  private int calculateDotsNumber(int width) {
    int number = (int) (width / (mDotSize + mSpacing));
    mPaddingLeft = (int) ((width % (mDotSize + mSpacing)) / 2);
    //setPadding(getPaddingLeft() + (int) mPaddingLeft, getPaddingTop(), getPaddingRight() + (int) mPaddingLeft, getPaddingBottom());
    return number;
  }

  public DottedProgressBar startProgress() {
    isInProgress = true;
    mActiveDotIndex = -1;
    mHandler.removeCallbacks(mRunnable);
    mHandler.post(mRunnable);
    return this;
  }

  public void stopProgress() {
    isInProgress = false;
    mHandler.removeCallbacks(mRunnable);
    invalidate();
  }
}

package com.android.benben.mymoneydemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Time      2017/5/18 18:24 .
 * Author   : LiYuanXiong.
 * Content  :圆形图
 */

public class BenSportView extends View {

    private String text;
    private int textColor;
    private int textSize;
    private int outCircleColor;
    private int inMainCircleColor;
    private int inCircleColor;
    /*绘制时控制文本绘制的范围*/
    private Paint mPaint, circlePaint,circlePaintMain;

    private Rect mBound;
    private RectF circleRect,circleRectMain;
    private float mCurrentAngle, mCurrentAngleMain;
    private float mStartSweepValue;
    private int mCurrentPercent, mTargetPercent;




    public BenSportView(Context context) {
        this(context, null);
    }

    public BenSportView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BenSportView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*获取我们自定义样式的属性*/
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BenSportView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.BenSportView_titleColor:
                    /*默认颜色设置为黑色*/
                    textColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.BenSportView_titleSize:
                    /*默认设置为16sp，typeValue也可以吧把sp转化为px*/
                    textSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.BenSportView_outCircleColor:
                    /*默认颜色为黑色*/
                    outCircleColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.BenSportView_inCircleColor:
                    /*默认颜色为黑色*/
                    inCircleColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.BenSportView_inMainCircleColor:
                    inMainCircleColor = array.getColor(attr, Color.BLACK);
                    break;
            }
        }
        array.recycle();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        /*创建画笔*/
        mPaint = new Paint();
        circlePaint = new Paint();
        circlePaintMain = new Paint();
        /*设置是否抗锯齿*/
        mPaint.setAntiAlias(true);
        /*圆形开始角度-90 正北方*/
        mStartSweepValue = -90;
        /*当前角度*/
        mCurrentAngle = 0;
        mCurrentAngleMain = 0;
        /*当前百分比*/
        mCurrentPercent = 0;
        mBound = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*设置外圆的颜色*/
        mPaint.setColor(outCircleColor);
        /*设置外圆为空心*/
        mPaint.setStyle(Paint.Style.STROKE);
        /*画外圆*/
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mPaint);

        circlePaintMain.setAntiAlias(true);
        circlePaintMain.setStyle(Paint.Style.STROKE);
        circlePaintMain.setStrokeWidth(50);
        circlePaintMain.setColor(inMainCircleColor);
        int y=30;
        circleRectMain = new RectF(y, y, getWidth() - y, getWidth() - y);
        canvas.drawArc(circleRectMain, mStartSweepValue, mCurrentAngleMain, false, circlePaintMain);
        mPaint.setStrokeWidth(1);

        if (mCurrentAngleMain < 360) {
            /*当前角度+3.6*/
            mCurrentAngleMain += 3.6;
            /*每100ms重画一次*/
            postInvalidateDelayed(45);
        }
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        /*设置圆弧的宽度*/
        circlePaint.setStrokeWidth(50);
        /*设置圆弧的颜色*/
        circlePaint.setColor(inCircleColor);
        /*圆弧范围*/
        int x=30;
        circleRect = new RectF(x, x, getWidth() - x, getWidth() - x);
        /*绘制圆弧*/
        canvas.drawArc(circleRect, mStartSweepValue, mCurrentAngle, false, circlePaint);

                /*设置字体颜色*/
        mPaint.setColor(textColor);
        /*设置字体大小*/
        mPaint.setTextSize(textSize);
        /*得到字体的宽高范围*/
        text = String.valueOf(mCurrentPercent+"%");
        mPaint.getTextBounds(text, 0, text.length(), mBound);
        /*绘制字体*/
        canvas.drawText(text
                , getWidth() / 2 - mBound.width() / 2,
                getWidth() / 2 + mBound.height() / 2,
                mPaint);


        /*判断当前百分比是否小于设置目标的百分比*/
        if (mCurrentPercent < mTargetPercent) {
            /*当前百分比+1*/
            mCurrentPercent += 1;
            /*当前角度+3.6*/
            mCurrentAngle += 3.6;
            /*每100ms重画一次*/
            postInvalidateDelayed(45);
        }
    }

    public void setmTargetPercent(int mTargetPercent) {
        this.mTargetPercent = mTargetPercent;
    }

    public void setNumber(int size){
        mCurrentPercent = 0;
        mTargetPercent = size;
        mCurrentAngle = 0;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果布局里面没有设置固定值,这里取字体的宽度
            width = widthSize * 1 / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 2;
        }
        setMeasuredDimension(width, height);
    }
}

package com.example.jetwang.testitout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jet Wang on 2016/8/2.
 */
public class ShapeSelectorView extends View {
    private static final String TAG = "ShapeSelectorView";
    private int shapeColor;
    private boolean displayShapeName;

    private int shapeWidth = 100;
    private int shapeHeight = 100;
    private int textXOffset = 0;
    private int textYOffset = 30;
    private Paint paintShape;

    private String[] shapeValues = {"square", "circle", "triangle"};
    private int currentShapeIndex = 0;

    public ShapeSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
        setupPaint();
    }

    private void setupPaint() {
        paintShape = new Paint();
        paintShape.setColor(shapeColor);
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setTextSize(30);
    }

    private void setupAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ShapeSelectorView, 0, 0);
        shapeColor = a.getColor(R.styleable.ShapeSelectorView_shapeColor, Color.BLACK);
        displayShapeName = a.getBoolean(R.styleable.ShapeSelectorView_displayShapeName, false);
        a.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            //TODO:++在前表示先执行加一，++在后表示等整个((++currentShapeIndex) % shapeValues.length)表达式执行完成后再给currentShapeIndex加一。
            currentShapeIndex = ((++currentShapeIndex) % shapeValues.length);
            Log.d(TAG, "onTouchEvent: Action_Down: " + currentShapeIndex);
            postInvalidate();
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("currentShapeIndex", currentShapeIndex);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            this.currentShapeIndex = bundle.getInt("currentShapeIndex");
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int textPadding = 10;
        int contentWidth = shapeWidth;

        int minw = contentWidth + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        int minh = shapeHeight + getPaddingTop() + getPaddingBottom();
        if (displayShapeName){
            minh += textYOffset + textPadding;
        }
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String shapeSelected = shapeValues[currentShapeIndex];
        Log.d(TAG, "onDraw: " + shapeSelected + currentShapeIndex);
        switch (shapeSelected){
            case "square":
                canvas.drawRect(0, 0, shapeWidth, shapeHeight, paintShape);
                textXOffset = 0;
                break;
            case "circle":
                canvas.drawCircle(shapeWidth/2, shapeHeight/2, shapeWidth/2, paintShape);
                textXOffset = 12;
                break;
            case "triangle":
                canvas.drawPath(getTrianglePath(), paintShape);
                textXOffset = 0;
                break;
        }

        if (displayShapeName) canvas.drawText(shapeSelected, textXOffset, shapeHeight+textYOffset, paintShape);
    }

    private Path getTrianglePath() {
        Point p1 = new Point(0, shapeHeight);
        Point p2 = new Point(p1.x + shapeWidth, p1.y);
        Point p3 = new Point(p1.x + shapeWidth/2, p1.y - shapeHeight);
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        return path;
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
        invalidate();
        requestLayout();
    }

    public boolean isDisplayShapeName() {
        return displayShapeName;
    }

    public void setDisplayShapeName(boolean displayShapeName) {
        this.displayShapeName = displayShapeName;
        invalidate();
        requestLayout();
    }
}

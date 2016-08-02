package com.example.jetwang.testitout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jet Wang on 2016/8/2.
 */
public class ShapeSelectorView extends View {
    private int shapeColor;
    private boolean displayShapeName;

    private int shapeWidth = 100;
    private int shapeHeight = 100;
    private int textXOffset = 0;
    private int textYOffset = 30;
    private Paint paintShape;

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, shapeWidth, shapeHeight, paintShape);
        if (displayShapeName) canvas.drawText("Square", textXOffset, shapeHeight+textYOffset, paintShape);
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

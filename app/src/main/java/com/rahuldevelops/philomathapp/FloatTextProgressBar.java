package com.rahuldevelops.philomathapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;


public class FloatTextProgressBar extends ProgressBar {


    private float progressHeight;


    private float floatRectWidth;

    private float floatRectHeight;


    private float triangleWidth;


    private float margin;


    private float textSize;


    private int triangleColor;


    private int rectColor;


    protected int fillColor;

    public FloatTextProgressBar(Context context) {
        super(context);
    }

    public FloatTextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FloatTextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void getDimension() {
        super.getDimension();
        progressHeight = height / 5;
        floatRectWidth = height /  5 * 4;
        floatRectHeight = height / 9 * 4;
        triangleWidth = height / 7 * 2;
        margin = dip2px(3);
        textSize = height / 4;
    }

    private void init(AttributeSet attrs){
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.floatTextProgressBar1);
        fillColor = a.getColor(R.styleable.floatTextProgressBar1_fillColor1, 0xffff0000);
        triangleColor = a.getColor(R.styleable.floatTextProgressBar1_triangleColor1, 0xffff0000);
        rectColor = a.getColor(R.styleable.floatTextProgressBar1_rectColor1, 0xffff0000);
        a.recycle();
    }

    @Override
    public void drawProgress(Canvas canvas) {
        paint.setColor(backgroundColor);
        RectF backgroundRectF = new RectF(0, height - progressHeight, width, height);
        canvas.drawRoundRect(backgroundRectF, progressHeight / 2, progressHeight / 2, paint);

        //绘制填充条
        paint.setColor(fillColor);
        RectF fillRectF = new RectF(0, height - progressHeight, progressWidth, height);
        canvas.drawRoundRect(fillRectF, progressHeight / 2, progressHeight / 2, paint);

        drawFloatRect(canvas);
    }




    private void drawFloatRect(Canvas canvas){
        if (progressWidth < floatRectWidth + margin){

            paint.setColor(rectColor);
            RectF floatRectF = new RectF(margin, 0, margin + floatRectWidth, floatRectHeight);
            canvas.drawRoundRect(floatRectF, dip2px(2), dip2px(2), paint);


            paint.setColor(triangleColor);
            Path path = new Path();
            path.moveTo(margin + floatRectWidth / 2 - triangleWidth / 2, height / 7 * 3);
            path.lineTo(margin + floatRectWidth / 2 + triangleWidth / 2, height / 7 * 3);
            path.lineTo(margin + floatRectWidth / 2, floatRectWidth / 4 + height / 7 * 3);
            path.close();
            canvas.drawPath(path, paint);
        } else if (width - progressWidth < floatRectWidth + margin){

            paint.setColor(rectColor);
            RectF floatRectF = new RectF(width - floatRectWidth - margin ,0, width - margin, floatRectHeight);
            canvas.drawRoundRect(floatRectF, dip2px(2), dip2px(2), paint);

            //绘制三角形
            paint.setColor(triangleColor);
            Path path = new Path();
            path.moveTo(width - margin - floatRectWidth / 2 - triangleWidth / 2, height / 7 * 3);
            path.lineTo(width - margin - floatRectWidth / 2 + triangleWidth / 2, height / 7 * 3);
            path.lineTo(width - margin - floatRectWidth / 2, floatRectWidth / 4 + height / 7 * 3);
            path.close();
            canvas.drawPath(path, paint);
        } else {

            paint.setColor(rectColor);
            RectF floatRectF = new RectF(progressWidth - floatRectWidth / 2 ,0, progressWidth + floatRectWidth / 2, floatRectHeight);
            canvas.drawRoundRect(floatRectF, dip2px(2), dip2px(2), paint);


            paint.setColor(triangleColor);
            Path path = new Path();
            path.moveTo(progressWidth - triangleWidth / 2, height / 7 * 3);
            path.lineTo(progressWidth + triangleWidth / 2, height / 7 * 3);
            path.lineTo(progressWidth, floatRectWidth / 4 + height / 7 * 3);
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public void drawText(Canvas canvas) {
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        String progressText = (progress * 100f / this.maxProgress) + "%";
        float textWidth = paint.measureText(progressText);
        if (progressWidth < floatRectWidth + margin) {
            canvas.drawText(progressText, margin + floatRectWidth / 2 - textWidth / 2, floatRectHeight / 2 + textSize / 4, paint);
        } else if (width - progressWidth < floatRectWidth + margin){
            canvas.drawText(progressText, width - margin - floatRectWidth / 2 - textWidth / 2, floatRectHeight / 2 + textSize / 4, paint);
        } else {
            canvas.drawText(progressText, progressWidth - textWidth / 2, floatRectHeight / 2 + textSize / 4, paint);
        }
    }


    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }


    public void setRectColor(int rectColor) {
        this.rectColor = rectColor;
    }


    public void setTriangleColor(int triangleColor) {
        this.triangleColor = triangleColor;
    }
}

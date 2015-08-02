package com.whereshappening.myapplication.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.whereshappening.myapplication.R;

import java.util.ArrayList;

/**
 * Created by pierro on 7/30/15.
 */
public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;


    //brush sizes
    private float brushSize, lastBrushSize;
    //erase flag
    private boolean erase=false;

    private Path lastdrawPath;
    private Paint lastdrawPaint;


    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }





    //setup drawing
    private void setupDrawing(){
        //prepare for drawing and setup paint stroke properties
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
        setPath();
        setPaint();
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    public void setPath(){
        drawPath = new Path();
    }

    public void setPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    //size assigned to view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }


    ArrayList<DrawPath> paths = new ArrayList<>();
    ArrayList<DrawPath> undoPaths = new ArrayList<>();

    //draw the view - will be called after touch event
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);

        drawCanvas.drawPath(drawPath, drawPaint);
       // lastdrawPaint = drawPaint;



    }


    public void addPath(Path drawPath, Paint drawPaint){

        DrawPath dp = new DrawPath();
        dp.paint = drawPaint;
        dp.path = drawPath;
        paths.add(dp);

    }



    public void undo(){
        undoPaths.add(paths.get(paths.size() - 1));
        paths.remove(paths.size() - 1);
        drawPaths();
    }

    public void redo() {
        paths.add(undoPaths.get(undoPaths.size()-1));
        undoPaths.remove(undoPaths.size()-1);
        drawPaths();
    }

    public void drawPaths(){
        drawCanvas.drawColor(Color.WHITE);
        for(DrawPath p:paths){
            drawCanvas.drawPath(p.path, p.paint);
        }
        invalidate();
    }

    public class DrawPath{
        public Path path;
        public Paint paint;
    }

    //register user touches as drawing action
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);

                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                //drawCanvas.drawPath(drawPath, drawPaint);

             /*   lastdrawPath = drawPath;
                lastdrawPaint =  drawPaint;*/
                addPath( drawPath,drawPaint);
                setPath();
                setPaint();
                break;
            default:
                return false;
        }

        //redraw
        invalidate();
        return true;

    }

    //update color
    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    //set brush size
    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    //get and set last brush size
    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }

    //set erase true or false
    public void setErase(boolean isErase){
        erase=isErase;

        if(erase){

            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        }

        else drawPaint.setXfermode(null);
    }

    //start new drawing
    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}


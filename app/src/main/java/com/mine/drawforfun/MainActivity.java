package com.mine.drawforfun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    Paint paint;
    Path path;
    Bitmap bitmap;
    RelativeLayout rl;
    View view;
    Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        rl = findViewById( R.id.relativelayout1 );
        button = findViewById( R.id.button );
        paint = new Paint();
        path = new Path();
        view = new sketchView( MainActivity.this );

        rl.addView( view, new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT ) );

        paint.setDither(true);
        paint.setColor( Color.parseColor("#000000"));
        paint.setAntiAlias( true );
        paint.setStrokeWidth( 2 );
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeJoin( Paint.Join.ROUND );
        paint.setStrokeCap( Paint.Cap.ROUND );

        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                path.reset();
                view.invalidate();
            }
        } );
    }

    class sketchView extends View{
        public sketchView(Context context){
            super(context);
            bitmap = Bitmap.createBitmap( 820, 480, Bitmap.Config.ARGB_4444 );
            canvas = new Canvas( bitmap );
            this.setBackgroundColor( Color.WHITE );
        }

        private ArrayList<DrawingClass> DrawingArrayList = new ArrayList<>();

        @Override
        public boolean onTouchEvent(MotionEvent event){

            canvas.drawPath( path, paint );

            DrawingClass paintingTime = new DrawingClass();
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    path.moveTo( x, y );
                    return true;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo( x, y );
                    paintingTime.setPath(path);
                    paintingTime.setPaint(paint);
                    DrawingArrayList.add(paintingTime);
                    break;
                default:
                    return false;
            }

            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw( canvas );
            if(DrawingArrayList.size()>0){
                canvas.drawPath(DrawingArrayList.get(DrawingArrayList.size() - 1).getPath(),

                        DrawingArrayList.get(DrawingArrayList.size() - 1).getPaint());
            }
        }
    }

    public class DrawingClass{
        Path DrawingPath;
        Paint DrawingPaint;

        public Path getPath(){
            return DrawingPath;
        }

        public void setPath(Path path){
            this.DrawingPath = path;
        }

        public Paint getPaint(){
            return DrawingPaint;
        }

        public void setPaint(Paint paint){
            this.DrawingPaint = paint;
        }
    }
}

package com.efunds.ximalaya.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.efunds.ximalaya.R;

@SuppressLint("AppCompatCustomView")
public class LoadingView extends ImageView {

    private int rotateDegree = 0;
    private boolean mNeedRotate = false;
    public LoadingView(Context context) {
        //为了统一一个进入口，所以不用super
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotate = true;
        //绑定到window的时候调用
        post(new Runnable() {
            @Override
            public void run() {
                rotateDegree += 30;
                rotateDegree = rotateDegree <= 360 ? rotateDegree : 0;
                invalidate();//用來刷新View的,再一次调用了draw的方法
                if(mNeedRotate){
                    postDelayed(this,100);
                }

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //从window中解绑调用\
        mNeedRotate = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        * 第一个参数旋转的角度
        * 第二个参数为X的坐标
        * 第三个参数为Y的坐标
        *
        * */
        canvas.rotate(rotateDegree,getWidth()/2,getHeight()/2);
        super.onDraw(canvas);
    }
}

package com.efunds.ximalaya.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
/**
 *  RecycleView 的item中的下划线
 */
public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {
    private Context co;
    private Drawable dr;
    private int sum;
    private int top,bottom,left,right;
    private int orientation;

    //采用系统内置的风格的分割线
    private static final int[] attrs=new int[]{android.R.attr.listDivider};

    public SimpleDividerDecoration(Context co, int orientation) {
        dr = co.obtainStyledAttributes(attrs).getDrawable(0);
        this.orientation = orientation;
    }

    //这是垂直分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);


        if(OrientationHelper.HORIZONTAL==orientation) {//判断是水平还是垂直
            //绘制水平方向的分割线
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            for (int i = 0; i < parent.getChildCount(); i++) {
                View childAt = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                left = childAt.getRight() + layoutParams.rightMargin;
                right = left + dr.getIntrinsicWidth();
                dr.setBounds(left, top, right, bottom);
                dr.draw(c);
            }
        }else {
            //绘制垂直方向的分割线
            left=parent.getPaddingLeft();
            right=parent.getWidth()-parent.getPaddingRight();
            for (int i = 0; i <parent.getChildCount() ; i++) {
                View childAt = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                top=childAt.getBottom()+layoutParams.bottomMargin;
                bottom=top+dr.getIntrinsicHeight();
                dr.setBounds(left,top,right,bottom);
                dr.draw(c);
            }
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (OrientationHelper.HORIZONTAL==orientation){//判断是水平还是垂直
            outRect.set(0,0,dr.getIntrinsicWidth(),0);
        }else {
            outRect.set(0,0,0,dr.getIntrinsicHeight());
        }
    }

}

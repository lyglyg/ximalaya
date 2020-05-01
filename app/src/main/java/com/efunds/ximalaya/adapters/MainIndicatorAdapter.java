package com.efunds.ximalaya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.efunds.ximalaya.MainActivity;
import com.efunds.ximalaya.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

public class MainIndicatorAdapter extends CommonNavigatorAdapter {

    private final String[] mTitleArr;
    private OnIndicatorTabClickListener onIndicatorTabClickListener;

    public void setOnIndicatorTabClickListener(MainIndicatorAdapter.OnIndicatorTabClickListener onIndicatorTabClickListener) {
        this.onIndicatorTabClickListener = onIndicatorTabClickListener;
    }

    public MainIndicatorAdapter(Context context) {
        mTitleArr = context.getResources().getStringArray(R.array.main_navigator_title);
    }

    @Override
    public int getCount() {
        if(mTitleArr != null){
            return mTitleArr.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        //创建View
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        //设置一般情况下的颜色是灰色
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        //设置选择的颜色为黑色
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FFFFFF"));
        //设置sp
        colorTransitionPagerTitleView.setTextSize(18);
        //设置要显示的内容
        colorTransitionPagerTitleView.setText(mTitleArr[index]);
        //设置title的点击事件，如果点击了title，那么选中下面的viewpager到对应的index里面去
        //也就是说，当我们点击title的时候，下面的viewpager会对应这index进行切换
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO;
                if(onIndicatorTabClickListener != null){
                    onIndicatorTabClickListener.tabItemClick(index);
                }
            }
        });
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setColors(Color.parseColor("#FFFFFF"));
        return indicator;
    }

    public interface OnIndicatorTabClickListener{
        public void tabItemClick(int position);
    }
}

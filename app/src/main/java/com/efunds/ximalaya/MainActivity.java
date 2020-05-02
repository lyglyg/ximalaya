package com.efunds.ximalaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.efunds.ximalaya.adapters.MainIndicatorAdapter;
import com.efunds.ximalaya.adapters.MainViewPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class MainActivity extends FragmentActivity {

    private MagicIndicator mMagicIndicator;
    private ViewPager mViewPager;
    private MainIndicatorAdapter mMainIndicatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    /***
     * 初始化事件
     */
    private void initEvent() {
        //mainIndicatorAdapter设置item点击事件
        mMainIndicatorAdapter.setOnIndicatorTabClickListener(new MainIndicatorAdapter.OnIndicatorTabClickListener() {
            @Override
            public void tabItemClick(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
    }

    /**
     * 初始化View
     */
    private void initView() {
        //初始化indicator
        mMagicIndicator = this.findViewById(R.id.main_magic_indicator);
        mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.bg_main_color));

        //设置indicator的适配器
        CommonNavigator commonNavigator = new CommonNavigator(this);
        mMainIndicatorAdapter = new MainIndicatorAdapter(this);
        commonNavigator.setAdapter(mMainIndicatorAdapter);
        //自动调整宽度平均分
        commonNavigator.setAdjustMode(true);
        mMagicIndicator.setNavigator(commonNavigator);

        //初始化ViewPager和设置ViewPager的适配器
        mViewPager = this.findViewById(R.id.main_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(fragmentManager);
        mViewPager.setAdapter(mainViewPagerAdapter);

        //指示器和viewpager进行绑定
        ViewPagerHelper.bind(mMagicIndicator,mViewPager);

    }
}

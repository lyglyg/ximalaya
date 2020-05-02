package com.efunds.ximalaya.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.efunds.ximalaya.R;
import com.efunds.ximalaya.base.BaseApplication;

/**
 * UI判断加载器，用来区分 四种状态的：正在加载中，加载完成后显示内容，加载完成后数据为空，网络出错
 */
public abstract class UILoader extends FrameLayout {

    private View mLoadingView = null;
    private View mSuccessView = null;
    private View mNetWorkErrorView = null;
    private View mEmptyView = null;
    private OnRetryClickListener mOnRetryClickListener;

    public void setOnRetryClickListener(OnRetryClickListener mOnRetryClickListener) {
        this.mOnRetryClickListener = mOnRetryClickListener;
    }

    public enum UIStatus{
        LOADING,SUCCESS,ENPTY,NETWORK_ERROR,NONE
    }
    public UIStatus mCurrentUIStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context,null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        switchUIByCurrentStatus();//根据当前状态进行切换UI界面

    }

    public void updateStatus(UIStatus status){
        mCurrentUIStatus = status;
        //更新UI一定要在主线程中进行更新
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }

    /**
     * 根据当前状态进行切换UI界面
     */
    private void switchUIByCurrentStatus() {
        if(mLoadingView == null){
            mLoadingView = getLoadingView();//一开始为空就显示正在加载中
            addView(mLoadingView);
        }
        //根据状态设置是否可见
        mLoadingView.setVisibility(mCurrentUIStatus == UIStatus.LOADING ? VISIBLE : GONE);

        if(mSuccessView == null){
            mSuccessView = getSuccessView();
            addView(mSuccessView);
        }
        //根据状态设置是否可见
        mSuccessView.setVisibility(mCurrentUIStatus == UIStatus.SUCCESS ? VISIBLE :GONE);

        if(mNetWorkErrorView == null){
            mNetWorkErrorView = getNetWorkErrorView();
            addView(mNetWorkErrorView);
        }
        //根据状态设置是否可见
        mNetWorkErrorView.setVisibility(mCurrentUIStatus == UIStatus.NETWORK_ERROR ? VISIBLE :GONE);

        if(mEmptyView == null){
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        //根据状态设置是否可见
        mEmptyView.setVisibility(mCurrentUIStatus == UIStatus.ENPTY ? VISIBLE :GONE);

    }

    private View getEmptyView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view,this,false);
        return view;
    }

    private View getNetWorkErrorView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_network_error_view, this, false);
        view.findViewById(R.id.network_error_ll).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnRetryClickListener != null){
                    mOnRetryClickListener.onRetryClick();
                }
            }
        });
        return view;
    }


    /**
     * 正在加载中的页面
     * @return
     */
    protected View getLoadingView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
        return view;
    }

    /**
     *
     */
    protected abstract View getSuccessView();

    public interface OnRetryClickListener{
       public void onRetryClick();
    }
}

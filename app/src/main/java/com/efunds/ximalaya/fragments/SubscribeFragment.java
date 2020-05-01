package com.efunds.ximalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efunds.ximalaya.R;
import com.efunds.ximalaya.base.BaseFragment;

/**
 * 订阅页面
 */
public class SubscribeFragment extends BaseFragment {
    @Override
    protected View subFragmentViewLoader(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_subscribe, container, false);
        return rootView;
    }
}

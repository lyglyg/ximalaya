package com.efunds.ximalaya.utils;

import com.efunds.ximalaya.base.BaseFragment;
import com.efunds.ximalaya.fragments.HistoryFragment;
import com.efunds.ximalaya.fragments.RecommendFragment;
import com.efunds.ximalaya.fragments.SubscribeFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreator {

    private static final int PAGE_RECOMMEND = 0;
    private static final int PAGE_SUBSCRIBE = 1;
    private static final int PAGE_HISTORY = 2;

    public static final int PAGE_COUNT = 3;

    public static Map<Integer,BaseFragment> mCache = new HashMap<>();

    public static BaseFragment getFragment(int index){
        BaseFragment baseFragment = mCache.get(index);
        if(baseFragment != null){
            return baseFragment;
        }

        switch (index){
            case PAGE_RECOMMEND:
                baseFragment = new RecommendFragment();
                break;
            case PAGE_SUBSCRIBE:
                baseFragment = new SubscribeFragment();
                break;
            case PAGE_HISTORY:
                baseFragment = new HistoryFragment();
                break;
        }
        mCache.put(index,baseFragment);
        return baseFragment;

    }
}

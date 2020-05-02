package com.efunds.ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 * 当网络请求完成后 根据情况回调对应的View
 */
public interface IRecommendViewCallback {
    /**
     * 获取推荐内容的结果
     * @param albumList
     */
    public void onRecommendListLoaded(List<Album> albumList);

    /**
     * 网络出错
     */
    public void onNetWorkError();

    /**
     * 数据为空
     */
    public void onRecommendDataEmpty();

    /**
     * 正在加载中
     */
    public void onRecommendLoading();
}

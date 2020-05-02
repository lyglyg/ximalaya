package com.efunds.ximalaya.interfaces;


/**
 *
 * mvp开发模式
 * 逻辑处理
 */
public interface IRecommendPresenter {

    public void getRecommendListData();

    public void registerViewCallback(IRecommendViewCallback callback);

    public void unRegisterViewCallback(IRecommendViewCallback callback);
}

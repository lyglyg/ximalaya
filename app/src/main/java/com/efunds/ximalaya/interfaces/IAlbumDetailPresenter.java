package com.efunds.ximalaya.interfaces;


public interface IAlbumDetailPresenter {

    /**
     * 下拉刷新
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void pushLoadMore();

    /**
     * 获取专辑数据内容详情
     */
    void getAlbumDetail(long albumId,int page);



}

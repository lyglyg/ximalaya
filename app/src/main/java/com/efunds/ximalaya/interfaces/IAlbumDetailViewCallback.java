package com.efunds.ximalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.List;

/**
 * 这个是加载数据完成后或者是完成逻辑后需要UI做出更新回调的方法
 */
public interface IAlbumDetailViewCallback {

    /**
     * 专辑列表数据返回处理显示在UI上
     * @param trackList
     */
    void onDetailListLoaded(List<Track> trackList);

    /**
     * 把album传UI使用
     *
     * @param album
     */
    void onAlbumLoaded(Album album);

    /**
     * 网络出错
     */
    public void onNetWorkError(int errorCode,String errorMsg);

    /**
     * 数据为空
     */
    public void onRecommendDataEmpty();

    /**
     * 正在加载中
     */
    public void onRecommendLoading();

}

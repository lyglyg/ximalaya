package com.efunds.ximalaya.presenters;

import com.efunds.ximalaya.interfaces.IAlbumDetailPresenter;
import com.efunds.ximalaya.interfaces.IAlbumDetailViewCallback;
import com.efunds.ximalaya.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.CommonTrackList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetailPresenter implements IAlbumDetailPresenter {

    private String TAG = "AlbumDetailPresenter";
    private static AlbumDetailPresenter sInstance = null;
    private Album mTargetAlbum = null;
    private List<IAlbumDetailViewCallback> mAlbumDetailCallbackList = new ArrayList<>();
    private List<Track> mTracks = new ArrayList<>();

    public void setTargetAlbum(Album mTargetAlbum) {
        this.mTargetAlbum = mTargetAlbum;
    }

    /**
     * 私有化构造器，确保都是同一个对象
     */
    private AlbumDetailPresenter(){

    }

    public static AlbumDetailPresenter getInstance(){
        if(sInstance == null){
            synchronized (AlbumDetailPresenter.class){
                if(sInstance == null){
                    sInstance = new AlbumDetailPresenter();
                }
            }
        }
        return sInstance;
    }

    public void registerViewCallback(IAlbumDetailViewCallback callback){
        if(mAlbumDetailCallbackList !=null && !mAlbumDetailCallbackList.contains(callback)){
            mAlbumDetailCallbackList.add(callback);
            if(mTargetAlbum != null){
                callback.onAlbumLoaded(mTargetAlbum);
            }
            if(mTracks != null){
                mTracks.clear();
            }

        }
    }

    public void unReginsterViewCallback(IAlbumDetailViewCallback callback){
        if(mAlbumDetailCallbackList !=null){
            mAlbumDetailCallbackList.remove(callback);
        }
    }


    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void pushLoadMore() {

    }

    @Override
    public void getAlbumDetail(long albumId, int page) {
        //根据专辑id和页码获取列表
        handleLoading();//正在加载中的页面
        LogUtils.d(TAG,"albumId-->:"+albumId);
        Map<String,String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID,albumId+"");
        map.put(DTransferConstants.SORT,"asc");
        map.put(DTransferConstants.PAGE,page+"");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                LogUtils.d(TAG,"trackList:-->"+ trackList.getTracks());
                List<Track> tracks = trackList.getTracks();
                if(tracks != null){
                    mTracks.addAll(tracks);
                    LogUtils.d(TAG,"mTracks:-->"+mTracks);
                }
                handlerSuccessResult(mTracks);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                handleError(errorCode,errorMsg);
            }
        });

    }

    private void handleError(int errorCode, String errorMsg) {
        if(mAlbumDetailCallbackList != null){
            for (IAlbumDetailViewCallback callback : mAlbumDetailCallbackList) {
                callback.onNetWorkError(errorCode,errorMsg);
            }
        }
    }

    private void handleLoading() {
        if(mAlbumDetailCallbackList != null){
            for (IAlbumDetailViewCallback callback: mAlbumDetailCallbackList){
                callback.onRecommendLoading();
            }
        }
    }

    /**
     * 处理返回结果
     * @param trackList
     */
    private void handlerSuccessResult(List<Track> trackList) {
        if (trackList != null && trackList.size() != 0) {
            LogUtils.d(TAG,"handlerSuccessResult:-->"+trackList);
            for(IAlbumDetailViewCallback callback :mAlbumDetailCallbackList){
                callback.onDetailListLoaded(trackList);
            }
        }else{
            for (IAlbumDetailViewCallback callback : mAlbumDetailCallbackList) {
                callback.onRecommendDataEmpty();
            }
        }

    }


}

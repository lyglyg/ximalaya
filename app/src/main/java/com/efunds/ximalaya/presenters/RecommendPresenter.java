package com.efunds.ximalaya.presenters;

import com.efunds.ximalaya.interfaces.IRecommendPresenter;
import com.efunds.ximalaya.interfaces.IRecommendViewCallback;
import com.efunds.ximalaya.utils.Constants;
import com.efunds.ximalaya.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
    private static final String TAG = "RecommendPresenter";

    private static RecommendPresenter mRecommendPresenter = null;

    private List<IRecommendViewCallback> mCallbackList = new ArrayList<>();


    private RecommendPresenter(){

    }

    /**
     * 使用懒汉式单例模式
     * @return
     */
    public static RecommendPresenter getInstance(){
        if(mRecommendPresenter == null){
            synchronized ( RecommendPresenter.class){
                if(mRecommendPresenter == null){
                    mRecommendPresenter = new RecommendPresenter();
                }
            }
        }
        return mRecommendPresenter;
    }

    /**
     * 获取推荐内容的数据
     */
    @Override
    public void getRecommendListData() {
        handlerLoading();//正在加载中
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.COUNT_RECOMMEND + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                //这里已经是主线程了，只用在主线程才能进行ui更新,否则会出现ANR(无法响应)
                LogUtils.d(TAG, "当前线程的名称：" + Thread.currentThread().getName());
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    if (albumList != null) {
                        LogUtils.d(TAG, albumList.toString());
                        //处理返回结果
                        handlerRecommendResult(albumList);
                    }

                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.d(TAG, "error code:" + i);
                LogUtils.d(TAG, "error msg:" + s);
                //处理网络出错的情况
                handlerRecommendError();
            }
        });


    }

    private void handlerLoading() {
        if(mCallbackList != null){
            for (IRecommendViewCallback callback:mCallbackList){
                callback.onRecommendLoading();
            }
        }
    }

    private void handlerRecommendError() {
        if(mCallbackList != null){
            for (IRecommendViewCallback callback: mCallbackList){
                callback.onNetWorkError();
            }
        }

    }

    private void handlerRecommendResult(List<Album> albumList) {
        //通知UI进行更新
        if(albumList != null){
            //测试一下网络数据为空的情况
//            albumList.clear();
            if(albumList.size() == 0){
                for (IRecommendViewCallback callback: mCallbackList){
                    callback.onRecommendDataEmpty();
                }
            }else{
                for(IRecommendViewCallback callback :mCallbackList){
                    callback.onRecommendListLoaded(albumList);
                }
            }
        }
    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if(mCallbackList != null && !mCallbackList.contains(callback)){
            mCallbackList.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if(mCallbackList != null){
            mCallbackList.remove(callback);
        }
    }
}

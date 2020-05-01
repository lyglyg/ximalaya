package com.efunds.ximalaya.fragments;

import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efunds.ximalaya.R;
import com.efunds.ximalaya.base.BaseFragment;
import com.efunds.ximalaya.utils.Constants;
import com.efunds.ximalaya.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;
import com.ximalaya.ting.android.opensdk.model.download.RecommendDownload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐页面
 */
public class RecommendFragment extends BaseFragment {
    private static final String TAG = "RecommendFragment";

    @Override
    protected View subFragmentViewLoader(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_recommend, container, false);
        getRecommendData();
        return rootView;
    }

    /**
     * 获取推荐内容
     */
    private void getRecommendData() {
        Map<String,String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.COUNT_RECOMMEND + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                if(gussLikeAlbumList != null){
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    if(albumList != null){
                        LogUtils.d(TAG,albumList.toString());
                        LogUtils.d(TAG, String.valueOf(albumList.size()));
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.d(TAG,"error code:"+i);
                LogUtils.d(TAG,"error msg:"+s);
            }
        });
    }


}

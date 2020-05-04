package com.efunds.ximalaya.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.efunds.ximalaya.R;
import com.efunds.ximalaya.adapters.AlbumDetailRecycleViewAdapter;
import com.efunds.ximalaya.base.BaseActivity;
import com.efunds.ximalaya.interfaces.IAlbumDetailViewCallback;
import com.efunds.ximalaya.presenters.AlbumDetailPresenter;
import com.efunds.ximalaya.utils.LogUtils;
import com.efunds.ximalaya.views.ImageBlur;
import com.efunds.ximalaya.views.RoundRectImageView;
import com.efunds.ximalaya.views.SimpleDividerDecoration;
import com.efunds.ximalaya.views.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.List;

public class AlbumDetailActivity extends BaseActivity implements IAlbumDetailViewCallback, UILoader.OnRetryClickListener, AlbumDetailRecycleViewAdapter.OnItemClickListener {

    private static final String TAG = "AlbumDetailActivity";
    private AlbumDetailPresenter mAlbumDetailPresenter;
    private ImageView mIvLargeCover;
    private RoundRectImageView mIvSmallCover;
    private TextView mTvAlbumTitle;
    private TextView mTvAlbumAutor;
    private RecyclerView mRvAlbumDetailList;
    private AlbumDetailRecycleViewAdapter mAlbumDetailRecycleViewAdapter;
    private Album mCurrentAlbum;

    private int mCurrentPage = 1;
    private UILoader mUILoader;
    private FrameLayout mFlAlbumListContainer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        initView();
        mAlbumDetailPresenter = AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAlbumDetailPresenter !=null){
            mAlbumDetailPresenter.unReginsterViewCallback(this);
        }
    }

    private void initEvent() {
        //网络异常，重新加载
        if (mUILoader != null) {
            mUILoader.setOnRetryClickListener(this);
        }
        //点击了recycleView的item触发事件
        if(mAlbumDetailRecycleViewAdapter != null){
            mAlbumDetailRecycleViewAdapter.setOnItemClickListener(this);
        }
    }

    private void initView() {
        mIvLargeCover = this.findViewById(R.id.iv_large_cover);
        mIvSmallCover = this.findViewById(R.id.iv_small_cover);
        mTvAlbumTitle = this.findViewById(R.id.tv_album_title);
        mTvAlbumAutor = this.findViewById(R.id.tv_album_autor);

        //实现跑马灯的效果，必须加上这句话
        mTvAlbumTitle.setSelected(true);

        if (mUILoader==null) {
            mUILoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
        }
        mFlAlbumListContainer = findViewById(R.id.fl_album_detail_list_container);
        mFlAlbumListContainer.removeAllViews();
        mFlAlbumListContainer.addView(mUILoader);
    }

    /**
     * 数据加载成功，创建列表页面
     * @return
     * @param container
     */
    private View createSuccessView(ViewGroup container) {
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_album_detail, container, false);

        mRvAlbumDetailList = view.findViewById(R.id.rv_album_detail_list);

        //RecycleView的使用和步骤
        //1、设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRvAlbumDetailList.setLayoutManager(linearLayoutManager);
        //2、设置适配器
        mAlbumDetailRecycleViewAdapter = new AlbumDetailRecycleViewAdapter();
        mRvAlbumDetailList.setAdapter(mAlbumDetailRecycleViewAdapter);
        //3、设置下划线
        mRvAlbumDetailList.addItemDecoration(new SimpleDividerDecoration(this, OrientationHelper.VERTICAL));
        return view;
    }


    @Override
    public void onDetailListLoaded(List<Track> trackList) {
        mAlbumDetailRecycleViewAdapter.setData(trackList);
        if(mUILoader!=null){
            mUILoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }
    }

    @Override
    public void onAlbumLoaded(Album album) {
        LogUtils.d(TAG, "Album:-->" + album);
        this.mCurrentAlbum = album;
        if(mAlbumDetailPresenter !=null){
            mAlbumDetailPresenter.getAlbumDetail(mCurrentAlbum.getId(),mCurrentPage);
        }
        if (mTvAlbumTitle != null) {
            mTvAlbumTitle.setText(album.getAlbumTitle());
        }
        if (mTvAlbumAutor != null) {
            mTvAlbumAutor.setText(album.getAnnouncer().getNickname());
        }

        if (mIvLargeCover != null && null != mIvLargeCover) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mIvLargeCover, new Callback() {
                @Override
                public void onSuccess() {
                    //Picasso是异步加载框架，所以需要加载完图片之后再进行一个高斯模糊，否则的话会出现空指针异常
                    ImageBlur.makeBlur(mIvLargeCover,AlbumDetailActivity.this);
                }

                @Override
                public void onError() {
                    LogUtils.d(TAG,"Picasso加载图片异常");
                }
            });
        }

        if (mIvSmallCover != null) {
            Picasso.with(this).load(album.getCoverUrlSmall()).into(mIvSmallCover);
        }
    }

    @Override
    public void onNetWorkError(int errorCode, String errorMsg) {
        if(mUILoader != null){
            mUILoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
        }
    }


    @Override
    public void onRecommendDataEmpty() {
        if(mUILoader !=null){
            mUILoader.updateStatus(UILoader.UIStatus.ENPTY);
        }
    }

    @Override
    public void onRecommendLoading() {
        if(mUILoader !=null ){
            mUILoader.updateStatus(UILoader.UIStatus.LOADING);
        }
    }

    /**
     * 网络异常重新,点击按钮重新加载
     */
    @Override
    public void onRetryClick() {
        if(mAlbumDetailPresenter != null){
            mAlbumDetailPresenter.getAlbumDetail(mCurrentAlbum.getId(),mCurrentPage);
        }
    }

    /**
     * 点击了item，实现跳转功能
     */
    @Override
    public void onItemClick() {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
}

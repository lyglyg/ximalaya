package com.efunds.ximalaya.fragments;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efunds.ximalaya.R;
import com.efunds.ximalaya.adapters.ReCommendRecycleViewAdapter;
import com.efunds.ximalaya.base.BaseFragment;
import com.efunds.ximalaya.interfaces.IRecommendViewCallback;
import com.efunds.ximalaya.presenters.RecommendPresenter;
import com.efunds.ximalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * 推荐页面
 */
public class RecommendFragment extends BaseFragment implements IRecommendViewCallback, UILoader.OnRetryClickListener {
    private static final String TAG = "RecommendFragment";
    private RecyclerView mRvRecommendList;
    private ReCommendRecycleViewAdapter mReCommendRecycleViewAdapter;
    private UILoader mUILoader;
    private RecommendPresenter mRecommendPresenter;

    @Override
    protected View subFragmentViewLoader(final LayoutInflater inflater, final ViewGroup container) {

        mUILoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView() {
                View successView = createSuccessView(inflater, container);
                return successView;
            }
        };

        //注册逻辑处理的Presenter(用来加载数据)
        mRecommendPresenter = RecommendPresenter.getInstance();
        mRecommendPresenter.registerViewCallback(this);
        //获取推荐列表的数据
        mRecommendPresenter.getRecommendListData();

        //当页面是为网络异常的时候，点击按钮重新加载数据
        mUILoader.setOnRetryClickListener(this);

        if (mUILoader.getParent() instanceof ViewGroup) {
           ((ViewGroup) mUILoader.getParent()).removeView(mUILoader);
        }
        return mUILoader;
    }

    private View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        //1、加载View
        View rootView = inflater.inflate(R.layout.fragment_recommend, container, false);

        //1.2RecycleView的使用
        //找到控件
        mRvRecommendList = rootView.findViewById(R.id.rv_recommend_list);
        //设置recycleView的布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvRecommendList.setLayoutManager(linearLayoutManager);
        //设置recycleView 每一个item的装饰
        mRvRecommendList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });

        //设置recycleView的适配器
        mReCommendRecycleViewAdapter = new ReCommendRecycleViewAdapter();
        mRvRecommendList.setAdapter(mReCommendRecycleViewAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecommendPresenter.unRegisterViewCallback(this);
    }

    @Override
    public void onRecommendListLoaded(List<Album> albumList) {
        mReCommendRecycleViewAdapter.setData(albumList);
        mUILoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetWorkError() {
        mUILoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onRecommendDataEmpty() {
        mUILoader.updateStatus(UILoader.UIStatus.ENPTY);

    }

    @Override
    public void onRecommendLoading() {
        mUILoader.updateStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onRetryClick() {
        if(mRecommendPresenter !=null ){
            mRecommendPresenter.getRecommendListData();
        }
    }
}

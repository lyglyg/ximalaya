package com.efunds.ximalaya.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efunds.ximalaya.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 推荐页面的RecycleView的适配器，获取数据用来配置页面的
 */
public class ReCommendRecycleViewAdapter extends RecyclerView.Adapter<ReCommendRecycleViewAdapter.InnerHolder> {

    private List<Album> mDataList = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这里是加载ItemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_list,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        //这里是设置数据
        holder.itemView.setTag(position);
        holder.setData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    /**
     * 获取Adapter的数据
     *
     * @param albumList
     */
    public void setData(List<Album> albumList) {
        if (mDataList != null) {
            mDataList.clear();
            mDataList.addAll(albumList);
        }
        //更新一下UI
        notifyDataSetChanged();
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //找到各个控件，设置数据
            ImageView albumCoverIv = itemView.findViewById(R.id.album_cover_iv);
            //title
            TextView albumTitleTv = itemView.findViewById(R.id.album_title_tv);
            //描述
            TextView albumDescTv = itemView.findViewById(R.id.album_description_tv);
            //播放数量
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            //专辑内容
            TextView albumContentSizeTv = itemView.findViewById(R.id.album_content_size);

            albumTitleTv.setText(album.getAlbumTitle());
            albumDescTv.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(album.getPlayCount() + "");
            albumContentSizeTv.setText(album.getIncludeTrackCount() + "");

            String coverUrlLarge = album.getCoverUrlLarge();
            if(!TextUtils.isEmpty(coverUrlLarge)){
                Picasso.with(itemView.getContext()).load(coverUrlLarge).into(albumCoverIv);
            }else{
                albumCoverIv.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }
}

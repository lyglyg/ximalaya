package com.efunds.ximalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efunds.ximalaya.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 专辑列表的Adapter
 */
public class AlbumDetailRecycleViewAdapter extends RecyclerView.Adapter<AlbumDetailRecycleViewAdapter.InnerHolder> {

    private List<Track> mTrackList = new ArrayList<>();
    private TextView mTvTrackId;
    private TextView mTvTrackTitle;
    private TextView mTvTrackPlayCount;
    private TextView mTvTrackDuration;
    private TextView mTvTrackDate;

    private SimpleDateFormat mDurationFormat = new SimpleDateFormat("mm:ss");
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail_list, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        //这里设置数据
        holder.itemView.setTag(position);
        holder.setData(mTrackList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener !=null){
                    mOnItemClickListener.onItemClick();
                }
//                Toast.makeText(v.getContext(),"点击了第"+position+"条目",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public void setData(List<Track> trackList) {
        if(trackList != null){
            mTrackList.clear();
            mTrackList.addAll(trackList);
            notifyDataSetChanged();
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }


        public void setData(Track track) {
            mTvTrackId = itemView.findViewById(R.id.tv_track_id);
            mTvTrackTitle = itemView.findViewById(R.id.tv_track_title);
            mTvTrackPlayCount = itemView.findViewById(R.id.tv_track_play_count);
            mTvTrackDuration = itemView.findViewById(R.id.tv_track_duration);
            mTvTrackDate = itemView.findViewById(R.id.tv_track_date);

            mTvTrackId.setText((Integer) itemView.getTag()+"");
            mTvTrackTitle.setText(track.getTrackTitle());
            mTvTrackPlayCount.setText(track.getPlayCount()+"");

            int durationMil = track.getDuration()*1000;
            mTvTrackDuration.setText( mDurationFormat.format(durationMil));
            mTvTrackDate.setText(mDateFormat.format(track.getUpdatedAt()));



        }
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick();
    }
}

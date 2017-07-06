package com.myself.show.show.Ui.music.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myself.show.show.R;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.net.responceBean.WySearchInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class MusicItemAdapter extends RecyclerView.Adapter {
    Context context;
    LayoutInflater minflater;
    BackCall backCall;


    public MusicItemAdapter(Context context, BackCall backCall) {
        this.context = context;
        this.backCall = backCall;
        minflater = LayoutInflater.from(context);
    }

    List<WySearchInfo.ResultBean.SongsBean> items = new ArrayList<>();

    public void setDate(List<WySearchInfo.ResultBean.SongsBean> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.music_item_layout,
                parent, false);
        MusicItemHolder viewHolder = new MusicItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MusicItemHolder) holder).musicTitle.setText(items.get(position).getName());
        ((MusicItemHolder) holder).musicAuther.setText(items.get(position).getArtists().size() == 0 ? "未知" : items.get(position).getArtists().get(0).getName());
        Glide.with(context).load(items.get(position).getAlbum().getBlurPicUrl()).into(((MusicItemHolder) holder).musicPic);
        ((MusicItemHolder) holder).musicItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCall.backCall(v.getId(), position);
            }
        });
        ((MusicItemHolder) holder).itemView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animal_translate));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addDate(List<WySearchInfo.ResultBean.SongsBean> songs) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.addAll(songs);
    }

    public List<WySearchInfo.ResultBean.SongsBean> getDate() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public class MusicItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.music_pic)
        ImageView musicPic;
        @BindView(R.id.music_item)
        LinearLayout musicItem;
        @BindView(R.id.music_title)
        TextView musicTitle;
        @BindView(R.id.music_auther)
        TextView musicAuther;

        View itemView;
        public MusicItemHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}

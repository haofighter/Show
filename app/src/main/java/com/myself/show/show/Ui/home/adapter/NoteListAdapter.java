package com.myself.show.show.Ui.home.adapter;

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
import com.myself.show.show.net.responceBean.NoteDate;
import com.myself.show.show.net.responceBean.WySearchInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class NoteListAdapter extends RecyclerView.Adapter {
    Context context;
    LayoutInflater minflater;
    BackCall backCall;


    public NoteListAdapter(Context context, BackCall backCall) {
        this.context = context;
        this.backCall = backCall;
        minflater = LayoutInflater.from(context);
    }

    List<NoteDate> items = new ArrayList<>();

    public void setDate(List<NoteDate> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.note_item_layout,
                parent, false);
        MusicItemHolder viewHolder = new MusicItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addDate(List<NoteDate> songs) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.addAll(songs);
    }

    public List<NoteDate> getDate() {
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

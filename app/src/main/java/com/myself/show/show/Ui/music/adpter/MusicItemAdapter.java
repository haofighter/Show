package com.myself.show.show.Ui.music.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.myself.show.show.net.responceBean.WySearchInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class MusicItemAdapter extends RecyclerView.Adapter {
    Context context;

    public MusicItemAdapter(Context context) {
        this.context = context;
    }

    List<WySearchInfo.ResultBean.SongsBean> items = new ArrayList<>();

    public void setDate(List<WySearchInfo.ResultBean.SongsBean> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class MusicItemHolder extends RecyclerView.ViewHolder{

        public MusicItemHolder(View itemView) {
            super(itemView);
        }
    }
}

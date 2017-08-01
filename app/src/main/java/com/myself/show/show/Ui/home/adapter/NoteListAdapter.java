package com.myself.show.show.Ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.Tools.Utils;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.net.responceBean.NoteDate;

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

    public NoteListAdapter(Context context) {
        this.context = context;
        minflater = LayoutInflater.from(context);
    }

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
        NoteTitleItemHolder viewHolder = new NoteTitleItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((NoteTitleItemHolder) holder).noteTitle.setText(items.get(position).getTitle().equals("")?"(无标题)":items.get(position).getTitle());
        ((NoteTitleItemHolder) holder).saveTime.setText(Utils.FormatDate(items.get(position).getSaveTime()));

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
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

    public class NoteTitleItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.author_name)
        TextView authorName;
        @BindView(R.id.save_time)
        TextView saveTime;
        @BindView(R.id.note_title)
        TextView noteTitle;
        View itemView;

        public NoteTitleItemHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}

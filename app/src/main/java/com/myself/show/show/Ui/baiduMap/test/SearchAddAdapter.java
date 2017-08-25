package com.myself.show.show.Ui.baiduMap.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.myself.show.show.R;
import com.myself.show.show.base.BackCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/24.
 */

public class SearchAddAdapter extends RecyclerView.Adapter {
    private Context context;
    private BackCall backCall;

    public SearchAddAdapter(Context context, BackCall backCall) {
        this.context = context;
        this.backCall=backCall;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VieHolder(LayoutInflater.from(context).inflate(R.layout.search_address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VieHolder) holder).setDate(date.get(position));
    }

    @Override
    public int getItemCount() {
        return date == null ? 0 : date.size();
    }

    List<SuggestionResult.SuggestionInfo> date;

    public void setDate(List<SuggestionResult.SuggestionInfo> date) {
        this.date = date;
    }

    class VieHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.detail)
        TextView detail;
        View itemView;

        public VieHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setDate(final SuggestionResult.SuggestionInfo suggestionInfo){
            address.setText(suggestionInfo.district+suggestionInfo.key);
            detail.setText(suggestionInfo.city);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backCall.backCall(1,suggestionInfo);
                }
            });
        }


    }
}

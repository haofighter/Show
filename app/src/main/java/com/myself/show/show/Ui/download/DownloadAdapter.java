package com.myself.show.show.Ui.download;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.net.downloaddemo.DownInfo;
import com.myself.show.show.net.downloaddemo.DownLoadListener.HttpProgressOnNextListener;
import com.myself.show.show.net.downloaddemo.HttpDownManager;
import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class DownloadAdapter extends RecyclerView.Adapter {
    Context context;
    LayoutInflater minflater;
    BackCall backCall;
    private final HttpDownManager manager;


    public DownloadAdapter(Context context, BackCall backCall) {
        manager = HttpDownManager.getInstance();
        this.context = context;
        this.backCall = backCall;
        minflater = LayoutInflater.from(context);
    }

    List<DownInfo> items = new ArrayList<>();

    public void setDate(List<DownInfo> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.download_item_layout,
                parent, false);
        DownItemHolder viewHolder = new DownItemHolder(view);
        Log.i("","绑定viewholder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.i("","填充数据");
        ((DownItemHolder) holder).setDownInfo(items.get(position));
        ((DownItemHolder) holder).down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("点击","点击的位置:"+position);
                manager.startDown(items.get(position));
            }
        });
        ((DownItemHolder) holder).pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("点击","点击的位置:"+position);
                manager.pause(items.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class DownItemHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.down)
        TextView down;
        @BindView(R.id.pause)
        TextView pause;
        @BindView(R.id.upload_progress)
        ProgressBar uploadProgress;
        View itemView;
        DownInfo downInfo;


        public void setDownInfo(DownInfo downInfo) {
            this.downInfo = downInfo;
            downInfo.setListener(httpProgressOnNextListener);
        }

        @BindView(R.id.downState)
        TextView downState;

        public DownItemHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }


        /*下载回调*/
        HttpProgressOnNextListener<DownInfo> httpProgressOnNextListener = new HttpProgressOnNextListener<DownInfo>() {
            @Override
            public void onNext(DownInfo baseDownEntity) {
                ToastUtils.showMessage(baseDownEntity.getSavePath());
            }

            @Override
            public void onStart() {
                downState.setText("提示:开始下载");
            }

            @Override
            public void onComplete() {
                downState.setText("提示：下载完成");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i("错误","==="+e.toString());
                downState.setText("失败:" + e.toString());
            }


            @Override
            public void onPuase() {
                super.onPuase();
                downState.setText("提示:暂停");
            }

            @Override
            public void onStop() {
                super.onStop();
            }

            @Override
            public void updateProgress(long readLength, long countLength) {
                downState.setText("提示:下载中");
                uploadProgress.setMax((int) countLength);
                uploadProgress.setProgress((int) readLength);

            }
        };


    }

}

package com.myself.show.show.listener;

/**
 * Created by Administrator on 2017/7/14.
 */

public interface UploadProgressListener {

    /**
     * 上传进度
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}

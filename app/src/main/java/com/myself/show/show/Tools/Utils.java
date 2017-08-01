package com.myself.show.show.Tools;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/1.
 */

public class Utils {

    public static String FormatDate(Long time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(time);
            return sdf.format(date);
        } catch (Exception e) {
            Log.e("时间转换错误", "位置 Utils.FormatDate");
            return null;
        }
    }
}

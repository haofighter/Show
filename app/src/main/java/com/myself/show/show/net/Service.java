package com.myself.show.show.net;

import com.myself.show.show.net.requestBean.FileInfo;
import com.myself.show.show.net.responceBean.BaseResponse;
import com.myself.show.show.net.responceBean.LoginResponse;
import com.myself.show.show.net.responceBean.MusicPath;
import com.myself.show.show.net.responceBean.WySearchInfo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


/**
 * Created by laucherish on 16/3/15.
 */
public interface Service {


//    @POST("api/User/PostUserRegister")
//    @FormUrlEncoded
//    Observable<LoginBean> register(@Field("mobile") String mobile, @Field("password") String password,
//                                   @Field("sex") String sex, @Field("age") String age,
//                                   @Field("city") String city, @Field("industry_id") String industry_id,
//                                   @Field("head_portrait") String head_portrait, @Field("user_name") String user_name);


//    @GET("api/User/GetCooperationMall")
//    Observable<HangYeBean> getHangYe();


    @POST("smarthouse/index.php/Api/api/login")
    @FormUrlEncoded
    Observable<LoginResponse> login(@Field("password") String password, @Field("username") String username);

    //网易云音乐查询
    @POST("http://music.163.com/api/search/pc")
    @FormUrlEncoded
    Observable<WySearchInfo> wyYun(@Field("s") String searchContent, @Field("offset") int page, @Field("limit") int limit, @Field("type") String type);


    ////网易云音乐下载地址
    @GET("http://music.163.com/api/song/enhance/download/url?br=320000")
    Observable<MusicPath> musicPath(@Query("id") int id);

//    @Multipart
//    @POST("/api/Public/upload_img")
//    Call<BaseResponse> uploadFile(@Part("") MultipartBody.Part file);

    @Multipart
    @POST("/api/Public/upload_img")
    Observable<BaseResponse> uploadFile1(@Part MultipartBody.Part file);


    @POST("/api/Public/upload_img")
    @Multipart
    Observable<ResponseBody> uploadFileInfo(@Part  RequestBody externalFileParameters) ;

    @POST("/api/Public/upload_img")
    @Multipart
    Observable<ResponseBody> uploadFileInfo(@PartMap Map<String, RequestBody> externalFileParameters) ;

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    @Streaming
    @GET("http://m10.music.126.net/20170726182543/28b866236bf54189103d09cb6cb74137/ymusic/6775/40b5/cfc1/ee973add1ad4c10dd5b23260983d8744.mp3")
    Observable<ResponseBody> downloadFile();


//    @GET( "https://wxt.hbglky.com/oauth-provider/oauth/token?client_id=drv-client&client_secret=D76A9FA10B87&grant_type=client_credentials&scope=pub_api")
//    Observable<BaseResponse> getToken();
//
//    @POST("api/User/SaveImage?")
//    Observable<LoginBean> SaveImage(@Query("strImg") String strImg);

//    @POST("api/User/PostUserInfoEdit?")
//    @FormUrlEncoded
//    Observable<response> WriteInfo(@Query("user_id") String user_id, @Query("sign") String sign, @Field("mobile") String nick_name, @Field("mobile") String avatar);

//    @POST("api/User/PostUserInfoEdit?user_id=1403&sign=dedd2f80621710490df454e682c075b3")
//    @FormUrlEncoded
//    Observable<response> WriteInfo1(@Field("mobile") String nick_name, @Field("mobile") String avatar);

}

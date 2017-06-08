package com.myself.show.show.net;

import com.myself.show.show.net.responceBean.LoginResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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

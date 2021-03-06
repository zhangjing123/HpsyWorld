package com.kuwai.ysy.module.find.api;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.bean.MyBlindBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.kuwai.ysy.module.find.bean.VideoChatBean;
import com.kuwai.ysy.module.findtwo.bean.VideoRecordBean;
import com.kuwai.ysy.module.mine.bean.IndentBean;
import com.kuwai.ysy.module.mine.bean.PrivateBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface FoundService {

    /**
     * 获取发现首页
     */
    @FormUrlEncoded
    @POST("Appointment/find")
    Observable<FoundBean> getFirstHomeData(@FieldMap Map<String,Object> map);

    //获取省市的全部信息
    @GET("Currency/getProvincesAndCitiesInfoList")
    Observable<ProvincesAndCityBean> getLocalData();

    //获取下一级地区列表
    @GET("Currency/getRegionInfoList")
    Observable<LocalNextBean> getNextData(@Query("region_name") String name);

    //同城约会
    @FormUrlEncoded
    @POST("Appointment/AppointmentList")
    Observable<CityMeetBean> getMeetListData(@FieldMap Map<String,Object> map);

    //约会-申请方-申请应约
    @POST("Appointment/invitation")
    Observable<BlindBean> sendMeetApply(@Query("r_id") int rid, @Query("uid") int uid, @Query("text") String text);

    //约会-申请方-取消应约
    @POST("Appointment/CancelTheContract")
    Observable<SimpleResponse> sendCancelApply(@Query("r_id") int rid, @Query("uid") int uid);

    //约会-发布方-同意应约
    @POST("Appointment/agreeInvi")
    Observable<BlindBean> sendMeetAgree(@Query("r_d_id") int rid, @Query("status") int status);

    //约会-补交诚意金
    @FormUrlEncoded
    @POST("Appointment/fund")
    Observable<SimpleResponse> addChengyi(@Field("uid") String uid, @Field("r_id") String rid, @Field("money") String money);

    //约会-补交礼物
    @FormUrlEncoded
    @POST("Appointment/addgift")
    Observable<SimpleResponse> addGift(@Field("uid") String uid, @Field("r_id") String rid, @Field("gift") String gift);

    //约会主题列表&数量
    @POST("Appointment/SearchAppointmentSincerity")
    Observable<MeetThemeBean> getMeetFilterData();

    //约会详情页（对方看到）
    @POST("Appointment/OtherAppointmentDetails")
    Observable<CommisDetailBean> getCommisDetailData(@Query("r_id") int rid, @Query("uid") String uid);

    //用户屏蔽
    @FormUrlEncoded
    @POST("Currency/InsertShield")
    Observable<SimpleResponse> userPing(@Field("uid") String uid, @Field("other_uid") int other_uid);

    //约会详情页（自己）
    @POST("Appointment/MyAppointmentDetails")
    Observable<MyCommisDetailBean> getMyCommisDetailData(@Query("r_id") int rid);

    //删除约会（自己）
    @FormUrlEncoded
    @POST("Appointment/DeleteAppointment")
    Observable<SimpleResponse> deleteAppoint(@Field("r_id") int rid);

    //相亲活动列表
    @FormUrlEncoded
    @POST("Appointment/EnrollActivityList")
    Observable<TuoDanBean> getTuoDanData(@FieldMap Map<String,Object> map);

    //我的相亲活动列表
    @POST("Appointment/MyEnrollActivityList")
    Observable<MyBlindBean> getMyBlindData(@Query("page") int page, @Query("uid") int uid);

    //发布约会
    @POST("Appointment/Appointment")
    @Multipart
    Observable<BlindBean> sendBlind(@PartMap HashMap<String, RequestBody> params);
//    Observable<BlindBean> sendBlind(@Query("uid") int uid,@Query("sincerity") int sincerity,@Query("release_time") int time,
//                                      @Query("area") String area,@Query("address") String address,@Query("girl_friend") int sex,
//                                      @Query("consumption_type") int payfor,@Query("earnest_money") int money,@Query("gift") String gift,
//                                      @Query("Message") String message,@Query("other") String othertheme);


    @FormUrlEncoded
    @POST("HomePage/VideoChatRoomList")
    Observable<VideoChatBean> getVideoList(@FieldMap Map<String,Object> map);

    //随机视频聊天加亲密值(每分钟1次)
    @FormUrlEncoded
    @POST("HomePage/VideoChatRoomIntimate")
    Observable<SimpleResponse> closeIncrease(@Field("uid") String uid, @Field("other_uid") String other_uid);

    //结束随机视频聊天
    @FormUrlEncoded
    @POST("HomePage/EndVideoChatRoom")
    Observable<SimpleResponse> finishVideoChat(@Field("uid") String uid, @Field("other_uid") String other_uid, @Field("end_time") String end_time);

    //随机视频聊天记录
    @FormUrlEncoded
    @POST("HomePage/VideoChatRoomLogList")
    Observable<VideoRecordBean> getChatRecord(@Field("uid") String uid, @Field("page") int page);

    //移除随机视频记录
    @FormUrlEncoded
    @POST("HomePage/DelVideoChatRoomLog")
    Observable<SimpleResponse> removeChatRecord(@Field("uid") String uid, @Field("vl_id") int vl_id);

    //隐私设置列表
    @FormUrlEncoded
    @POST("HomePage/PrivacySettingsList")
    Observable<PrivateBean> getPrivateList(@Field("uid") String uid);

    //隐私设置
    @FormUrlEncoded
    @POST("HomePage/PrivacySettings")
    Observable<SimpleResponse> privateSet(@Field("uid") String uid, @Field("type") int type,@Field("settings") String settings);

    //照片认证的时间
    @FormUrlEncoded
    @POST("HomePage/getPhoneIdentTime")
    Observable<IndentBean> getIdentime(@Field("uid") String uid);
}

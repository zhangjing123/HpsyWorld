package com.kuwai.ysy.module.mine.api;


import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.bean.RResponse;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.mine.bean.BlackListBean;
import com.kuwai.ysy.module.mine.bean.ChangeHeadBean;
import com.kuwai.ysy.module.mine.bean.CheckInBean;
import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.module.mine.bean.EarlierBean;
import com.kuwai.ysy.module.mine.bean.FootCity;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.GiftExchangeBean;
import com.kuwai.ysy.module.mine.bean.GiftWithdrawalsBean;
import com.kuwai.ysy.module.mine.bean.HomepageListBean;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.bean.JobBean;
import com.kuwai.ysy.module.mine.bean.LikeEach;
import com.kuwai.ysy.module.mine.bean.LikeParent;
import com.kuwai.ysy.module.mine.bean.LikeParentBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.module.mine.bean.MyWalletBean;
import com.kuwai.ysy.module.mine.bean.NoticeSetBean;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.PersonalTreeHole;
import com.kuwai.ysy.module.mine.bean.RechargeRecordBean;
import com.kuwai.ysy.module.mine.bean.ShieldAndBlackListBean;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.kuwai.ysy.module.mine.bean.TestVoice;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.TodayIntegral;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.VisitorMore;
import com.kuwai.ysy.module.mine.bean.WallBean;
import com.kuwai.ysy.module.mine.bean.WalletDetailsBean;
import com.kuwai.ysy.module.mine.bean.WheelBean;
import com.kuwai.ysy.module.mine.bean.WithdrawalsRecordBean;
import com.kuwai.ysy.module.mine.bean.place.LatestPlace;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface MineService {

    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Observable<ResponseBody> login(@FieldMap Map<String, String> params);

    //个人主页-列表
    @FormUrlEncoded
    @POST("My/PersonalList")
    Observable<UserInfo> getUsetInfo(@Field("uid") String uid);

    //个人主页-资料-本人查看
    @FormUrlEncoded
    @POST("My/PersonalInfo")
    Observable<PersolHomePageBean> getUsetInfoMine(@Field("uid") String uid);

    /**
     * 个人主页-动态-本人查看
     */
    @FormUrlEncoded
    @POST("My/PersonalDynamic")
    Observable<DyMainListBean> getPersonalDynamic(@Field("uid") String uid,
                                                  @Field("page") int page);

    /**
     * 个人主页-树洞-本人查看
     */
    @FormUrlEncoded
    @POST("My/PersonalTreeHole")
    Observable<PersonalTreeHole> getPersonalTreeHole(@Field("uid") String uid,
                                                     @Field("page") int page);

    /**
     * 个人主页-资料-别人查看
     */
    @FormUrlEncoded
    @POST("My/OtherPersonalInfo")
    Observable<PersolHomePageBean> getOtherPersonalInfo(@Field("uid") String uid,
                                                        @Field("other_uid") String otherid);

    /**
     * 个人主页-资料-别人查看
     */
    @FormUrlEncoded
    @POST("HomePage/OtherPersonalInfo_three")
    Observable<PersolHome2PageBean> getOtherPersonal2Info(@Field("uid") String uid,
                                                         @Field("other_uid") String otherid);

    /**
     * 购买查看素颜的特权
     */
    @FormUrlEncoded
    @POST("My/PurchaseViewPlainFace")
    Observable<SimpleResponse> getPurchaseViewPlainFace(@Field("uid") String uid,
                                                        @Field("other_uid") String otherid);

    /**
     * 设置上线提醒
     */
    @FormUrlEncoded
    @POST("My/Reminder")
    Observable<SimpleResponse> getReminder(@Field("uid") String uid,
                                           @Field("other_uid") String otherid);

    /**
     * 取消上线提醒
     */
    @FormUrlEncoded
    @POST("My/CancelReminder")
    Observable<SimpleResponse> getCancelReminder(@Field("uid") String uid,
                                                 @Field("other_uid") String otherid);

    /**
     * 喜欢/取消喜欢
     *
     * @param type 1：喜欢，2：取消喜欢
     */
    @FormUrlEncoded
    @POST("My/UserLike")
    Observable<SimpleResponse> getUserLike(@Field("uid") String uid,
                                           @Field("other_uid") String otherid,
                                           @Field("type") int type);

    /**
     * 我收到的礼物列表
     */
    @FormUrlEncoded
    @POST("My/UserReceiveGiftsnAllList")
    Observable<GiftAcceptBean> getGiftMyAccept(@Field("uid") String uid,
                                               @Field("page") int page);

    /**
     * 我发送的礼物列表
     */
    @FormUrlEncoded
    @POST("My/UserGiveGiftsnAllList")
    Observable<GiftAcceptBean> getGiftMySend(@Field("uid") String uid,
                                             @Field("page") int page);

    /**
     * 背景墙
     */
    @FormUrlEncoded
    @POST("My/UserPhotoWallList")
    Observable<WallBean> getWall(@Field("uid") String uid);

    /**
     * 访客记录-谁看过我/我看过谁-所有
     *
     * @param type 1:谁看过我，2:是我看过谁
     */
    @FormUrlEncoded
    @POST("My/VisitorsRecordSeenMe")
    Observable<VisitorBean> getVisitors(@Field("uid") String uid,
                                        @Field("type") int type);

    /**
     * 访客记录-谁看过我/我看过谁-更早
     *
     * @param type 1:谁看过我，2:是我看过谁
     */
    @FormUrlEncoded
    @POST("My/VisitorsRecordSeenMeEarlier")
    Observable<VisitorMore> getVisitorsEarlier(@Field("uid") String uid,
                                               @Field("type") int type,
                                               @Field("page") int page);

    /**
     * 访客记录-删除
     *
     * @param type 1:谁看过我，2:是我看过谁
     */
    @FormUrlEncoded
    @POST("My/DelVisitorsRecord")
    Observable<SimpleResponse> getDelVisitorsRecord(@Field("uid") String uid,
                                                    @Field("v_id") int vid,
                                                    @Field("type") String type);

    /**
     * 我喜欢的
     */
    @FormUrlEncoded
    @POST("My/UserLoveMe")
    Observable<LikeParentBean> getIlike(@Field("uid") String uid);

    /**
     * 我喜欢的-更早-分页
     */
    @FormUrlEncoded
    @POST("My/UserLoveMeEarlier")
    Observable<EarlierBean> getIloveEarlier(@Field("uid") String uid,
                                            @Field("page") int page);

    /**
     * 喜欢我的
     */
    @FormUrlEncoded
    @POST("My/UserILikeIt")
    Observable<LikeParentBean> getLikeMe(@Field("uid") String uid);

    /**
     * 喜欢我的-更早-分页
     */
    @FormUrlEncoded
    @POST("My/UserILikeItEarlier")
    Observable<EarlierBean> getUserILikeItEarlier(@Field("uid") String uid,
                                                  @Field("page") int page);


    /**
     * 互相喜欢的（好友）
     */
    @FormUrlEncoded
    @POST("My/UserLikeEachOther")
    Observable<LikeEach> getLikeEach(@Field("uid") String uid, @Field("page") int page);

    /**
     * 修改昵称
     */
    @FormUrlEncoded
    @POST("Modify/UpdateUserNickname")
    Observable<SimpleResponse> getUpdateUserNickname(@Field("uid") String uid, @Field("new_nickname") String nickname);

    /**
     * 设置支付密码
     */
    @FormUrlEncoded
    @POST("Modify/AddParmentPassword")
    Observable<SimpleResponse> getAddParmentPassword(@Field("uid") String uid,
                                                     @Field("password") String psd,
                                                     @Field("parment_password") String pay_psd,
                                                     @Field("repeat_password") String re_pay_psd);

    /**
     * 修改 登录密码/支付密码
     *
     * @param type 1：登录密码，2：支付密码
     */
    @FormUrlEncoded
    @POST("Modify/UpdatePasswordOrParmentPassword")
    Observable<SimpleResponse> updatePasswordOrParmentPassword(@Field("uid") String uid,
                                                               @Field("password") String psd,
                                                               @Field("new_password") String pay_psd,
                                                               @Field("repeat_password") String re_pay_psd,
                                                               @Field("type") int type);

    /**
     * 修改手机号
     */
    @FormUrlEncoded
    @POST("Modify/UpdateUserPhone")
    Observable<SimpleResponse> updateUserPhone(@Field("uid") String uid,
                                               @Field("phone") String phone,
                                               @Field("new_phone") String newPhone,
                                               @Field("new_check_code") String newVcode);

    /**
     * 修改个性签名
     */
    @FormUrlEncoded
    @POST("Modify/UpdateUserSig")
    Observable<SimpleResponse> updateUserSig(@Field("uid") String uid,
                                             @Field("new_sig") String sig);

    /**
     * Ta 收到的礼物
     */
    @FormUrlEncoded
    @POST("My/OtherGiftList")
    Observable<TaGiftBean> getTaGift(@Field("uid") String uid,
                                     @Field("page") int page);

    /**
     * 礼物箱
     */
    @FormUrlEncoded
    @POST("My/GiftBox")
    Observable<GiftBoxBean> getGiftBox(@Field("uid") String uid);

    /**
     * 我的想问-列表
     */
    @FormUrlEncoded
    @POST("My/ProblemList")
    Observable<MyAskBean> getAskList(@Field("uid") String uid);

    /**
     * 删除问题
     */
    @FormUrlEncoded
    @POST("My/DelProblem")
    Observable<SimpleResponse> getDelAsk(@Field("uid") String uid, @Field("p_id") int pid);

    /**
     * 邀请上传个人图片、视频
     */
    @FormUrlEncoded
    @POST("My/InvitationToUpload")
    Observable<SimpleResponse> inviteToUpdate(@Field("uid") String uid, @Field("other_uid") String other_uid);

    /**
     * 添加问题
     */
    @FormUrlEncoded
    @POST("My/AddProblem")
    Observable<SimpleResponse> getAddAsk(@Field("uid") String uid, @Field("problem") String problem);

    /**
     * 修改 问题/回答/添加回答
     */
    @FormUrlEncoded
    @POST("My/UpdateProblem")
    Observable<SimpleResponse> getUpdateProblem(@Field("uid") String uid,
                                                @Field("p_id") int pid,
                                                @Field("problem") String pro,
                                                @Field("answer") String answer);

    /**
     * 用户积分明细-列表
     */
    @FormUrlEncoded
    @POST("My/UserIntegralDetails")
    Observable<IntegralDetailBean> getUserIntegralDetails(@Field("uid") String uid,
                                                          @Field("page") int page);

    /**
     * 今日-用户积分明细-列表
     */
    @FormUrlEncoded
    @POST("My/UserTodayIntegralDetails")
    Observable<TodayIntegral> getUserTodayIntegralDetails(@Field("uid") String uid,
                                                          @Field("page") int page);

    /**
     * 签到列表
     */
    @FormUrlEncoded
    @POST("My/UserIntegralCheckInList")
    Observable<CheckInBean> getUserIntegralCheckInList(@Field("uid") String uid);

    /**
     * 点击签到
     */
    @FormUrlEncoded
    @POST("My/UserIntegralCheckIn")
    Observable<SimpleResponse> getUserIntegralCheckIn(@Field("uid") String uid);


    /**
     * 信用认证-列表
     */
    @FormUrlEncoded
    @POST("My/MyAuthenticationList")
    Observable<CreditBean> getMyAuthenticationList(@Field("uid") String uid);

    /**
     * 实名认证
     */
    @Multipart
    @POST("My/AddRealnameAuthentication")
    Observable<SimpleResponse> getRealnameAuthentication(@PartMap Map<String, RequestBody> map);

    /**
     * 学历认证
     */
    @Multipart
    @POST("My/AddEducationAuthentication")
    Observable<SimpleResponse> getEducationAuthentication(@PartMap Map<String, RequestBody> map);

    /**
     * 车辆认证
     */
    @Multipart
    @POST("My/AddVehicleAuthentication")
    Observable<SimpleResponse> getVehicleAuthentication(@PartMap Map<String, RequestBody> map);

    /**
     * 房产认证(不动产认证)
     */
    @Multipart
    @POST("My/AddHouseAuthentication")
    Observable<SimpleResponse> getHouseAuthentication(@PartMap Map<String, RequestBody> map);

    /**
     * 屏蔽与黑名单-列表
     */
    @FormUrlEncoded
    @POST("My/ShieldAndBlacklistList")
    Observable<ShieldAndBlackListBean> getShieldAndBlackList(@Field("uid") String uid);

    /**
     * 消息设置-列表
     */
    @FormUrlEncoded
    @POST("My/MessageNotificationList")
    Observable<NoticeSetBean> noticeSet(@Field("uid") String uid);

    /**
     * 消息设置
     */
    @FormUrlEncoded
    @POST("My/MessageSettings")
    Observable<SimpleResponse> noticeChange(@Field("uid") String uid, @Field("type") int type, @Field("set") String set);

    /**
     * 删除足迹
     */
    @FormUrlEncoded
    @POST("My/DelFootprints")
    Observable<SimpleResponse> deleteFoot(@Field("uid") String uid, @Field("f_id") String fid, @Field("type") int type);

    /**
     * 黑名单-列表
     */
    @FormUrlEncoded
    @POST("My/BlacklistList")
    Observable<BlackListBean> getBlackList(@Field("uid") String uid,
                                           @Field("page") int page);

    /**
     * 足迹地点-城市
     */
    @FormUrlEncoded
    @POST("My/UserFootprints")
    Observable<FootCity> getFootCity(@Field("uid") String uid, @Field("page") int page);

    /**
     * 屏蔽名单-列表
     */
    @FormUrlEncoded
    @POST("My/ShieldList")
    Observable<BlackListBean> getPingList(@Field("uid") String uid,
                                          @Field("page") int page);

    /**
     * 帖子屏蔽
     */
    @FormUrlEncoded
    @POST("Currency/ScreenPosts")
    Observable<SimpleResponse> ping(@Field("uid") String uid,
                                    @Field("t_id") int t_id, @Field("type") int type);

    /**
     * 积分兑换桃花币
     */
    @FormUrlEncoded
    @POST("My/UserExchange")
    Observable<SimpleResponse> getUserExchange(@Field("uid") String uid,
                                               @Field("integral_exchange") int ex);

    /**
     * 礼物单条兑换
     */
    @FormUrlEncoded
    @POST("My/GiftExchangePeachCoin")
    Observable<SimpleResponse> getGiftExchangePeachCoin(@Field("uid") String uid,
                                                        @Field("g_g_id") int gid,
                                                        @Field("type") int type,
                                                        @Field("g_nums") int nums);

    /**
     * 礼物全部提现-列表
     */
    @FormUrlEncoded
    @POST("My/UserWithdrawalsAllList")
    Observable<GiftWithdrawalsBean> getUserWithdrawalsAllList(@Field("uid") String uid);

    /**
     * 礼物多条提现
     */
    @FormUrlEncoded
    @POST("My/GiftExchangePeachCoinAll")
    Observable<SimpleResponse> getGiftExchangePeachCoinAll(@Field("uid") String uid,
                                                           @Field("type_1") String type1,
                                                           @Field("type_2") String type2);

    /**
     * 礼物兑换实物-信息展示
     */
    @FormUrlEncoded
    @POST("My/GiftExchangeEntityInfo")
    Observable<GiftExchangeBean> getGiftExchangeEntityInfo(@Field("uid") String uid,
                                                           @Field("g_g_id") int gid,
                                                           @Field("type") int type,
                                                           @Field("g_nums") int nums);

    /**
     * 礼物兑换实物-信息展示
     */
    @FormUrlEncoded
    @POST("My/GiftExchangeEntity")
    Observable<SimpleResponse> getGiftExchangeEntity(@FieldMap HashMap<String, String> infos);

    /**
     * 我的钱包
     */
    @FormUrlEncoded
    @POST("My/MyWallet")
    Observable<MyWalletBean> getMyWallet(@Field("uid") String uid);

    /**
     * 钱包明细
     */
    @FormUrlEncoded
    @POST("My/WalletList")
    Observable<WalletDetailsBean> getWalletList(@Field("uid") String uid,
                                                @Field("page") int page);

    /**
     * 钱包提现
     */
    @FormUrlEncoded
    @POST("My/WalletWithdrawals")
    Observable<SimpleResponse> walletWithdrawals(@Field("uid") String uid,
                                                 @Field("forward_amount") String money,
                                                 @Field("alipay_id") String payid,
                                                 @Field("alipay_name") String payName);

    /**
     * 钱包-充值记录
     */
    @FormUrlEncoded
    @POST("My/WalletRechargeRecord")
    Observable<RechargeRecordBean> walletRechargeRecord(@Field("uid") String uid,
                                                        @Field("page") int page);

    /**
     * 钱包-提现记录
     */
    @FormUrlEncoded
    @POST("My/WalletWithdrawalsRecord")
    Observable<WithdrawalsRecordBean> walletWithdrawalsRecord(@Field("uid") String uid,
                                                              @Field("page") int page);

    /**
     * 会员列表
     */
    @GET("My/AndroidFirstClassMember")
    Observable<VipBean> getVipList();

    /**
     * zfb订单获取
     */
    @FormUrlEncoded
    @POST("Payment/pagePay")
    Observable<AliOrderInfoBean> getAliOrderInfo(@Field("uid") String uid,
                                                 @Field("money") String money);

    /**
     * 最近足迹
     */
    @FormUrlEncoded
    @POST("My/RecentFootprints")
    Observable<LatestPlace> getLatestPlace(@Field("uid") String uid,
                                           @Field("page") int page);

    /**
     * 修改个人资料
     */
    @FormUrlEncoded
    @POST("My/EditingMaterials")
    Observable<SimpleResponse> changeInfo(@FieldMap Map<String, Object> infos);

    /**
     * 修改个人资料2.0
     */
    @FormUrlEncoded
    @POST("HomePage/EditingMaterials_three")
    Observable<SimpleResponse> change2Info(@FieldMap Map<String, String> infos);

    /**
     * 照片墙添加视频
     */
    @Multipart
    @POST("My/AddUserPhotoWall")
    Observable<SimpleResponse> addVideoWall(@PartMap Map<String, RequestBody> map);

    /**
     * 照片墙添加图片
     */
    @Multipart
    @POST("My/AddUserImgae")
    Observable<SimpleResponse> addPhotoWall(@PartMap Map<String, RequestBody> map);

    /**
     * 修改头像
     */
    @Multipart
    @POST("User/UpdateAvatar")
    Observable<ChangeHeadBean> changeHead(@PartMap Map<String, RequestBody> map);

    /**
     * 修改头像
     */
    @Multipart
    @POST("HomePage/addVoice")
    Observable<SimpleResponse> uploadVoice(@PartMap Map<String, RequestBody> map);

    /**
     * 开通会员
     */
    @FormUrlEncoded
    @POST("UserVip/OpenMembership")
    Observable<SimpleResponse> openVip(@FieldMap Map<String, Object> infos);

    /**
     * 职业接口
     */
    @GET("HomePage/OccupationList")
    Observable<JobBean> getAllJob();

    /**
     * 照片墙删除图片/视频
     */
    @FormUrlEncoded
    @POST("My/DelUserPhotoWall")
    Observable<SimpleResponse> deleteVideo(@Field("uid") String uid, @Field("v_id") int v_id, @Field("type") int type);

    @Multipart
    @POST("HomePage/PhoneIdent")
    Observable<SimpleResponse> photoAuth(@PartMap Map<String, RequestBody> map);

    /**
     * 修改个人资料
     */
    @FormUrlEncoded
    @POST("HomePage/Myinformationdisplay")
    Observable<HomepageListBean> getHomepageInfo(@Field("uid") String uid);

    /**
     * 修改个人资料-获取数据列表
     */
    @FormUrlEncoded
    @POST("HomePage/getPersonalDataList")
    Observable<WheelBean> getWheelInfo(@Field("type") String type);

    /**
     * 职业接口
     */
    @GET("HomePage/getVoice")
    Observable<TestVoice> getVoice();
}

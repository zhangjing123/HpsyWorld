package com.kuwai.ysy.module.home.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bailingcloud.bailingvideo.engine.binstack.util.FinLog;
import com.google.gson.Gson;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.app.MyApp;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.StartPageBean;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.controller.NavigationController;
import com.kuwai.ysy.module.chat.ChatMainFragment;
import com.kuwai.ysy.module.circle.business.DongtaiFragment;
import com.kuwai.ysy.module.find.PerVideoActivity;
import com.kuwai.ysy.module.find.business.FoundHome.FoundFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.module.home.business.main.HomeRadioFragment;
import com.kuwai.ysy.module.mine.PersonVideoActivity;
import com.kuwai.ysy.module.mine.SingleCallActivity;
import com.kuwai.ysy.module.mine.business.mine.MineLoginFragment;
import com.kuwai.ysy.module.mine.business.mine.MineLoginTwoFragment;
import com.kuwai.ysy.socket.WsManager;
import com.kuwai.ysy.socket.WsStatusListener;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.SPUtils;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.PageNavigationView;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;
import io.rong.callkit.RongCallAction;
import io.rong.callkit.RongVoIPIntent;
import io.rong.calllib.IRongReceivedCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Response;
import okio.ByteString;


public class HomeActivity extends BaseActivity implements AMapLocationListener, IUnReadMessageObserver {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FORTH = 3;
    public static final int FIFTH = 4;
    private static final String WEBSOCKET_URL = "ws://192.168.0.161:2346";

    public static NavigationController mNavigationController;
    private RBaseFragment[] mFragments = new RBaseFragment[5];
    private AMapLocation mAmapLocation;
    private AMapLocationClient mlocationClient;

    private String user1 = "bBp48xhvTH1txJ8TJcYxZLmIC5Mv+fpWZ4zmDkh2pTLGVZEU6ZNOS4PwHGMF7gUw95eSiYc7cZpNLaX6kxdHOA==";
    private String user2 = "r15Y4G6BcSpmSgXbJrf/ClUbwhMPS+kf5yBTiVt919N9HJEQV3wopiEsnyZK5KbUzIcg7rRdn8ZWr7Sv9AIj0A==";
    private UserInfo userInfo;

    private StartPageBean startPageBean;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private WsManager wsBaseManager;
    private boolean mViewLoaded;

    private boolean needImmersive() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("type", Build.MANUFACTURER);
        Log.e("type", Build.HARDWARE);
        Log.e("type", Build.DEVICE);
        super.onCreate(savedInstanceState);
        //Utils.setCustomDensity(this, MyApp.getAppContext());
        mViewLoaded = true;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //mFragments[FIRST] = VideohomeActivity.newInstance();
        mFragments[FIRST] = HomeRadioFragment.newInstance(null);
        mFragments[SECOND] = new FoundFragment();
        mFragments[THIRD] = ChatMainFragment.newInstance();
        mFragments[FORTH] = DongtaiFragment.newInstance();
        mFragments[FIFTH] = MineLoginTwoFragment.newInstance();
        PageNavigationView pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.btn_tab_home_default, R.drawable.btn_tab_home_pressed, getResources().getString(R.string.tab_home))
                .addItem(R.drawable.btn_tab_dongtai_default, R.drawable.btn_tab_dongtai_pressed, getResources().getString(R.string.tab_action), getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_chat_default, R.drawable.btn_tab_chat_pressed, getResources().getString(R.string.tab_chat), getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_faxian_default, R.drawable.btn_tab_faxian_pressed, getResources().getString(R.string.tab_near), getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_wode_default, R.drawable.btn_tab_wode_pressed, getResources().getString(R.string.tab_mine), getResources().getColor(R.color.theme))
                .build();

//        loadMultipleRootFragment(R.id.fl_tab_container,FIRST,mFragments[FIRST],mFragments[SECOND],mFragments[THIRD]);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TestViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(5);
        mNavigationController.setupWithViewPager(viewPager);

        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE};

        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);

        getLocation();
        autoLogin();
        requestReadPermission();
        // connectRongYun(user1);
        /*wsBaseManager = new WsManager.Builder(getBaseContext())
                .client(new OkHttpClient().newBuilder()
                        .pingInterval(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .needReconnect(true)
                .wsUrl(WEBSOCKET_URL)
                .build();
        wsBaseManager.setWsStatusListener(wsBaseStatusListener);
        wsBaseManager.startConnect();*/
    }

    WsStatusListener wsBaseStatusListener = new WsStatusListener() {
        @Override
        public void onOpen(Response response) {
            super.onOpen(response);
            //协议初始化  心跳等
        }

        @Override
        public void onMessage(String text) {
            super.onMessage(text);
            //消息处理
        }

        @Override
        public void onMessage(ByteString bytes) {
            super.onMessage(bytes);
            //消息处理
        }

        @Override
        public void onClosing(int code, String reason) {
            super.onClosing(code, reason);
        }

        @Override
        public void onClosed(int code, String reason) {
            super.onClosed(code, reason);
        }

        @Override
        public void onFailure(Throwable t, Response response) {
            super.onFailure(t, response);
        }
    };

    private void autoLogin() {
        HashMap<String, String> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("password_"))) {
            //手机号登录
            param.put("type", C.LOGIN_PHONE);
            param.put("login_type", "1"); //1代表android
            param.put("phone", SPManager.get().getStringValue("phone_"));
            param.put("password", SPManager.get().getStringValue("password_"));
            login(param, "");
        } else if (!Utils.isNullString(SPManager.get().getStringValue(C.SAN_FANG_ID))) {
            //三方登陆
            param.put("type", SPManager.get().getStringValue(C.SAN_FANG));
            param.put("login_type", "1"); //1代表android
            //param.put(SPManager.get().getStringValue(C.SAN_FANG), SPManager.get().getStringValue(C.SAN_FANG_ID));
            login(param, SPManager.get().getStringValue(C.SAN_FANG));
        } else if (!Utils.isNullString(SPManager.get().getStringValue("token"))) {
            //验证码登录
            param.put("type", "token");
            param.put("login_type", "1"); //1代表android
            param.put("phone", SPManager.get().getStringValue("phone_"));
            param.put("token", SPManager.get().getStringValue("token"));
            login(param, "");
        }
    }

    public void login(Map<String, String> param, final String type) {
        addSubscription(HomeApiFactory.login(param).subscribe(new Consumer<LoginBean>() {
            @Override
            public void accept(LoginBean loginBean) throws Exception {
                LogUtils.error("autoLogin",loginBean.toString());
                if (loginBean.getCode() == 200) {
                    SPManager.get().putString(C.SAN_FANG, type);
                    SPUtils.savaLogin(loginBean);
                    SPManager.get().putString("password_", SPManager.get().getStringValue("password_"));
                    connectRongYun(loginBean.getData().getRongyun_token(), loginBean);
                } else {
                    if (C.LOGIN_QQ.equals(SPManager.get().getStringValue(C.SAN_FANG))) {
                        UMShareAPI.get(mContext).deleteOauth(HomeActivity.this, SHARE_MEDIA.QQ, null);
                    } else if (C.LOGIN_SINA.equals(SPManager.get().getStringValue(C.SAN_FANG))) {
                        UMShareAPI.get(mContext).deleteOauth(HomeActivity.this, SHARE_MEDIA.SINA, null);
                    } else if (C.LOGIN_WECHAT.equals(SPManager.get().getStringValue(C.SAN_FANG))) {
                        UMShareAPI.get(mContext).deleteOauth(HomeActivity.this, SHARE_MEDIA.WEIXIN, null);
                    }
                    //SPManager.clear();
                    RongIM.getInstance().disconnect();
                    //startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //ToastUtils.showShort(R.string.server_error);
                LogUtils.error("autoLogin",throwable);
            }
        }));
    }

    private void connectRongYun(String token, final LoginBean loginBean) {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("xxx", "onTokenIncorrect: ");
            }

            @Override
            public void onSuccess(String s) {
                Log.i("xxx", "onTokenIncorrect: ");
                IRongReceivedCallListener callListener = new IRongReceivedCallListener() {
                    @Override
                    public void onReceivedCall(final RongCallSession callSession) {
                        FinLog.d("VoIPReceiver", "onReceivedCall");
                        if (mViewLoaded) {
                            FinLog.d("VoIPReceiver", "onReceivedCall->onCreate->mViewLoaded=true");
                            startVoIPActivity(mContext, callSession, false);
                        } else {
                            FinLog.d("VoIPReceiver", "onReceivedCall->onCreate->mViewLoaded=false");
                            //mCallSession = callSession;
                        }
                    }

                    @Override
                    public void onCheckPermission(RongCallSession callSession) {
                        FinLog.d("VoIPReceiver", "onCheckPermissions");
                        //mCallSession = callSession;
                        if (mViewLoaded) {
                            startVoIPActivity(mContext, callSession, true);
                        }
                    }
                };

                RongCallClient.setReceivedCallListener(callListener);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("xxx", "onTokenIncorrect: ");
            }
        });
    }

    private void startVoIPActivity(Context context, final RongCallSession callSession, boolean startForCheckPermissions) {
        FinLog.d("VoIPReceiver", "startVoIPActivity");
        String action;
            if (callSession.getMediaType().equals(RongCallCommon.CallMediaType.VIDEO)) {
                action = RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEVIDEO;
            } else {
                action = RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEAUDIO;
            }
            Intent intent = new Intent(HomeActivity.this, SingleCallActivity.class);
            intent.putExtra("callSession", callSession);
            intent.putExtra("callAction", RongCallAction.ACTION_INCOMING_CALL.getName());
            if (startForCheckPermissions) {
                intent.putExtra("checkPermissions", true);
            } else {
                intent.putExtra("checkPermissions", false);
            }
            intent.setAction(action);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.setPackage(context.getPackageName());
            context.startActivity(intent);
        //mCallSession = null;
    }

    private void getLocation() {

        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置为高精度定位模式
        mLocationOption.setOnceLocation(true);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mlocationClient.startLocation();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mAmapLocation = amapLocation;
                if (mAmapLocation != null) {
                    SPManager.get().putString("longitude", String.valueOf(mAmapLocation.getLongitude()));
                    SPManager.get().putString("latitude", String.valueOf(mAmapLocation.getLatitude()));
                    SPManager.get().putString("ysy_city", mAmapLocation.getCity());
                    SPManager.get().putString("ysy_dis", mAmapLocation.getDistrict());
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        UMShareAPI.get(this).release();
    }

    /**
     * 监听列表的滑动来控制底部导航栏的显示与隐藏
     */
    public static class ListScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 8) {//列表向上滑动
                mNavigationController.hideBottomLayout();
            } else if (dy < -8) {//列表向下滑动
                mNavigationController.showBottomLayout();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class ScroolListener implements View.OnScrollChangeListener {

        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY > oldScrollY) {//列表向上滑动
                mNavigationController.hideBottomLayout();
            } else if (scrollY < oldScrollY) {//列表向下滑动
                mNavigationController.showBottomLayout();
            }
        }
    }

    //下面几个类都是为了测试写的

    private class TestViewPagerAdapter extends FragmentPagerAdapter {

        TestViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public RBaseFragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mNavigationController.getItemCount();
        }
    }

    public static void reStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    ToastUtils.showShort("再次按返回退出");
                    firstTime = System.currentTimeMillis();
                } else {
                    finish();
                    //System.exit(0);
                }
            } else {
                getSupportFragmentManager().popBackStack();
                //取出我们返回栈保存的Fragment,这里会从栈顶开始弹栈
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void getStartPage() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        OkHttpUtils
                .post()
                .url(C.QiDong)
                .addParam("img_width", String.valueOf(screenWidth))
                .addParam("img_height", String.valueOf(screenHeight))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        Log.e("", "");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            startPageBean = new Gson().fromJson(response, StartPageBean.class);
                            startPageBean.getData().getImg_url();
                            startPageBean.getData().getStart_time();
                            startPageBean.getData().getEnd_time();

                            getPageIMG();
                        } catch (Exception e) {
                            Log.e("", "");
                        }
                    }
                });
    }

    private void getPageIMG() {

        OkHttpUtils
                .get()
                .url(startPageBean.getData().getImg_url())
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dskgxt/pic/", "ysy_start_page.jpg") {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {
                        sharedPreferences = getSharedPreferences(C.SP_NAME, Context.MODE_PRIVATE); //私有数据
                        editor = sharedPreferences.edit();
                        editor.putLong("start_time", startPageBean.getData().getStart_time());
                        editor.putLong("end_time", startPageBean.getData().getEnd_time());
                        editor.putLong("show_time", startPageBean.getData().getShow_time());

                        editor.apply();//提交修改
                    }
                });

    }


    private void requestReadPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        getStartPage();
                    }
                });
    }

    @Override
    public void onCountChanged(int count) {
       /* if(count>0){
            mNavigationController.setHasMessage(2,true);
        }else{
            mNavigationController.setHasMessage(2,false);
        }*/
        mNavigationController.setMessageNumber(2,count);
    }
}

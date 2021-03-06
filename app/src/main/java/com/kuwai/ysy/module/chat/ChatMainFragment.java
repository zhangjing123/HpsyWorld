package com.kuwai.ysy.module.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.UserInfoBean;
import com.kuwai.ysy.module.chat.business.NoticeFragment;
import com.kuwai.ysy.module.mine.business.gift.GiftMyAcceptFragment;
import com.kuwai.ysy.module.mine.business.gift.GiftMySendFragment;
import com.kuwai.ysy.module.mine.business.gift.GiftRealExchangeFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.pageitem.RoundMessageView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StatusBarUtil;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class ChatMainFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager pager;
    private List<Fragment> fragments;
    private RadioGroup radioGroup;
    private ImageView imgChat;

    private RadioButton radioButtonLookme, radioButtonMylook;
    private UserInfo userInfo;
    private RoundMessageView red_tv;

    public static ChatMainFragment newInstance() {
        Bundle args = new Bundle();
        ChatMainFragment fragment = new ChatMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_conversationlist;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    startActivity(new Intent(getActivity(), MyFriendActivity.class));
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        pager = mRootView.findViewById(R.id.vp_dy_detail);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        imgChat = mRootView.findViewById(R.id.chat);
        red_tv = mRootView.findViewById(R.id.red_tv);
        radioGroup = (RadioGroup) mRootView.findViewById(R.id.main_radiogroup);
        radioButtonLookme = mRootView.findViewById(R.id.tv_chat_count);
        radioButtonMylook = mRootView.findViewById(R.id.tv_notice);
        radioButtonLookme.setOnClickListener(this);
        radioButtonMylook.setOnClickListener(this);
        imgChat.setOnClickListener(this);
        radioButtonLookme.setTextSize(22);

        //refreshContent();


        fragments = new ArrayList<>();
       /* //会话列表
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
//                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                //.appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "true")
                //.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话，该会话非聚合显示
                .build();
        conversationListFragment.setUri(uri);
        fragments.add(conversationListFragment);
        fragments.add(NoticeFragment.newInstance());*/

       /* RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {

                return findUserById(userId);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。SDK
            }

        }, true);*/

        // 添加页面切换事件的监听器
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //pager.resetHeight(position);
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);
                switch (position) {
                    case 0:
                        radioButtonLookme.setTextSize(22);
                        radioButtonMylook.setTextSize(16);
                        radioButtonLookme.setTextColor(getResources().getColor(R.color.theme));
                        radioButtonMylook.setTextColor(getResources().getColor(R.color.gray_7b));
                        break;
                    case 1:
                        radioButtonLookme.setTextSize(16);
                        radioButtonMylook.setTextSize(22);
                        radioButtonLookme.setTextColor(getResources().getColor(R.color.gray_7b));
                        radioButtonMylook.setTextColor(getResources().getColor(R.color.theme));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tv_chat_count:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.tv_notice:
                        pager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    private void refreshContent() {
        if (!Utils.isNullString(SPManager.get().getStringValue("uid")) && fragments != null) {
            mLayoutStatusView.showContent();
            fragments.clear();
            //会话列表
            ConversationListFragment conversationListFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
//                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    //.appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "true")
                    //.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话，该会话非聚合显示
                    .build();
            conversationListFragment.setUri(uri);
            fragments.add(conversationListFragment);
            fragments.add(NoticeFragment.newInstance());
            pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

                @Override
                public int getCount() {
                    return fragments.size();
                }

                @Override
                public Fragment getItem(int arg0) {
                    return fragments.get(arg0);
                }
            });
        } else {
            mLayoutStatusView.showError();
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //getConversation();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        refreshContent();
    }

    //该方法不建议放在有生命周期的地方
   /* private UserInfo findUserById(String userId) {
        addSubscription(ChatApiFactory.getUserInfo(userId,SPManager.get().getStringValue("uid")).subscribe(new Consumer<UserInfoBean>() {
            @Override
            public void accept(UserInfoBean userInfoBean) throws Exception {
                if (userInfoBean.getCode() == 200) {
                    userInfo = new UserInfo(String.valueOf(userInfoBean.getData().getUid()), userInfoBean.getData().getNickname(), Uri.parse(userInfoBean.getData().getAvatar()));
                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                } else {
                    //ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //ToastUtils.showShort("网络错误");
            }
        }));
        return userInfo;
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (C.MSG_LOGIN == event.getCode()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            refreshContent();
        } else if (C.MSG_LOG_OUT == event.getCode()) {
            refreshContent();
        } else if (C.MSG_FRIEND_REFRESH == event.getCode()) {
            getNewFriends();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getActivity() != null) {
            if (isVisibleToUser) {
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    getNewFriends();
                }
            }
        }
    }

    void getNewFriends() {
        addSubscription(ChatApiFactory.getNewFriends(SPManager.get().getStringValue("uid")).subscribe(new Consumer<MyFriends>() {
            @Override
            public void accept(MyFriends myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    if (myBlindBean.getData() != null && myBlindBean.getData().size() > 0) {
                        red_tv.setHasMessage(true);
                    } else {
                        red_tv.setHasMessage(false);
                    }
                } else {
                    //ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

}

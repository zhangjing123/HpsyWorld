package com.kuwai.ysy.module.circle.business.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.circle.adapter.message.DashangAdapter;
import com.kuwai.ysy.module.circle.adapter.message.LikeMsgAdapter;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.AllLikeBean;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class DyLikeMesFragment extends BaseFragment implements View.OnClickListener {

    private LikeMsgAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private NavigationLayout navigationLayout;
    private int mPage = 1;
    private String uid = "";
    private SmartRefreshLayout mRefreshLayout;
    private AllLikeBean mAllLikeBean;

    public static DyLikeMesFragment newInstance() {
        Bundle args = new Bundle();
        DyLikeMesFragment fragment = new DyLikeMesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.msg_recyclerview;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getFriends();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore();
            }
        });

        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setTitle("点赞");
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new LikeMsgAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        uid = SPManager.get().getStringValue("uid");
        getFriends();
    }

    void getFriends() {
        addSubscription(CircleApiFactory.getAllLikeListData(uid, mPage).subscribe(new Consumer<AllLikeBean>() {
            @Override
            public void accept(AllLikeBean myBlindBean) throws Exception {
                EventBusUtil.sendEvent(new MessageEvent(C.MSG_UNREAD_UPDATE));
                mRefreshLayout.finishRefresh();
                if (myBlindBean.getCode() == 200) {
                    mAllLikeBean = myBlindBean;
                    mDateAdapter.replaceData(myBlindBean.getData());
                } else {
                    ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络错误");
            }
        }));
    }

    private void getMore() {
        addSubscription(CircleApiFactory.getAllLikeListData(uid, mPage + 1).subscribe(new Consumer<AllLikeBean>() {
            @Override
            public void accept(AllLikeBean myFriends) throws Exception {
                if (myFriends.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mAllLikeBean.getData().addAll(myFriends.getData());
                mDateAdapter.addData(myFriends.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}

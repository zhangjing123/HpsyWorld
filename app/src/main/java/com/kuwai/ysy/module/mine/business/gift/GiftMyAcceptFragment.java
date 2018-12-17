package com.kuwai.ysy.module.mine.business.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.DyZanAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.GiftAdapter;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

public class GiftMyAcceptFragment extends BaseFragment<GiftMyAcceptPresenter> implements GiftMyAcceptContract.IHomeView, View.OnClickListener {

    private GiftAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private int page = 1;

    public static GiftMyAcceptFragment newInstance() {
        Bundle args = new Bundle();
        GiftMyAcceptFragment fragment = new GiftMyAcceptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected GiftMyAcceptPresenter getPresenter() {
        return new GiftMyAcceptPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mDongtaiList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mDateAdapter = new GiftAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.getStringValue("uid", "1"), page);
    }

    @Override
    public void setHomeData(GiftAcceptBean giftAcceptBean) {
        mDateAdapter.addData(giftAcceptBean.getData().getGift());

    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}

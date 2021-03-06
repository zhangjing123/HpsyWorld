package com.kuwai.ysy.module.circle.business.holedashang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.message.DashangAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;

import java.util.ArrayList;
import java.util.List;

public class HoleDashangListFragment extends BaseFragment<HoleDashangPresenter> implements HoleDashangContract.IHomeView, View.OnClickListener {

    private DashangAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private String did;
    private int page = 1;

    public static HoleDashangListFragment newInstance(Bundle bundle) {
        HoleDashangListFragment fragment = new HoleDashangListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.dashang_recyclerview;
    }

    @Override
    protected HoleDashangPresenter getPresenter() {
        return new HoleDashangPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        //mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDongtaiList.setLayoutManager(linearLayoutManager);
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new DashangAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (TextUtils.isEmpty(getArguments().getString("did"))) {
            did = getArguments().getString("did");
            mPresenter.requestHomeData(String.valueOf(did), page);
        } else {
            did = getArguments().getString("did");
            mPresenter.requestHoleData(String.valueOf(did), page);
        }
    }


    @Override
    public void setHomeData(DyRewardlistBean dyRewardlistBean) {
        if (dyRewardlistBean.getData() != null) {
            mLayoutStatusView.showContent();
            mDateAdapter.addData(dyRewardlistBean.getData());
        } else {
            mLayoutStatusView.showEmpty();
        }

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

package com.kuwai.ysy.module.circle.business.DyZan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.DyZanAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyLikeListBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

public class DyZanListFragment extends BaseFragment<DyZanPresenter> implements DyZanContract.IHomeView, View.OnClickListener {

    private DyZanAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private int page = 1;
    private String did;

    public static DyZanListFragment newInstance(Bundle bundle) {
        DyZanListFragment fragment = new DyZanListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.dashang_recyclerview;
    }

    @Override
    protected DyZanPresenter getPresenter() {
        return new DyZanPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        //mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDongtaiList.setLayoutManager(linearLayoutManager);
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new DyZanAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        did = getArguments().getString("did");
        mPresenter.requestHomeData(did, SPManager.get().getStringValue("uid", "1"), page);
    }

    @Override
    public void setHomeData(DyLikeListBean dyLikeListBean) {
        if (dyLikeListBean.getData().getGood().size() > 0) {
            mLayoutStatusView.showContent();
            mDateAdapter.addData(dyLikeListBean.getData().getGood());
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

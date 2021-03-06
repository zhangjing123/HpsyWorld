package com.kuwai.ysy.module.circle.business.DyZan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.DyZanAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyLikeListBean;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DyZanListFragment extends BaseFragment<DyZanPresenter> implements DyZanContract.IHomeView, View.OnClickListener {

    private DyZanAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private DyLikeListBean mDyLikeListBean;
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
        EventBusUtil.register(this);
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDongtaiList.setLayoutManager(linearLayoutManager);
        mDateAdapter = new DyZanAdapter();
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    if (!SPManager.get().getStringValue("uid").equals(String.valueOf(mDyLikeListBean.getData().getGood().get(position).getUid()))) {
                        Intent intent1 = new Intent(mContext, CardDetailActivity.class);
                        intent1.putExtra("id", String.valueOf(mDyLikeListBean.getData().getGood().get(position).getUid()));
                        startActivity(intent1);
                    }
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        did = getArguments().getString("did");
        mPresenter.requestHomeData(did, SPManager.get().getStringValue("uid", "1"), page);
    }

    @Override
    public void setHomeData(DyLikeListBean dyLikeListBean) {
        mDyLikeListBean = dyLikeListBean;
        if (dyLikeListBean.getData().getGood().size() > 0) {
            mLayoutStatusView.showContent();
            mDateAdapter.replaceData(dyLikeListBean.getData().getGood());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_ZAN_DY) {
            mPresenter.requestHomeData(did, SPManager.get().getStringValue("uid", "1"), page);
        }
    }
}

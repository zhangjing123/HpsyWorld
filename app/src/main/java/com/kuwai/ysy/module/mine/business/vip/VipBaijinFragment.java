package com.kuwai.ysy.module.mine.business.vip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.mine.adapter.vip.HuangjinVipFeeAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.TequanAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.VipRightAdapter;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDAlertDialog;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VipBaijinFragment extends BaseFragment<VipHuangjinPresenter> implements VipHuangjinContract.IMineView,View.OnClickListener {

    private CustomFontTextview tv_money;
    private int selectPos = 0;

    private HuangjinVipFeeAdapter mDateAdapter;
    private List<VipPayBean> mDataList = new ArrayList<>();
    private RecyclerView rl_fee, rlRight;

    private VipBean.DataBean mVipdata = null;
    private SuperButton mSubmitBtn;
    private TextView mXieyiTv, mZhengceTv;

    public static VipBaijinFragment newInstance(Bundle args) {
        VipBaijinFragment fragment = new VipBaijinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_baijin;
    }

    @Override
    protected VipHuangjinPresenter getPresenter() {
        return new VipHuangjinPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                new MDAlertDialog.Builder(getActivity())
                        .setTitleVisible(false)
                        .setContentText("确认开通铂金会员？")
                        .setHeight(0.16f)
                        .setOnclickListener(new com.rayhahah.dialoglib.DialogInterface.OnLeftAndRightClickListener<MDAlertDialog>() {
                            @Override
                            public void clickLeftButton(MDAlertDialog dialog, View view) {
                                dialog.dismiss();
                            }

                            @Override
                            public void clickRightButton(MDAlertDialog dialog, View view) {
                                dialog.dismiss();
                                Map<String, Object> param = new HashMap<String, Object>();
                                param.put("uid", SPManager.get().getStringValue("uid"));
                                param.put("v_id", mDataList.get(selectPos).getVipType());
                                param.put("type", mDataList.get(selectPos).getDay());
                                param.put("source", "Android");
                                mPresenter.getAliOrderInfo(param);
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .build().show();
                break;
            case R.id.tv_xieyi:
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra(C.H5_FLAG, C.H5_URL + C.HUIYUANTIOAKUAN);
                startActivity(intent);
                break;
            case R.id.tv_zhengce:
                Intent intent1 = new Intent(getActivity(), WebviewH5Activity.class);
                intent1.putExtra(C.H5_FLAG, C.H5_URL + C.BAOHUZHENGCE);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rl_fee = mRootView.findViewById(R.id.rl_fee);
        rlRight = mRootView.findViewById(R.id.rl_right);
        mXieyiTv = mRootView.findViewById(R.id.tv_xieyi);
        mZhengceTv = mRootView.findViewById(R.id.tv_zhengce);
        mXieyiTv.setOnClickListener(this);
        mZhengceTv.setOnClickListener(this);
        tv_money = mRootView.findViewById(R.id.tv_money);
        mSubmitBtn = mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(this);

        mVipdata = (VipBean.DataBean) getArguments().getSerializable("data");
        tv_money.setText(mVipdata.getMonthly_card() + "");
        mDataList.add(new VipPayBean(mVipdata.getV_id(),"月度VIP", String.valueOf(mVipdata.getMonthly_card()), mVipdata.getMonthly_card() + "元/每月", true,"30"));
        mDataList.add(new VipPayBean(mVipdata.getV_id(),"季度VIP", String.valueOf(mVipdata.getSeason_card()), mVipdata.getSeason_card() + "元/每季", false,"90"));
        mDataList.add(new VipPayBean(mVipdata.getV_id(),"年度VIP", String.valueOf(mVipdata.getYear_card()), mVipdata.getYear_card() + "元/每年", false,"365"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_fee.setLayoutManager(linearLayoutManager);
        mDateAdapter = new HuangjinVipFeeAdapter(mDataList);
        rl_fee.setAdapter(mDateAdapter);

        rlRight.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rlRight.setAdapter(new VipRightAdapter(mVipdata.getPrivilege(),mVipdata.getV_id()));

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (VipPayBean bean : mDataList) {
                    bean.setCheck(false);
                }
                selectPos = position;
                tv_money.setText(mDataList.get(position).getPrice());
                mDataList.get(position).setCheck(true);
                mDateAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void setAliOrderInfo(SimpleResponse infoBean) {
        if (infoBean.code == 200) {
           initCleanDialog().show();
        }
        ToastUtils.showShort(infoBean.msg);
    }
    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("提示")
                .setContentText(getResources().getString(R.string.vip_tips))
                .setSingleButtonText("好的，知道了")
                .setSingleMode(true)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .build();
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

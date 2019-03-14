package com.kuwai.ysy.module.home.business.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeRadioFragment extends BaseFragment implements View.OnClickListener {

    private List<Fragment> mFragments;
    private RadioGroup radioGroup;
    private int mIndex = 0;

    private RadioButton mTvTui;
    private View mLine;
    private RadioButton mTvNear;

    public static HomeRadioFragment newInstance(Bundle bundle) {
        HomeRadioFragment fragment = new HomeRadioFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tui:
                mTvTui.setTextSize(20);
                mTvNear.setTextSize(16);
                setIndexSelected(0);
                break;
            case R.id.tv_near:
                mTvTui.setTextSize(16);
                mTvNear.setTextSize(20);
                setIndexSelected(1);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mTvTui = mRootView.findViewById(R.id.tv_tui);
        mLine = mRootView.findViewById(R.id.line);
        mTvTui.setTextSize(20);
        mTvNear = mRootView.findViewById(R.id.tv_near);
        radioGroup = (RadioGroup) mRootView.findViewById(R.id.main_radiogroup);
        mRootView.findViewById(R.id.tv_tui).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_near).setOnClickListener(this);
        MainFragment mainActivity = new MainFragment();
        NearPerFragment nearPerFragment = NearPerFragment.newInstance();
        mFragments.add(mainActivity);
        mFragments.add(nearPerFragment);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.content, mainActivity).commit();
        setIndexSelected(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void setIndexSelected(int index) {

        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();


        //隐藏
        ft.hide(mFragments.get(mIndex));
        //判断是否添加
        if (!mFragments.get(index).isAdded()) {
            ft.add(R.id.content, mFragments.get(index)).show(mFragments.get(index));
        } else {
            ft.show(mFragments.get(index));
        }

        ft.commit();
        //再次赋值
        mIndex = index;

    }
}

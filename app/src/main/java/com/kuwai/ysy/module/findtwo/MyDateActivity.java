package com.kuwai.ysy.module.findtwo;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.findtwo.business.MyMeetFragment;
import com.kuwai.ysy.module.mine.business.wallet.MyWalletFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class MyDateActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar mTitleBar;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_container;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        loadRootFragment(R.id.container, new MyMeetFragment(),false,true);
    }

    @Override
    public void onClick(View v) {

    }
}

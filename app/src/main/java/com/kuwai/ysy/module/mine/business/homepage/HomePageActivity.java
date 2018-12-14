package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.rayhahah.rbase.base.RBasePresenter;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar mTitleBar;
    private Bundle bundle;

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
        bundle = new Bundle();
//        bundle.putString("id", getIntent().getStringExtra("uid"));
        bundle.putString("id", "1");

        loadRootFragment(R.id.container, OtherHomepageFragment.newInstance(bundle), false, true);
    }

    @Override
    public void onClick(View v) {

    }
}

package com.kuwai.ysy.module.mine.business.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainFragment;
import com.kuwai.ysy.module.mine.adapter.PicAdapter;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.TabEntity;
import com.kuwai.ysy.module.mine.bean.vip.GallaryBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import cc.shinichi.library.ImagePreview;
import io.rong.imkit.RongIM;

public class OtherHomepageFragment extends BaseFragment<OtherHomepagePresenter> implements OtherHomepageContract.IHomeView, View.OnClickListener {

    private ImageView mLeft;
    private TextView mTitle;
    private TextView mSubTitle;
    private ImageView mRight;
    private CircleImageView mImgHead;
    private TextView mTvNick;
    private ImageView mImgVip;
    private TextView mTvLevel;
    private TextView mTvSign;
    private ImageView mImgRight;
    private TextView mTvXinyong;
    private ImageView mImgXinyong;
    private View mLine1;
    private TextView mTvShangxian;
    private ImageView mImgShangxian;
    private SuperButton mBtnLike;
    private SuperButton mBtnChat;
    private SuperButton mBtnSendGift;
    private RecyclerView mRlPic;
    private PicAdapter mDateAdapter;

    private ViewPager viewPager;
    private final String[] mTitles = {"资料信息", "动态"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private String otherid;
    private PersolHomePageBean mPersolHomePageBean;

    private ArrayList<PersolHomePageBean.DataBean.InfoBean.VideoBean> videos = new ArrayList<>();

    public static OtherHomepageFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        OtherHomepageFragment fragment = new OtherHomepageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mem_homepage;
    }

    @Override
    protected OtherHomepagePresenter getPresenter() {
        return new OtherHomepagePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chat:
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                RongIM.getInstance().startPrivateChat(getActivity(), otherid, mTvNick.getText().toString());
                //私聊
                break;
            case R.id.tv_xinyong:
                //信用度
                break;
            case R.id.btn_like:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    if ("喜欢".equals(mBtnLike.getText().toString())) {
                        mPresenter.like(SPManager.get().getStringValue("uid"), otherid, 1);
                    } else {
                        mPresenter.like(SPManager.get().getStringValue("uid"), otherid, 2);
                    }
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                //喜欢
                break;
            case R.id.btn_send_gift:
                //弹出打赏
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        otherid = getArguments().getString("id");
        mLeft = mRootView.findViewById(R.id.left);
        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mTitle = mRootView.findViewById(R.id.title);
        mSubTitle = mRootView.findViewById(R.id.sub_title);
        mRight = mRootView.findViewById(R.id.right);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvNick = mRootView.findViewById(R.id.tv_nick);
        mImgVip = mRootView.findViewById(R.id.img_vip);
        mTvLevel = mRootView.findViewById(R.id.tv_level);
        mTvSign = mRootView.findViewById(R.id.tv_sign);
        mImgRight = mRootView.findViewById(R.id.img_right);
        mTvXinyong = mRootView.findViewById(R.id.tv_xinyong);
        mImgXinyong = mRootView.findViewById(R.id.img_xinyong);
        mLine1 = mRootView.findViewById(R.id.line1);
        mTvShangxian = mRootView.findViewById(R.id.tv_shangxian);
        mImgShangxian = mRootView.findViewById(R.id.img_shangxian);
        mBtnLike = mRootView.findViewById(R.id.btn_like);
        mBtnChat = mRootView.findViewById(R.id.btn_chat);
        mBtnSendGift = mRootView.findViewById(R.id.btn_send_gift);
        mRlPic = mRootView.findViewById(R.id.rl_pic);

        mBtnChat.setOnClickListener(this);
        mTvXinyong.setOnClickListener(this);
        mBtnLike.setOnClickListener(this);
        mBtnSendGift.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlPic.setLayoutManager(linearLayoutManager);

        mDateAdapter = new PicAdapter();
        mRlPic.setAdapter(mDateAdapter);

        viewPager = mRootView.findViewById(R.id.vp);
        slidingTabLayout = mRootView.findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        Bundle bundle = new Bundle();
        bundle.putString("id", otherid);

        mFragments.add(PageDetailFragment.newInstance(bundle));
        mFragments.add(DongtaiOtherFragment.newInstance(otherid));
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        //slidingTabLayout.setViewPager(viewPager);\
        slidingTabLayout.setTabData(mTabEntities);

        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isNullString(videos.get(position).getVideo_id())) {
                    //图片
                    ImagePreview
                            .getInstance()
                            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                            .setContext(getActivity())
                            // 从第几张图片开始，索引从0开始哦~
                            .setIndex(0)
                            // 只有一张图片的情况，可以直接传入这张图片的url
                            .setImage(videos.get(position).getAttach())
                            // 加载策略，详细说明见下面“加载策略介绍”。默认为手动模式
                            .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)
                            // 保存的文件夹名称，会在SD卡根目录进行文件夹的新建。
                            // (你也可设置嵌套模式，比如："BigImageView/Download"，会在SD卡根目录新建BigImageView文件夹，并在BigImageView文件夹中新建Download文件夹)
                            .setFolderName("YsyDownload")
                            // 缩放动画时长，单位ms
                            .setZoomTransitionDuration(300)
                            // 是否启用上拉/下拉关闭。默认不启用
                            .setEnableDragClose(true)
                            // 是否显示下载按钮，在页面右下角。默认显示
                            .setShowDownButton(false)
                            // 设置是否显示顶部的指示器（1/9）默认显示
                            //.setShowIndicator(false)
                            // 设置失败时的占位图，默认为R.drawable.load_failed，设置为 0 时不显示
                            .setErrorPlaceHolder(R.drawable.load_failed)
                            // 开启预览
                            .start();
                } else {
                    //视频
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vid", videos.get(position).getVideo_id());
                    bundle.putString("imgurl", videos.get(position).getAttach());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mPresenter.requestHomeData(otherid, SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(PersolHomePageBean persolHomePageBean) {
        mPersolHomePageBean = persolHomePageBean;

        mTitle.setText(persolHomePageBean.getData().getInfo().getNickname());
        if (persolHomePageBean.getData().getLove() == 1) {
            mBtnLike.setText("取消喜欢");
        }
        List<String> subtitle = new ArrayList<>();
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAge())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAge() + "岁");
        }
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()))) {
            subtitle.add(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()) + "cm");
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getEducation())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getEducation());
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAnnual_income())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAnnual_income());
        }
        mSubTitle.setText(StringUtils.join(subtitle.toArray(), "|"));

        GlideUtil.load(mContext, persolHomePageBean.getData().getInfo().getAvatar(), mImgHead);
        mTvNick.setText(persolHomePageBean.getData().getInfo().getNickname());

        switch (persolHomePageBean.getData().getInfo().getIs_vip()) {
            case 0:
                mImgVip.setVisibility(View.GONE);
                break;
            case 1:
                mImgVip.setVisibility(View.VISIBLE);
                break;
        }

        mTvLevel.setText(String.valueOf(persolHomePageBean.getData().getInfo().getGrade()));

        List<String> info = new ArrayList<>();
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getUid()))) {
            info.add("ID:" + String.valueOf(persolHomePageBean.getData().getInfo().getUid()));
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getCity())) {
            info.add(persolHomePageBean.getData().getInfo().getCity());
        }
        mTvSign.setText(StringUtils.join(info.toArray(), "|"));

        videos.clear();
        for (PersolHomePageBean.DataBean.InfoBean.VideoBean img : persolHomePageBean.getData().getInfo().getVideo()) {
            videos.add(img);
        }
        for (PersolHomePageBean.DataBean.InfoBean.ImageBean img : persolHomePageBean.getData().getInfo().getImage()) {
            PersolHomePageBean.DataBean.InfoBean.VideoBean bean = new PersolHomePageBean.DataBean.InfoBean.VideoBean();
            bean.setAttach(img.getImg());
            videos.add(bean);
        }
        mDateAdapter.replaceData(videos);
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void likeResult(SimpleResponse response) {
        if (response.code == 200) {
            if ("喜欢".equals(mBtnLike.getText().toString())) {
                mBtnLike.setText("取消喜欢");
                ToastUtils.showShort("喜欢成功");
            } else {
                mBtnLike.setText("取消成功");
            }
        }
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

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}

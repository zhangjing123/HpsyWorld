package com.kuwai.ysy.module.circle.business.DyDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.second.CommentDetailBean;
import com.kuwai.ysy.module.circle.business.DyDashang.DyDashangListFragment;
import com.kuwai.ysy.module.circle.business.dycomment.DySecFragment;
import com.kuwai.ysy.module.circle.business.DyZan.DyZanListFragment;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.others.NineImageAdapter;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.GiftPannelView;
import com.kuwai.ysy.widget.MyViewPager;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.NiceImageView;
import com.kuwai.ysy.widget.NineGridView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.MainActivity;

public class DyDetailMainFragment extends BaseFragment<DyDetailPresenter> implements DyDetailContract.IHomeView, View.OnClickListener, GiftClickCallback {

    private MyViewPager pager;
    private NavigationLayout navigationLayout;
    private List<Fragment> fragments;
    private RadioGroup radioGroup;
    private List<String> imgList = new ArrayList<>();

    private SuperButton mDshangBtn;
    private LinearLayout mBottomLay;
    private BottomSheetDialog dialog;
    private String did, otherId;
    private NineGridView nineGridView;
    private NineImageAdapter nineImageAdapter;

    private NiceImageView mImgHead;
    private int index;
    /**
     * 杨和苏Ysny
     */
    private TextView mTvNick;

    private ImageView mIvSex;
    /**
     * 19岁|185cm|本科|12万元
     */
    private TextView mTvInfo;
    /**
     * 非常不错的一个公园，很大，十一人还是很多的，很适合拍照。带娃的可以慢慢逛，至少花做得不错，建设的可以的呢。
     * 也有很多有不错的游乐项目，小火车，划船...推荐傍晚太阳没下山前去，应该人会少很多呢。
     * 朋友圈发了后都问我是哪里。
     */
    private TextView mTvContent;
    /**
     * 苏州
     */
    private TextView mTvLocation;
    /**
     * 46分钟前
     */
    private TextView mTvTime;
    /**
     * 打赏(20)
     */
    private RadioButton mRadioDashang, mRadioReward, mRadioZan;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private DyDetailBean mDyDetailBean;

    private CustomDialog customDialog;
    private GiftPopBean giftPopBean;
    private boolean isLike = false;

    private RelativeLayout rl_play;
    private ImageView iv_playimg;

    public static DyDetailMainFragment newInstance(Bundle bundle) {
        DyDetailMainFragment fragment = new DyDetailMainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_dy_detail;
    }

    @Override
    protected DyDetailPresenter getPresenter() {
        return new DyDetailPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_playimg:
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("vid", mDyDetailBean.getData().getVideo_id());
                bundle.putString("imgurl", mDyDetailBean.getData().getAttach().get(0));
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.bottom_lay:
                showCommentDialog();
                break;
            case R.id.dialog_comment_bt:
                switch (mDshangBtn.getText().toString()) {
                    case "打赏":
                        if (giftPopBean != null) {
                            GiftPannelView pannelView = new GiftPannelView(getActivity());
                            pannelView.setData(giftPopBean.getData(), getActivity());
                            pannelView.setGiftClickCallBack(this);
                            if (customDialog == null) {
                                customDialog = new CustomDialog.Builder(getActivity())
                                        .setView(pannelView)
                                        .setTouchOutside(true)
                                        .setItemHeight(0.4f)
                                        .setDialogGravity(Gravity.BOTTOM)
                                        .build();
                            }
                            customDialog.show();
                        }
                        break;
                    case "赞一下":
                        mPresenter.dyDetailZan(did, SPManager.get().getStringValue("uid"), otherId, 1);
                        break;
                    case "取消赞":
                        mPresenter.dyDetailZan(did, SPManager.get().getStringValue("uid"), otherId, 2);
                        break;
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        did = getArguments().getString("did");
        index = getArguments().getInt("index");
        mImgHead = (NiceImageView) mRootView.findViewById(R.id.img_head);
        mTvNick = (TextView) mRootView.findViewById(R.id.tv_nick);
        mIvSex = mRootView.findViewById(R.id.iv_sex);
        rl_play = mRootView.findViewById(R.id.rl_play);
        iv_playimg = mRootView.findViewById(R.id.iv_playimg);
        mTvInfo = (TextView) mRootView.findViewById(R.id.tv_info);
        mTvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        mTvLocation = (TextView) mRootView.findViewById(R.id.tv_location);
        mTvTime = (TextView) mRootView.findViewById(R.id.tv_time);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar_layout);


        pager = mRootView.findViewById(R.id.vp_dy_detail);
        pager.setOffscreenPageLimit(3);
        radioGroup = (RadioGroup) mRootView.findViewById(R.id.main_radiogroup);
        mBottomLay = mRootView.findViewById(R.id.bottom_lay);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mDshangBtn = mRootView.findViewById(R.id.dialog_comment_bt);
        mBottomLay.setOnClickListener(this);
        mRadioDashang = (RadioButton) mRootView.findViewById(R.id.radio_dashang);
        mRadioReward = (RadioButton) mRootView.findViewById(R.id.radio_reward);
        mRadioZan = (RadioButton) mRootView.findViewById(R.id.radio_zan);

        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        mRadioDashang.setOnClickListener(this);
        mRadioReward.setOnClickListener(this);
        mRadioZan.setOnClickListener(this);
        mDshangBtn.setOnClickListener(this);
        iv_playimg.setOnClickListener(this);

        nineGridView = mRootView.findViewById(R.id.nine_grid_view);

        nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, View view) {

            }
        });

        fragments = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();
        bundle.putString("did", did);
        bundle.putString("type", "1");

        fragments.add(DyDashangListFragment.newInstance(bundle));
        fragments.add(DySecFragment.newInstance(bundle));
        fragments.add(DyZanListFragment.newInstance(bundle));

        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        });
        // 添加页面切换事件的监听器
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //pager.resetHeight(position);
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);
                switch (position) {
                    case 0:
                        mDshangBtn.setVisibility(View.VISIBLE);
                        mDshangBtn.setText("打赏");
                        mBottomLay.setVisibility(View.GONE);
                        break;
                    case 1:
                        mDshangBtn.setVisibility(View.GONE);
                        mBottomLay.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        if (isLike) {
                            mDshangBtn.setText("取消赞");
                        } else {
                            mDshangBtn.setText("赞一下");
                        }
                        mDshangBtn.setVisibility(View.VISIBLE);
                        mBottomLay.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_dashang:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.radio_reward:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.radio_zan:
                        pager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    private void share() {
        /*UMImage image = new UMImage(getActivity(), R.drawable.center_mark_ic_more);//网络图片
        //image.setThumb(image);
        image.compressStyle = UMImage.CompressStyle.QUALITY;*/
        UMImage image = null;
        if (mDyDetailBean != null) {
            if (mDyDetailBean.getData().getAttach() != null && mDyDetailBean.getData().getAttach().size() > 0) {
                image = new UMImage(getActivity(), mDyDetailBean.getData().getAttach().get(0));//网络图片
            } else {
                image = new UMImage(getActivity(), R.mipmap.ic_sading);//网络图片
            }
            String url = "http://api.yushuiyuan.cn/h5/trend-detail.html?did=" + did;
            UMWeb web = new UMWeb(url);
            web.setTitle("鱼水缘动态");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(mDyDetailBean.getData().getText());//描述
            new ShareAction(getActivity())
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                    .setCallback(shareListener).open();
        }

    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();
        }
    };

    private void showCommentDialog() {
        if (dialog == null) {
            dialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetEdit);
            View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout, null);
            final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
            final SuperButton bt_comment = (SuperButton) commentView.findViewById(R.id.dialog_comment_bt);
            dialog.setContentView(commentView);
            /**
             * 解决bsd显示不全的情况
             */
            View parent = (View) commentView.getParent();
            BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
            commentView.measure(0, 0);
            behavior.setPeekHeight(commentView.getMeasuredHeight());

            bt_comment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String commentContent = commentText.getText().toString().trim();
                    if (!TextUtils.isEmpty(commentContent)) {
                        dialog.dismiss();
                        mPresenter.addFirComment(did, SPManager.get().getStringValue("uid"), commentContent);
                        Utils.showOrHide(getActivity(), commentText);
                        commentText.setText("");
                    } else {
                        Toast.makeText(getActivity(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        dialog.show();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(did, SPManager.get().getStringValue("uid"));
        mPresenter.getAllGifts();
    }

    @Override
    public void setHomeData(DyDetailBean dyDetailBean) {
        mDyDetailBean = dyDetailBean;
        GlideUtil.load(mContext, dyDetailBean.getData().getAvatar(), mImgHead);
        otherId = String.valueOf(mDyDetailBean.getData().getUid());
        mTvNick.setText(dyDetailBean.getData().getNickname());
        isLike = mDyDetailBean.getData().getWhatgood() == 0 ? false : true;
        switch (dyDetailBean.getData().getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, mIvSex);
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, mIvSex);
                break;
        }

        List<String> info = new ArrayList<>();
        if (!TextUtils.isEmpty(String.valueOf(dyDetailBean.getData().getAge()))) {
            info.add(dyDetailBean.getData().getAge() + "岁");
        }
        if (!TextUtils.isEmpty(String.valueOf(dyDetailBean.getData().getHeight()))) {
            info.add(dyDetailBean.getData().getHeight() + "cm");
        }
        if (!TextUtils.isEmpty(dyDetailBean.getData().getEducation())) {
            info.add(dyDetailBean.getData().getEducation());
        }
        if (!TextUtils.isEmpty(dyDetailBean.getData().getAnnual_income())) {
            info.add(dyDetailBean.getData().getAnnual_income());
        }
        mTvInfo.setText(StringUtils.join(info.toArray(), "|"));
        mTvContent.setText(mDyDetailBean.getData().getText());
        switch (dyDetailBean.getData().getType()) {
            case C.DY_TXT:
                break;
            case C.DY_PIC:
                rl_play.setVisibility(View.GONE);
                nineGridView.setVisibility(View.VISIBLE);
                imgList = dyDetailBean.getData().getAttach();
                nineImageAdapter = new NineImageAdapter(mContext, imgList);
                nineGridView.setAdapter(nineImageAdapter);
                break;
            case C.DY_FILM:
                rl_play.setVisibility(View.VISIBLE);
                nineGridView.setVisibility(View.GONE);
                if (dyDetailBean.getData().getAttach() != null && dyDetailBean.getData().getAttach().size() > 0) {
                    RequestOptions myOptions = new RequestOptions()
                            .centerCrop()
                            .override(300, 500);
                    Glide.with(mContext).load(dyDetailBean.getData().getAttach().get(0)).apply(myOptions).into(iv_playimg);
                }

                break;
        }

        mTvLocation.setText(Utils.isNullString(dyDetailBean.getData().getAddress()) ? "" : dyDetailBean.getData().getAddress() + "    ");
        mTvTime.setText(DateTimeUitl.getStandardDate(String.valueOf(dyDetailBean.getData().getUpdate_time())));
        //mRadioDashang.setText("打赏（" + String.valueOf(dyDetailBean.getData().getReward()) + "）");
        //mRadioReward.setText("评论（" + String.valueOf(dyDetailBean.getData().getComment()) + "）");
        //mRadioZan.setText("点赞（" + String.valueOf(dyDetailBean.getData().getGood()) + "）");

    }

    @Override
    public void addFirCallBack(SimpleResponse response) {
        EventBusUtil.sendEvent(new MessageEvent(C.MSG_COMMENT));
        Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setGifts(GiftPopBean popBean) {
        giftPopBean = popBean;
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void zanResult() {

        isLike = !isLike;
        if (isLike) {
            mDshangBtn.setText("取消赞");
            EventBusUtil.sendEvent(new MessageEvent(C.MSG_ZAN_DY,"1"));
        } else {
            mDshangBtn.setText("赞一下");
            EventBusUtil.sendEvent(new MessageEvent(C.MSG_ZAN_DY,"0"));
        }
    }

    @Override
    public void rewardSuc() {
        EventBusUtil.sendEvent(new MessageEvent(C.MSG_REWARD_DY));
        if (customDialog != null) {
            customDialog.dismiss();
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

    @Override
    public void giftClick(GiftPopBean.DataBean giftBean) {
        mPresenter.dyReward(SPManager.get().getStringValue("uid"), "1", did, giftBean.getG_id(), giftBean.num);
    }
}

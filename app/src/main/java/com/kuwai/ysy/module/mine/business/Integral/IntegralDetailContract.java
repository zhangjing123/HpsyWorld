package com.kuwai.ysy.module.mine.business.Integral;

import com.kuwai.ysy.module.mine.adapter.IntegralDetailAdapter;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.rayhahah.rbase.base.IRBaseView;

public class IntegralDetailContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(IntegralDetailBean integralDetailBean);

        /**
         * 设置加载更多的数据
         */
        //void setMoreData(ArrayList<HomeBean.Issue.Item> itemList);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
    }

    public interface IHomePresenter {

        /**
         * 获取首页精选数据
         */
        void requestHomeData(String uid,int page);
    }
}

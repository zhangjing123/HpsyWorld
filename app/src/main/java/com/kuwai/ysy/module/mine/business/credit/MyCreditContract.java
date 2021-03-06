package com.kuwai.ysy.module.mine.business.credit;

import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.rayhahah.rbase.base.IRBaseView;

public class MyCreditContract {

    public interface IHomeView extends IRBaseView {

        /**
         * 设置第一次请求的数据
         */
        void setHomeData(CreditBean creditBean);

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
        void requestHomeData(String uid);
    }
}

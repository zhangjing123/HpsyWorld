package com.kuwai.ysy.module.circle.business.holecomment;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.circle.bean.HoleCommentListBean;
import com.rayhahah.rbase.base.IRBaseView;

public class HoleCommentContract {

    public interface IPublishView extends IRBaseView {

        void setCommenList(DyCommentListBean dyDetailBean);
        void addSecCallBack(SimpleResponse response);

        /**
         * 显示错误信息
         */
        void showError(int errorCode, String msg);
        void commantZanResult();
    }

    public interface IPublishPresenter {

        void getCommentList(String d_id, String uid, int page);

        void addSecComment(String d_clid, String uid, String text, int other_uid);

        void commenZan(String d_id, String uid, int status, int d_clid, int other_uid);
    }
}

package com.kuwai.ysy.module.find.business.PostAppointment;

import android.util.Log;

import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.find.business.FoundHome.FoundContract;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class PostAppointmentPresenter extends RBasePresenter<PostAppointmentContract.IHomeView> implements PostAppointmentContract.IHomePresenter {
    private static final String TAG = "PostAppointment";

    public PostAppointmentPresenter(PostAppointmentContract.IHomeView view) {
        super(view);
    }

    @Override
    public void sendInfo(HashMap<String, RequestBody> params) {
        addSubscription(FoundApiFactory.sendBlind(params).subscribe(new Consumer<BlindBean>() {
            @Override
            public void accept(BlindBean blindBean) throws Exception {
                mView.getInfo(blindBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showViewError(throwable);
                Log.i(TAG, "accept: " + throwable);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }
}

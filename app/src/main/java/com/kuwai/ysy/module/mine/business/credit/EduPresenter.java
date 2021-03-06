package com.kuwai.ysy.module.mine.business.credit;

import android.util.Log;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 * @author
 * @time
 * @tips 这个类是Object的子类
 * @fuction
 */
public class EduPresenter extends RBasePresenter<EduContract.IHomeView> implements EduContract.IHomePresenter {
    private static final String TAG = "MyCreditPresenter";

    public EduPresenter(EduContract.IHomeView view) {
        super(view);
    }

    @Override
    public void requestHomeData(Map<String, RequestBody> map) {
        addSubscription(MineApiFactory.getEducationAuthentication(map).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                mView.setHomeData(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
                ToastUtils.showShort("网络错误");
            }
        }));
    }
}

package com.kuwai.ysy.listener;

import android.webkit.JavascriptInterface;


public class UpdateCall extends Object{

    private H5CallBack callBack = null;
    public interface H5CallBack{
        void update();
    }

    public void setCallback(H5CallBack h5CallBack){
        callBack = h5CallBack;
    }

    @JavascriptInterface
    public void update() {
        callBack.update();
    }
}

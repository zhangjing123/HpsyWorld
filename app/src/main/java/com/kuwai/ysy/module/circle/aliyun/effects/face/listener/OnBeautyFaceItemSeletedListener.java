package com.kuwai.ysy.module.circle.aliyun.effects.face.listener;


import com.kuwai.ysy.module.circle.aliyun.BeautyLevel;

/**
 * Created by Akira on 2018/5/30.
 */

public interface OnBeautyFaceItemSeletedListener {
    /**
     * 美颜item点击事件接口
     * @param postion 选中下标
     * @param beautyLevel 美颜级别 0~5
     */
    void onNormalSelected(int postion, BeautyLevel beautyLevel);

    void onAdvancedSelected(int postion, BeautyLevel beautyLevel);
}

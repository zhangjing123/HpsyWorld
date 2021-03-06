package com.kuwai.ysy.module.circle.aliyun.effects.face.listener;

import android.widget.RadioGroup;

/**
 * 美颜模式改变listener接口
 * @author xlx
 */
public interface OnBeautyModeChangeListener {
    /**
     * 美颜模式改变, 普通or高级
     * @param group RadioGroup
     * @param checkedId 选中的id
     */
    void onModeChange(RadioGroup group, int checkedId);
}

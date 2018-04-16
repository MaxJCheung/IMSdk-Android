package com.master.im;


import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.viewfeatures.MvpView;

import java.util.List;

/**
 * 聊天界面的接口
 */
public interface SubjectiveView extends MvpView {

    /**
     * 显示消息
     */
    void showMessage(TIMMessage message);


}

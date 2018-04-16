package com.master.im;


import com.tencent.TIMConversationType;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.event.MessageEvent;

import java.util.Observable;
import java.util.Observer;


/**
 */
public class SubjectivePresenter implements Observer {

    private static final String TAG = SubjectivePresenter.class.getSimpleName();
    private SubjectiveView view;

    public SubjectivePresenter(SubjectiveView view) {
        this.view = view;
    }

    public void start() {
        //注册消息监听
        MessageEvent.getInstance().addObserver(this);
    }

    /**
     * 中止页面逻辑
     */
    public void stop() {
        //注销消息监听
        MessageEvent.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {

        if (observable instanceof MessageEvent) {
            if (data instanceof TIMMessage || data == null) {
                TIMMessage msg = (TIMMessage) data;
                if (msg != null && msg.getConversation().getType() == TIMConversationType.C2C) {
                    view.showMessage(msg);
                }
            }


        }

    }
}

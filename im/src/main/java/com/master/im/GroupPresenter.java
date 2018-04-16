package com.master.im;

import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMValueCallBack;

import java.util.List;

/**
 * Created by 8000m on 2017/9/2.
 */

public class GroupPresenter {

    public static void getGroupDetail(List<String> groupIds, final TIMValueCallBack<List<TIMGroupDetailInfo>> callBack)
    {
        TIMGroupManager.getInstance().getGroupPublicInfo(groupIds, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
            @Override
            public void onError(int i, String s) {
                callBack.onError(i, s);
            }

            @Override
            public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                callBack.onSuccess(timGroupDetailInfos);
            }
        });
    }

}

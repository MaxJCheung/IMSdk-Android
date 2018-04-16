package com.tencent.qcloud.presentation.business;

import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;
import com.tencent.qcloud.presentation.UserIm;

/**
 * 登录
 */
public class LoginBusiness {

    private static final String TAG = "LoginBusiness";

    private LoginBusiness() {
    }

    public static void loginIm(UserIm im, TIMCallBack callBack) {

//        if (identify == null || userSig == null) return;
        TIMUser user = new TIMUser();
        user.setIdentifier(im.getIdentifier());
        //发起登录请求
        TIMManager.getInstance().login(
                Integer.parseInt(im.getSdkAppId()),
                user,
                im.getUserSig(),                    //用户帐号签名，由私钥加密获得，具体请参考文档
                callBack);
    }

    /**
     * 登出imsdk
     *
     * @param callBack 登出后回调
     */
    public static void logout(TIMCallBack callBack) {
        TIMManager.getInstance().logout(callBack);
    }
}

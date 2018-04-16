package com.tencent.qcloud.presentation;

/**
 *
 */

public class UserIm {

    private String accountType;
    private String appIdAt3rd;
    private String identifier;
    private String userSig;
    private String sdkAppId;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAppIdAt3rd() {
        return appIdAt3rd;
    }

    public void setAppIdAt3rd(String appIdAt3rd) {
        this.appIdAt3rd = appIdAt3rd;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        this.userSig = userSig;
    }

    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
    }
}

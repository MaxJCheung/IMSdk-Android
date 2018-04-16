package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.util.Log;

import com.tencent.TIMCustomElem;
import com.tencent.TIMMessage;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 自定义消息
 */
public class SubjectiveMessage extends Message {


    private String TAG = getClass().getSimpleName();

    private final int TYPE_TYPING = 14;

    private String type;
    private String desc;
    private String data;
    public static String[] TYPE = {"TIMCustomElem"};
    String msgContent;

    public SubjectiveMessage(TIMMessage message){
        this.message = message;
        TIMCustomElem elem = (TIMCustomElem) message.getElement(0);
        parse(elem.getData());

    }

    public SubjectiveMessage(String type){
        message = new TIMMessage();
        String data = "";
        JSONObject dataJson = new JSONObject();
//        try{
//            switch (type){
//                case TYPE[0]:
//                    dataJson.put("userAction",TYPE_TYPING);
//                    dataJson.put("actionParam","EIMAMSG_InputStatus_Ing");
//                    data = dataJson.toString();
//            }
//        }catch (JSONException e){
//            Log.e(TAG, "generate json error");
//        }
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(data.getBytes());
        message.addElement(elem);
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private void parse(byte[] data){

        try{
            msgContent = new String(data, "UTF-8");
        }catch (IOException e){
            Log.e(TAG, "parse json error");

        }
    }

    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {

    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return null;
    }

    /**
     * 保存消息或消息文件
     */
    @Override
    public void save() {

    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    //    public enum Type{
//        TYPING,
//        INVALID,
//    }



}

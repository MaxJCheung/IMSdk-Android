package com.tencent.qcloud.presentation;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 聊天界面输入控件
 */
public class ChatInput extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "ChatInput";

    private EditText editText;
    private InputMode inputMode = InputMode.NONE;
    private ChatView chatView;
    private ImageView edit;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private long lastSendMsgTime = 0;//最后一次发消息时间

    public ChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.chat_input, this);
        initView();
    }

    private void initView() {
        edit = (ImageView) findViewById(R.id.edit);
        edit.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.input);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    chatView.sendText(v.getText().toString());
                    chatView.hideKeyBoard();
                    editText.setVisibility(GONE);
                    editText.setText("");
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new MyTextWatcher(editText, 80, ImApplication2.getContext()));
    }

    private void updateVoiceView() {

    }

    public void setEditImageVisible(int visible) {
        edit.setVisibility(visible);
    }

    /**
     * 关联聊天界面逻辑
     */
    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Activity activity = (Activity) getContext();
        int id = v.getId();
        if (id == R.id.edit) {
            if (System.currentTimeMillis() - getLastSendMsgTime() < 6 * 1000) {
                chatView.showToast("发送消息太频繁");
                return;
            }
            setLastSendMsgTime(System.currentTimeMillis());
            editText.setVisibility(VISIBLE);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
            chatView.startInput();
        }
    }


    /**
     * 设置输入模式
     */
    public void setInputMode(InputMode mode) {
//        updateView(mode);
    }

    public void hideEditText() {
        editText.setVisibility(GONE);
    }


    public enum InputMode {
        TEXT,
        VOICE,
        EMOTICON,
        MORE,
        VIDEO,
        NONE,
    }

//    private boolean requestVideo(Activity activity){
//        if (afterM()){
//            final List<String> permissionsList = new ArrayList<>();
//            if ((activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.CAMERA);
//            if ((activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.RECORD_AUDIO);
//            if (permissionsList.size() != 0){
//                activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return false;
//            }
//            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
//            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
//                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean requestCamera(Activity activity){
//        if (afterM()){
//            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
//            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
//                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean requestAudio(Activity activity){
//        if (afterM()){
//            int hasPermission = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO);
//            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
//                activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean requestStorage(Activity activity){
//        if (afterM()){
//            int hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
//                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return false;
//            }
//        }
//        return true;
//    }

//    private boolean afterM(){
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
//    }


    public class MyTextWatcher implements TextWatcher {

        private int limit;// 字符个数限制
        private EditText text;// 编辑框控件
        private Context context;// 上下文对象

        int cursor = 0;// 用来记录输入字符的时候光标的位置
        int before_length;// 用来标注输入某一内容之前的编辑框中的内容的长度

        public MyTextWatcher(EditText text, int limit,
                             Context context) {
            this.limit = limit;
            this.text = text;
            this.context = context;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            before_length = s.length();
        }

        /**
         * s 编辑框中全部的内容 、start 编辑框中光标所在的位置（从0开始计算）、count 从手机的输入法中输入的字符个数
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            cursor = start;
//      Log.e("此时光标的位置为", cursor + "");
        }

        @Override
        public void afterTextChanged(Editable s) {
            // 这里可以知道你已经输入的字数，大家可以自己根据需求来自定义文本控件实时的显示已经输入的字符个数
            Log.e("此时你已经输入了", "" + s.length());

            int after_length = s.length();// 输入内容后编辑框所有内容的总长度
            // 如果字符添加后超过了限制的长度，那么就移除后面添加的那一部分，这个很关键
            if (after_length > limit) {

                // 比限制的最大数超出了多少字
                int d_value = after_length - limit;
                // 这时候从手机输入的字的个数
                int d_num = after_length - before_length;

                int st = cursor + (d_num - d_value);// 需要删除的超出部分的开始位置
                int en = cursor + d_num;// 需要删除的超出部分的末尾位置
                // 调用delete()方法将编辑框中超出部分的内容去掉
                Editable s_new = s.delete(st, en);
                // 给编辑框重新设置文本
                text.setText(s_new.toString());
                // 设置光标最后显示的位置为超出部分的开始位置，优化体验
                text.setSelection(st);
                // 弹出信息提示已超出字数限制
                Toast.makeText(context, "超出80字数限制", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void saveInputMsg(String msg) {
        if (null != editText) {
            editText.setText(msg);
        }
    }

    public long getLastSendMsgTime() {
        return lastSendMsgTime;
    }

    public void setLastSendMsgTime(long lastSendMsgTime) {
        this.lastSendMsgTime = lastSendMsgTime;
    }

}

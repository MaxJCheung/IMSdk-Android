package com.tencent.qcloud.timchat.utils.richtext;

public interface OnTextClickListener {
  void onClicked(CharSequence text, Range range, Object tag);
}

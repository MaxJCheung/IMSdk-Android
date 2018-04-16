package com.tencent.qcloud.timchat.utils.richtext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MSimpleText extends SpannableString {

    private static final int SPAN_MODE = SPAN_EXCLUSIVE_EXCLUSIVE;
    private ArrayList<Range> rangeList = new ArrayList<>();
    private ArrayMap<Range, Object> tagsMap = new ArrayMap<>();
    private Context context;
    private int textColor;
    private int pressedTextColor;
    private int pressedBackgroundColor;
    private int pressedBackgroundRadius;

    private MSimpleText(Context context, CharSequence text) {
        super(text);
        this.context = context;
    }

    public static MSimpleText create(Context context, CharSequence text) {
        return new MSimpleText(context, text);
    }

    public MSimpleText first(String target) {
        rangeList.clear();
        int index = toString().indexOf(target);
        Range range = Range.create(index, index + target.length());
        rangeList.add(range);
        return this;
    }

    public MSimpleText last(String target) {
        rangeList.clear();
        int index = toString().lastIndexOf(target);
        Range range = Range.create(index, index + target.length());
        rangeList.add(range);
        return this;
    }

    public MSimpleText all(String target) {
        rangeList.clear();
        List<Integer> indexes = Utils.indexesOf(toString(), target);
        for (Integer index : indexes) {
            Range range = Range.create(index, index + target.length());
            rangeList.add(range);
        }
        return this;
    }

    public MSimpleText all() {
        rangeList.clear();
        Range range = Range.create(0, toString().length());
        rangeList.add(range);
        return this;
    }

    public MSimpleText allStartWith(String... prefixs) {
        rangeList.clear();
        for (String prefix : prefixs) {
            List<Range> ranges = Utils.ranges(toString(), prefix + "\\w+");
            for (Range range : ranges) {
                rangeList.add(range);
            }
        }
        return this;
    }

    public MSimpleText range(int from, int to) {
        rangeList.clear();
        Range range = Range.create(from, to + 1);
        rangeList.add(range);
        return this;
    }

    public MSimpleText ranges(List<Range> ranges) {
        rangeList.clear();
        rangeList.addAll(ranges);
        return this;
    }

    public MSimpleText between(String startText, String endText) {
        rangeList.clear();
        int startIndex = toString().indexOf(startText) + startText.length() + 1;
        int endIndex = toString().lastIndexOf(endText) - 1;
        Range range = Range.create(startIndex, endIndex);
        rangeList.add(range);
        return this;
    }

    public MSimpleText size(int dp) {
        for (Range range : rangeList) {
            setSpan(new AbsoluteSizeSpan(dp, true), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText scaleSize(int proportion) {
        for (Range range : rangeList) {
            setSpan(new RelativeSizeSpan(proportion), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText bold() {
        for (Range range : rangeList) {
            setSpan(new StyleSpan(Typeface.BOLD), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText italic() {
        for (Range range : rangeList) {
            setSpan(new StyleSpan(Typeface.ITALIC), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText font(String font) {
        for (Range range : rangeList) {
            setSpan(new TypefaceSpan(font), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText strikethrough() {
        for (Range range : rangeList) {
            setSpan(new StrikethroughSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText underline() {
        for (Range range : rangeList) {
            setSpan(new UnderlineSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText background(@ColorRes int colorRes) {
        int color = ContextCompat.getColor(context, colorRes);
        for (Range range : rangeList) {
            setSpan(new BackgroundColorSpan(color), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText background(@ColorRes int colorRes, int radiusDp) {
        int color = ContextCompat.getColor(context, colorRes);
        int radiusPx = Utils.dp2px(context, radiusDp);
        for (Range range : rangeList) {
            setSpan(new RoundedBackgroundSpan(color, textColor, radiusPx), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText textColor(@ColorRes int colorRes) {
        textColor = ContextCompat.getColor(context, colorRes);
        for (Range range : rangeList) {
            setSpan(new ForegroundColorSpan(textColor), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText subscript() {
        for (Range range : rangeList) {
            setSpan(new SubscriptSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText superscript() {
        for (Range range : rangeList) {
            setSpan(new SuperscriptSpan(), range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText url(final String url) {
        for (Range range : rangeList) {
            CustomClickableSpan span = new CustomClickableSpan(
                    subSequence(range.from, range.to),
                    url,
                    range,
                    new com.tencent.qcloud.timchat.utils.richtext.OnTextClickListener() {
                        @Override
                        public void onClicked(CharSequence text, Range range, Object tag) {
                            Utils.openURL(context, url);
                        }
                    });
            setSpan(span, range.from, range.to, SPAN_MODE);
        }
        return this;
    }

    public MSimpleText pressedTextColor(@ColorRes int colorRes) {
        this.pressedTextColor = ContextCompat.getColor(context, colorRes);
        return this;
    }

    public MSimpleText pressedBackground(@ColorRes int colorRes, int radiusDp) {
        this.pressedBackgroundColor = ContextCompat.getColor(context, colorRes);
        this.pressedBackgroundRadius = Utils.dp2px(context, radiusDp);
        return this;
    }

    public MSimpleText pressedBackground(@ColorRes int colorRes) {
        return pressedBackground(colorRes, 0);
    }

    public MSimpleText onClick(final com.tencent.qcloud.timchat.utils.richtext.OnTextClickListener onTextClickListener) {
        for (final Range range : rangeList) {
            CustomClickableSpan span = new CustomClickableSpan(
                    subSequence(range.from, range.to),
                    tagsMap.get(range),
                    range,
                    onTextClickListener);
            setSpan(span, range.from, range.to, SPAN_MODE);
        }

        return this;
    }

    public MSimpleText onLongClick(final OnTextLongClickListener onTextLongClickListener) {
        for (final Range range : rangeList) {
            CustomClickableSpan span = new CustomClickableSpan(
                    subSequence(range.from, range.to),
                    tagsMap.get(range),
                    range,
                    onTextLongClickListener);
            setSpan(span, range.from, range.to, SPAN_MODE);
        }

        return this;
    }

    public MSimpleText tag(Object tag) {
        Range lastRange = rangeList.get(rangeList.size() - 1);
        tagsMap.put(lastRange, tag);
        return this;
    }

    public MSimpleText tags(Object... tags) {
        return tags(Arrays.asList(tags));
    }

    public MSimpleText tags(List<Object> tags) {
        int i = 0;
        for (Object tag : tags) {
            tagsMap.put(rangeList.get(i++), tag);
        }
        return this;
    }

    public void linkify(TextView textView) {
        textView.setHighlightColor(Color.TRANSPARENT);
        textView.setMovementMethod(new LinkTouchMovementMethod(pressedTextColor, pressedBackgroundColor, pressedBackgroundRadius));
    }

    @Deprecated
    public interface OnTextClickListener {
        void onTextClicked(CharSequence text, Range range);
    }

    @Deprecated
    public MSimpleText clickable(@ColorRes int pressedTextColor,
                                 @ColorRes int pressedBackgroundColor,
                                 int pressedBackgroundRadius,
                                 final OnTextClickListener onTextClickListener) {

        this.pressedTextColor = ContextCompat.getColor(context, pressedTextColor);
        this.pressedBackgroundColor = ContextCompat.getColor(context, pressedBackgroundColor);
        this.pressedBackgroundRadius = Utils.dp2px(context, pressedBackgroundRadius);

        for (final Range range : rangeList) {
            ClickableSpan span = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onTextClickListener.onTextClicked(subSequence(range.from, range.to), range);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };
            setSpan(span, range.from, range.to, SPAN_MODE);
        }

        return this;
    }

}

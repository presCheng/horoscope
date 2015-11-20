package com.jingling.practice.horoscope;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Spring on 2015/11/5.
 * 自定义ImageView
 */
public class ResizableImageView extends ImageView {
    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * onMeasure来设置我们的视图的大小，但还有一个疑惑的地方，
     * EXACTLY，AT_MOST，UNSPECIFIED和layout_是如何对应的呢？什么情况下对应什么值呢？
     * MATCH_PARENT对应于EXACTLY，WRAP_CONTENT对应于AT_MOST，
     * 其他情况也对应于EXACTLY，它和MATCH_PARENT的区别在于size值不一样。
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if(d != null){
            // MeasureSpec.getSize(widthMeasureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
            int width = MeasureSpec.getSize(widthMeasureSpec);
            //Math.ceil() 得到不小于他的最小整数
            //获取Drawable d 对象的大小d.getIntrinsicHeight(),d.getIntrinsicWidth()
            int height = (int)Math.ceil((float) width * (float)d.getIntrinsicHeight() / d.getIntrinsicWidth());
            //设置自定义View的大小。详细描述见：http://blog.csdn.net/pi9nc/article/details/18764863
            setMeasuredDimension(width,height);


        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

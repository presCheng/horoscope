package com.jingling.practice.horoscope;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duducat.activeconfig.ActiveConfig;

/**
 * Created by Spring on 2015/11/17.
 */
public class HoroscopeFragment extends Fragment {

    /**
     *
     * @param inflater 布局
     * @param container    容器
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //i是Key后面的数字，实例化fragment后返回的参数
        int i = getArguments().getInt("i") + 1;
        //设置View的布局
        final View view = inflater.inflate(R.layout.fragment_main,container,false);
        /**
         * 该方法用以异步获取在服务器配置的文字项内容，并设置为textView的文本，
         * 如获取失败，textView的文本会设置为defaultValue。
         * 详情见http://www.duducat.com/?article-doc.html
         */
        ActiveConfig.setTextViewWithKey("Key"+i,null,(TextView)view.findViewById(R.id.content));
        ActiveConfig.setImageViewWithKey("Key" + i,null,(ImageView)view.findViewById(R.id.cover));
        ActiveConfig.getImageAsync("Key" + i,new ActiveConfig.AsyncGetImageHandler() {
            /**
             * 异步获取图片，成功设置显示
             * @param drawable
             */
            @Override
            public void OnSuccess(Drawable drawable) {
                ((ImageView) view.findViewById(R.id.cover)).setImageDrawable(drawable);
                view.findViewById(R.id.loading).setVisibility(View.INVISIBLE);
            }

            @Override
            public void OnFailed() {


            }
        });
        /**
         * 长点击时，清除缓存
         */
//        view.findViewById(R.id.content).setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ActiveConfig.clearCache();
//                return true;
//            }
//        });


        return view;
    }



}

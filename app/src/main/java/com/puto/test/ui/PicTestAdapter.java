package com.puto.test.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.puto.test.R;
import com.puto.test.bitmap.ImageDownloader;

/**
 * Created by GZK on 2015/10/9.
 */
public class PicTestAdapter extends BaseAdapter {
    Context context;
    private String[] picStr;
    private LayoutInflater inflater;
    ImageDownloader imageDownloader;
    private Boolean isScroll;

    public PicTestAdapter(Context context, String[] picStr) {
        this.context = context;
        this.picStr = picStr;
        inflater = LayoutInflater.from(context);
        imageDownloader = ImageDownloader.getImageDownloader(context);
    }

    @Override
    public int getCount() {
        return picStr.length;
    }

    @Override
    public Object getItem(int position) {
        return picStr[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("log", "===parent:"+parent+"======position:" + position+"========convertView:"+convertView);
        Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pictest_item, null);
            holder = new Holder();
            holder.testTip = (TextView) convertView.findViewById(R.id.tv_item);
            holder.textImg = (ImageView) convertView.findViewById(R.id.im_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.testTip.setText(position + "");
        holder.textImg.setTag(picStr[position]);
        /*if (!isScroll) {
            Log.e("log", "======isScroll=======" + isScroll);
            imageDownloader.displayAndSave(holder.textImg, picStr[position]);
        }*/

        imageDownloader.displayAndSave(holder.textImg, picStr[position]);

        return convertView;
    }

    public void loadImage(ImageView imageView, int startIndex, int endIndex) {
        for (; startIndex <= endIndex; startIndex++) {
            imageDownloader.displayAndSave(imageView, picStr[startIndex]);
        }
    }

    public void setIsScroll(Boolean isScroll) {
        this.isScroll = isScroll;
    }

    static class Holder {
        TextView testTip;
        ImageView textImg;
    }
}

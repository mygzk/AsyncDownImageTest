package com.puto.test.bitmap;

import android.graphics.Bitmap;

/**
 * Created by GZK on 2015/10/8.
 */
public class ImageDownItem {
    //下载链接
    private String imageUrl;
    //图片宽度
    private int imageWidth;
    //图片高度
    private int imageHeight;
    //处理类型
    private int type;
    //下载后得到的bimap
    private Bitmap bitmap;
    //图片本地保存路径
    private String localURL;
    // 回调函数
    public ImageDownCallback imageDownCallback;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getLocalURL() {
        return localURL;
    }

    public void setLocalURL(String localURL) {
        this.localURL = localURL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ImageDownCallback getImageDownCallback() {
        return imageDownCallback;
    }

    public void setImageDownCallback(ImageDownCallback imageDownCallback) {
        this.imageDownCallback = imageDownCallback;
    }

    @Override
    public String toString() {
        return "ImageDownItem{" +
                "bitmap=" + bitmap +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageWidth=" + imageWidth +
                ", imageHerght=" + imageHeight +
                ", type=" + type +
                ", localURL='" + localURL + '\'' +
                '}';
    }
}

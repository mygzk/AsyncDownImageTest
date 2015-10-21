package com.puto.test.bitmap;

import android.graphics.Bitmap;

/**
 * Created by GZK on 2015/10/8.
 */
public interface ImageDownCallback {
    /**
     * 图片下载完毕后 处理图片
     * @param bitmap 图片
     * @param imageUrl 下载链接
     */
    public void updateImage(Bitmap bitmap,String imageUrl);
}

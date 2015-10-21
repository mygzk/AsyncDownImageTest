package com.puto.test.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;

/**
 * Created by GZK on 2015/10/8.
 */
public class ImageCache {
    public static int cacheSize;

    static {
        //使用最大可用内存的1/8
        cacheSize=(int) Runtime.getRuntime().maxMemory()/8;
    }

    /**
     * 为了加快速度，在内存中开启缓存,最新的LruCache.
     */
    private static LruCache<String, Bitmap> bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
        protected int sizeOf(String key, Bitmap bitmap) {
            return getByteCount(bitmap, Bitmap.CompressFormat.JPEG);
        }
    };

    /**
     * 描述：从缓存中获取这个Bitmap.
     *
     * @param key the key
     * @return the bitmap from mem cache
     */
    public static Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) bitmapCache.get(key);
    }

    /**
     * 描述：增加一个图片到缓存.
     *
     * @param key    一般为一个网络文件的url
     * @param bitmap the bitmap
     */
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (bitmap == null || TextUtils.isEmpty(key)) {
            return;
        }
        if (getBitmapFromMemCache(key) == null) {
            bitmapCache.put(key, bitmap);
        }
    }


    /**
     * 获取Bitmap大小.
     *
     * @param bitmap          the bitmap
     * @param mCompressFormat 图片格式 Bitmap.CompressFormat.JPEG,CompressFormat.PNG
     * @return 图片的大小
     */
    public static int getByteCount(Bitmap bitmap,
                                   Bitmap.CompressFormat mCompressFormat) {
        int size = 0;
        ByteArrayOutputStream output = null;
        try {
            output = new ByteArrayOutputStream();
            bitmap.compress(mCompressFormat, 100, output);
            byte[] result = output.toByteArray();
            size = result.length;
            // bitmap.recycle();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }
}

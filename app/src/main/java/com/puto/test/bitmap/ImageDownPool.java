
package com.puto.test.bitmap;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by GZK on 2015/10/8.
 */
public class ImageDownPool {
    private static final String TAG = "ImageDownloadPool";

    //单例
    private static ImageDownPool imageDownPool = null;
    //固定5个线程
    private static int nThreads = 5;

    //executorService
    private ExecutorService executorService = null;

    public ImageDownPool(int nThreads) {
        executorService = Executors.newFixedThreadPool(nThreads);
    }

    public static ImageDownPool getInstance() {
        if (imageDownPool == null) {
            nThreads = getNumCores();
            //imageDownPool = new ImageDownPool(1);
            imageDownPool = new ImageDownPool(nThreads * 5);
        }
        return imageDownPool;
    }

    private static Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageDownItem imageDownItem = (ImageDownItem) msg.obj;
            imageDownItem.getImageDownCallback().updateImage(imageDownItem.getBitmap(), imageDownItem.getImageUrl());
        }
    };

    public void download(final ImageDownItem imageDownItem) {
        String imageUrl = imageDownItem.getImageUrl();

        if (TextUtils.isEmpty(imageUrl)) {
            Log.e(TAG, "图片下载链接为空");
        } else {
            imageUrl = imageUrl.trim();
        }

        Bitmap bitmap = ImageCache.getBitmapFromMemCache(MD5Util.getEncryptByMD5(imageUrl +
                imageDownItem.getImageWidth() + imageDownItem.getImageHeight()));
        if (bitmap == null) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                      String imageLocalURL = ImageFile.downFileToSD(imageDownItem.getImageUrl());

                        imageDownItem.setLocalURL(imageLocalURL);
                        Bitmap bitmapUrl = ImageFile.getBitmapFromSD(new File(imageDownItem.getLocalURL()),
                                imageDownItem.getType(), imageDownItem.getImageWidth(), imageDownItem.getImageHeight());

                        String md5Str=MD5Util.getEncryptByMD5(imageDownItem.getImageUrl() + imageDownItem.getImageWidth()
                                + imageDownItem.getImageHeight());

                        ImageCache.addBitmapToMemoryCache(MD5Util.getEncryptByMD5(imageDownItem.getImageUrl() +
                                imageDownItem.getImageWidth() + imageDownItem.getImageHeight()), bitmapUrl);
                        Bitmap b=ImageCache.getBitmapFromMemCache(md5Str);


                        imageDownItem.setBitmap(bitmapUrl);
                        if (imageDownItem.getImageDownCallback() != null) {
                            Message msg = imageHandler.obtainMessage();
                            msg.obj = imageDownItem;
                            imageHandler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            imageDownItem.setBitmap(bitmap);
            if (imageDownItem.getImageDownCallback() != null) {
                Message msg = imageHandler.obtainMessage();
                msg.obj = imageDownItem;
                imageHandler.sendMessage(msg);
            }
        }


    }

    /**
     * 描述：立即关闭.
     */
    public void shutdownNow() {
        if (!executorService.isTerminated()) {
            executorService.shutdownNow();
            listenShutdown();
        }

    }

    /**
     * 描述：平滑关闭.
     */
    public void shutdown() {
        if (!executorService.isTerminated()) {
            executorService.shutdown();
            listenShutdown();
        }
    }

    /**
     * 描述：关闭监听.
     */
    public void listenShutdown() {
        try {
            while (!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) {
                Log.d(TAG, "线程池未关闭");
            }
            Log.d(TAG, "线程池已关闭");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    //Check if filename is "cpu", followed by a single digit number
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }

            });
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Default to return 1 core
            e.printStackTrace();
            return 1;
        }
    }
}

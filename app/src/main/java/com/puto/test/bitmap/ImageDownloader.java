package com.puto.test.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by GZK on 2015/10/8.
 */
public class ImageDownloader {

    /**
     * The tag.
     */
    private static String TAG = "AbImageDownloader";
    /**
     * 动画持续时间
     */
    private static final int animationDuration = 2 * 100;

    /**
     * Context.
     */
    private static Context context = null;

    /**
     * 显示的图片的宽.
     */
//    private int width;

    /**
     * 显示的图片的高.
     */
//    private int height;

    /**
     * 图片的处理类型（剪切或者缩放到指定大小，参考AbConstant类）.
     */
    private int type = ImageConstant.ORIGINALIMG;

    /**
     * 图片显示动画.
     */
    private boolean animationEnabled = false;

    /**
     * 显示为下载中的图片.
     */
    private int loadingImage;

    /**
     * 显示下载失败的图片.
     */
    private int errorImage;

    /**
     * 图片未找到的图片.
     */
    private int noImage;

    /**
     * 线程池.
     */
    private ImageDownPool mAbImageDownloadPool = null;

    private static ImageDownloader imageDownloader = null;

    public static ImageDownloader getImageDownloader(Context context) {
        if (imageDownloader == null) {
            imageDownloader = new ImageDownloader(context.getApplicationContext());
        }
        return imageDownloader;
    }

    public ImageDownloader(Context context) {
        this.context = context;
        this.mAbImageDownloadPool = ImageDownPool.getInstance();
    }

    /**
     * 显示这个图片并保存默认路径
     *
     * @param imageView 显示view
     * @param url       网络url
     */
    public void displayAndSave(final ImageView imageView, String url) {
        displayAndSave(imageView, url, null, 0, 0);
    }

    /**
     * 显示这个图片并保存为指定本地url.
     *
     * @param imageView 显示view
     * @param url       网络url
     * @param localURL  报讯路径
     */
    public void displayAndSave(final ImageView imageView, String url, String localURL) {
        displayAndSave(imageView, url, localURL, 0, 0);
    }

    public int getNoImage() {
        return noImage;
    }

    public void setNoImage(int noImage) {
        this.noImage = noImage;
    }

    public int getErrorImage() {
        return errorImage;
    }

    public void setErrorImage(int errorImage) {
        this.errorImage = errorImage;
    }

    public int getLoadingImage() {
        return loadingImage;
    }

    public void setLoadingImage(int loadingImage) {
        this.loadingImage = loadingImage;
    }

    /**
     * 显示这个图片并保存为指定本地url.
     *
     * @param imageView 显得的View
     * @param url       网络url
     * @param localURL
     * @param width
     * @param height
     */
    public void displayAndSave(final ImageView imageView, String url, String localURL, int width, int height) {

        //先显示加载中
        if (TextUtils.isEmpty(url)) {
            if (noImage != 0) {
                imageView.setImageResource(noImage);
            }
            return;
        }

        //设置下载项
        ImageDownItem item = new ImageDownItem();
        //设置显示的大小
        item.setImageWidth(width);
        item.setImageHeight(height);
        //设置为缩放
        int type;
        if (width == 0 || height == 0) {
            type = ImageConstant.ORIGINALIMG;
        } else {
            type = ImageConstant.SCALEIMG;
        }
        item.setType(type);
        item.setImageUrl(url);

        if (localURL == null || localURL.length() == 0) {
//            item.localURL = FileUtil.getCacheFolder();
            localURL = ImageFile.getDownPathImageDir();
        }

        item.setLocalURL(localURL);


        Bitmap bitmap = ImageCache.getBitmapFromMemCache(MD5Util.getEncryptByMD5(url + item.getImageWidth()
                + item.getImageHeight()));
        item.setBitmap(bitmap);

        if (item.getBitmap() == null) {
            imageView.setImageResource(loadingImage);
            //下载完成后更新界面
            item.imageDownCallback = new ImageDownCallback() {
                @Override
                public void updateImage(Bitmap bitmap, String imageUrl) {
                    if (imageView.getTag() != null && imageView.getTag().equals(imageUrl) && bitmap != null) {
                        if (animationEnabled) {
                            TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                                    new ColorDrawable(android.R.color.transparent),
                                    new BitmapDrawable(context.getResources(), bitmap)});
                            imageView.setImageDrawable(td);
                            td.startTransition(animationDuration);
                        } else {
                            imageView.setImageBitmap(bitmap);
                        }


                    }else {
                     if (errorImage != 0) {
                            imageView.setImageResource(errorImage);
                        }
                    }
                }
            };

            mAbImageDownloadPool.download(item);
        } else if (animationEnabled) {
            TransitionDrawable td = new TransitionDrawable(
                    new Drawable[]{new ColorDrawable(android.R.color.transparent),
                            new BitmapDrawable(context.getResources(), item.getBitmap())});
            imageView.setImageDrawable(td);
            td.startTransition(animationDuration);
        } else {
            imageView.setImageBitmap(item.getBitmap());
        }
    }
}

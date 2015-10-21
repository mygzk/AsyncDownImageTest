package com.puto.test.bitmap;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by GZK on 2015/10/8.
 */
public class ImageFile {

    private static final String TAG = "imagefile";

    /**
     * 默认下载文件地址.
     */
    private static String downPathRootDir = File.separator + "download" + File.separator;

    /**
     * 默认下载图片文件地址.
     */
    private static String downPathImageDir = downPathRootDir + "cache_images" + File.separator;


    /**
     * MB  单位B
     */
    private static int MB = 1024 * 1024;

    /**
     * 缓存文件夹的大小200M  单位B
     */
    private static int cacheSize = 200 * MB;

    /**
     * 剩余空间大于200M才使用缓存
     */
    private static int freeSdSpaceNeededToCache = 200 * MB;

    /**
     * 缓存空间当前的大小，临时
     */
    private static int dirSize = -1;

    /**
     * 根据下载链接获取文件名
     *
     * @param str
     * @return
     */
    public static String getFileName(String strurl) {
        String filename = null;

        if(TextUtils.isEmpty(strurl)){
            return null;
        }

        filename = strurl.substring(strurl.lastIndexOf("/") + 1);

        return filename;

    }

    public static String downFileToSD(String url) {


        InputStream in = null;
        FileOutputStream fileOutputStream = null;
        HttpURLConnection con = null;
        String downFilePath = null;
        File file = null;
        if(TextUtils.isEmpty(url)){
            return null;
        }
        String name=getFileName(url);
        try {
            if (!isCanUseSD()) {
                return null;
            }
            File path = Environment.getExternalStorageDirectory();
            File fileDirectory = new File(path.getAbsolutePath() + downPathImageDir);
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    return null;
                }
            }
            file = new File(fileDirectory, name);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return null;
                }
            } else {
                //文件已经存在
                if (file.length() != 0) {
                    return file.getPath();
                }
            }

            downFilePath = file.getPath();
            URL mUrl = new URL(url);
            con = (HttpURLConnection) mUrl.openConnection();
            con.connect();
            in = con.getInputStream();
            fileOutputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int temp = 0;
            while ((temp = in.read(b)) != -1) {
                fileOutputStream.write(b, 0, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                //检查文件大小,如果文件为0B说明网络不好没有下载成功，要将建立的空文件删除
                if (file.length() == 0) {
                    file.delete();
                }
                //检查空间将很久未使用的文件删除
                removeCache();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return downFilePath;
    }

    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算存储目录下的文件大小，
     * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
     * 那么删除40%最近没有被使用的文件
     */
    public static boolean removeCache() {

        try {
            if (!isCanUseSD()) {
                return false;
            }

            File dir = new File(downPathImageDir);
            File[] files = dir.listFiles();
            if (files == null) {
                return true;
            }
            if (dirSize == -1) {
                dirSize += 1;
                for (int i = 0; i < files.length; i++) {
                    dirSize += files[i].length();
                }
            }

            //当前大小大于预定缓存空间
            if (dirSize > cacheSize) {
                int removeFactor = (int) ((0.4 * files.length) + 1);
                Arrays.sort(files, new FileLastModifSort());
                for (int i = 0; i < removeFactor; i++) {
                    dirSize -= files[i].length();
                    files[i].delete();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 根据文件的最后修改时间进行排序
     */
    public static class FileLastModifSort implements Comparator<File> {
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }


    /**
     * 描述：通过文件的本地地址从SD卡读取图片.
     *
     * @param file      the file
     * @param type      图片的处理类型（剪切或者缩放到指定大小，参考AbConstant类）
     *                  如果设置为原图，则后边参数无效，得到原图
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getBitmapFromSD(File file, int type, int newWidth, int newHeight) {
        Bitmap bit = null;
        try {
            //SD卡是否存在
            if (!isCanUseSD()) {
                return null;
            }

            if (type != ImageConstant.ORIGINALIMG && (newWidth <= 0 || newHeight <= 0)) {
                throw new IllegalArgumentException("缩放和裁剪图片的宽高设置不能小于0");
            }


            //文件是否存在
            if (!file.exists()) {
                return null;
            }
            //文件存在
            if (type == ImageConstant.CUTIMG) {
                bit = ImageUtil.cutImg(file, newWidth, newHeight);
            } else if (type == ImageConstant.SCALEIMG) {
                bit = ImageUtil.scaleImg(file, newWidth, newHeight);
            } else {
                bit = ImageUtil.originalImg(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bit;
    }


    public static String getDownPathImageDir() {
        return downPathImageDir;
    }

    public static void setDownPathImageDir(String downPathImageDir) {
        ImageFile.downPathImageDir = downPathImageDir;
    }

    public static String getDownPathRootDir() {
        return downPathRootDir;
    }

    public static void setDownPathRootDir(String downPathRootDir) {
        ImageFile.downPathRootDir = downPathRootDir;
    }
}

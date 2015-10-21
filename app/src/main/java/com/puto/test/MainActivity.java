package com.puto.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.puto.test.bitmap.ImageCache;
import com.puto.test.bitmap.ImageDownloader;
import com.puto.test.bitmap.MD5Util;
import com.puto.test.ui.ImageListTestActivity;


public class MainActivity extends Activity {
    ImageView imagetest;
    Button btnTest;
    ImageDownloader imageDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageDownloader = ImageDownloader.getImageDownloader(this);
        imageDownloader.setErrorImage(R.mipmap.error_image);
        imageDownloader.setLoadingImage(R.mipmap.loading_image);
        imageDownloader.setNoImage(R.mipmap.no_image);

        initview();
    }

    String[] strUrl = {"http://img2.3lian.com/img2007/19/33/005.jpg", "http://i6.topit.me/6/5d/45/1131907198420455d6o.jpg", "http://image.photophoto.cn/nm-6/018/030/0180300244.jpg", "http://pic.nipic.com/2007-11-09/2007119121849495_2.jpg",
            "http://img1.3lian.com/img2008/06/019/ych.jpg", "http://img1.3lian.com/img2008/06/019/ych.jpg", "http://pic12.nipic.com/20110209/2929643_150952237191_2.jpg",
            "http://zx.kaitao.cn/UserFiles/Image/beijingtupian6.jpg", "http://pic25.nipic.com/20121126/8305779_171431388000_2.jpg", "http://pic15.nipic.com/20110624/7348760_084532494318_2.jpg",
            "http://bj.ruideppt.com/upfile/proimage/20114291828135932.jpg", "http://bj.ruideppt.com/upfile/proimage/20114291282611171.jpg", "http://bj.ruideppt.com/upfile/proimage/201142918164560826.jpg",
            "http://pic.nipic.com/2007-11-08/200711819133664_2.jpg", "http://www.xxjxsj.cn/article/UploadPic/2009-6/20096150194754601.jpg", "http://pic28.nipic.com/20130401/9252150_195455485000_2.jpg",
            "http://pic1.nipic.com/2009-02-19/200921922311483_2.jpg", "http://pic28.nipic.com/20130425/9252150_172633927000_2.jpg", "http://picm.photophoto.cn/012/077/018/0770180177.jpg",
            "http://pica.nipic.com/2007-11-08/2007118193744143_2.jpg", "http://pic1.ooopic.com/uploadfilepic/sheji/2010-01-15/OOOPIC_1982zpwang407_2010011599c5bddc9477931d.jpg", "http://pic.nipic.com/2008-01-05/200815191150944_2.jpg"};

    private void initview() {
        imagetest = (ImageView) findViewById(R.id.imagetest);
        final String url = "http://img2.3lian.com/img2007/19/33/005.jpg";

        imagetest.setTag(url);
        //imageDownloader.displayAndSave(imagetest, url);


        btnTest = (Button) findViewById(R.id.btntest);
        btnTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              /*  String md5str = MD5Util.getEncryptByMD5(url + 0 + 0);
                Bitmap bt = ImageCache.getBitmapFromMemCache(md5str);
                Log.e("log", "=========bt=========" + bt);*/

                Intent it=new Intent(MainActivity.this, ImageListTestActivity.class);
                startActivity(it);
            }
        });
    }

}

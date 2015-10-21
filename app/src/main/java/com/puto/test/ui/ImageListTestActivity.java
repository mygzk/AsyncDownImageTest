package com.puto.test.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.puto.test.R;
import com.puto.test.bitmap.ImageDownloader;

public class ImageListTestActivity extends Activity {

    ListView lsPicTest;
    PicTestAdapter picAdapter;
    private static final String TAG=ImageListTestActivity.class.getSimpleName();
    private int fistItemIndex;


    String[] strUrl = {"http://img2.3lian.com/img2007/19/33/005.jpg", "http://image.photophoto.cn/nm-6/018/030/0180300244.jpg", "http://pic.nipic.com/2007-11-09/2007119121849495_2.jpg",
            "http://img1.3lian.com/img2008/06/019/ych.jpg", "http://pic12.nipic.com/20110209/2929643_150952237191_2.jpg",
            "http://zx.kaitao.cn/UserFiles/Image/beijingtupian6.jpg", "http://pic25.nipic.com/20121126/8305779_171431388000_2.jpg", "http://pic15.nipic.com/20110624/7348760_084532494318_2.jpg",
            "http://bj.ruideppt.com/upfile/proimage/20114291828135932.jpg", "http://bj.ruideppt.com/upfile/proimage/20114291282611171.jpg", "http://bj.ruideppt.com/upfile/proimage/201142918164560826.jpg",
            "http://pic.nipic.com/2007-11-08/200711819133664_2.jpg", "http://www.xxjxsj.cn/article/UploadPic/2009-6/20096150194754601.jpg", "http://pic28.nipic.com/20130401/9252150_195455485000_2.jpg",
            "http://pic1.nipic.com/2009-02-19/200921922311483_2.jpg", "http://pic28.nipic.com/20130425/9252150_172633927000_2.jpg", "http://picm.photophoto.cn/012/077/018/0770180177.jpg",
            "http://pica.nipic.com/2007-11-08/2007118193744143_2.jpg", "http://pic1.ooopic.com/uploadfilepic/sheji/2010-01-15/OOOPIC_1982zpwang407_2010011599c5bddc9477931d.jpg", "http://pic.nipic.com/2008-01-05/200815191150944_2.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list_test);

        lsPicTest=(ListView)findViewById(R.id.ls_pictest);
        picAdapter=new PicTestAdapter(this,strUrl);
        lsPicTest.setAdapter(picAdapter);




/*        lsPicTest.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e(TAG, "=======onScrollStateChanged====view==:" + view);
                Log.e(TAG, "=======onScrollStateChanged====scrollState==:" + scrollState);
                if (scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    picAdapter.setIsScroll(false);
                    int count=view.getChildCount();
                    for(int i=0;i<count;i++){
                        ImageView iv=(ImageView)view.findViewWithTag(strUrl[fistItemIndex]);
                        ImageDownloader.getImageDownloader(ImageListTestActivity.this).displayAndSave(iv, strUrl[fistItemIndex]);
                        fistItemIndex++;
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                picAdapter.setIsScroll(true);
                fistItemIndex=firstVisibleItem;

            }
        });*/



    }

}

package co.example.hzq.jokertwo.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import co.example.hzq.jokertwo.Media.Uri2Path;
import co.example.hzq.jokertwo.NormalProgress.NormalProgress2;
import co.example.hzq.jokertwo.R;

public class ImageDisplayActivity extends BaseActivity {
    private static final String TAG = "ImageDisplayActivity";
    Bitmap bitmap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        Uri data = getIntent().getData();
        String path = null;
        if (data!=null){
            path = Uri2Path.getRealPathFromUri(this, data);
        }else{
            path = getFilesDir()+"/pictures/file.jpg";
        }

        start(path);
        imageView = (ImageView) findViewById(R.id.image_display);
        bitmap = BitmapFactory.decodeFile(path).copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    private void start(String path){
        NormalProgress2 normalProgress2 = new NormalProgress2(handler, this);
        normalProgress2.uploadFile(path);

    }

    public void changeUI(int[] face_positions){
        Log.e(TAG, "changeUI: " );
        Canvas canvas=new Canvas(bitmap);//创建一个空画布，并给画布设置位图
        Paint p=new Paint();
        p.setColor(0xffff0000);//设置画笔颜色
        p.setAntiAlias(true);//抗锯齿
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(new Rect(face_positions[0],face_positions[1],face_positions[0]+face_positions[2],face_positions[1]+face_positions[3]),p);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 涉及到相机 999
     * 涉及到相册 998
     * 涉及到list 997
     *通过face++返回的user_id，找到需要改变状态的学生
     */
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 997:

                    break;
                case 123:
                    Bundle data = msg.getData();
                    int[] face_positions = data.getIntArray("face_position");
                    changeUI(face_positions);
                    break;

            }
        }
    };

}

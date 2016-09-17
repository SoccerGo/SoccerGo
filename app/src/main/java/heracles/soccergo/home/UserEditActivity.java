package heracles.soccergo.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.User;

public class UserEditActivity extends AppCompatActivity
{
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "soc_img.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 32;
    private static int output_Y = 32;

    private SimpleDraweeView sdvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        if(User.mUserInfo.getHead_link()!=null)
            sdvUser.setImageURI(CONSTANT.HOST+ User.mUserInfo.getHead_link());
        sdvUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                choseHeadImageFromGallery();
            }
        });
    }

    private void getWidget()
    {
        sdvUser = (SimpleDraweeView) findViewById(R.id.sdvUser);
    }

    // 从本地相册选取图片作为头像
    public void choseHeadImageFromGallery()
    {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");//选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        //如果你想在Activity中得到新打开Activity关闭后返回的数据，
        //你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    public void choseHeadImageFromCameraCapture()
    {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard())
        {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent)
    {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED)
        {//取消
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        switch (requestCode)
        {
            case CODE_GALLERY_REQUEST://如果是来自本地的Q
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard())
                {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else
                {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG).show();
                }
                break;

            case CODE_RESULT_REQUEST:
                if (intent != null)
                {
                    setImageToHeadView(intent);//设置图片框
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面
        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent)
    {
        Bundle extras = intent.getExtras();

        if (extras != null)
        {
            Bitmap photo = extras.getParcelable("data");
            sdvUser.setImageBitmap(photo);
            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            File nf = new File(Environment.getExternalStorageDirectory() + "/SoccerGoIcon");
            nf.mkdir();

            //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            String fileUrl = Environment.getExternalStorageDirectory() + "/SoccerGoIcon";

            String fileName = String.valueOf(System.currentTimeMillis())+".jpg";
            File f = new File(fileUrl, fileName);
            FileOutputStream out = null;

            try
            {//打开输出流 将图片数据填入文件中
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                try
                {
                    out.flush();
                    out.close();
                    Intent intent1 = new Intent();
                    intent1.putExtra("file",fileUrl+fileName);
                    setResult(CONSTANT.RETIMG,intent1);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard()
    {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
        {
            // 有存储的SDCard
            return true;
        } else
        {
            return false;
        }
    }
}

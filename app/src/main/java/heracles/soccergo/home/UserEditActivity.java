package heracles.soccergo.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.HttpConnectionUtil;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.User;

public class UserEditActivity extends AppCompatActivity
{
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 200;
    private static int output_Y = 200;

    private SimpleDraweeView sdvUser = null;
    private EditText etChineseName,etEnglishName,etBirthdate,etAge,etNumber;
    private Spinner spiGender,spiPosition;
    private Button btnSumbit;

    private File tempFile;

    private ProgressDialog progressDialog;

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
        Intent intent = getIntent();
        if(User.mUserInfo.getHead_link()!=null)
            sdvUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/user/"+User.mUserInfo.getHead_link()));
        etChineseName.setText(intent.getStringExtra("chineseName"));
        etEnglishName.setText(intent.getStringExtra("englishName"));
        String age = intent.getStringExtra("age");
        if(age!=null)
            etAge.setText(age.split("岁")[0]);
        String num = intent.getStringExtra("num");
        if(!num.isEmpty())
            etNumber.setText(num.split("：")[1]);
        String birthdaty = intent.getStringExtra("birthdaty");
        if(!birthdaty.isEmpty())
            etBirthdate.setText(birthdaty.split("：")[1]);
        String sex = intent.getStringExtra("sex");
        if(sex.equals("男"))
            spiGender.setSelection(0);
        else if(sex.equals("女"))
            spiGender.setSelection(1);
        String position = intent.getStringExtra("position");
        if(position.equals("守门员"))
            spiPosition.setSelection(0);
        else if(position.equals("后卫"))
            spiPosition.setSelection(1);
        else if(position.equals("中场"))
            spiPosition.setSelection(2);
        else if(position.equals("前锋"))
            spiPosition.setSelection(3);

        sdvUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                choseHeadImageFromGallery();
            }
        });

        etBirthdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(UserEditActivity.this, new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth)
                    {
                        // TODO Auto-generated method stub
                        Calendar c2 = Calendar.getInstance();
                        c2.set(year, monthOfYear, dayOfMonth);

                        etBirthdate.setText(year + "-" + (monthOfYear + 1)
                                + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSumbit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String sAge = etAge.getText().toString();
                String sNum = etNumber.getText().toString();
                String chineseName = etChineseName.getText().toString();
                String englishName = etEnglishName.getText().toString();
                String birthdate = etBirthdate.getText().toString();
                String sex = spiGender.getSelectedItem().toString();
                String position = spiPosition.getSelectedItem().toString();
                if(sAge.isEmpty())
                    etAge.setError("请输入年龄");
                else if(sNum.isEmpty())
                    etNumber.setError("请输入球衣号");
                else if(chineseName.isEmpty())
                    etChineseName.setError("请输入中文名");
                else if(englishName.isEmpty())
                    etEnglishName.setError("请输入英文名");
                else if(birthdate.isEmpty())
                    etBirthdate.setError("请选择出生年月日");
                else if(sex.isEmpty())
                    Toast.makeText(UserEditActivity.this,"请选择性别",Toast.LENGTH_LONG).show();
                else if(position.isEmpty())
                    Toast.makeText(UserEditActivity.this,"请选择常用位置",Toast.LENGTH_LONG).show();
                else
                {
                    int age = Integer.parseInt(sAge);
                    int num = Integer.parseInt(sNum);
                    if(age>100||age<0)
                        etAge.setError("请输入1-100之间的整数");
                    else if(num>100||num<0)
                        etNumber.setError("请输入1-100之间的整数");
                    else
                    {
                        progressDialog.show();

                        Map<String,Object> map = new HashMap<>();
                        map.put("chinese_name",chineseName);
                        map.put("english_name",englishName);
                        map.put("sex",sex);
                        map.put("age",sAge);
                        map.put("birthday",birthdate);
                        map.put("shirt_num",sNum);
                        map.put("location",position);
                        map.put("user_id", User.mUserInfo.getUser_id());

                        new Submit(map).execute();
                    }
                }
            }
        });

    }

    private void getWidget()
    {
        sdvUser = (SimpleDraweeView) findViewById(R.id.sdvUser);
        btnSumbit = (Button) findViewById(R.id.btnSumbit);
        etChineseName = (EditText) findViewById(R.id.etChineseName);
        etEnglishName = (EditText) findViewById(R.id.etEnglishName);
        etBirthdate = (EditText) findViewById(R.id.etBirthdate);
        etAge = (EditText) findViewById(R.id.etAge);
        etNumber = (EditText) findViewById(R.id.etNumber);
        spiGender = (Spinner) findViewById(R.id.spiGender);
        spiPosition = (Spinner) findViewById(R.id.spiPosition);

        progressDialog = new ProgressDialog(this);
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery()
    {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture()
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
        {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode)
        {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard())
                {
                    tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else
                {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null)
                {
                    setImageToHeadView(intent);
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

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        if (hasSdcard())
        {
            tempFile = new File(
                    Environment.getExternalStorageDirectory(),
                    IMAGE_FILE_NAME);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFile));
        } else
        {
            Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                    .show();
        }

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

    // 异步获取
    class Submit extends AsyncTask<Void, Integer, Void>
    {
        Map<String,Object> result;
        Map<String,Object> datas;

        public Submit(Map<String,Object> datas)
        {
            this.datas = datas;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                result = HttpConnectionUtil.doPostPicture(CONSTANT.HOST+"app/user/perfectUser",datas,tempFile);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            progressDialog.close();
            finish();
        }
    }
}

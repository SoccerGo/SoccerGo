package heracles.soccergo.community;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import heracles.soccergo.ImagePickerView.GlideImageLoader;
import heracles.soccergo.ImagePickerView.ImagePickerAdapter;
import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.HttpConnectionUtil;
import heracles.soccergo.Tools.User;

public class PublishActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener
{
    private EditText etContent;
    private CheckBox cbShowClub;
    private RecyclerView recyclerView;
    private Button btnSend;
    private ImageView ivBack;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;               //允许选择图片最大数

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xel_mine_feedback);
        initImagePicker();
        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String content = etContent.getText().toString();
                if (content.isEmpty())
                    etContent.setError("请输入内容");
                else
                {
                    if (selImageList.size() == 0)
                    {
                        Log.d("send", "没有图片");
                        Map<String,Object> map = new HashMap<>();
                        map.put("content",content);
                        map.put("address",User.mProvince+User.mCity);
                        map.put("user_id", User.mUserInfo.getUser_id());
                        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d1=new Date(time);
                        String t1=format.format(d1);
                        map.put("f_time", t1);
                        new SendMsg(CONSTANT.HOST+"Social/sendSocial",map,null).execute();
                    }
                    else {
                        Log.d("send", "有图片");
                        Map<String,Object> map = new HashMap<>();
                        map.put("content",content);
                        map.put("address",User.mProvince+User.mCity);
                        map.put("user_id", User.mUserInfo.getUser_id());
                        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d1=new Date(time);
                        String t1=format.format(d1);
                        Log.d("时间",t1);
                        map.put("f_time", t1);
                        String filePath = selImageList.get(0).path;
                        Log.d("图片",filePath);
                        File file = new File(filePath);
                        new SendMsg(CONSTANT.HOST+"Social/sendSocial",map,file).execute();
                    }
                }
            }
        });

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getWidget()
    {
        etContent = (EditText) findViewById(R.id.etContent);
        cbShowClub = (CheckBox) findViewById(R.id.cbShowClub);
        recyclerView = (RecyclerView) findViewById(R.id.rvAlterIcon);
        btnSend = (Button) findViewById(R.id.btnSend);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    private void initImagePicker()
    {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }


    @Override
    public void onItemClick(View view, int position)
    {
        switch (position)
        {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS)
        {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT)
            {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                if (selImageList.isEmpty())
                {
                } else
                {
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK)
        {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW)
            {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                if (selImageList.isEmpty())
                {
                } else
                {
                }
            }
        }
    }

    // 异步获取
    class SendMsg extends AsyncTask<Void, Integer, Void>
    {
        private boolean state;
        private String urlStr;
        private Map<String, Object> paramMap;
        private File pictureFile;

        public SendMsg(String urlStr, Map<String, Object> paramMap, File pictureFile)
        {
            this.urlStr = urlStr;
            this.paramMap = paramMap;
            this.pictureFile = pictureFile;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                state = (boolean) HttpConnectionUtil.doPostPicture(urlStr,paramMap,pictureFile).get("ret");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(state)
            {
                Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_LONG).show();
                finish();
            }
            else
                Toast.makeText(PublishActivity.this,"发布失败",Toast.LENGTH_LONG).show();
        }
    }
}

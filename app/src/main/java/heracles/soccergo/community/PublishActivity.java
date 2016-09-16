package heracles.soccergo.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import heracles.soccergo.ImagePickerView.GlideImageLoader;
import heracles.soccergo.ImagePickerView.ImagePickerAdapter;
import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.GetLocalImage;
import heracles.soccergo.Tools.GetLocalImageDialog;

public class PublishActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener
{
    private EditText etTitle, etContent;
    private ImageView ivAddImage;
    private CheckBox cbShowClub;
    private RelativeLayout layoutVisable;
    private Button btnPublish;
    private GetLocalImageDialog getLocalImageDialog;



    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    String content = null;

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
   //     initWidget();
    }

//    private void initWidget()
//    {
//        getWidget();
//        setWidget();
//    }

    private void setWidget()
    {
        ivAddImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getLocalImageDialog = new GetLocalImageDialog(PublishActivity.this);
                Intent intent = new Intent(PublishActivity.this, GetLocalImage.class);
                intent.putExtra("type", "fromGallery");
                startActivityForResult(intent, CONSTANT.GETIMG);
            }
        });
    }

    private void getWidget()
    {
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        ivAddImage = (ImageView) findViewById(R.id.ivAddImage);
        cbShowClub = (CheckBox) findViewById(R.id.cbShowClub);
        layoutVisable = (RelativeLayout) findViewById(R.id.layoutVisable);
        btnPublish = (Button) findViewById(R.id.btnPublish);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        Log.d("fileUrl",data.getStringExtra("file"));
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode)
//        {
//            case CONSTANT.GETIMG:
//                Log.d("fileUrl",data.getStringExtra("file"));
//                break;
//        }
//    }

    private void initImagePicker() {
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

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                if (selImageList.isEmpty()) {
                    //setImageItemsRequest(100);
                } else {
                    //setImageItemsRequest(200);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                //setImageItemsRequest(200);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                if (selImageList.isEmpty()) {
                    //setImageItemsRequest(100);
                } else {
                    //setImageItemsRequest(200);
                }
            }
        }
    }
}

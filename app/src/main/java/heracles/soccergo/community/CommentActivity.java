package heracles.soccergo.community;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.HttpConnectionUtil;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;

public class CommentActivity extends AppCompatActivity
{
    private SimpleDraweeView sdvUser;
    private TextView tvUser;
    private TextView tvTime;
    private TextView tvContent;
    private SimpleDraweeView sdvContentImg;
    private TextView tvFrom;
    private Button btnSend;
    private EditText etComment;
    private RecyclerView rvPingLun;
    private List<Comments_User> datas;
    private RVPingLunAdapter rvPingLunAdapter;

    private String friendId;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
        new GetPingLun().execute();
    }

    private void setWidget()
    {
        Intent intent = getIntent();
        friendId = intent.getStringExtra("friendId");
        if(intent.getStringExtra("pic").isEmpty())
            sdvContentImg.setVisibility(View.GONE);
        else
            sdvContentImg.setImageURI(Uri.parse(intent.getStringExtra("pic")));
        if(!intent.getStringExtra("head_link").isEmpty())
            sdvUser.setImageURI(Uri.parse(intent.getStringExtra("head_link")));
        tvUser.setText(intent.getStringExtra("name"));
        content = intent.getStringExtra("content");
        tvContent.setText(content);
        tvTime.setText(intent.getStringExtra("time"));
        tvFrom.setText(intent.getStringExtra("from"));
        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Test.flag)
                    Log.d("发布评论","OK");
                content = etComment.getText().toString();
                if(content.isEmpty())
                    etComment.setError("请输入内容");
                else
                {
                    new SendPingLun().execute();
                }
            }
        });

        //初始化适配器以及添加监听
        datas = new ArrayList<>();
        rvPingLunAdapter = new RVPingLunAdapter(this, datas);
        rvPingLun.setAdapter(rvPingLunAdapter);
        rvPingLun.setLayoutManager(new LinearLayoutManager(this));
        rvPingLun.setItemAnimator(new DefaultItemAnimator());
    }

    private void getWidget()
    {
        sdvUser = (SimpleDraweeView) findViewById(R.id.sdvUser);
        tvUser = (TextView) findViewById(R.id.tvUser);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvContent = (TextView) findViewById(R.id.tvContent);
        sdvContentImg = (SimpleDraweeView) findViewById(R.id.sdvContentImg);
        tvFrom = (TextView) findViewById(R.id.tvFrom);
        btnSend = (Button) findViewById(R.id.btnSend);
        etComment = (EditText) findViewById(R.id.etComment);
        rvPingLun = (RecyclerView) findViewById(R.id.rvPingLun);
    }

    // 异步获取
    class SendPingLun extends AsyncTask<Void, Integer, Void>
    {
        private boolean ret;
        @Override
        protected Void doInBackground(Void... params)
        {
            Map<String,Object> map = new HashMap<>();
            map.put("friends_id",friendId);
            map.put("content", content);
            map.put("user_name", User.mUserInfo.getUser_id());
            try
            {
                ret = (boolean) HttpConnectionUtil.doPostPicture(CONSTANT.HOST+"Social/addComments",map,null).get("ret");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(ret)
            {
                Toast.makeText(CommentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(CommentActivity.this,"评论失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 异步获取
    class GetPingLun extends AsyncTask<Void, Integer, Void>
    {
        private boolean ret;
        private Map<String,Object> result;
        @Override
        protected Void doInBackground(Void... params)
        {
            Map<String,Object> map = new HashMap<>();
            map.put("friends_id",friendId);
            try
            {
                result =  HttpConnectionUtil.doPostPicture(CONSTANT.HOST+"Social/findComments",map,null);
                ret = (boolean) result.get("ret");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(ret)
            {
                Toast.makeText(CommentActivity.this,"读取评论成功",Toast.LENGTH_SHORT).show();
                datas =  JSON.parseArray((String) result.get("data"), Comments_User.class);
                if(Test.flag)
                    Log.d("datas",datas.toString());
                rvPingLunAdapter.changeData(datas);
            }
            else {
                Toast.makeText(CommentActivity.this,"读取评论失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

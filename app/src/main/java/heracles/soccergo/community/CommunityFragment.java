package heracles.soccergo.community;


import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.Friends_User;
import heracles.soccergo.Tools.HttpConnectionUtil;
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.race.HoldRaceActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment
{
    private ImageView ivAdd;
    private RecyclerView rvCommunity;
    private RVCommunityAdapter rvCommunityAdapter;
    private List<Friends_User> datas;
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        progressDialog = new ProgressDialog(getContext());
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        ivAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Dialog dialog = new Dialog(getContext(), R.style.Dialog_Alpha_Notitle);
                dialog.setContentView(R.layout.dialog_ivadd);
                LinearLayout layoutPublish = (LinearLayout) dialog.findViewById(R.id.layoutPublish);
                LinearLayout layoutHoldRace = (LinearLayout) dialog.findViewById(R.id.layoutHoldRace);
                layoutPublish.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getActivity(), PublishActivity.class);
                        startActivity(intent);
                    }
                });
                layoutHoldRace.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getActivity(), HoldRaceActivity.class);
                        startActivity(intent);
                    }
                });
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = -300;
                dialogWindow.setAttributes(lp);
                dialog.show();
            }
        });

        //初始化适配器以及添加监听
        datas = new ArrayList<>();
        rvCommunityAdapter = new RVCommunityAdapter(getContext(), datas);
        rvCommunityAdapter.setOnItemClickListener(new RVCommunityAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                if(Test.flag)
                    Log.d("评论","ok");
                Friends_User friends_user = datas.get(position);
                Intent intent = new Intent(getContext(),CommentActivity.class);
                intent.putExtra("name",friends_user.getUser_name());
                intent.putExtra("time",format.format(friends_user.getF_time()));
                intent.putExtra("content",friends_user.getContent());
                intent.putExtra("from",friends_user.getAddress());
                intent.putExtra("friendId",friends_user.getFriends_id());
                if(friends_user.getHead_link()!=null)
                    intent.putExtra("head_link",CONSTANT.HOST+"resources/upload/image/social/"+friends_user.getHead_link());
                else
                    intent.putExtra("head_link","");
                if(friends_user.getPic()!=null)
                {
                    intent.putExtra("pic",CONSTANT.HOST+"resources/upload/image/social/"+friends_user.getPic());
                }
                else
                {
                    intent.putExtra("pic","");
                }
                startActivity(intent);
            }

            @Override
            public void onLikeClick(View view, int position)
            {
                Log.d("点赞", "OK");
                new DianZan(view,position).execute();
            }
        });
        rvCommunity.setAdapter(rvCommunityAdapter);
        rvCommunity.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCommunity.setItemAnimator(new DefaultItemAnimator());
    }

    private void getWidget()
    {
        ivAdd = (ImageView) getActivity().findViewById(R.id.ivAdd);
        rvCommunity = (RecyclerView) getActivity().findViewById(R.id.rvCommunity);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        progressDialog.show();
        new GetFriendsInfo().execute();
    }

    // 异步获取
    class GetFriendsInfo extends AsyncTask<Void, Integer, Void>
    {
        private String url = CONSTANT.HOST + "Social/selectSocial";

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                // 连接web,提交帐号密码
                URL httpUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(5000);

                //读取服务器返回结果
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer result = new StringBuffer();
                String str;
                while ((str = reader.readLine()) != null)
                {
                    result.append(str);
                }
                if (Test.flag)
                    Log.d("friends", result.toString());

                //解析返回值
                final JSONObject jsonObject = new JSONObject(result.toString());
                int ret = jsonObject.getInt("success");
                switch (ret)
                {
                    case CONSTANT.SUCCESS:
                        datas = JSON.parseArray(jsonObject.getString("data"), Friends_User.class);
                        break;
                    case CONSTANT.ERROR:

                        break;
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (ProtocolException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (datas.size() != 0)
            {
                Log.d("datas", datas.toString());
            }
            rvCommunityAdapter.changeData(datas);
            progressDialog.close();
        }
    }

    // 异步获取
    class DianZan extends AsyncTask<Void, Integer, Void>
    {
        private ImageView imageView;
        private int position;
        private boolean ret;

        public DianZan(View view, int position)
        {
            imageView = (ImageView) view.findViewById(R.id.ivAdd);
            this.position = position;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("friends_id", datas.get(position).getFriends_id());
            try
            {
                ret = (boolean) HttpConnectionUtil.doPostPicture(CONSTANT.HOST + "Social/addNum", map, null).get("ret");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (ret)
            {
                progressDialog.show();
                new GetFriendsInfo().execute();
            } else
            {
                Toast.makeText(getContext(),"点赞失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

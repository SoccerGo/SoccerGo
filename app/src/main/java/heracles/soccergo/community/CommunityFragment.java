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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.HttpConnectionUtil;
import heracles.soccergo.Tools.User;
import heracles.soccergo.race.HoldRaceActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment
{
    private ImageView ivAdd;
    private RecyclerView rvCommunity;
    private RVCommunityAdapter rvCommunityAdapter;
    private List<Map<String, Object>> list;

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
                layoutPublish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),PublishActivity.class);
                        startActivity(intent);
                    }
                });
                layoutHoldRace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        rvCommunityAdapter = new RVCommunityAdapter(getContext(), initList());
        rvCommunity.setAdapter(rvCommunityAdapter);
        rvCommunity.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCommunity.setItemAnimator(new DefaultItemAnimator());
    }

    private List<Map<String, Object>> initList()
    {
        list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("tvUser", "灰常得流弊");
        map.put("tvTime", "4天前");
        map.put("tvContent", "今天一起来踢足球吧！");
        map.put("tvFrom", "东软足球俱乐部");
        list.add(map);
        map = new HashMap<>();
        map.put("tvUser", "灰常得流弊");
        map.put("tvTime", "4天前");
        map.put("tvContent", "今天一起来踢足球吧！");
        map.put("tvFrom", "东软足球俱乐部");
        list.add(map);
        return list;
    }

    private void getWidget()
    {
        ivAdd = (ImageView) getActivity().findViewById(R.id.ivAdd);
        rvCommunity = (RecyclerView) getActivity().findViewById(R.id.rvCommunity);
    }

    // 异步获取
    class GetRaceInfo extends AsyncTask<Void, Integer, Void>
    {
        private String url = CONSTANT.HOST + "Game/sendSocial";
        private String city = "大连";

        @Override
        protected Void doInBackground(Void... params)
        {
            final Map<String,Object> paramMap = new HashMap<String, Object>(); //文本数据全部添加到Map里
            paramMap.put("address","大连");
            paramMap.put("content", "傻逼你好，傻逼你好。傻逼你好，傻逼你好。傻逼你好，傻逼你好。傻逼你好，傻逼你好。");
            paramMap.put("user_id", User.mUserInfo.getUser_id());

            String path = "********"; //此处写上要上传的文件在系统中的路径
            final File pictureFile = new File(path); //通过路径获取文件

            try
            {
                HttpConnectionUtil.doPostPicture(url, paramMap, pictureFile);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}

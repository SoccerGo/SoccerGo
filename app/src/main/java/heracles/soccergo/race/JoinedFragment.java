package heracles.soccergo.race;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import heracles.soccergo.Tools.ProgressDialog;
import heracles.soccergo.Tools.Test;
import heracles.soccergo.Tools.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedFragment extends Fragment
{
    private List<Map<String, Object>> list;
    private ListView lvJoined;
    private List<Game> games;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_joined, container, false);
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
        progressDialog.show();
        new GetRaceInfo().execute();
    }

    private void getWidget()
    {
        progressDialog = new ProgressDialog(getActivity());
        lvJoined = (ListView) getActivity().findViewById(R.id.lvJoined);
    }

    private void setWidget()
    {

    }

    private void initList()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        list = new ArrayList<>();
        for (Game game : games)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("title", game.getGame_name());
            map.put("local", game.getGame_address());
            map.put("size", game.getGame_standard()+"人制");
            map.put("cost", game.getCost()+"元/人");
            map.put("time", format.format(game.getGame_date()));
            map.put("id", game.getGame_id());
            list.add(map);
        }
    }

    // 异步获取
    class GetRaceInfo extends AsyncTask<Void, Integer, Void>
    {
        private String url = CONSTANT.HOST + "Game/findUserAddGame";
        private String city = "大连";

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                // 连接web,提交帐号密码
                URL httpUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(5000);
                OutputStream out = httpURLConnection.getOutputStream();
                final String content = "user_id=" + User.mUserInfo.getUser_id();
                out.write(content.getBytes());

                //读取服务器返回结果
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer result = new StringBuffer();
                String str;
                while ((str = reader.readLine()) != null)
                {
                    result.append(str);
                }
                if (Test.flag)
                    Log.d("joinedFragment", result.toString());

                //解析返回值，判断是否登入成功
                final JSONObject jsonObject = new JSONObject(result.toString());
                int ret = jsonObject.getInt("success");
                switch (ret)
                {
                    case CONSTANT.SUCCESS:
                        games = JSON.parseArray(jsonObject.getString("data"), Game.class);
                        initList();
                        break;
                    case CONSTANT.ERROR:
                        Toast.makeText(getActivity(), jsonObject.getString("error"), Toast.LENGTH_LONG).show();
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

            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),list,R.layout.item_joined_race_listview,
                    new String[]{"title","local","size","cost","time"},
                    new int[]{R.id.tvItemTitle,R.id.tvItemLocale,R.id.tvItemSize,R.id.tvItemCost,R.id.tvItemTime});
            lvJoined.setAdapter(simpleAdapter);
            progressDialog.close();
        }
    }

}

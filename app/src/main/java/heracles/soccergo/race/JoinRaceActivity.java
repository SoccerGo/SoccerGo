package heracles.soccergo.race;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class JoinRaceActivity extends AppCompatActivity
{
    private Button btnHoldRace;
    private RecyclerView rvJoinRace;
    private RVJoinRaceAdapter joinRaceAdapter;
    private List<Map<String, Object>> list;

    private List<Game> games;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_race);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void getWidget()
    {
        btnHoldRace = (Button) findViewById(R.id.btnHoldRace);
        rvJoinRace = (RecyclerView) findViewById(R.id.rvJoinRace);
        progressDialog = new ProgressDialog(JoinRaceActivity.this);
    }

    private void setWidget()
    {
        btnHoldRace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(JoinRaceActivity.this, HoldRaceActivity.class);
                startActivity(intent);
            }
        });
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
            map.put("size", game.getGame_standard());
            map.put("cost", game.getCost());
            map.put("time", format.format(game.getGame_date()));
            map.put("id", game.getGame_id());
            list.add(map);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        progressDialog.show();
        new GetRaceInfo().execute();
    }

    // 异步获取
    class GetRaceInfo extends AsyncTask<Void, Integer, Void>
    {
        private String url = CONSTANT.HOST + "Game/findGame";
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
                final String content = "city=" + city+"&p_user_id=" + User.mUserInfo.getUser_id();
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
                    Log.d("result", result.toString());

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
                        Toast.makeText(JoinRaceActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
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
            //初始化适配器以及添加监听
            joinRaceAdapter = new RVJoinRaceAdapter(JoinRaceActivity.this, list);
            rvJoinRace.setAdapter(joinRaceAdapter);
            rvJoinRace.setLayoutManager(new LinearLayoutManager(JoinRaceActivity.this));
            rvJoinRace.setItemAnimator(new DefaultItemAnimator());
            joinRaceAdapter.setOnItemBtnClickListener(new RVJoinRaceAdapter.OnItemBtnClickListener()
            {
                @Override
                public void onItemBtnClick(View view, int position)
                {
                    Toast.makeText(JoinRaceActivity.this, (String) list.get(position).get("title"), Toast.LENGTH_SHORT).show();
                    new JoinRaceThread(JoinRaceActivity.this, handler, User.mUserInfo.getUser_id(), String.valueOf(list.get(position).get("id"))).start();
                }
            });
            progressDialog.close();
        }
    }
}

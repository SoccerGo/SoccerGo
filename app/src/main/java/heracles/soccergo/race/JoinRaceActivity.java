package heracles.soccergo.race;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;

public class JoinRaceActivity extends AppCompatActivity
{
    private Button btnHoldRace;
    private RecyclerView rvJoinRace;
    private RVJoinRaceAdapter joinRaceAdapter;
    private List<Map<String, Object>> list;

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
        initList();

        //初始化适配器以及添加监听
        joinRaceAdapter = new RVJoinRaceAdapter(JoinRaceActivity.this,list);
        rvJoinRace.setAdapter(joinRaceAdapter);
        rvJoinRace.setLayoutManager(new LinearLayoutManager(this));
        rvJoinRace.setItemAnimator(new DefaultItemAnimator());
        joinRaceAdapter.setOnItemBtnClickListener(new RVJoinRaceAdapter.OnItemBtnClickListener()
        {
            @Override
            public void onItemBtnClick(View view, int position)
            {
                Toast.makeText(JoinRaceActivity.this,(String)list.get(position).get("title"),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initList()
    {
        list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("title", "快来约战！");
        map.put("locale", "东软学院三期球场");
        map.put("size", "五人制");
        map.put("cost", "免费");
        map.put("time", "7月17日 17:00");
        list.add(map);

        map = new HashMap<>();
        map.put("title", "来战！");
        map.put("locale", "东软学院五期球场");
        map.put("size", "十人制");
        map.put("cost", "100元");
        map.put("time", "7月11日 13:00");
        list.add(map);

        map = new HashMap<>();
        map.put("title", "跪地求虐！");
        map.put("locale", "东软学院四期球场");
        map.put("size", "五人制");
        map.put("cost", "50元");
        map.put("time", "7月27日 18:00");
        list.add(map);
    }
}

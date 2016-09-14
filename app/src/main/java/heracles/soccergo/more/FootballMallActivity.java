package heracles.soccergo.more;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;

public class FootballMallActivity extends AppCompatActivity
{
    ListView lvFootballMall;
    List<Map<String,Object>> list;
    Button btnFootball,btnClothe,btnShoes,btnTrainTools;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_mall);
        initWidget();
    }

    private void initWidget() {
        getWidget();
        setWidget();
    }

    private void setWidget() {
        lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this));
        btnFootball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this));
            }
        });
        btnClothe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this));
            }
        });
        btnShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this));
            }
        });
        btnTrainTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this));
            }
        });
    }

    private void getWidget() {
        lvFootballMall = (ListView) findViewById(R.id.lvFootballMall);
        btnFootball = (Button) findViewById(R.id.btnFootball);
        btnClothe = (Button) findViewById(R.id.btnShoes);
        btnTrainTools = (Button) findViewById(R.id.btnTrainTools);
        btnShoes = (Button) findViewById(R.id.btnShoes);
    }

    private class FootballMallAdapter extends BaseAdapter{
        Context context;

        public FootballMallAdapter(Context context) {
            this.context = context;
            initList();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_football_mall,null);
            }
            TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
            TextView tvItemContent = (TextView) convertView.findViewById(R.id.tvItemContent);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);

            tvItemName.setText(list.get(position).get("itemName").toString());
            tvItemContent.setText(list.get(position).get("itemContent").toString());
            tvPrice.setText(list.get(position).get("price").toString());
            return convertView;
        }
    }

    private void initList() {
        list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("itemName","球衣1");
        map.put("itemContent","本球衣非常的amazing，必买!!!");
        map.put("price","3110");
        list.add(map);

        map = new HashMap<>();
        map.put("itemName","球衣1");
        map.put("itemContent","本球衣非常的amazing，必买!!!");
        map.put("price","3110");
        list.add(map);

        map = new HashMap<>();
        map.put("itemName","球衣1");
        map.put("itemContent","本球衣非常的amazing，必买!!!");
        map.put("price","3110");
        list.add(map);
    }

}

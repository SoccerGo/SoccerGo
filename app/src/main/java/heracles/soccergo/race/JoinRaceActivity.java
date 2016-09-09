package heracles.soccergo.race;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import heracles.soccergo.Tools.Test;

public class JoinRaceActivity extends AppCompatActivity {
    private Button btnHoldRace,btnItemJoin;
    private ListView lvJoinRace;
    private List<Map<String,Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_race);

        initWidget();
    }
    private void initWidget(){
        getWidget();
        setWidget();
    }

    private void getWidget() {
        btnHoldRace = (Button) findViewById(R.id.btnHoldRace);
        lvJoinRace = (ListView) findViewById(R.id.lvJoinRace);
    }

    private void setWidget() {
        btnHoldRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinRaceActivity.this,HoldRaceActivity.class);
                startActivity(intent);
            }
        });
        initList();
        lvJoinRace.setAdapter(new JoinRaceAdapter(this));
    }

    private void initList() {
        list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("title","快来约战！");
        map.put("locale","东软学院三期球场");
        map.put("size","五人制");
        map.put("cost","免费");
        map.put("time","7月17日 17:00");
        list.add(map);

        map = new HashMap<>();
        map.put("title","");
        map.put("locale","");
        map.put("size","");
        map.put("cost","");
        map.put("time","");
        list.add(map);

        map.put("title","");
        map.put("locale","");
        map.put("size","");
        map.put("cost","");
        map.put("time","");
        list.add(map);
    }

    class JoinRaceAdapter extends BaseAdapter{
        private Context context;

        private JoinRaceAdapter(Context context){
            this.context = context;
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
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_join_race_listview,null);
            }
            if(position % 2 != 0){
                if(Test.flag)
                    Log.d("color", String.valueOf(getResources().getColor(R.color.colorDeepGreen)));
                convertView.setBackgroundColor(getResources().getColor(R.color.colorDeepGreen));

            }
            TextView tvItemTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);
            TextView tvItemLocale = (TextView) convertView.findViewById(R.id.tvItemLocale);
            TextView tvItemSize = (TextView) convertView.findViewById(R.id.tvItemSize);
            TextView tvItemCost = (TextView) convertView.findViewById(R.id.tvItemCost);
            TextView tvItemTime = (TextView) convertView.findViewById(R.id.tvItemTime);

            tvItemTitle.setText(list.get(position).get("title").toString());
            tvItemLocale.setText(list.get(position).get("locale").toString());
            tvItemSize.setText(list.get(position).get("size").toString());
            tvItemCost.setText(list.get(position).get("cost").toString());
            tvItemTime.setText(list.get(position).get("time").toString());

            return convertView;
        }
    }
}

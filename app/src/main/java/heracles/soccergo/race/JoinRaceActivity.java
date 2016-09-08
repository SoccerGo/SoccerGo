package heracles.soccergo.race;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class JoinRaceActivity extends AppCompatActivity {
    private Button btnHoldRace,btnItemJoin;
    private ListView lvJoinRace;
    private TextView tvItemTitle,tvItemLocale,tvItemSize,tvItemCost,tvItemTime;
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
        btnHoldRace = (Button) findViewById(R.id.btnJoinRace);
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
            return null;
        }
    }
}

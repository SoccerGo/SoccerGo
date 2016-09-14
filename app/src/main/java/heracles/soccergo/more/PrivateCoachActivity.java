package heracles.soccergo.more;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class PrivateCoachActivity extends AppCompatActivity {
    ListView lvPrivateCoach;
    List<Map<String,Object>> list;
    Button btnPro,btnParttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_private_coach);
        super.onCreate(savedInstanceState);
        initWidget();
    }

    private void initWidget() {
        getWidget();
        setWidget();
    }

    private void setWidget() {
        lvPrivateCoach.setAdapter(new PrivateCoachAdapter(PrivateCoachActivity.this));
        btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvPrivateCoach.setAdapter(new PrivateCoachAdapter(PrivateCoachActivity.this));
            }
        });
        btnParttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvPrivateCoach.setAdapter(new PrivateCoachAdapter(PrivateCoachActivity.this));
            }
        });
    }

    private void getWidget() {
        lvPrivateCoach = (ListView) findViewById(R.id.lvPrivateCoach);
        btnPro = (Button) findViewById(R.id.btnPro);
        btnParttime = (Button) findViewById(R.id.btnParttime);
    }

    private class PrivateCoachAdapter extends BaseAdapter{
        Context context;

        public PrivateCoachAdapter(Context context) {
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
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_private_coach,null);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvAge = (TextView) convertView.findViewById(R.id.tvAge);
            TextView tvJob = (TextView) convertView.findViewById(R.id.tvJob);
            TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            TextView tvRate = (TextView) convertView.findViewById(R.id.tvRate);
            TextView tvTel = (TextView) convertView.findViewById(R.id.tvTel);

            tvName.setText(list.get(position).get("name").toString());
            tvAge.setText(list.get(position).get("age").toString());
            tvJob.setText(list.get(position).get("job").toString());
            tvContent.setText(list.get(position).get("content").toString());
            tvPrice.setText(list.get(position).get("price").toString());
            tvRate.setText(list.get(position).get("rate").toString());
            tvTel.setText(list.get(position).get("tel").toString());

            return convertView;
        }
    }

    private void initList() {
        list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name","杨凯");
        map.put("age","60");
        map.put("job","大连东软信息学院体育教师");
        map.put("content","很帅，很酷，你值得拥有的教练");
        map.put("price","998");
        map.put("rate","4.98");
        map.put("tel","84830000");
        list.add(map);

        map = new HashMap<>();
        map.put("name","杨凯");
        map.put("age","60");
        map.put("job","大连东软信息学院体育教师");
        map.put("content","很帅，很酷，你值得拥有的教练");
        map.put("price","998");
        map.put("rate","4.98");
        map.put("tel","84830000");
        list.add(map);
    }
}

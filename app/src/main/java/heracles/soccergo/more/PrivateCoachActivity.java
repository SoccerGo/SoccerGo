package heracles.soccergo.more;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;

public class PrivateCoachActivity extends AppCompatActivity {
    private ListView lvPrivateCoach;
    private List<Map<String,Object>> list;
    private Button btnPro,btnParttime;
    private ImageView ivBack;

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
        lvPrivateCoach.setAdapter(new PrivateCoachAdapter(PrivateCoachActivity.this,1));
        btnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvPrivateCoach.setAdapter(new PrivateCoachAdapter(PrivateCoachActivity.this,1));
            }
        });
        btnParttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvPrivateCoach.setAdapter(new PrivateCoachAdapter(PrivateCoachActivity.this,2));
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getWidget() {
        lvPrivateCoach = (ListView) findViewById(R.id.lvPrivateCoach);
        btnPro = (Button) findViewById(R.id.btnPro);
        btnParttime = (Button) findViewById(R.id.btnParttime);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    private class PrivateCoachAdapter extends BaseAdapter{
        Context context;

        public PrivateCoachAdapter(Context context,int coachType) {
            this.context = context;
            initList(coachType);
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
            ImageView tvPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

            tvName.setText(list.get(position).get("name").toString());
            tvAge.setText(list.get(position).get("age").toString());
            tvJob.setText(list.get(position).get("job").toString());
            tvContent.setText(list.get(position).get("content").toString());
            tvPrice.setText(list.get(position).get("price").toString());
            tvRate.setText(list.get(position).get("rate").toString());
            tvTel.setText(list.get(position).get("tel").toString());
            tvPhoto.setImageDrawable(getDrawable((Integer) list.get(position).get("img")));

            return convertView;
        }
    }

    private void initList(int coachType) {
        list = new ArrayList<>();
        Map<String, Object> map;
        switch (coachType) {
            case 1:
                map = new HashMap<>();
                map.put("name", "高波");
                map.put("age", "31");
                map.put("job", "大连恒隆俱乐部认证教练");
                map.put("content", "曾在大连恒隆俱乐部担任前锋，屡获佳绩，战功显赫，退役后成为大连恒隆俱乐部认证教练");
                map.put("price", "890");
                map.put("rate", "4.93");
                map.put("tel", "18600340517");
                map.put("img",R.drawable.coach_gaobo);
                list.add(map);

                map = new HashMap<>();
                map.put("name", "张联国");
                map.put("age", "36");
                map.put("job", "大连阿尔滨俱乐部认证教练");
                map.put("content", "曾荣获大连市青年足球赛最佳射手，退役后加入大连阿尔滨俱乐部，现任阿尔滨俱乐部认证教练，" +
                        "具有4年足球学院教学经验");
                map.put("price", "1013");
                map.put("rate", "4.96");
                map.put("tel", "18600340517");
                map.put("img",R.drawable.coach_zhanglianguo);
                list.add(map);
                break;

            case 2:
                map = new HashMap<>();
                map.put("name", "杨凯");
                map.put("age", "29");
                map.put("job", "大连东北财经大学体育教师");
                map.put("content", "平时十分热爱足球运动，现在在东财担任一个体育老师，希望在周末" +
                        "时间能教教孩子们踢球，同时也......");
                map.put("price", "520");
                map.put("rate", "4.85");
                map.put("tel", "13342278939");
                map.put("img",R.drawable.coach_yangkai);
                list.add(map);

                map = new HashMap<>();
                map.put("name", "熊超");
                map.put("age", "35");
                map.put("job", "自由职业者，足球爱好者");
                map.put("content", "因为家里开着商店，所以平时有比较充足的时间踢球看球，是一名足球" +
                        "铁杆粉，现在一直在大连恒隆俱乐部踢球......");
                map.put("price", "456");
                map.put("rate", "4.92");
                map.put("tel", "18504281320");
                map.put("img",R.drawable.coach_xiongchao);
                list.add(map);

                map = new HashMap<>();
                map.put("name", "柳临");
                map.put("age", "24");
                map.put("job", "大连东北财经大学体育特长生");
                map.put("content", "十分热爱体育运动，尤其是足球，对足球有较多的了解，希望能在课余时间" +
                        "喜欢和孩子们一起，教他们踢球......");
                map.put("price", "453");
                map.put("rate", "4.85");
                map.put("tel", "18942866020");
                map.put("img",R.drawable.coach_liulin);
                list.add(map);
                break;
        }
    }
}

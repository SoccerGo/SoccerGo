package heracles.soccergo.club;

import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.User;

public class ClubMoreActivity extends AppCompatActivity
{
    ListView lvClubMore;
    List<Map<String, Object>> list;

    private TextView tvCityLocate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_more);

        initWidget();
    }

    private void initWidget()
    {
        getWidget();
        setWidget();
    }

    private void setWidget()
    {
        lvClubMore.setAdapter(new ClubMoreAdapter(ClubMoreActivity.this));
        tvCityLocate.setText(User.mCity);
    }

    private void getWidget()
    {
        lvClubMore = (ListView) findViewById(R.id.lvClubMore);
        tvCityLocate = (TextView) findViewById(R.id.tvCityLocate);
    }

    class ClubMoreAdapter extends BaseAdapter
    {
        Context context;

        public ClubMoreAdapter(Context context)
        {
            this.context = context;
            initList();
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_club_more, null);
            }
            ImageView ivClubImage = (ImageView) convertView.findViewById(R.id.ivClubImage);
            TextView tvClubName = (TextView) convertView.findViewById(R.id.tvClubName);
            Button button = (Button) convertView.findViewById(R.id.btnJoinClub);

            ivClubImage.setImageDrawable(getDrawable((Integer) list.get(position).get("img")));
            tvClubName.setText(list.get(position).get("name").toString());
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(ClubMoreActivity.this,"已提交申请",Toast.LENGTH_LONG).show();
                }
            });

            return convertView;
        }
    }

    private void initList()
    {
        list = new ArrayList<>();
        Map<String, Object> map;

        map = new HashMap<>();
        map.put("name", "大连ASIA俱乐部");
        map.put("img", R.drawable.club_asia);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "哈诺伊俱乐部");
        map.put("img", R.drawable.club_hanoi);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "菲戈俱乐部");
        map.put("img", R.drawable.club_feige);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "大连娃娃头俱乐部");
        map.put("img", R.drawable.club_wawatou);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "特刃加努俱乐部");
        map.put("img", R.drawable.club_terengganu);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "擦枪走火俱乐部");
        map.put("img", R.drawable.club_caqiangzouhuo);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "足球小将俱乐部");
        map.put("img", R.drawable.club_zuqiuxiaojiang);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "讷马俱乐部");
        map.put("img", R.drawable.club_nama);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "大连甘井子区俱乐部");
        map.put("img", R.drawable.club_ganjingzi);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "绿茵96俱乐部");
        map.put("img", R.drawable.club_96);
        list.add(map);
    }
}

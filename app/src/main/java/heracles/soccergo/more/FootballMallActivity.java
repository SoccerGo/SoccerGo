package heracles.soccergo.more;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class FootballMallActivity extends AppCompatActivity
{
    private ListView lvFootballMall;
    private List<Map<String,Object>> list;
    private Button btnFootball,btnClothe,btnShoes,btnTrainTools;
    private static int NUM_FOOTBALL = 1, NUM_CLOTHE = 2, NUM_SHOES = 3, NUM_TRAIN_TOOLS = 4;
    private ImageView ivBack;
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
        lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this,NUM_FOOTBALL));
        btnFootball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this,NUM_FOOTBALL));
            }
        });
        btnClothe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this,NUM_CLOTHE));
            }
        });
        btnShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this,NUM_SHOES));
            }
        });
        btnTrainTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvFootballMall.setAdapter(new FootballMallAdapter(FootballMallActivity.this,NUM_TRAIN_TOOLS));
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
        lvFootballMall = (ListView) findViewById(R.id.lvFootballMall);
        btnFootball = (Button) findViewById(R.id.btnFootball);
        btnClothe = (Button) findViewById(R.id.btnClothe);
        btnTrainTools = (Button) findViewById(R.id.btnTrainTools);
        btnShoes = (Button) findViewById(R.id.btnShoes);
        ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    private class FootballMallAdapter extends BaseAdapter{
        Context context;

        public FootballMallAdapter(Context context,int mallNum) {
            this.context = context;
            initList(mallNum);
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
            ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

            tvItemName.setText(list.get(position).get("itemName").toString());
            tvItemContent.setText(list.get(position).get("itemContent").toString());
            tvPrice.setText(list.get(position).get("price").toString());
            ivImage.setImageDrawable(getDrawable((Integer) list.get(position).get("img")));
            return convertView;
        }
    }

    private void initList(int mallNum) {
        list = new ArrayList<>();
        Map<String,Object> map;
        switch (mallNum){
            case 1:
                map = new HashMap<>();
                map.put("itemName","耐克足球5号NIKE英超足球");
                map.put("itemContent","12块几何球皮设计，Aerowtrac凹槽，球面图案绚丽，助你一路驰骋绿茵");
                map.put("price","2900");
                map.put("img",R.drawable.soccer_1);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","火车头足球精品 4号足球");
                map.put("itemContent","精品款系列，全面升级，进口材质，更耐磨、弹性更足");
                map.put("price","1960");
                map.put("img",R.drawable.soccer_2);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","战舰正品 训练比赛用球");
                map.put("itemContent","标准5号球，超软不伤脚，酷炫；送气针网兜、气筒 ");
                map.put("price","580");
                map.put("img",R.drawable.soccer_3);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","阿迪达斯 欧冠比赛足球");
                map.put("itemContent","秉持追求完美理念，与世界各地著名运动员及科研机构紧密结合，改进足球运动装备" +
                        "，提升运动表现，挑战运动极限");
                map.put("price","5980");
                map.put("img",R.drawable.soccer_4);
                list.add(map);
                break;

            case 2:
                map = new HashMap<>();
                map.put("itemName","阿迪达斯儿童球衣（名字印刷）");
                map.put("itemContent","可任意选球队，球衣号，印刷名字服务");
                map.put("price","2899");
                map.put("img",R.drawable.clothes_1);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","麦凯乐耐克儿童球衣定制");
                map.put("itemContent","100%绝对正品足球球衣，大连麦凯乐实体店直属店铺");
                map.put("price","3500");
                map.put("img",R.drawable.clothes_2);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","正品李宁足球队球衣（儿童款）");
                map.put("itemContent","李宁足球球衣，可选择自己喜爱的球队，尺码自选，定制专属球衣");
                map.put("price","2650");
                map.put("img",R.drawable.clothes_3);
                list.add(map);
                break;

            case 3:
                map = new HashMap<>();
                map.put("itemName","MERCURIAL 男子人造草地足球鞋");
                map.put("itemContent","采用简洁的纹理鞋面，结合富有创意的鞋钉排布，在人造场地为你打造" +
                        "非凡触球感与抓地力");
                map.put("price","11900");
                map.put("img",R.drawable.shoes_1);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","阿迪达斯 TF碎钉足球鞋");
                map.put("itemContent","经典配色，防滑耐磨，基础训练，天然橡胶碎钉外底");
                map.put("price","4780");
                map.put("img",R.drawable.shoes_2);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","361度足球鞋 2016夏季碎钉鞋");
                map.put("itemContent","鞋身大面积舒适透气，层次感车线设计，IP中底轻质缓震，为激烈的足球" +
                        "运动增添保护");
                map.put("price","2890");
                map.put("img",R.drawable.shoes_3);
                list.add(map);
                break;

            case 4:
                map = new HashMap<>();
                map.put("itemName","LIVEUP 标志碟障碍锥");
                map.put("itemContent","材质柔软，防踩踏，减少因踩踏异物造成的脚部损伤");
                map.put("price","1580");
                map.put("img",R.drawable.train_item_1);
                list.add(map);

                map = new HashMap<>();
                map.put("itemName","踢球训练运动用品颠球袋");
                map.put("itemContent","材料厚实，捡球省时省力");
                map.put("price","2899");
                map.put("img",R.drawable.train_item_2);
                list.add(map);
                break;
        }
    }

}

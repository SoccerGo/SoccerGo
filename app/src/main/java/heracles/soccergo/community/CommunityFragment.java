package heracles.soccergo.community;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heracles.soccergo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {
    ImageView ivAdd;
    ListView lvCommunity;
    List<Map<String,Object>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWeight();
    }

    private void initWeight() {
        getWeight();
        setWeight();
    }

    private void setWeight() {
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext(),R.style.Dialog_Alpha_Notitle);
                dialog.setContentView(R.layout.dialog_ivadd);
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = -300;
                dialogWindow.setAttributes(lp);
                dialog.show();
            }
        });
        lvCommunity.setAdapter(new CommunityAdapter(getContext()));
    }

    private List<Map<String, Object>> initList() {
        list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("tvUser","灰常得流弊");
        map.put("tvTime","4天前");
        map.put("tvContent","今天一起来踢足球吧！");
        map.put("tvFrom","东软足球俱乐部");
        list.add(map);
        map = new HashMap<>();
        map.put("tvUser","灰常得流弊");
        map.put("tvTime","4天前");
        map.put("tvContent","今天一起来踢足球吧！");
        map.put("tvFrom","东软足球俱乐部");
        list.add(map);
        return list;
    }

    private void getWeight() {
        ivAdd = (ImageView) getActivity().findViewById(R.id.ivAdd);
        lvCommunity = (ListView) getActivity().findViewById(R.id.lvCommunity);
    }

    private class CommunityAdapter extends BaseAdapter{
        Context context;
        public CommunityAdapter(Context context) {
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
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_community,null);
            }
            TextView tvUser = (TextView) convertView.findViewById(R.id.tvUser);
            TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            TextView tvFrom = (TextView) convertView.findViewById(R.id.tvFrom);

            tvUser.setText(list.get(position).get("tvUser").toString());
            tvTime.setText(list.get(position).get("tvTime").toString());
            tvContent.setText(list.get(position).get("tvContent").toString());
            tvFrom.setText(list.get(position).get("tvFrom").toString());
            return convertView;
        }
    }

}

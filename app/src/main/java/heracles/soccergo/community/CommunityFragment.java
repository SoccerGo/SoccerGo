package heracles.soccergo.community;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

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
                Dialog dialog = new Dialog(getContext(),android.R.style.Theme_Holo_Light_Dialog);
                dialog.show();
            }
        });
    }

    private void getWeight() {
        ivAdd = (ImageView) getActivity().findViewById(R.id.ivAdd);
        lvCommunity = (ListView) getActivity().findViewById(R.id.lvCommunity);
    }

}

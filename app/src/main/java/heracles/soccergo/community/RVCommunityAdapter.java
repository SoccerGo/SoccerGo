package heracles.soccergo.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

import heracles.soccergo.R;

/**
 * Created by 10539 on 2016/9/13.
 */
public class RVCommunityAdapter extends RecyclerView.Adapter<CommunityViewHolder>
{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Map<String, Object>> mDatas;

    public RVCommunityAdapter(Context context, List<Map<String, Object>> datas)
    {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.item_community, parent, false);
        CommunityViewHolder communityViewHolder = new CommunityViewHolder(view);
        return communityViewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position)
    {
        holder.tvUser.setText(mDatas.get(position).get("tvUser").toString());
        holder.tvTime.setText(mDatas.get(position).get("tvTime").toString());
        holder.tvContent.setText(mDatas.get(position).get("tvContent").toString());
        holder.tvFrom.setText(mDatas.get(position).get("tvFrom").toString());
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
}

class CommunityViewHolder extends RecyclerView.ViewHolder
{
    public SimpleDraweeView sdvUser;
    public TextView tvUser;
    public TextView tvContent;
    public ImageView ivImage;
    public TextView tvFrom;
    public TextView tvLikeCount;
    public TextView tvTime;

    public CommunityViewHolder(View itemView)
    {
        super(itemView);

        sdvUser = (SimpleDraweeView) itemView.findViewById(R.id.ivUser);
        tvUser = (TextView) itemView.findViewById(R.id.tvUser);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        tvFrom = (TextView) itemView.findViewById(R.id.tvFrom);
        tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
    }
}

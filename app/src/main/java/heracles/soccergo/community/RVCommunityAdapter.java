package heracles.soccergo.community;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.List;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;
import heracles.soccergo.Tools.Friends_User;

/**
 * Created by 10539 on 2016/9/13.
 */
public class RVCommunityAdapter extends RecyclerView.Adapter<CommunityViewHolder>
{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Friends_User> datas;
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public RVCommunityAdapter(Context context, List<Friends_User> datas)
    {
        this.mContext = context;
        this.datas = datas;
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
        Friends_User friends_user = datas.get(position);
        holder.tvUser.setText(friends_user.getUser_name());
        holder.tvTime.setText(format.format(friends_user.getF_time()));
        holder.tvContent.setText(friends_user.getContent());
        holder.tvFrom.setText(friends_user.getAddress());
        if(friends_user.getHead_link()!=null)
            holder.sdvUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/social/"+friends_user.getHead_link()));
        if(friends_user.getPic()!=null)
        {
            holder.sdvContentImg.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/social/"+friends_user.getPic()));
        }
        else
        {
            holder.sdvContentImg.setVisibility(View.GONE);
        }
        holder.tvLikeCount.setText("（"+friends_user.getNum()+"）");
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    public void changeData(List<Friends_User> datas)
    {
        this.datas = datas;
    }
}

class CommunityViewHolder extends RecyclerView.ViewHolder
{
    public SimpleDraweeView sdvUser;
    public TextView tvUser;
    public TextView tvContent;
    public TextView tvFrom;
    public TextView tvLikeCount;
    public TextView tvTime;
    public SimpleDraweeView sdvContentImg;

    public CommunityViewHolder(View itemView)
    {
        super(itemView);

        sdvUser = (SimpleDraweeView) itemView.findViewById(R.id.sdvUser);
        tvUser = (TextView) itemView.findViewById(R.id.tvUser);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        tvFrom = (TextView) itemView.findViewById(R.id.tvFrom);
        tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        sdvContentImg = (SimpleDraweeView) itemView.findViewById(R.id.sdvContentImg);
    }
}

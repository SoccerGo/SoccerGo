package heracles.soccergo.community;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import heracles.soccergo.R;
import heracles.soccergo.Tools.CONSTANT;

/**
 * Created by 10539 on 2016/9/17.
 */
public class RVPingLunAdapter extends RecyclerView.Adapter<PingLunViewHolder>
{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Comments_User> datas;

    public RVPingLunAdapter(Context context, List<Comments_User> datas)
    {
        this.mContext = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PingLunViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.item_comment, parent, false);
        PingLunViewHolder pingLunViewHolder = new PingLunViewHolder(view);
        return pingLunViewHolder;
    }

    public void changeData(List<Comments_User> datas)
    {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PingLunViewHolder holder, int position)
    {
        Comments_User comments_user = datas.get(position);
        if(comments_user.getHead_link()!=null)
            holder.sdvUser.setImageURI(Uri.parse(CONSTANT.HOST+"resources/upload/image/social/"+comments_user.getHead_link()));
        holder.tvContent.setText(comments_user.getC_content());
        holder.tvUser.setText(comments_user.getUser_name());
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }
}

class PingLunViewHolder extends RecyclerView.ViewHolder
{
    public SimpleDraweeView sdvUser;
    public TextView tvUser;
    public TextView tvContent;

    public PingLunViewHolder(View itemView)
    {
        super(itemView);

        sdvUser = (SimpleDraweeView) itemView.findViewById(R.id.sdvUser);
        tvUser = (TextView) itemView.findViewById(R.id.tvUser);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);
    }
}

package heracles.soccergo.club;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

import heracles.soccergo.R;

/**
 * Created by 10539 on 2016/9/7.
 */
public class RVClubAdapter extends RecyclerView.Adapter<ClubViewHolder>
{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Map<String, Object>> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public RVClubAdapter(Context context, List<Map<String, Object>> datas)
    {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.item_club,parent,false);
        ClubViewHolder clubViewHolder = new ClubViewHolder(view);
        return clubViewHolder;
    }

    @Override
    public void onBindViewHolder(final ClubViewHolder holder, int position)
    {
        holder.tvName.setText((String)mDatas.get(position).get("name"));
        holder.sdvClub.setImageURI((Uri) mDatas.get(position).get("img"));

        if(mOnItemClickListener!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    mOnItemClickListener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
}

class ClubViewHolder extends RecyclerView.ViewHolder
{
    public SimpleDraweeView sdvClub;
    public TextView tvName;

    public ClubViewHolder(View itemView)
    {
        super(itemView);
        sdvClub = (SimpleDraweeView) itemView.findViewById(R.id.sdvClub);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
    }
}
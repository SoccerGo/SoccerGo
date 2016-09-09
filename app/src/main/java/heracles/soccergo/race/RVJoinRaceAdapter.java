package heracles.soccergo.race;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import heracles.soccergo.R;
import heracles.soccergo.Tools.Test;

/**
 * Created by 10539 on 2016/9/9.
 */
public class RVJoinRaceAdapter extends RecyclerView.Adapter<JoinRaceViewHolder>
{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Map<String, Object>> mDatas;
    private OnItemBtnClickListener mOnItemClickListener;

    public RVJoinRaceAdapter(Context context, List<Map<String, Object>> datas)
    {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemBtnClickListener(OnItemBtnClickListener listener)
    {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemBtnClickListener
    {
        void onItemBtnClick(View view,int position);
    }

    @Override
    public JoinRaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.item_join_race_listview,parent,false);
        JoinRaceViewHolder joinRaceViewHolder = new JoinRaceViewHolder(view);
        return joinRaceViewHolder;
    }

    @Override
    public void onBindViewHolder(final JoinRaceViewHolder holder, int position)
    {
        holder.tvItemTitle.setText((String)mDatas.get(position).get("title"));
        holder.tvItemSize.setText((String)mDatas.get(position).get("size"));
        holder.tvItemLocale.setText((String)mDatas.get(position).get("local"));
        holder.tvItemTime.setText((String)mDatas.get(position).get("time"));
        holder.tvItemCost.setText((String)mDatas.get(position).get("cost"));

        if (position % 2 != 0)
        {
            if (Test.flag)
                Log.d("color", String.valueOf(mContext.getResources().getColor(R.color.colorDeepGreen)));
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorDeepGreen));

        }

        if(mOnItemClickListener != null)
        {
            holder.btnItemJoin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemBtnClick(holder.btnItemJoin,holder.getLayoutPosition());
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

class JoinRaceViewHolder extends RecyclerView.ViewHolder
{
    public TextView tvItemTitle;
    public TextView tvItemSize;
    public TextView tvItemLocale;
    public TextView tvItemTime;
    public TextView tvItemCost;
    public Button btnItemJoin;

    public JoinRaceViewHolder(View itemView)
    {
        super(itemView);
        tvItemTitle = (TextView) itemView.findViewById(R.id.tvItemTitle);
        tvItemSize = (TextView) itemView.findViewById(R.id.tvItemSize);
        tvItemLocale = (TextView) itemView.findViewById(R.id.tvItemLocale);
        tvItemTime = (TextView) itemView.findViewById(R.id.tvItemTime);
        tvItemCost = (TextView) itemView.findViewById(R.id.tvItemCost);
        btnItemJoin = (Button) itemView.findViewById(R.id.btnItemJoin);
    }
}
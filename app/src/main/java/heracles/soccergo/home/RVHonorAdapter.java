package heracles.soccergo.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import heracles.soccergo.R;
import heracles.soccergo.Tools.Honor;

/**
 * Created by 10539 on 2016/9/19.
 */
public class RVHonorAdapter extends RecyclerView.Adapter<RVHonorViewHolder>
{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Honor> datas;

    public RVHonorAdapter(Context context, List<Honor> datas)
    {
        this.mContext = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RVHonorViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.item_honor, parent, false);
        RVHonorViewHolder rvHonorViewHolder = new RVHonorViewHolder(view);
        return rvHonorViewHolder;
    }

    @Override
    public void onBindViewHolder(RVHonorViewHolder holder, int position)
    {
        holder.tvContent.setText(datas.get(position).getContent());
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }
}

class RVHonorViewHolder extends RecyclerView.ViewHolder
{
    public TextView tvContent;

    public RVHonorViewHolder(View itemView)
    {
        super(itemView);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);
    }
}
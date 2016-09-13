package heracles.soccergo.community;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import heracles.soccergo.R;
import heracles.soccergo.Tools.RoundImageView;

/**
 * Created by 10539 on 2016/9/13.
 */
public class RVCommunityAdapter
{

}

class CommunityViewHolder extends RecyclerView.ViewHolder
{
    public RoundImageView ivUser;
    public TextView tvUser;
    public TextView tvContent;
    public ImageView ivImage;
    public TextView tvFrom;
    public TextView tvLikeCount;

    public CommunityViewHolder(View itemView)
    {
        super(itemView);

        ivUser = (RoundImageView) itemView.findViewById(R.id.ivUser);
        tvUser = (TextView) itemView.findViewById(R.id.tvUser);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        tvFrom = (TextView) itemView.findViewById(R.id.tvFrom);
        tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
    }
}

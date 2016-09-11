package heracles.soccergo.Tools;

import java.util.Date;

/**
 * 荣誉记录
 *
 * @author 郑德星
 */
public class Honor
{

    private String honor_id;            //荣誉id
    private String user_id;            //用户id
    private Date h_time;                //时间
    private String content;            //内容


    public String getHonor_id()
    {
        return honor_id;
    }

    public void setHonor_id(String honor_id)
    {
        this.honor_id = honor_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public Date getH_time()
    {
        return h_time;
    }

    public void setH_time(Date h_time)
    {
        this.h_time = h_time;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "Honor [honor_id=" + honor_id + ", user_id=" + user_id + ", h_time=" + h_time + ", content=" + content
                + "]";
    }
}

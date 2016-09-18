package heracles.soccergo.Tools;

/**
 * Created by 10539 on 2016/9/11.
 */
public class User
{
    public static User_abilities_club_club mUserInfo;

    public static String mCity;

    public static String mProvince;

    public static void setUser(User_abilities_club_club userInfo)
    {
        mUserInfo = userInfo;
    }

    public static void setCity(String city)
    {
        mCity = city;
    }

    public static void setmProvince(String province)
    {
        mProvince = province;
    }
}

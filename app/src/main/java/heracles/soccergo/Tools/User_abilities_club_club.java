package heracles.soccergo.Tools;

/**
 * 主页展示实体，用户，综合能力，俱乐部多表联查
 *
 * @author 郑德星
 */
public class User_abilities_club_club
{


    //用户表
    private String user_id;                    //用户id
    private String user_name;                    //用户账号
    private String password;                    //密码
    private String chinese_name;                //中文名
    private String english_name;                //英文名称
    private String sex;                        //性别
    private String head_link;                    //头像地址
    private int age;                        //年龄
    private String birthday;                    //出生日期
    private int shirt_num;                    //球衣号
    private String location;                    //常用位置
    private int role;                        //角色
    private int helab;                        //赫拉币
    private String goods_address;                //收货地址
    private int user_leve;                    //用户等级
    private String club_club_id;                //俱乐部id

    //综合能力
    private int score;                        //速度分数
    private int chuanqiu;                    //传球分数
    private int pandai;                        //盘带分数
    private int liliang;                    //力量分数
    private int fangshou;                    //防守分数
    private int shoot_grade;                //射门分数


    //俱乐部
    private String club_club_originator;        //俱乐部创始人
    private String originator_pic;                //创始人图片
    private String club_club_name;                //俱乐部名称
    private String club_club_logo;                //俱乐部logo
    private int club_club_leve;                //俱乐部等级
    private int club_club_number;            //俱乐部人数


    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getChinese_name()
    {
        return chinese_name;
    }

    public void setChinese_name(String chinese_name)
    {
        this.chinese_name = chinese_name;
    }

    public String getEnglish_name()
    {
        return english_name;
    }

    public void setEnglish_name(String english_name)
    {
        this.english_name = english_name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getHead_link()
    {
        return head_link;
    }

    public void setHead_link(String head_link)
    {
        this.head_link = head_link;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getShirt_num()
    {
        return shirt_num;
    }

    public void setShirt_num(int shirt_num)
    {
        this.shirt_num = shirt_num;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public int getShoot_grade()
    {
        return shoot_grade;
    }

    public void setShoot_grade(int shoot_grade)
    {
        this.shoot_grade = shoot_grade;
    }

    public int getRole()
    {
        return role;
    }

    public void setRole(int role)
    {
        this.role = role;
    }

    public int getHelab()
    {
        return helab;
    }

    public void setHelab(int helab)
    {
        this.helab = helab;
    }

    public String getGoods_address()
    {
        return goods_address;
    }

    public void setGoods_address(String goods_address)
    {
        this.goods_address = goods_address;
    }

    public int getUser_leve()
    {
        return user_leve;
    }

    public void setUser_leve(int user_leve)
    {
        this.user_leve = user_leve;
    }

    public String getClub_club_id()
    {
        return club_club_id;
    }

    public void setClub_club_id(String club_club_id)
    {
        this.club_club_id = club_club_id;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getChuanqiu()
    {
        return chuanqiu;
    }

    public void setChuanqiu(int chuanqiu)
    {
        this.chuanqiu = chuanqiu;
    }

    public int getPandai()
    {
        return pandai;
    }

    public void setPandai(int pandai)
    {
        this.pandai = pandai;
    }

    public int getLiliang()
    {
        return liliang;
    }

    public void setLiliang(int liliang)
    {
        this.liliang = liliang;
    }

    public int getFangshou()
    {
        return fangshou;
    }

    public void setFangshou(int fangshou)
    {
        this.fangshou = fangshou;
    }

    public String getClub_club_originator()
    {
        return club_club_originator;
    }

    public void setClub_club_originator(String club_club_originator)
    {
        this.club_club_originator = club_club_originator;
    }

    public String getOriginator_pic()
    {
        return originator_pic;
    }

    public void setOriginator_pic(String originator_pic)
    {
        this.originator_pic = originator_pic;
    }

    public String getClub_club_name()
    {
        return club_club_name;
    }

    public void setClub_club_name(String club_club_name)
    {
        this.club_club_name = club_club_name;
    }

    public String getClub_club_logo()
    {
        return club_club_logo;
    }

    public void setClub_club_logo(String club_club_logo)
    {
        this.club_club_logo = club_club_logo;
    }

    public int getClub_club_leve()
    {
        return club_club_leve;
    }

    public void setClub_club_leve(int club_club_leve)
    {
        this.club_club_leve = club_club_leve;
    }

    public int getClub_club_number()
    {
        return club_club_number;
    }

    public void setClub_club_number(int club_club_number)
    {
        this.club_club_number = club_club_number;
    }

    @Override
    public String toString()
    {
        return "User_abilities_club_club [user_id=" + user_id + ", user_name=" + user_name + ", password=" + password
                + ", chinese_name=" + chinese_name + ", english_name=" + english_name + ", sex=" + sex + ", head_link="
                + head_link + ", age=" + age + ", birthday=" + birthday + ", shirt_num=" + shirt_num + ", location="
                + location + ", shoot_grade=" + shoot_grade + ", role=" + role + ", helab=" + helab + ", goods_address="
                + goods_address + ", user_leve=" + user_leve + ", club_club_id=" + club_club_id + ", score=" + score
                + ", chuanqiu=" + chuanqiu + ", pandai=" + pandai + ", liliang=" + liliang + ", fangshou=" + fangshou
                + ", club_club_originator=" + club_club_originator + ", originator_pic=" + originator_pic
                + ", club_club_name=" + club_club_name + ", club_club_logo=" + club_club_logo + ", club_club_leve="
                + club_club_leve + ", club_club_number=" + club_club_number + "]";
    }
}

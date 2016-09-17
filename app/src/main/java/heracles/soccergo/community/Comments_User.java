package heracles.soccergo.community;

import java.util.Date;

public class Comments_User {
	private String comments_id; // 评论id
	private String friends_id; // 朋友圈id
	private String c_content; // 评论
	private String user_name; // 用户名称
	private String user_id; // 用户id

	private String password; // 密码
	private String chinese_name; // 中文名
	private String english_name; // 英文名称
	private String sex; // 性别
	private String head_link; // 头像地址
	private int age; // 年龄
	private Date birthday; // 出生日期
	private int shirt_num; // 球衣号
	private String location; // 常用位置
	private int shoot_grade; // 射门分数
	private int role; // 角色
	private int helab; // 赫拉币
	private String goods_address; // 收货地址
	private int user_leve; // 用户等级
	private String club_club_id; // 俱乐部id
	public String getComments_id() {
		return comments_id;
	}
	public void setComments_id(String comments_id) {
		this.comments_id = comments_id;
	}
	public String getFriends_id() {
		return friends_id;
	}
	public void setFriends_id(String friends_id) {
		this.friends_id = friends_id;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChinese_name() {
		return chinese_name;
	}
	public void setChinese_name(String chinese_name) {
		this.chinese_name = chinese_name;
	}
	public String getEnglish_name() {
		return english_name;
	}
	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHead_link() {
		return head_link;
	}
	public void setHead_link(String head_link) {
		this.head_link = head_link;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public int getShirt_num() {
		return shirt_num;
	}
	public void setShirt_num(int shirt_num) {
		this.shirt_num = shirt_num;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getShoot_grade() {
		return shoot_grade;
	}
	public void setShoot_grade(int shoot_grade) {
		this.shoot_grade = shoot_grade;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getHelab() {
		return helab;
	}
	public void setHelab(int helab) {
		this.helab = helab;
	}
	public String getGoods_address() {
		return goods_address;
	}
	public void setGoods_address(String goods_address) {
		this.goods_address = goods_address;
	}
	public int getUser_leve() {
		return user_leve;
	}
	public void setUser_leve(int user_leve) {
		this.user_leve = user_leve;
	}
	public String getClub_club_id() {
		return club_club_id;
	}
	public void setClub_club_id(String club_club_id) {
		this.club_club_id = club_club_id;
	}
	@Override
	public String toString() {
		return "Comments_User [comments_id=" + comments_id + ", friends_id=" + friends_id + ", c_content=" + c_content
				+ ", user_name=" + user_name + ", user_id=" + user_id + ", password=" + password + ", chinese_name="
				+ chinese_name + ", english_name=" + english_name + ", sex=" + sex + ", head_link=" + head_link
				+ ", age=" + age + ", birthday=" + birthday + ", shirt_num=" + shirt_num + ", location=" + location
				+ ", shoot_grade=" + shoot_grade + ", role=" + role + ", helab=" + helab + ", goods_address="
				+ goods_address + ", user_leve=" + user_leve + ", club_club_id=" + club_club_id + "]";
	}
	
	
	
}

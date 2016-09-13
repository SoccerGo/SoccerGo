package heracles.soccergo.community;


/**
 * 朋友圈
 * 
 * @author 郑德星
 *
 */
public class Friends {
	private String 		friends_id;			//朋友圈id
	private String    	address;  			//方位
	private String      content; 			//内容
	private String      pic;				//图片地址
	private int			num;				//点赞数
	private	String		user_id;			//用户id
	public String getFriends_id() {
		return friends_id;
	}
	public void setFriends_id(String friends_id) {
		this.friends_id = friends_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "Friends [friends_id=" + friends_id + ", address=" + address + ", content=" + content + ", pic=" + pic
				+ ", num=" + num + ", user_id=" + user_id + "]";
	}
	
	
	
}

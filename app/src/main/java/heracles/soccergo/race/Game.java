package heracles.soccergo.race;

import java.util.Date;

/**
 * 约赛
 * @author 郑德星
 *
 */
public class Game {

	private String 		game_id;  		//比赛id
	private String		user_id;  		//用户id
	private String 		game_name;		//比赛标题
	private int    		game_standard;	//比赛规模 人数X2
	private Date   		game_data;    	//比赛日期
	private String 		game_address;	//地点
	private int    		cost;        	//费用
	private int    		join_num;    	//参加人数
	
	
	
	public String getGame_id() {
		return game_id;
	}
	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getGame_name() {
		return game_name;
	}
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}
	public int getGame_standard() {
		return game_standard;
	}
	public void setGame_standard(int game_standard) {
		this.game_standard = game_standard;
	}
	public Date getGame_data() {
		return game_data;
	}
	public void setGame_data(Date game_data) {
		this.game_data = game_data;
	}
	public String getGame_address() {
		return game_address;
	}
	public void setGame_address(String game_address) {
		this.game_address = game_address;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getJoin_num() {
		return join_num;
	}
	public void setJoin_num(int join_num) {
		this.join_num = join_num;
	}
	@Override
	public String toString() {
		return "Game [game_id=" + game_id + ", user_id=" + user_id + ", game_name=" + game_name + ", game_standard="
				+ game_standard + ", game_data=" + game_data + ", game_address=" + game_address + ", cost=" + cost
				+ ", join_num=" + join_num + "]";
	}
	
	
	
	
}

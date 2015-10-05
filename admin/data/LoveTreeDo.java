package cn.com.tx.aus.domain;

import java.io.Serializable;

/**
 * 相亲树对象
 *
 */
public class LoveTreeDo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	long id;
	//用户id
	int uid;
	
	int sex;
	
	UserDo user;
	
	String content;
	
	String photo;
	//新建时间
	long ctime;
	//状态 0 未审核 -1 审核失败 1 审核通过
	int state;
	//审核时间
	long utime;
	
	public UserDo getUser() {
		return user;
	}
	public void setUser(UserDo user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getUtime() {
		return utime;
	}
	public void setUtime(long utime) {
		this.utime = utime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
}

package cn.com.tx.aus.domain;

import java.util.List;

public class UserWrap {
	
	String skey;
	
	String ip;
	
	Integer port;

	UserDo user;

	UserWantDo userWant;
	
	List<UserPhotoDo> photos;
	
	UserSyncDo userSync;

	public UserDo getUser() {
		return user;
	}

	public void setUser(UserDo user) {
		this.user = user;
	}

	public UserWantDo getUserWant() {
		return userWant;
	}

	public void setUserWant(UserWantDo userWant) {
		this.userWant = userWant;
	}

	public List<UserPhotoDo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<UserPhotoDo> photos) {
		this.photos = photos;
	}

	public UserSyncDo getUserSync() {
		return userSync;
	}

	public void setUserSync(UserSyncDo userSync) {
		this.userSync = userSync;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}

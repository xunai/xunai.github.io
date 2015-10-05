package cn.com.tx.aus.domain;

/**
 * 用户照片
 *
 */
public class UserPhotoDo {

	Integer uid;
	
	String photo;
	
	Long ctime;

	public Long getCtime() {
		return ctime;
	}

	public String getPhoto() {
		return photo;
	}

	public Integer getUid() {
		return uid;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
}

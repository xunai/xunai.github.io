package cn.com.tx.aus.domain;

import java.io.Serializable;

/**
 * 头像审核
 * @since Mar 25, 2015
 *
 */
public class FaceVerifyDo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int id;
	
	int uid;

	int sex;
	
	String face;
	//1未审核 2审核通过 3审核失败
	int state;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	
}

package cn.com.tx.aus.domain;

import java.io.Serializable;

public class AdminActionStat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9138812180421832429L;
	
	//管理员id
	int userid;
	//已聊多少句
	int chats;
	//已聊用户数
	int chatUsers;
	//审核头像数
	int faceVerifies;
	//审核身份证数
	int idcardVerifies;
	//审核相亲树数
	int lovetreeVerifies;
	
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	/**
	 * @return the chats
	 */
	public int getChats() {
		return chats;
	}
	/**
	 * @param chats the chats to set
	 */
	public void setChats(int chats) {
		this.chats = chats;
	}
	/**
	 * @return the chatUsers
	 */
	public int getChatUsers() {
		return chatUsers;
	}
	/**
	 * @param chatUsers the chatUsers to set
	 */
	public void setChatUsers(int chatUsers) {
		this.chatUsers = chatUsers;
	}
	/**
	 * @return the faceVerifies
	 */
	public int getFaceVerifies() {
		return faceVerifies;
	}
	/**
	 * @param faceVerifies the faceVerifies to set
	 */
	public void setFaceVerifies(int faceVerifies) {
		this.faceVerifies = faceVerifies;
	}
	/**
	 * @return the idcardVerifies
	 */
	public int getIdcardVerifies() {
		return idcardVerifies;
	}
	/**
	 * @param idcardVerifies the idcardVerifies to set
	 */
	public void setIdcardVerifies(int idcardVerifies) {
		this.idcardVerifies = idcardVerifies;
	}
	/**
	 * @return the lovetreeVerifies
	 */
	public int getLovetreeVerifies() {
		return lovetreeVerifies;
	}
	/**
	 * @param lovetreeVerifies the lovetreeVerifies to set
	 */
	public void setLovetreeVerifies(int lovetreeVerifies) {
		this.lovetreeVerifies = lovetreeVerifies;
	}
}

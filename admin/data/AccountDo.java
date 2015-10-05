package cn.com.tx.aus.domain;

public class AccountDo {

	int uid;
	
	String password;
	
	String skey;
	//-1:停用
	//0:正常
	byte state;
	
	String ip;
	
	int port;
	
	long ctime;
	
	public long getCtime() {
		return ctime;
	}
	public String getPassword() {
		return password;
	}

	public int getUid() {
		return uid;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getSkey() {
		return skey;
	}
	public void setSkey(String skey) {
		this.skey = skey;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}

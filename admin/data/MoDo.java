package cn.com.tx.aus.domain;

import java.io.Serializable;

public class MoDo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用户id
	int uid;
	//机器人id
	int rid;
	//是否有新消息
	boolean hasNew;
	//是否忽略
	boolean ignore;
	//最后更新时间
	long utime;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public long getUtime() {
		return utime;
	}
	public void setUtime(long utime) {
		this.utime = utime;
	}
	public boolean isHasNew() {
		return hasNew;
	}
	public void setHasNew(boolean hasNew) {
		this.hasNew = hasNew;
	}
	public boolean isIgnore() {
		return ignore;
	}
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
}

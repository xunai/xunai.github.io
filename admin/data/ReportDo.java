package cn.com.tx.aus.domain;

/***
 * 消息
 * @author Administrator
 *
 */
public class ReportDo{

	private int uid;
	private String content;
	private long ctime;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
	
}

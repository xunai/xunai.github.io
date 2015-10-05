package cn.com.tx.aus.domain;

/***
 * 消息
 * @author Administrator
 *
 */
public class ChatDo{

	private int fuid;
	private int tuid;
	private String content;
	private boolean read;
	private long ctime;
	
	public int getFuid() {
		return fuid;
	}
	public void setFuid(int fuid) {
		this.fuid = fuid;
	}
	public int getTuid() {
		return tuid;
	}
	public void setTuid(int tuid) {
		this.tuid = tuid;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCtime() {
		return ctime;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
}

package cn.com.tx.aus.dao.domain;

public class ChatVoiceDo extends ChatType{

	public ChatVoiceDo(){
		super.chatType = 3;
	}
	
	String url;
	
	String localUrl;
	
	long duration;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
	
}

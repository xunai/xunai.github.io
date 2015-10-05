package cn.com.tx.aus.dao.domain;

public class ChatPhotoDo extends ChatType{

	public ChatPhotoDo(){
		super.chatType = 2;
	}
	
	String url;
	
	String localUrl;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}

	
	
}

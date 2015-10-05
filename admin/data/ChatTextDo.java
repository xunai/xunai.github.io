package cn.com.tx.aus.dao.domain;

public class ChatTextDo extends ChatType{

	public ChatTextDo(){
		super.chatType = 1;
	}
	
	String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

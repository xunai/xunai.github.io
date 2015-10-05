package cn.com.tx.aus.domain;

import java.io.Serializable;

public class AusResultDo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;
	
	private String errorMessage;
	
	private Object resut;

	public String getErrorMessage() {
		return errorMessage;
	}

	public Object getResut() {
		return resut;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setResut(Object resut) {
		this.resut = resut;
	}
	
}

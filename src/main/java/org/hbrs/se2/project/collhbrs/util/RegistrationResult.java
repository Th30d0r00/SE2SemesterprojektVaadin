package org.hbrs.se2.project.collhbrs.util;

public class RegistrationResult {

	public boolean success;
	public String message;

	public RegistrationResult() {

	}

	public RegistrationResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean getSuccess(){
		return success;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message = message;
	}
}

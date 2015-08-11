package test.fmc.simpleclient.protocol.impl;

import test.fmc.simpleclient.protocol.ClientData;

public class DefaultClientData implements ClientData {

	private String fio;
	private String workPhone;
	private String mobilePhone;
	private String email;
	
	public DefaultClientData() {
		
	}
	
	public String getFio() {
		return fio;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}



	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "DefaultClientData [fio=" + fio + ", workPhone=" + workPhone
				+ ", mobilePhone=" + mobilePhone + ", email=" + email + "]";
	}
}

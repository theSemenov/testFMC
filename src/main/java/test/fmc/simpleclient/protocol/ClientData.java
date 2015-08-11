package test.fmc.simpleclient.protocol;

import java.io.Serializable;

public interface ClientData extends Serializable {

	String getFio();
	String getWorkPhone();
	String getMobilePhone();
	String getEmail();
}

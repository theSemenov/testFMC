package test.fmc.simpleclient.protocol;


public interface Service {

	void getClientData(ResponseHandler<ClientData> handler);
	void setClientData(ClientData data, ResponseHandler<ClientData> handler);
}

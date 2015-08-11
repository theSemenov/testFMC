package test.fmc.simpleclient.protocol.impl;


import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.sound.midi.VoiceStatus;

import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import test.fmc.simpleclient.protocol.ClientData;
import test.fmc.simpleclient.protocol.Command;
import test.fmc.simpleclient.protocol.CryptAlgoritm;
import test.fmc.simpleclient.protocol.ResponseHandler;
import test.fmc.simpleclient.protocol.Service;
import test.fmc.simpleclient.utils.ContextUtil;


public class SecureService implements Service {
	@Resource(name="SimpleClient/remote-url")
	private String url;
	@Resource(name="SimpleClient/remote-port")
	private String port;
	@Resource(name="SimpleClient/command-prefix")
	private String commandPrefix;
	@Resource(name="SimpleClient/remote-key")
	private String key;
	@Resource(name="SimpleClient/remote-login")
	private String login;
	@Inject
	private CryptAlgoritm cryptAlgoritm;
	private URLCodec urlCodec = new URLCodec();

	public void getClientData(ResponseHandler<ClientData> handler) {
		String command = CommandBuilder.asString(CommandBuilder.createGetDataCommand());
		 this.<ClientData> executeCommand(command, handler);
		
	}

	
	public void setClientData(ClientData data, ResponseHandler<ClientData> handler) {
		String command = CommandBuilder.asString(CommandBuilder.createSetDataCommand(data));
		 this.<ClientData> executeCommand(command, handler);
	}

	private <T> void executeCommand(String command, ResponseHandler<T> handler) {
		T data = null;
		try {
			String endpoint = constructEndpoint(command);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(new HttpGet(endpoint));
			data = handler.processResponse(response);
		} catch (Throwable t) {
			handler.onFault(t);
			return;
		}
		handler.onSuccess(data);
	}

	private String constructEndpoint(String command) throws Exception {
		if(url.endsWith("/"))
			url = url.substring(0, url.length()-1);
		String endpoint = url + ":" + port + "/" + commandPrefix;
		String httpCommand = "[";
		httpCommand += key + CommandBuilder.defaultDelimeter;
		httpCommand += login + CommandBuilder.defaultDelimeter;
		String encryptedCommand = cryptAlgoritm.crypt(command);
		httpCommand += encryptedCommand + CommandBuilder.defaultDelimeter + "]";
		httpCommand = urlCodec.encode(httpCommand, "UTF-8");
		endpoint += httpCommand;
		return endpoint;
		
	}
}
